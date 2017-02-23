package cn.zheteng123.m_volunteer.ui.my_activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.entity.my_activity.MyActivityEntity;

/**
 * Created on 2017/2/23.
 */


public class MyActivityAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyActivityEntity> mMyActivityEntityList;
    private LayoutInflater mLayoutInflater;

    public MyActivityAdapter(Context context, List<MyActivityEntity> myActivityEntityList) {
        mContext = context;
        mMyActivityEntityList = myActivityEntityList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mMyActivityEntityList.size();
    }

    @Override
    public MyActivityEntity getItem(int position) {
        return mMyActivityEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_my_activity, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyActivityEntity myActivity = getItem(position);

        Glide.with(mContext).load(BuildConfig.API_BASE_URL + myActivity.getPicture()).into(viewHolder.ivActivity);
        viewHolder.tvTitle.setText(myActivity.getName());
        viewHolder.tvOrganization.setText(myActivity.getOrganization());
        viewHolder.tvTime.setText("发布时间：" + myActivity.getTimestamp());
        viewHolder.tvRecruitStatus.setText(myActivity.getActivityStatus());
        viewHolder.tvInterviewStatus.setText(myActivity.getInterviewStatus());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/2/23 跳转到详情页
            }
        });

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.iv_activity) ImageView ivActivity;
        @BindView(R.id.tv_activity_title) TextView tvTitle;
        @BindView(R.id.tv_organization) TextView tvOrganization;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_recruit_status) TextView tvRecruitStatus;
        @BindView(R.id.tv_interview_status) TextView tvInterviewStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
