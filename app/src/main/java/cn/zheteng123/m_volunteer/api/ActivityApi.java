package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.activity.ActivityDetail;
import cn.zheteng123.m_volunteer.entity.activity_add.ActivityEntity;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/21.
 */


public interface ActivityApi {

    @GET("api/activity")
    Observable<Result<PageInfo<HomeActivityEntity>>> getHomeActivity(
            @Query("coordLong") double lon,
            @Query("coordLat") double lat,
            @Query("page") int page,
            @Query("rows") int rows
    );

    @GET("api/activity/category")
    Observable<Result<PageInfo<HomeActivityEntity>>> getActivityByParam(
            @Query("coordLong") double lon,
            @Query("coordLat") double lat,
            @Query("page") int page,
            @Query("rows") int rows,
            @Query("category") String category,
            @Query("collation") int collation,
            @Query("district") String district
    );

    @GET("api/activity/{id}")
    Observable<Result<ActivityDetail>> getActivityDetailById(
            @Path("id") int id,
            @Query("coordLong") double lng,
            @Query("coordLat") double lat
    );

    @POST("api/activity")
    Observable<Result<String>> addActivity(@Body ActivityEntity activityEntity);
}
