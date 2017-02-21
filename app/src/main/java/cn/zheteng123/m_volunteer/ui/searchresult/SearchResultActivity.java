package cn.zheteng123.m_volunteer.ui.searchresult;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.ui.home.adapter.HomeActivityAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchResultActivity extends AppCompatActivity {

    private int mPageNow = 1;
    private int mPageSize = 10;
    private boolean mIsLastPage = false;

    private List<HomeActivityEntity> mHomeActivityEntityList = new ArrayList<>();

    private HomeActivityAdapter mHomeActivityAdapter;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.lv_search_activity)
    ListView mLvSearchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        String query = getIntent().getStringExtra("query");

        initListener();

        mHomeActivityAdapter = new HomeActivityAdapter(this, mHomeActivityEntityList);
        mLvSearchActivity.setAdapter(mHomeActivityAdapter);

        if (query != null && !query.equals("")) {
            Networks
                    .getInstance()
                    .getSearchApi()
                    .getResultOfActivity(120, 30, mPageNow, mPageSize, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result<PageInfo<HomeActivityEntity>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Result<PageInfo<HomeActivityEntity>> pageInfoResult) {
                            List<HomeActivityEntity> homeActivityEntityList = pageInfoResult.getData().getList();
                            mHomeActivityEntityList.clear();
                            mHomeActivityEntityList.addAll(homeActivityEntityList);
                            mHomeActivityAdapter.notifyDataSetChanged();
                            if (pageInfoResult.getData().isLastPage()) {
                                mIsLastPage = true;
                            } else {
                                mPageNow++;
                            }
                        }
                    });
        }
    }

    private void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
