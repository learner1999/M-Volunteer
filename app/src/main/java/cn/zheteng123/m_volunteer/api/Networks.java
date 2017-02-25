package cn.zheteng123.m_volunteer.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.util.LoginInfo;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2017/2/21.
 */


public class Networks {

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;

    private static Networks mNetworks;

    private static ActivityApi mActivityApi;

    private static SearchApi mSearchApi;

    private static LoginApi mLoginApi;

    private static SignInApi mSignInApi;

    private static FileUploadApi mFileUploadApi;

    private static VolunteerApi mVolunteerApi;

    private static ActivityUserApi mActivityUserApi;

    public static Networks getInstance() {
        if (mNetworks == null) {
            mNetworks = new Networks();
        }
        return mNetworks;
    }

    public ActivityApi getActivityApi() {
        return mActivityApi == null ? configRetrofit(ActivityApi.class) : mActivityApi;
    }

    public SearchApi getSearchApi() {
        return mSearchApi == null ? configRetrofit(SearchApi.class) : mSearchApi;
    }

    public LoginApi getLoginApi() {
        return mLoginApi == null ? configRetrofit(LoginApi.class) : mLoginApi;
    }

    public SignInApi getSignInApi() {
        return mSignInApi == null ? configRetrofit(SignInApi.class) : mSignInApi;
    }

    public VolunteerApi getVolunteerApi() {
        return mVolunteerApi == null ? configRetrofit(VolunteerApi.class) : mVolunteerApi;
    }

    public FileUploadApi getFileUploadApi() {
        return mFileUploadApi == null ? configRetrofit(FileUploadApi.class) : mFileUploadApi;
    }

    public ActivityUserApi getActivityUserApi() {
        return mActivityUserApi == null ? configRetrofit(ActivityUserApi.class) : mActivityUserApi;
    }

    private <T> T configRetrofit(Class<T> service) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    private OkHttpClient configClient() {
        final Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization", "Bearer " + LoginInfo.token);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(tokenInterceptor);
        return okHttpClient.build();
    }
}
