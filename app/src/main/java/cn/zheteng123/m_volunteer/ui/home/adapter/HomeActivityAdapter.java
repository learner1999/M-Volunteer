package cn.zheteng123.m_volunteer.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.entity.HomeActivityEntity;
import cn.zheteng123.m_volunteer.ui.activity_detail.DetailActivity;

/**
 * Created on 2017/2/17.
 */


public class HomeActivityAdapter extends BaseAdapter {

    private List<HomeActivityEntity> mHomeActivityList;
    private LayoutInflater mInflater;
    private Context mContext;

    public HomeActivityAdapter(Context context, List<HomeActivityEntity> homeActivityList) {
        mHomeActivityList = homeActivityList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mHomeActivityList.size();
    }

    @Override
    public HomeActivityEntity getItem(int position) {
        return mHomeActivityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        final HomeActivityEntity homeActivity = getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_home_activity, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_item_activity_title);
            viewHolder.tvDistance = (TextView) convertView.findViewById(R.id.tv_distance);
            viewHolder.tvDistrict = (TextView) convertView.findViewById(R.id.tv_district);
            viewHolder.tvEnrollNum = (TextView) convertView.findViewById(R.id.tv_person);
            viewHolder.ivBackground = (ImageView) convertView.findViewById(R.id.iv_background);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(homeActivity.getTitle());

        double distance = homeActivity.getDistance();
        if (distance > 50) {
            viewHolder.tvDistance.setText(">" + distance + "km");
        } else {
            viewHolder.tvDistance.setText(distance + "km");
        }

        viewHolder.tvDistrict.setText(homeActivity.getDistrict());

        viewHolder.tvEnrollNum.setText(homeActivity.getEnrollNum() + "/" + homeActivity.getTotalNum());

        Glide.with(mContext).load(BuildConfig.API_BASE_URL + homeActivity.getPicture()).into(viewHolder.ivBackground);

        viewHolder.ivBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.actionStart(mContext, homeActivity.getId());
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvDistance;
        TextView tvDistrict;
        TextView tvEnrollNum;
        ImageView ivBackground;
    }
}
