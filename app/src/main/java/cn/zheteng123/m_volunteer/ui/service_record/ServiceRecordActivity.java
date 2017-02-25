package cn.zheteng123.m_volunteer.ui.service_record;

import android.content.Context;
import android.content.Intent;
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
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.service_record.ServiceRecordEntity;
import cn.zheteng123.m_volunteer.ui.service_record.adapter.ServiceRecordAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceRecordActivity extends AppCompatActivity {

    List<ServiceRecordEntity> mServiceRecordEntityList = new ArrayList<>();
    ServiceRecordAdapter mServiceRecordAdapter;

    @BindView(R.id.lv_my_activity)
    ListView mLvMyActivity;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_record);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        initView();
        initListener();
        initData();
    }

    private void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ServiceRecordActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        Networks
                .getInstance()
                .getVolunteerApi()
                .getMyActivityHistory(1, 10) // // TODO: 2017/2/23 分页
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<PageInfo<ServiceRecordEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<PageInfo<ServiceRecordEntity>> pageInfoResult) {
                        mServiceRecordEntityList.clear();
                        List<ServiceRecordEntity> serviceRecordEntity = pageInfoResult.getData().getList();
                        mServiceRecordEntityList.addAll(serviceRecordEntity);
                        mServiceRecordAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mServiceRecordAdapter = new ServiceRecordAdapter(this, mServiceRecordEntityList);
        mLvMyActivity.setAdapter(mServiceRecordAdapter);
    }
}
