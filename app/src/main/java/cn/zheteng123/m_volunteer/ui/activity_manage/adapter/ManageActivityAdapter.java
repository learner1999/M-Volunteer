package cn.zheteng123.m_volunteer.ui.activity_manage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.BuildConfig;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.api.Networks;
import cn.zheteng123.m_volunteer.entity.Result;
import cn.zheteng123.m_volunteer.entity.organization.ManageActivityEntity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created on 2017/2/25.
 */


public class ManageActivityAdapter extends BaseAdapter {

    private static final String TAG = "ManageActivityAdapter";

    private boolean bFirst = true;

    private Context mContext;
    private List<ManageActivityEntity> mManageActivityEntityList;
    private LayoutInflater mInflater;

    public ManageActivityAdapter(Context context, List<ManageActivityEntity> manageActivityEntityList) {
        mContext = context;
        mManageActivityEntityList = manageActivityEntityList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mManageActivityEntityList.size();
    }

    @Override
    public ManageActivityEntity getItem(int position) {
        return mManageActivityEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ManageActivityEntity manageActivityEntity = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_manage_activity, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(manageActivityEntity.getName());

        Date date = manageActivityEntity.getTimestamp();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String time = "发布时间：" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        viewHolder.tvTime.setText(time);

        viewHolder.spinnerRecruitStatus.setSelection(
                ((ArrayAdapter)viewHolder.spinnerRecruitStatus.getAdapter()).getPosition(manageActivityEntity.getDescription()));
        viewHolder.spinnerRecruitStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.WHITE);
                if (bFirst) {
                    bFirst = false;
                    return;
                }
                Networks
                        .getInstance()
                        .getActivityApi()
                        .modifyActivityStatus(manageActivityEntity.getId(), position + 1)
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
        Glide.with(mContext).load(BuildConfig.API_BASE_URL + manageActivityEntity.getPicture()).into(viewHolder.ivActivity);

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.iv_activity)
        ImageView ivActivity;

        @BindView(R.id.tv_activity_title)
        TextView tvTitle;

        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.spinner_recruit_status)
        Spinner spinnerRecruitStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
