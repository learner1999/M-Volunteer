package cn.zheteng123.m_volunteer.application;

import android.app.Application;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.baidu.mapapi.SDKInitializer;

import cn.zheteng123.m_volunteer.ui.aliyunwang.ChattingUICustom;
import cn.zheteng123.m_volunteer.ui.aliyunwang.ConversationListUICustom;
import cn.zheteng123.m_volunteer.ui.aliyunwang.IMConversationListOperationCustom;

/**
 * Created on 2017/2/19.
 */


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化百度地图 sdk
        SDKInitializer.initialize(getApplicationContext());


        final String APP_KEY = "23653358";
        //必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        //第一个参数是Application Context
        //这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            YWAPI.init(this, APP_KEY);
        }

        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ConversationListUICustom.class);
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingUICustom.class);
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_OPERATION_POINTCUT, IMConversationListOperationCustom.class);
    }
}
