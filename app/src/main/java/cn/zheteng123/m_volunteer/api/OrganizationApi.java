package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.interview_manage.InterviewManageEntity;
import cn.zheteng123.m_volunteer.entity.organization.ManageActivityEntity;
import cn.zheteng123.m_volunteer.entity.organization.ManageMemberEntity;
import cn.zheteng123.m_volunteer.entity.organization.OrganizationEntity;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created on 2017/2/25.
 */


public interface OrganizationApi {

    @GET("api/organization")
    Observable<Result<OrganizationEntity>> getOrganization();

    @GET("api/organization/activities")
    Observable<Result<PageInfo<ManageActivityEntity>>> getManageActivity(@Query("page") int page, @Query("rows") int rows);

    @GET("api/organization/volunteers")
    Observable<Result<PageInfo<ManageMemberEntity>>> getManageMemberEntity(@Query("page") int page, @Query("rows") int rows);

    @GET("api/activityUser")
    Observable<Result<PageInfo<InterviewManageEntity>>> getInterviewManageEntity(@Query("page") int page, @Query("rows") int rows);

    @PUT("api/activityUser/{id}")
    Observable<Result<String>> modifyInterviewStatus(@Path("id") int id, @Query("activityUserStatusId") int activityUserStatusId);
}
