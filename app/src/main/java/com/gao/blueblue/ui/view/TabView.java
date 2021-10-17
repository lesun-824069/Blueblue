package com.gao.blueblue.ui.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @program: Blueblue
 * @description: 主界面底部菜单封装
 * @author: wuzewen
 * @create: 2021-10-16 15:34
 **/
public class TabView {

    private int iconIn, iconOn;

    private int textColorIn, textColorOn;

    private ImageView iconImageview;

    private TextView nameTextView;
    private LinearLayout llRoot;

    public void setIcon(int iconIn, int iconOn) {
        this.iconIn = iconIn;
        this.iconOn = iconOn;
    }

    public void setTextColor(int textColorIn, int textColorOn) {
        this.textColorIn = textColorIn;
        this.textColorOn = textColorOn;
    }

    public void setView(ImageView iconImageview, TextView nameTextView) {
        this.iconImageview = iconImageview;
        this.nameTextView = nameTextView;
    }

    public void setView(ImageView iconImageview, TextView nameTextView, LinearLayout llParent) {
        this.iconImageview = iconImageview;
        this.llRoot=llParent;
        this.nameTextView = nameTextView;
    }

    public void clickOn() {
        this.iconImageview.setBackgroundResource(iconOn);
        this.nameTextView.setTextColor(textColorOn);
    }

    public void clickIn() {
        this.iconImageview.setBackgroundResource(iconIn);
        this.nameTextView.setTextColor(textColorIn);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.llRoot.setOnClickListener(listener);
    }


}
