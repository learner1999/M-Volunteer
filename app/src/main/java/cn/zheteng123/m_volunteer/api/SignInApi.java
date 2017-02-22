package cn.zheteng123.m_volunteer.api;


import cn.zheteng123.m_volunteer.entity.Result;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/21.
 */


public interface SignInApi {

    @POST("api/signin")
    Observable<Result<String>> signIn(@Query("code") int code);
}
