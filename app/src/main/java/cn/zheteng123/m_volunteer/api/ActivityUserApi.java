package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.activity.ActivityUser;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/25.
 */


public interface ActivityUserApi {

    @POST("api/activityUser")
    Observable<Result<String>> enrollActivity(@Body ActivityUser activityUser);

    @DELETE("api/activityUser")
    Observable<Result<String>> cancelActivity(@Query("activityId") int activityId);

}
