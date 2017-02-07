package cn.zheteng123.m_volunteer.mvpframe.base;

/**
 * Created on 2017/2/7.
 */


public abstract class BasePresenter<M, V> {

    public M mModel;
    public V mView;

    // public RxManager mRxManager = new RxManager();

    public void attachVM(V v, M m) {
        this.mModel = m;
        this.mView = v;
    }

    public void detachVM() {
        // mRxManager.clear();
        mView = null;
        mModel = null;
    }
}
