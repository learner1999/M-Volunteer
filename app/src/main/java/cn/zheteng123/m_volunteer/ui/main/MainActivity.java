package cn.zheteng123.m_volunteer.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.ui.home.HomeFragment;
import cn.zheteng123.m_volunteer.ui.main.adapter.ViewPagerAdapter;
import cn.zheteng123.m_volunteer.ui.signin.SignInFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

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

        // mHomeFragment = new HomeFragment();
        // mFragmentList.add(mHomeFragment);
        SignInFragment signInFragment = new SignInFragment();
        mFragmentList.add(signInFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

        mViewPager = (ViewPager) findViewById(R.id.vp_page);
        mViewPager.setAdapter(viewPagerAdapter);

    }
}
