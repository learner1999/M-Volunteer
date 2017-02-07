package cn.zheteng123.m_volunteer.mvpframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.zheteng123.m_volunteer.common.BaseActivity;
import cn.zheteng123.m_volunteer.mvpframe.util.TUtil;

/**
 * Created on 2017/2/7.
 */


public class BaseFrameActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity implements BaseView {

    public P mPresenter;

    public M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        mPresenter.attachVM(this, mModel);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onInternetError() {

    }
}
