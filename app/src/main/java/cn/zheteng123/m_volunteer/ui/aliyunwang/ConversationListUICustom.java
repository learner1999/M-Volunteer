package cn.zheteng123.m_volunteer.ui.aliyunwang;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;

import cn.zheteng123.m_volunteer.R;
import cn.zheteng123.m_volunteer.util.WindowAttr;

/**
 * Created on 2017/2/26.
 */


public class ConversationListUICustom extends IMConversationListUI {

    public ConversationListUICustom(Pointcut pointcut) {
        super(pointcut);
    }

    /**
     * 返回会话列表的自定义标题
     *
     * @param fragment
     * @param context
     * @param inflater
     * @return
     */
    @Override
    public View getCustomConversationListTitle(final Fragment fragment,
                                               final Context context, LayoutInflater inflater) {
        LinearLayout customView = (LinearLayout) inflater
                .inflate(R.layout.layout_title, new LinearLayout(context),false);

        View viewStatusBarFixer = customView.findViewById(R.id.v_status_bar_fix);
        // 设置状态栏高度
        viewStatusBarFixer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        WindowAttr.getStatusBarHeight(fragment.getActivity()))
        );

        return customView;
    }
}
