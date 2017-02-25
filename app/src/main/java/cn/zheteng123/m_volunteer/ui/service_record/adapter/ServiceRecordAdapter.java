package cn.zheteng123.m_volunteer.ui.service_record.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.entity.service_record.ServiceRecordEntity;
import cn.zheteng123.m_volunteer.ui.activity_detail.DetailActivity;

/**
 * Created on 2017/2/25.
 */


public class ServiceRecordAdapter extends BaseAdapter {

    private Context mContext;
    private List<ServiceRecordEntity> mServiceRecordEntityList;
    private LayoutInflater mLayoutInflater;

    public ServiceRecordAdapter(Context context, List<ServiceRecordEntity> serviceRecordEntityList) {
        mContext = context;
        mServiceRecordEntityList = serviceRecordEntityList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mServiceRecordEntityList.size();
    }

    @Override
    public ServiceRecordEntity getItem(int position) {
        return mServiceRecordEntityList.get(position);
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

        final ServiceRecordEntity serviceRecordEntity = getItem(position);

        Glide.with(mContext).load(BuildConfig.API_BASE_URL + serviceRecordEntity.getPicture()).into(viewHolder.ivActivity);
        viewHolder.tvTitle.setText(serviceRecordEntity.getName());
        viewHolder.tvOrganization.setText(serviceRecordEntity.getOrganization());
        viewHolder.tvTime.setText(String.format(Locale.CHINA, "发布时间：%s", serviceRecordEntity.getTimestamp()));
        viewHolder.tvRecruitStatus.setText(serviceRecordEntity.getActivityStatus());
        viewHolder.tvInterviewStatus.setText(String.format(Locale.CHINA, "工时加%.0fh", serviceRecordEntity.getWorkingHours()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.actionStart(mContext, serviceRecordEntity.getId());
            }
        });

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.iv_activity)
        ImageView ivActivity;
        @BindView(R.id.tv_activity_title)
        TextView tvTitle;
        @BindView(R.id.tv_organization) TextView tvOrganization;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_recruit_status) TextView tvRecruitStatus;
        @BindView(R.id.tv_interview_status) TextView tvInterviewStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
