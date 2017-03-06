package cn.zheteng123.m_volunteer.ui.member_manage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.organization.ManageMemberEntity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2017/2/26.
 */


public class MemberAdapter extends BaseAdapter{

    private static final String TAG = "MemberAdapter";

    private boolean bFirst = true;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ManageMemberEntity> mManageMemberEntityList;

    public MemberAdapter(Context context, List<ManageMemberEntity> manageMemberEntityList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mManageMemberEntityList = manageMemberEntityList;
    }

    @Override
    public int getCount() {
        return mManageMemberEntityList.size();
    }

    @Override
    public ManageMemberEntity getItem(int position) {
        return mManageMemberEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ManageMemberEntity manageMemberEntity = getItem(position);

        bFirst = true;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_member_activity, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvMemberName.setText(manageMemberEntity.getName());

        viewHolder.tvWorkingHours.setText("工时：" + manageMemberEntity.getWorkingHours() + " h");

        if (manageMemberEntity.getCertificateStatusId() == null) {
            viewHolder.spinnerCertificateStatus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.spinnerCertificateStatus.setSelection(manageMemberEntity.getCertificateStatusId());
            viewHolder.spinnerCertificateStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view;
                    textView.setTextColor(Color.WHITE);
                    if (position + 1 == manageMemberEntity.getCertificateStatusId()) {
                        return;
                    }
                    Networks
                            .getInstance()
                            .getActivityApi()
                            .modifyActivityStatus(manageMemberEntity.getId(), position + 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result<String>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "onError: " + e.getMessage());
                                    Toast.makeText(mContext, "修改失败！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNext(Result<String> stringResult) {
                                    Toast.makeText(mContext, "修改成功！", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        Glide.with(mContext).load(BuildConfig.API_BASE_URL + manageMemberEntity.getAvatar()).into(viewHolder.ivAvatar);

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        @BindView(R.id.tv_member_name)
        TextView tvMemberName;

        @BindView(R.id.tv_working_hours)
        TextView tvWorkingHours;

        @BindView(R.id.spinner_certificate_status)
        Spinner spinnerCertificateStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
