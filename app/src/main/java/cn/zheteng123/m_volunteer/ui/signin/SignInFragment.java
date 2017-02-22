package cn.zheteng123.m_volunteer.ui.signin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.mvpframe.base.BaseFrameFragment;
import cn.zheteng123.m_volunteer.util.WindowAttr;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

        option.setIsNeedLocationDescribe(true);
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

        final String locationDescribe = bdLocation.getLocationDescribe();

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
                showSignInDialog(locationDescribe, ll);
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

    private void showSignInDialog(final String locationDescribe, final LatLng ll) {
        //创建InfoWindow展示的view
        View view = getActivity().getLayoutInflater().inflate(R.layout.map_sign_in, null, false);
        Button button = (Button) view.findViewById(R.id.btn_sign_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputCodeDialog(locationDescribe, ll);
            }
        });
        TextView tvLocation = (TextView) view.findViewById(R.id.tv_location);
        tvLocation.setText(locationDescribe);
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(view, ll, 0);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    private void showInputCodeDialog(final String locationDescribe, final LatLng ll) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.map_input_code, null, false);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInDialog(locationDescribe, ll);
            }
        });
        EditText etCode = (EditText) view.findViewById(R.id.et_code);
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    Networks
                            .getInstance()
                            .getSignInApi()
                            .signIn(Integer.parseInt(s.toString()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Result<String>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("SignInFragment", "onNext: " + e.getMessage());
                                    Toast.makeText(getActivity(), "签到失败，请检查活动编码！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNext(Result<String> stringResult) {
                                    Log.d("SignInFragment", "onNext: 签到成功");
                                    Toast.makeText(getActivity(), "签到成功！", Toast.LENGTH_SHORT).show();
                                    showSignInDialog(locationDescribe, ll);
                                }
                            });

                }
            }
        });
        mBaiduMap.showInfoWindow(new InfoWindow(view, ll, 0));
    }
}
