package cn.zheteng123.m_volunteer.api;

import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.certificate.CertificateEntity;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created on 2017/2/25.
 */


public interface CertificateApi {

    @POST("api/certificate")
    Observable<Result<String>> addCertificate(@Body CertificateEntity certificateEntity);
}
