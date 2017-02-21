package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.Token;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/21.
 */


public interface LoginApi {

    @Headers("Authorization:Basic Y2xpZW50Om1fdm9sdW50ZWVy")
    @POST("oauth/token")
    Observable<Token> login(
            @Query("grant_type") String grantType,
            @Query("username") String username,
            @Query("password") String password
    );
}
