package cn.zheteng123.m_volunteer.api;


import cn.zheteng123.m_volunteer.entity.Result;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created on 2017/2/24.
 */


public interface FileUploadApi {

    @Multipart
    @POST("api/image/upload")
    Observable<Result<String>> uploadImage(@Part MultipartBody.Part file);
}
