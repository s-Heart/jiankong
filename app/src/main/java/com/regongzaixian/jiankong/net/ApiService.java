package com.regongzaixian.jiankong.net;

import com.regongzaixian.jiankong.model.InstrumentDataEntity;
import com.regongzaixian.jiankong.model.InstrumentEntity;
import com.regongzaixian.jiankong.model.UserEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Author: tony(110618445@qq.com)
 * Date: 2017/3/28
 * Time: 下午9:57
 * Description:接口声明处
 */
public interface ApiService {
    /*登录************************************************************************/

    @POST("auth/local")
    Observable<UserEntity> login(@Body Map<String, String> params);

    /**
     * 设备列表 GET instrument
     *
     * @return
     */
    @GET("instrument")
    Observable<List<InstrumentEntity>> queryInstruments();

    /**
     * 单个设备GET instrument/{id}
     */
    @GET("instrument/{id}")
    Observable<InstrumentEntity> queryInstrumentById(@Path("id") int instrumentId);

    /**
     * @param params where={“instrument”:”<设备ID>”}
     * @return
     */
    @GET("instrumentdata")
    Observable<InstrumentDataEntity> queryInstrumentData(@QueryMap Map<String, String> params);

    /**
     * 设定参数
     *
     * @return
     */
    @POST("")
    Observable<Object> modifySettings(@Body Map<String, String> params);
}
