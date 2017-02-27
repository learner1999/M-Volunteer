package cn.zheteng123.m_volunteer.ui.aliyunwang;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMChattingPageUI;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationType;

/**
 * Created on 2017/2/27.
 */


public class ChattingUICustom extends IMChattingPageUI {
    public ChattingUICustom(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 聊天窗口消息item的头像上侧是否需要显示消息发送者昵称
     * @param conversation 当前聊天窗口所在会话
     * @param self         是否是自己发送的消息
     * @return true：显示昵称  false：不显示昵称
     */
    @Override
    public boolean needShowName(YWConversation conversation, boolean self) {
        if (self){  //自己发送的消息，即右侧的消息
            return false;
        } else {
            YWConversationType type = conversation.getConversationType();
            if (type == YWConversationType.SHOP || type == YWConversationType.Tribe){
                return true;
            } else {
                return false;
            }
        }
    }
}
