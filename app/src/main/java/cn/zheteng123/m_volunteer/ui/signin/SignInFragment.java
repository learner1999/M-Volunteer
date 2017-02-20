package cn.zheteng123.m_volunteer.ui.signin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.mvpframe.base.BaseFrameFragment;
import cn.zheteng123.m_volunteer.util.WindowAttr;

/**
 * Created on 2017/2/19.
 */


public class SignInFragment extends BaseFrameFragment<SignInPresenter, SignInModel> implements SignInContract.View, BDLocationListener {

    @BindView(R.id.v_status_bar_fix)
    View mViewStatusBarFixer;

    @BindView(R.id.bmapView)
    MapView mMapView;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = this;
    BaiduMap mBaiduMap = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_in);
        ButterKnife.bind(this, getContentView());

        // 声明LocationClient类
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        // 注册监听函数
        mLocationClient.registerLocationListener( myListener );

        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);

        initLocation();
        requestPermissions();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        // 设置状态栏高度
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(getActivity()))
        );


        mLocationClient.start();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // 同时销毁地图
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        // 实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        // 实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocationClient.stop();
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        // int span=1000;
        // option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(false);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(false);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(false);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        double latitude = bdLocation.getLatitude();
        double longitude = bdLocation.getLongitude();

        // Toast.makeText(getActivity(), latitude + " " + longitude, Toast.LENGTH_SHORT).show();

        // 移动地图至当前位置
        final LatLng ll = new LatLng(latitude, longitude);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 19f);
        mBaiduMap.animateMapStatus(update);

        // 显示当前位置
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        MyLocationData locationData = locationBuilder.latitude(latitude).longitude(longitude).build();
        mBaiduMap.setMyLocationData(locationData);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //创建InfoWindow展示的view
                Button button = new Button(getActivity().getApplicationContext());
                button.setText("点击签到");
                button.setTextColor(Color.rgb(255, 255, 255));
                button.setBackgroundColor(Color.argb(50, 0, 255, 0));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = new Button(getActivity().getApplicationContext());
                        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        mBaiduMap.showInfoWindow(new InfoWindow(button, ll, 0));
                    }
                });
                //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                InfoWindow mInfoWindow = new InfoWindow(button, ll, -47);
                //显示InfoWindow
                mBaiduMap.showInfoWindow(mInfoWindow);
            }
        });

    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须统一所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                    mLocationClient.start();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void requestPermissions() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission.READ_PHONE_STATE
        ) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            mLocationClient.start();
        }
    }
}
