package com.regongzaixian.jiankong.net;

import android.content.Intent;

import com.regongzaixian.jiankong.BuildConfig;
import com.regongzaixian.jiankong.base.JianKongApp;
import com.regongzaixian.jiankong.util.Preferences;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/3/27
 * Time: 下午10:28
 * Description:
 * 网络操作管理类，请求都从此类发出
 * 1.登录/注册成功后调用refreshRetrofit(),将Authorization重新赋值
 */
public class NetManager {
    private static NetManager instance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private Map<String, String> headerMap;

    private NetManager() {
        refreshRetrofit();
    }

    /**
     * 刷新retrofit的请求头,让之后请求全都带有token
     */
    public void refreshRetrofit() {
        initHeaderMap();
        initRetrofit();
    }

    private void initHeaderMap() {
        this.headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        if (!Preferences.getInstance().getToken().isEmpty()) {
            headerMap.put("Authorization", "bearer " + Preferences.getInstance().getToken());
        }
    }

    private void initRetrofit() {
        this.okHttpClient = (new OkHttpClient.Builder())
                .readTimeout(30L, TimeUnit.SECONDS)
                .connectTimeout(30L, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //给请求统一加头的拦截器
                        Request request = chain
                                .request()
                                .newBuilder()
                                .headers(Headers.of(headerMap))
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static NetManager getInstance() {
        if (instance == null) {
            synchronized (NetManager.class) {
                if (instance == null) {
                    instance = new NetManager();
                }
            }
        }
        return instance;
    }

    public <M> void runRxJava(Observable<M> observable, final Subscriber<M> subscriberCallBack) {
        //网络不可用，直接return
        if (!NetWorkUtils.isConnectedByState(JianKongApp.getInstance())) {
            subscriberCallBack.onError(new Exception("请检查网络连接"));
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<M>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //  the base class of
                        //  ConnectException/NoRouteToHostException/
                        //  is SocketException
                        if (throwable instanceof SocketException) {
                            subscriberCallBack.onError(new Exception("网络连接异常,请检查网络连接"));
                            return;
                        }
                        //  the base class of
                        //  SocketTimeoutException
                        //  is InterruptedIOException
                        if (throwable instanceof InterruptedIOException) {
                            subscriberCallBack.onError(new Exception("网络请求超时,请稍后重试"));
                            return;
                        }
                        //  上面的实例，基类都属于IOException，所以放到最后处理
                        //  the base class of
                        //  UnknownHostException/UnknownServiceException.....
                        //  is IOException
                        if (throwable instanceof IOException) {
                            subscriberCallBack.onError(new Exception("网络异常,请检查网络连接"));
                            return;
                        }

                        //  其他情况都是HttpException
                        //  token失效处理
                        int errorCode = ((HttpException) throwable).response().code();
                        if (errorCode == 401) {
                            subscriberCallBack.onError(new Exception("token失效,请重新登录"));
                            JianKongApp.getInstance().sendBroadcast(new Intent("token_invalid"));
                            return;
                        }

                        ResponseBody body = ((HttpException) throwable).response().errorBody();
                        try {
                            JSONObject response = new JSONObject(body.string());
                            String msg = (String) response.get("message");
                            subscriberCallBack.onError(new Exception(msg));
                        } catch (Exception e1) {
                            subscriberCallBack.onError(new Exception("unKnown error"));
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(M m) {
                        subscriberCallBack.onNext(m);
                    }
                });
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
