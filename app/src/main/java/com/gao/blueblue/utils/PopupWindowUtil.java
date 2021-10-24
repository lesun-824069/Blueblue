package com.gao.blueblue.utils;

import android.view.View;
import android.widget.PopupWindow;

import com.gao.blueblue.provider.UserProvider;

/**
 * @program: Blueblue
 * @description:
 * @author: wuzewen
 * @create: 2021-10-23 20:29
 **/
public class PopupWindowUtil {

    private static PopupWindowUtil popupWindowUtil;
    private PopupWindow popupWindow;
    private View popuView;

    public synchronized static PopupWindowUtil getInstance() {
        if (null == popupWindowUtil) {
            popupWindowUtil = new PopupWindowUtil();
        }
        return popupWindowUtil;
    }


}
