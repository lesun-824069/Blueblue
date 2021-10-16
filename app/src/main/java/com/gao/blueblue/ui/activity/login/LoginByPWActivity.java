package com.gao.blueblue.ui.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;

public class LoginByPWActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LoginByPWActivity";

    /**
     * 返回到选择登录方式界面
     */
    private TextView tv_back;

    /**
     * 找回密码
     */
    private TextView tv_forget_password;

    /**
     * 密码是否可见
     */
    private ImageView iv_password_visibility;

    /**
     * 账号输入
     */
    private EditText et_user_name_input;

    /**
     * 密码输入
     */
    private EditText et_pass_word_input;


    /**
     * 登录
     */
    private RelativeLayout rl_login;

    /**
     * 切换到短信验证码登录
     */
    private RelativeLayout rl_try_sms_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_pw);


        bindView();

    }

     /**
      *绑定控件
      */
     private void bindView() {

         tv_back = findViewById(R.id.tv_back);
         tv_back.setOnClickListener(this);

         tv_forget_password = findViewById(R.id.tv_forget_password);
         tv_forget_password.setOnClickListener(this);

         iv_password_visibility = findViewById(R.id.iv_pw_visibility);
         iv_password_visibility.setOnClickListener(this);

         rl_login = findViewById(R.id.rl_login);
         rl_login.setOnClickListener(this);

         rl_try_sms_login = findViewById(R.id.rl_try_sms);
         rl_try_sms_login.setOnClickListener(this);

         et_user_name_input = findViewById(R.id.et_input_identification);
         et_user_name_input.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             }
             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
             }
             @Override
             public void afterTextChanged(Editable s) {

             }
         });

         et_pass_word_input = findViewById(R.id.et_input_password);
         et_pass_word_input.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             }
             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
             }
             @Override
             public void afterTextChanged(Editable s) {

             }
         });


    }

     /**
      * 点击事件监听
      */
     @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                Log.e(TAG, "onClick: 返回选择登录方式界面" );
                startActivity(new Intent(mContext, LoginTypeActivity.class));
                break;
            case R.id.tv_forget_password:
                Log.e(TAG, "onClick: 找回密码" );
                break;
            case R.id.iv_pw_visibility:
                Log.e(TAG, "onClick: 设置面是否可见" );
                break;
            case R.id.rl_login:
                Log.e(TAG, "onClick: 登录" );
                break;
            case R.id.rl_try_sms:
                Log.e(TAG, "onClick: 切换到短信验证码登录" );
                startActivity(new Intent(mContext, LoginBySMSActivity.class));
                break;
        }
    }
}