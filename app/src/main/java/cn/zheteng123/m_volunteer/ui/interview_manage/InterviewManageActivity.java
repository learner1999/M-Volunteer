package cn.zheteng123.m_volunteer.ui.interview_manage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.PageInfo;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.interview_manage.InterviewManageEntity;
import cn.zheteng123.m_volunteer.ui.interview_manage.adapter.InterviewManageAdapter;
import cn.zheteng123.m_volunteer.util.WindowAttr;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InterviewManageActivity extends AppCompatActivity {

    private static final String TAG = "InterviewManageActivity";

    private List<InterviewManageEntity> mInterviewManageEntityList = new ArrayList<>();
    private InterviewManageAdapter mInterviewManageAdapter;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.lv_interview_manage)
    ListView mLvInterviewManage;

    @BindView(R.id.v_status_bar_fix)
    View mViewStatusBarFixer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_manage);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            // 设置状态栏高度
            mViewStatusBarFixer.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            WindowAttr.getStatusBarHeight(this))
            );
        }

        initView();
        initListener();
        initData();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, InterviewManageActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        Networks
                .getInstance()
                .getOrganizationApi()
                .getInterviewManageEntity(1, 10) // TODO: 2017/2/27 分页
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<PageInfo<InterviewManageEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<PageInfo<InterviewManageEntity>> pageInfoResult) {
                        List<InterviewManageEntity> interviewManageEntityList = pageInfoResult.getData().getList();
                        mInterviewManageEntityList.clear();
                        mInterviewManageEntityList.addAll(interviewManageEntityList);
                        mInterviewManageAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mInterviewManageAdapter = new InterviewManageAdapter(this, mInterviewManageEntityList);
        mLvInterviewManage.setAdapter(mInterviewManageAdapter);
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
