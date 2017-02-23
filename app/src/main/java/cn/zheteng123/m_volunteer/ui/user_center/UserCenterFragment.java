package cn.zheteng123.m_volunteer.ui.user_center;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;

/**
 * Created on 2017/2/23.
 */


public class UserCenterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_center, container, false);
        ButterKnife.bind(this, view);


        return view;
    }
}
