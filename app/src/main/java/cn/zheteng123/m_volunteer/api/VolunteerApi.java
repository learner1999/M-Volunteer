package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.my_activity.MyActivityEntity;
import cn.zheteng123.m_volunteer.entity.service_record.ServiceRecordEntity;
import cn.zheteng123.m_volunteer.entity.user_center.VolunteerEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/23.
 */


public interface VolunteerApi {

    @GET("api/volunteer/activities")
    Observable<Result<PageInfo<MyActivityEntity>>> getMyActivities(@Query("page") int page, @Query("rows") int rows);

    @GET("api/volunteer")
    Observable<Result<VolunteerEntity>> getVolunteerInfo();

    @GET("api/volunteer/history")
    Observable<Result<PageInfo<ServiceRecordEntity>>> getMyActivityHistory(@Query("page") int page, @Query("rows") int rows);
}
