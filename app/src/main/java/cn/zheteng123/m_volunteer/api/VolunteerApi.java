package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.my_activity.MyActivityEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/23.
 */


public interface VolunteerApi {

    @GET("api/volunteer/activities")
    Observable<Result<PageInfo<MyActivityEntity>>> getMyActivities(@Query("page") int page, @Query("rows") int rows);
}
