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
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.my_activity.MyActivityEntity;
import cn.zheteng123.m_volunteer.ui.my_activity.MyActivityActivity;
import cn.zheteng123.m_volunteer.ui.my_activity.adapter.MyActivityAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceRecordActivity extends AppCompatActivity {

    List<MyActivityEntity> mMyActivityEntityList = new ArrayList<>();
    MyActivityAdapter mMyActivityAdapter;

    @BindView(R.id.lv_my_activity)
    ListView mLvMyActivity;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_record);

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
        Intent intent = new Intent(context, MyActivityActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        Networks
                .getInstance()
                .getVolunteerApi()
                .getMyActivities(1, 10) // // TODO: 2017/2/23 分页
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<PageInfo<MyActivityEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<PageInfo<MyActivityEntity>> pageInfoResult) {
                        mMyActivityEntityList.clear();
                        List<MyActivityEntity> myActivityEntityList = pageInfoResult.getData().getList();
                        mMyActivityEntityList.addAll(myActivityEntityList);
                        mMyActivityAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mMyActivityAdapter = new MyActivityAdapter(this, mMyActivityEntityList);
        mLvMyActivity.setAdapter(mMyActivityAdapter);
    }
}
