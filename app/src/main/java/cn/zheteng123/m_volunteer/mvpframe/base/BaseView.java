package cn.zheteng123.m_volunteer.mvpframe.base;

/**
 * Created on 2017/2/7.
 */


public interface BaseView {

    // void showError(String msg);

    void onRequestStart();
    void onRequestError(String msg);
    void onRequestEnd();
    void onInternetError();
}
