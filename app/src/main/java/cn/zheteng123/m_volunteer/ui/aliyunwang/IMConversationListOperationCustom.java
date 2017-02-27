package cn.zheteng123.m_volunteer.ui.aliyunwang;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListOperation;
import com.alibaba.mobileim.conversation.YWConversation;

import cn.zheteng123.m_volunteer.R;

/**
 * Created on 2017/2/27.
 */


public class IMConversationListOperationCustom extends IMConversationListOperation {
    public IMConversationListOperationCustom(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 返回自定义会话和群会话的默认头像 如返回本地的 R.drawable.test
     * @param fragment      会话页面fragment
     * @param conversation  会话对象
     * @return 头像资源id
     */
    @Override
    public int getConversationDefaultHead(Fragment fragment, YWConversation conversation) {
        return R.mipmap.ic_launcher;
    }
}
