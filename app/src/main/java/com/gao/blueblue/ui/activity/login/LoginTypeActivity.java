package com.gao.blueblue.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;


public class LoginTypeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginTypeActivity";

    /**
     * 短信验证码登录
     */
    private RelativeLayout rl_sms_login;

    /**
     * 账号密码登录
     */
    private RelativeLayout rl_password_login;

    /**
     * 注册账号
     */
    private RelativeLayout rl_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        rl_sms_login = findViewById(R.id.rl_sms_login_root);
        rl_sms_login.setOnClickListener(this);

        rl_password_login = findViewById(R.id.rl_user_login_root);
        rl_password_login.setOnClickListener(this);

        rl_register = findViewById(R.id.rl_register_root);
        rl_register.setOnClickListener(this);

    }

    /**
     * 点击事件监听，跳转到新的界面
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_sms_login_root:
                Log.e(TAG, "onClick: 短信验证码登录");
                startActivity(new Intent(mContext, LoginBySMSActivity.class));
                break;
            case R.id.rl_user_login_root:
                Log.e(TAG, "onClick: 账号密码登录");
                startActivity(new Intent(mContext, LoginByPWActivity.class));
                break;
            case R.id.rl_register_root:
                Log.e(TAG, "onClick: 注册账号");
                break;

        }

    }
}