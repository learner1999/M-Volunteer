package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.organization.OrganizationEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created on 2017/2/25.
 */


public interface OrganizationApi {

    @GET("api/organization")
    Observable<Result<OrganizationEntity>> getOrganization();
}
