package com.regongzaixian.jiankong.net;

import com.regongzaixian.jiankong.model.UserEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
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

}
