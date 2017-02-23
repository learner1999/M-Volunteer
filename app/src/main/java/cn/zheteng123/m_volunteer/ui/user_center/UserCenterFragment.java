package cn.zheteng123.m_volunteer.ui.user_center;

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
import cn.zheteng123.m_volunteer.entity.user_center.VolunteerEntity;
import cn.zheteng123.m_volunteer.ui.my_activity.MyActivityActivity;
import cn.zheteng123.m_volunteer.ui.service_record.ServiceRecordActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2017/2/23.
 */


public class UserCenterFragment extends Fragment {

    @BindView(R.id.ll_my_activity)
    LinearLayout mLlMyActivity;

    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;

    @BindView(R.id.tv_volunteer_name)
    TextView mTvVolunteerName;

    @BindView(R.id.tv_working_hours)
    TextView mTvWorkingHours;

    @BindView(R.id.ll_service_record)
    LinearLayout mLlServiceRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_center, container, false);
        ButterKnife.bind(this, view);

        initListener();
        initData();

        return view;
    }

    private void putDataIntoView(VolunteerEntity volunteerEntity) {
        Glide.with(this).load(BuildConfig.API_BASE_URL + volunteerEntity.getAvatar()).into(mCivAvatar);
        mTvVolunteerName.setText(volunteerEntity.getName());
        mTvWorkingHours.setText("工时：" + volunteerEntity.getWorkingHours() + " h");
    }

    private void initData() {
        Networks
                .getInstance()
                .getVolunteerApi()
                .getVolunteerInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<VolunteerEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<VolunteerEntity> volunteerEntityResult) {
                        putDataIntoView(volunteerEntityResult.getData());
                    }
                });
    }

    private void initListener() {
        mLlMyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivityActivity.actionStart(getActivity());
            }
        });

        mLlServiceRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceRecordActivity.actionStart(getActivity());
            }
        });
    }
}
