package cn.zheteng123.m_volunteer.ui.signin;

import cn.zheteng123.m_volunteer.mvpframe.base.BaseModel;
import cn.zheteng123.m_volunteer.mvpframe.base.BasePresenter;
import cn.zheteng123.m_volunteer.mvpframe.base.BaseView;

/**
 * Created on 2017/2/19.
 */


public interface SignInContract {

    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<Model, View> {

    }
}
