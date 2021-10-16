package com.gao.blueblue.ui.activity.base;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.gao.blueblue.R;

public class BaseActivity extends AppCompatActivity {

     /**
      * 上下文
      */
     public Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }
}