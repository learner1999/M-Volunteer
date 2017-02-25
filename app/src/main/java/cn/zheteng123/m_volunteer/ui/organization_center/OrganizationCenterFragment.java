package cn.zheteng123.m_volunteer.ui.organization_center;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.organization.OrganizationEntity;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2017/2/25.
 */


public class OrganizationCenterFragment extends Fragment {

    @BindView(R.id.ll_manage_activity)
    LinearLayout mLlManageActivity;

    @BindView(R.id.ll_my_member)
    LinearLayout mLlMember;

    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;

    @BindView(R.id.tv_organization_name)
    TextView mTvOrganizationName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organization_center, container, false);
        ButterKnife.bind(this, view);

        initListener();
        initData();

        return view;
    }

    private void initData() {
        Networks
                .getInstance()
                .getOrganizationApi()
                .getOrganization()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<OrganizationEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<OrganizationEntity> organizationEntityResult) {
                        OrganizationEntity organizationEntity = organizationEntityResult.getData();
                        Glide.with(getActivity()).load(BuildConfig.API_BASE_URL + organizationEntity.getAvatar()).into(mCivAvatar);
                        mTvOrganizationName.setText(organizationEntity.getName());
                    }
                });
    }

    private void initListener() {
        mLlManageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/2/25 跳转至活动管理界面
            }
        });

        mLlMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/2/25 跳转至我的成员界面
            }
        });
    }
}
