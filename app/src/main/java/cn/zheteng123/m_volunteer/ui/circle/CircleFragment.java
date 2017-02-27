package cn.zheteng123.m_volunteer.ui.circle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.entity.circle.Status;
import cn.zheteng123.m_volunteer.entity.circle.User;
import cn.zheteng123.m_volunteer.ui.circle.adapter.StatusAdapter;
import cn.zheteng123.m_volunteer.util.WindowAttr;

/**
 * Created on 2017/2/27.
 */


public class CircleFragment extends Fragment {

    private List<Status> mStatusList = new ArrayList<>();

    private View mView;
    private ListView mLvCircle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView();
        loadData();
        return mView;
    }

    private void loadData() {
        mStatusList.clear();
        Status status = new Status();
        User user = new User();
        user.setProfile_image_url("http://115.159.45.39:9090/public/img/default_avatar.jpg");
        user.setName("王伟");
        status.setUser(user);
        status.setCreated_at("2017-02-22");
        status.setSource("Android");
        status.setText("参加了社区长者读书分享会");
        status.setThumbnail_pic("http://115.159.45.39:9090/public/img/c2a36ba3-91dc-442f-a864-06c6162c6ded.jpeg");
        status.setReposts_count(0);
        status.setComments_count(0);
        status.setAttitudes_count(3);


        mStatusList.add(status);
        mLvCircle.setAdapter(new StatusAdapter(getActivity(), mStatusList));
    }

    private void initView() {
        mView = View.inflate(getActivity(), R.layout.fragment_circle, null);

        // 设置状态栏高度
        View viewStatusBarFixer = mView.findViewById(R.id.v_status_bar_fix);
        viewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(getActivity()))
        );

        mLvCircle = (ListView) mView.findViewById(R.id.lv_home);
    }
}
