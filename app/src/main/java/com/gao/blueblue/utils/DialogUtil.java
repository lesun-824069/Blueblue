package com.gao.blueblue.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gao.blueblue.AppContext;
import com.gao.blueblue.R;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

/**
 * @program: Blueblue
 * @description: 对话框工具类
 * @author: wuzewen
 * @create: 2021-10-17 00:40
 **/
public class DialogUtil {

    private static Context mContext = AppContext.getContext();
    private static NiceDialog dialog;

    public synchronized static NiceDialog getInstance() {
        if (null == dialog) {
            dialog = new NiceDialog();
        }
        return dialog;
    }

    public DialogUtil() {
    }


}
