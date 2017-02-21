package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import retrofit2.http.GET;
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

}
