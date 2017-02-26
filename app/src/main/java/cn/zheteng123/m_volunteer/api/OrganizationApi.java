package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.organization.ManageActivityEntity;
import cn.zheteng123.m_volunteer.entity.organization.ManageMemberEntity;
import cn.zheteng123.m_volunteer.entity.organization.OrganizationEntity;
import retrofit2.http.GET;
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
}
