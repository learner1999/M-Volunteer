package cn.zheteng123.m_volunteer.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created on 2017/2/19.
 */


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化百度地图 sdk
        SDKInitializer.initialize(getApplicationContext());
    }
}
