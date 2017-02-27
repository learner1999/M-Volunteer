package cn.zheteng123.m_volunteer.ui.interview_manage.adapter;

import android.content.Context;
import android.content.Intent;
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
import cn.zheteng123.m_volunteer.entity.interview_manage.InterviewManageEntity;
import cn.zheteng123.m_volunteer.util.LoginInfo;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 2017/2/27.
 */


public class InterviewManageAdapter extends BaseAdapter {

    private static final String TAG = "InterviewManageAdapter";

    private Context mContext;
    private List<InterviewManageEntity> mInterviewManageEntityList;
    private LayoutInflater mInflater;

    public InterviewManageAdapter(Context context, List<InterviewManageEntity> interviewManageEntityList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInterviewManageEntityList = interviewManageEntityList;
    }

    @Override
    public int getCount() {
        return mInterviewManageEntityList.size();
    }

    @Override
    public InterviewManageEntity getItem(int position) {
        return mInterviewManageEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final InterviewManageEntity interviewManageEntity = mInterviewManageEntityList.get(position);

        final int interviewStatus = interviewManageEntity.getInterviewStatus();

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_interview_manage, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvUsername.setText(interviewManageEntity.getUsername());

        viewHolder.tvActivityTitle.setText(interviewManageEntity.getActivityName());


        viewHolder.spinnerInterviewStatus.setSelection(interviewManageEntity.getInterviewStatus() - 1);
        viewHolder.spinnerInterviewStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.WHITE);
                Log.d(TAG, "onItemSelected: " + position + ", " + interviewStatus);
                if (position + 1 == interviewStatus) {
                    return;
                }
                Networks
                        .getInstance()
                        .getOrganizationApi()
                        .modifyInterviewStatus(interviewManageEntity.getActivityUserId(), position + 1)
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

        Glide.with(mContext).load(BuildConfig.API_BASE_URL + interviewManageEntity.getAvatar()).into(viewHolder.ivAvatar);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = interviewManageEntity.getUsername();// 消息接收者ID
                Intent intent = LoginInfo.sYWIMKit.getChattingActivityIntent(target);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;

        @BindView(R.id.tv_username)
        TextView tvUsername;

        @BindView(R.id.tv_activity_title)
        TextView tvActivityTitle;

        @BindView(R.id.spinner_interview_status)
        Spinner spinnerInterviewStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
