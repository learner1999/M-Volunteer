package cn.zheteng123.m_volunteer.api;


import cn.zheteng123.m_volunteer.entity.baidu.ConverseGeographyResult;
import cn.zheteng123.m_volunteer.entity.baidu.GeographyResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/25.
 */


public interface GeographyApi {

    @GET("http://api.map.baidu.com/geocoder/v2/")
    Observable<GeographyResult> getGeographyByAddress(
            @Query("output") String output,
            @Query("address") String address,
            @Query("ak") String ak
    );

    @GET("http://api.map.baidu.com/geocoder/v2/")
    Observable<ConverseGeographyResult> getConverseGeographyByCoordinate(
            @Query("output") String output,
            @Query("location") String location,
            @Query("ak") String ak
    );
}
