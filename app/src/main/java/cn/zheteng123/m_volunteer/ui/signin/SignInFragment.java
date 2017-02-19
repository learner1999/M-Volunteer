package cn.zheteng123.m_volunteer.ui.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.mvpframe.base.BaseFrameFragment;
import cn.zheteng123.m_volunteer.util.WindowAttr;

/**
 * Created on 2017/2/19.
 */


public class SignInFragment extends BaseFrameFragment<SignInPresenter, SignInModel> implements SignInContract.View {

    @BindView(R.id.v_status_bar_fix)
    View mViewStatusBarFixer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_in);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        // 设置状态栏高度
        mViewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(getActivity()))
        );


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {

    }


}
