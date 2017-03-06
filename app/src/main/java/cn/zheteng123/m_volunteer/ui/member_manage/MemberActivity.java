package cn.zheteng123.m_volunteer.ui.member_manage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import cn.zheteng123.m_volunteer.entity.organization.ManageMemberEntity;
import cn.zheteng123.m_volunteer.ui.member_manage.adapter.MemberAdapter;
import cn.zheteng123.m_volunteer.util.WindowAttr;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MemberActivity extends AppCompatActivity {

    private static final String TAG = "MemberActivity";

    private List<ManageMemberEntity> mMemberEntityList = new ArrayList<>();
    private MemberAdapter mMemberAdapter;

    @BindView(R.id.lv_member)
    ListView mLvMember;

    @BindView(R.id.v_status_bar_fix)
    View mViewStatusBarFixer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // 设置状态栏高度
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(this))
        );

        initView();
        initData();
    }

    private void initData() {
        Networks
                .getInstance()
                .getOrganizationApi()
                .getManageMemberEntity(1, 10) // TODO: 2017/2/26 分页
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<PageInfo<ManageMemberEntity>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Result<PageInfo<ManageMemberEntity>> pageInfoResult) {
                        List<ManageMemberEntity> manageMemberEntityList = pageInfoResult.getData().getList();
                        mMemberEntityList.clear();
                        mMemberEntityList.addAll(manageMemberEntityList);
                        mMemberAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mMemberAdapter = new MemberAdapter(this, mMemberEntityList);
        mLvMember.setAdapter(mMemberAdapter);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MemberActivity.class);
        context.startActivity(intent);
    }
}
