package com.gao.blueblue.ui.activity.base;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import com.gao.blueblue.utils.ActivityManagerUtil;
public class BaseActivity extends AppCompatActivity {

     /**
      * 上下文
      */
     public Activity mContext;
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityManagerUtil.getActivityManager().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: 调用者：" + this.getClass().getSimpleName() + "内容信息：" + intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " + this.getClass().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " + this.getClass().toString() + "销毁");
        ActivityManagerUtil.getActivityManager().finishActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " + this.getClass().toString() + " 调用者：" + this.getClass().getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " + this.getClass().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " + this.getClass().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " + this.getClass().toString());
    }
}