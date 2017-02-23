package cn.zheteng123.m_volunteer.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.customview.MyViewPager;
import cn.zheteng123.m_volunteer.ui.home.HomeFragment;
import cn.zheteng123.m_volunteer.ui.main.adapter.ViewPagerAdapter;
import cn.zheteng123.m_volunteer.ui.signin.SignInFragment;
import cn.zheteng123.m_volunteer.ui.user_center.UserCenterFragment;

public class MainActivity extends AppCompatActivity {

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

        mHomeFragment = new HomeFragment();
        mFragmentList.add(mHomeFragment);
        SignInFragment signInFragment = new SignInFragment();
        mFragmentList.add(signInFragment);
        UserCenterFragment userCenterFragment = new UserCenterFragment();
        mFragmentList.add(userCenterFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

        mViewPager = (MyViewPager) findViewById(R.id.vp_page);
        mViewPager.setScrollable(false);
        mViewPager.setAdapter(viewPagerAdapter);

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
}
