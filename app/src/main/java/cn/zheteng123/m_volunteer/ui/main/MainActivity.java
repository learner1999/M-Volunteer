package cn.zheteng123.m_volunteer.ui.main;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.customview.MyViewPager;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.login.Role;
import cn.zheteng123.m_volunteer.ui.circle.CircleFragment;
import cn.zheteng123.m_volunteer.ui.home.HomeFragment;
import cn.zheteng123.m_volunteer.ui.login.LoginActivity;
import cn.zheteng123.m_volunteer.ui.main.adapter.ViewPagerAdapter;
import cn.zheteng123.m_volunteer.ui.organization_center.OrganizationCenterFragment;
import cn.zheteng123.m_volunteer.ui.signin.SignInFragment;
import cn.zheteng123.m_volunteer.ui.user_center.UserCenterFragment;
import cn.zheteng123.m_volunteer.util.LoginInfo;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyViewPager mViewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        autoLogin();

        mViewPager = (MyViewPager) findViewById(R.id.vp_page);

        // 配置底栏点击事件
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        mViewPager.setCurrentItem(0);
                        break;

                    case R.id.tab_group:
                        mViewPager.setCurrentItem(1);
                        break;

                    case R.id.tab_sign_in:
                        mViewPager.setCurrentItem(2);
                        break;

                    case R.id.tab_circle:
                        mViewPager.setCurrentItem(3);
                        break;

                    case R.id.tab_ranking_list:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });



    }

    private void initView() {
        mHomeFragment = new HomeFragment();
        mFragmentList.add(mHomeFragment);
        Fragment conversationFragment = LoginInfo.sYWIMKit.getConversationFragment();
        mFragmentList.add(conversationFragment);
        // Fragment contactsFragment = LoginInfo.sYWIMKit.getContactsFragment();
        // mFragmentList.add(contactsFragment);
        SignInFragment signInFragment = new SignInFragment();
        mFragmentList.add(signInFragment);
        CircleFragment circleFragment = new CircleFragment();
        mFragmentList.add(circleFragment);
        if (LoginInfo.sRole.getName().equals("volunteer")) {
            UserCenterFragment userCenterFragment = new UserCenterFragment();
            mFragmentList.add(userCenterFragment);
        } else {
            OrganizationCenterFragment organizationCenterFragment = new OrganizationCenterFragment();
            mFragmentList.add(organizationCenterFragment);
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

        mViewPager.setScrollable(false);
        mViewPager.setAdapter(viewPagerAdapter);
    }

    private void autoLogin() {
        // 保持登录
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = pref.getString("token", "");
        Log.d(TAG, "autoLogin: " + token);
        final String username = pref.getString("username", "");
        final String password = pref.getString("password", "");
        LoginInfo.token = token;
        Networks
                .getInstance()
                .getLoginApi()
                .getUserRoles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Role>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        LoginActivity.actionStart(MainActivity.this);
                        finish();
                    }

                    @Override
                    public void onNext(Result<List<Role>> listResult) {
                        LoginInfo.sRole = listResult.getData().get(0);

                        LoginInfo.sYWIMKit = YWAPI.getIMKitInstance(username, "23653358");

                        final IYWLoginService loginService = LoginInfo.sYWIMKit.getLoginService();

                        YWLoginParam loginParam = YWLoginParam.createLoginParam(username, password);

                        loginService.login(loginParam, new IWxCallback() {

                            @Override
                            public void onSuccess(Object... arg0) {
                                initView();
                            }

                            @Override
                            public void onProgress(int arg0) {
                                // TODO Auto-generated method stub
                            }

                            @Override
                            public void onError(int errCode, String description) {
                                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                            }
                        });
                    }
                });
    }
}
