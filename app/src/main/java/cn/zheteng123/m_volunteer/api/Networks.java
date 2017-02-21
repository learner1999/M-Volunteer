package cn.zheteng123.m_volunteer.api;

import java.util.concurrent.TimeUnit;

import cn.zheteng123.m_volunteer.BuildConfig;
import okhttp3.OkHttpClient;
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

    public static Networks getInstance() {
        if (mNetworks == null) {
            mNetworks = new Networks();
        }
        return mNetworks;
    }

    public ActivityApi getActivityApi() {
        return mActivityApi == null ? configRetrofit(ActivityApi.class) : mActivityApi;
    }

    public SearchApi getSearchApiApi() {
        return mSearchApi == null ? configRetrofit(SearchApi.class) : mSearchApi;
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
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return okHttpClient.build();
    }
}
