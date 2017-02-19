package cn.zheteng123.m_volunteer.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.ui.home.adapter.HomeActivityAdapter;
import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.util.WindowAttr;

/**
 * Created on 2017/2/12.
 */


public class HomeFragment extends Fragment {

    @BindView(R.id.roll_pager_view)
    RollPagerView mRollPagerView;

    @BindView(R.id.lv_home_activity)
    ListView mLvActivity;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        // 设置状态栏高度
        View mViewStatusBarFixer = view.findViewById(R.id.v_status_bar_fix);
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(getActivity()))
        );

        // 设置轮播图
        mRollPagerView.setAdapter(new StaticPagerAdapter() {
            private int[] imgs = {
                    R.drawable.banner1,
                    R.drawable.banner2,
            };

            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setImageResource(imgs[position]);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                return view;
            }

            @Override
            public int getCount() {
                return imgs.length;
            }
        });

        // 设置 ListView
        List<HomeActivityEntity> homeActivityList = new ArrayList<>();
        HomeActivityEntity homeActivityEntity = new HomeActivityEntity();
        homeActivityEntity.setTitle("这是一个什么活动呢");
        homeActivityEntity.setDistance(20);
        homeActivityEntity.setDistrict("西湖区");
        homeActivityEntity.setEnrollNum(2);
        homeActivityEntity.setTotalNum(11);
        homeActivityList.add(homeActivityEntity);
        HomeActivityAdapter homeActivityAdapter = new HomeActivityAdapter(getActivity(), homeActivityList);
        mLvActivity.setAdapter(homeActivityAdapter);

        // 设置下拉刷新
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                mSrl.setRefreshing(false);
            }
        });

        return view;
    }
}
