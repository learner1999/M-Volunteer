package cn.zheteng123.m_volunteer.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.ui.activity_category.CategoryActivity;
import cn.zheteng123.m_volunteer.ui.home.adapter.HomeActivityAdapter;
import cn.zheteng123.m_volunteer.ui.search.SearchActivity;
import cn.zheteng123.m_volunteer.util.WindowAttr;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static cn.zheteng123.m_volunteer.R.id.ll_category1;

/**
 * Created on 2017/2/12.
 */


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private static final int REQUEST_CODE_PICK_CITY = 0;

    List<HomeActivityEntity> mHomeActivityList = new ArrayList<>();
    HomeActivityAdapter mHomeActivityAdapter;

    @BindView(R.id.roll_pager_view)
    RollPagerView mRollPagerView;

    @BindView(R.id.lv_home_activity)
    ListView mLvActivity;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSrl;

    @BindView(R.id.tv_city)
    TextView mTvCity;

    @BindView(R.id.iv_search)
    ImageView mIvSearch;

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
        mHomeActivityAdapter = new HomeActivityAdapter(getActivity(), mHomeActivityList);
        mLvActivity.setAdapter(mHomeActivityAdapter);

        // 设置下拉刷新
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                mSrl.setRefreshing(false);
            }
        });

        // 设置城市选择器
        mTvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);

            }
        });

        // 设置搜索
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        Networks
                .getInstance()
                .getActivityApi()
                .getHomeActivity(120, 30, 1, 10) // TODO: 2017/2/22 定位
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<PageInfo<HomeActivityEntity>>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<PageInfo<HomeActivityEntity>> result) {
                        Log.d(TAG, "onNext: ok");
                        mHomeActivityList.clear();
                        mHomeActivityList.addAll(result.getData().getList());
                        mHomeActivityAdapter.notifyDataSetChanged();
                    }
                });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                mTvCity.setText(city);
            }
        }
    }

    @OnClick({R.id.ll_category1, R.id.ll_category2, R.id.ll_category3, R.id.ll_category4,
            R.id.ll_category5, R.id.ll_category6, R.id.ll_category7, R.id.ll_category8})
    public void onClick_Event(View v) {
        String[] categorys = new String[]{"全部类型", "青少年服务", "敬老助残", "扶贫帮困",
                "文明礼仪", "平安守护", "环境保护", "文化体育", "便民服务"};

        Intent intent = new Intent(getActivity(), CategoryActivity.class);

        switch (v.getId()) {
            case ll_category1:
                intent.putExtra("category", categorys[1]);
                break;

            case R.id.ll_category2:
                intent.putExtra("category", categorys[2]);
                break;

            case R.id.ll_category3:
                intent.putExtra("category", categorys[3]);
                break;

            case R.id.ll_category4:
                intent.putExtra("category", categorys[4]);
                break;

            case R.id.ll_category5:
                intent.putExtra("category", categorys[5]);
                break;

            case R.id.ll_category6:
                intent.putExtra("category", categorys[6]);
                break;

            case R.id.ll_category7:
                intent.putExtra("category", categorys[7]);
                break;

            case R.id.ll_category8:
                intent.putExtra("category", categorys[8]);
                break;
        }
        startActivity(intent);
    }
}
