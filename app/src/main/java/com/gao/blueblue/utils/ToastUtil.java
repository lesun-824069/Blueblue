package com.gao.blueblue.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gao.blueblue.AppContext;
import com.gao.blueblue.R;

/**
 * @program: Blueblue
 * @description: 通知工具类
 * @author: wuzewen
 * @create: 2021-10-17 00:50
 **/
public class ToastUtil {

    private static Context mContext = AppContext.getContext();

    private static void showToast(String content, int backGroundColor, int iconId) {
        mContext = AppContext.getContext();

        int statusBarHeight1 = -1;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        final View view = View.inflate(mContext, R.layout.blueblue_toast, null);//选择一个xml文件充斥view
        view.findViewById(R.id.ll_blueblue_toast).setBackgroundColor(mContext.getResources().getColor(backGroundColor));

        if (iconId != 0) {
            ImageView ivIcon = view.findViewById(R.id.iv_blueblue_toast_icon);
            ivIcon.setBackgroundResource(iconId);
        }

        TextView tvContent = view.findViewById(R.id.tv_blueblue_toast_content);
        tvContent.setText(content);
        Toast toast = new Toast(mContext);
        toast.setView(view);
        toast.setDuration(3 * 1000);
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
        toast.show();
    }

    public static void showSuccessedToast(String content) {
        showToast(content, R.color.toast_successed, R.mipmap.icon_top_popu_success);
    }

    public static void showSuccessedToast(int id) {
        showToast(mContext.getString(id), R.color.toast_successed, R.mipmap.icon_top_popu_success);
    }

    public static void showCAWarningToast(String id) {
        showToast(id, R.color.warning_msg, 0);
    }


    public static void showErrorToast(String content) {
        showToast(content, R.color.toast_faild, R.mipmap.icon_top_popu_erro);
    }

    public static void showErrorToast(int id) {
        showToast(mContext.getString(id), R.color.toast_faild, R.mipmap.icon_top_popu_erro);
    }

}
