package cn.zheteng123.m_volunteer.util;

import android.app.Activity;

/**
 * Created on 2017/2/13.
 */


public class WindowAttr {

    public static int getStatusBarHeight(Activity a) {
        int result = 0;

        int resourceId = a.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = a.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
