package cn.zheteng123.m_volunteer.ui.activity_detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.activity.ActivityDetail;
import cn.zheteng123.m_volunteer.entity.activity.ActivityUser;
import cn.zheteng123.m_volunteer.util.WindowAttr;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private int mActivityId;

    private boolean mIsEnroll;

    @BindView(R.id.iv_background)
    ImageView mIvBackground;

    @BindView(R.id.tv_activity_title)
    TextView mTvTitle;

    @BindView(R.id.tv_distance)
    TextView mTvDistance;

    @BindView(R.id.tv_address)
    TextView mTvAddress;

    @BindView(R.id.tv_organization)
    TextView mTvOrganization;

    @BindView(R.id.tv_activity_date)
    TextView mTvDate;

    @BindView(R.id.tv_superintendent_name)
    TextView mTvSuperintendentName;

    @BindView(R.id.tv_superintendent_telephone)
    TextView mTvTelephone;

    @BindView(R.id.tv_category)
    TextView mTvCategory;

    @BindView(R.id.tv_activity_detail)
    TextView mTvDetail;

    @BindView(R.id.tv_enroll_num)
    TextView mTvEnrollNum;

    @BindView(R.id.tv_total_num)
    TextView mTvTotalNum;

    @BindView(R.id.btn_enroll)
    Button mBtnEnroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // 设置状态栏高度
        View mViewStatusBarFixer = findViewById(R.id.v_status_bar_fix);
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(this))
        );

        Intent intent = getIntent();
        mActivityId = intent.getIntExtra("activityId", 0);

        initView();
        initListener();
        initData();
    }

    private void initListener() {
        mBtnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsEnroll) {
                    Networks
                            .getInstance()
                            .getActivityUserApi()
                            .cancelActivity(mActivityId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result<String>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "onError: " + e.getMessage());
                                }

                                @Override
                                public void onNext(Result<String> stringResult) {
                                    Log.d(TAG, "onNext: " + "取消报名成功");
                                    mIsEnroll = false;
                                    mBtnEnroll.setBackgroundResource(R.drawable.shape_button_border_radius);
                                    mBtnEnroll.setText("我要报名");
                                }
                            });
                } else {
                    ActivityUser activityUser = new ActivityUser();
                    activityUser.setActivityId(mActivityId);
                    Networks
                            .getInstance()
                            .getActivityUserApi()
                            .enrollActivity(activityUser)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result<String>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "onError: " + e.getMessage());
                                }

                                @Override
                                public void onNext(Result<String> stringResult) {
                                    Log.d(TAG, "onNext: " + "报名成功");
                                    mIsEnroll = true;
                                    mBtnEnroll.setBackgroundResource(R.drawable.shape_button_grey_border_radius);
                                    mBtnEnroll.setText("取消报名");
                                }
                            });
                }
            }
        });
    }

    public static void actionStart(Context context, int activityId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("activityId", activityId);
        context.startActivity(intent);
    }

    private void initView() {
    }

    private void initData() {
        Networks
                .getInstance()
                .getActivityApi()
                .getActivityDetailById(mActivityId, 120, 30) // TODO: 2017/2/25 定位
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<ActivityDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<ActivityDetail> activityDetailResult) {
                        putDataIntoView(activityDetailResult.getData());
                    }
                });
    }

    private void putDataIntoView(ActivityDetail activityDetail) {
        mTvTitle.setText(activityDetail.getName());
        mTvDistance.setText(String.format(Locale.CHINA, "%.1fkm", activityDetail.getDistance()));
        mTvEnrollNum.setText(String.valueOf(activityDetail.getVolunteers().size()));
        mTvTotalNum.setText(String.valueOf(activityDetail.getRecruitPersonNumber()));
        Glide.with(this).load(BuildConfig.API_BASE_URL + activityDetail.getPicture()).into(mIvBackground);
        mTvAddress.setText(activityDetail.getAddressStreet());
        mTvOrganization.setText(activityDetail.getOrganization());
        mTvDate.setText(activityDetail.getTime());
        mTvSuperintendentName.setText(activityDetail.getPrincipalName());
        mTvTelephone.setText(activityDetail.getPrincipalContact());
        mTvCategory.setText(activityDetail.getServiceType());
        mTvDetail.setText(activityDetail.getDescription());
        if (activityDetail.isSignUp()) {
            mIsEnroll = true;
            mBtnEnroll.setBackgroundResource(R.drawable.shape_button_grey_border_radius);
            mBtnEnroll.setText("取消报名");
        }
    }
}
