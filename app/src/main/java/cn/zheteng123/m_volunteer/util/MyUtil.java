package cn.zheteng123.m_volunteer.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import cn.zheteng123.m_volunteer.api.GeographyApi;
import cn.zheteng123.m_volunteer.entity.baidu.ConverseGeographyResult;
import cn.zheteng123.m_volunteer.entity.baidu.GeographyResult;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created on 2017/2/24.
 */


public class MyUtil {

    /**
     * Uri获取图片真实路径
     * @param context 上下文
     * @param contentUri Uri
     * @return 图片真实路径
     */
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static Observable<GeographyResult> getGeographyByAddress(String address) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GeographyApi geographyApi = retrofit.create(GeographyApi.class);
        return geographyApi.getGeographyByAddress("json", address, "xLduVfBKAMbu91qegYlAu9BeL19waEcL");
    }

    public static Observable<ConverseGeographyResult> getConverseGeographyByCoordinate(double lon, double lat) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.map.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GeographyApi geographyApi = retrofit.create(GeographyApi.class);
        return geographyApi.getConverseGeographyByCoordinate("json", lat + "," + lon, "xLduVfBKAMbu91qegYlAu9BeL19waEcL");
    }
}
