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

public class LoginBySMSActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "LoginBySMSActivity";

    /**
     * 返回到选择登录方式界面
     */
    private TextView tv_back;

    /**
     * 图形验证码
     */
    private ImageView iv_photo_verification_code;

    /**
     * 获取短信验证码
     */
    private TextView tv_get_sms_verification_code;

    /**
     * 获取不到短信验证码
     */
    private TextView tv_get_sms_verification_code_help;

     /**
      * 手机号输入
      */
     private EditText et_phone_input;

      /**
       * 清空手机号
       */
      private ImageView iv_clean;

    /**
     * 图形验证码输入
     */
    private EditText et_photo_verificaotion_code_input;

     /**
      * 短信验证码输入
      */
     private EditText et_sms_verification_code_input;


      /**
       * 登录
       */
      private RelativeLayout rl_login;

       /**
        * 切换到账号密码登录
        */
       private RelativeLayout rl_try_pw_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_sms);

        bindView();

    }

     /**
      *绑定控件
      */
     private void bindView() {

         tv_back = findViewById(R.id.tv_back);
         tv_back.setOnClickListener(this);

         iv_photo_verification_code = findViewById(R.id.iv_verification_code);
         iv_photo_verification_code.setOnClickListener(this);

         tv_get_sms_verification_code = findViewById(R.id.btn_get_sms_verification_code);
         tv_get_sms_verification_code.setOnClickListener(this);

         tv_get_sms_verification_code_help = findViewById(R.id.tv_get_sms_help);
         tv_get_sms_verification_code_help.setOnClickListener(this);

         iv_clean = findViewById(R.id.iv_clean);
         iv_clean.setOnClickListener(this);

         rl_login = findViewById(R.id.rl_login);
         rl_login.setOnClickListener(this);

         rl_try_pw_login = findViewById(R.id.rl_try_pw);
         rl_try_pw_login.setOnClickListener(this);

         et_phone_input = findViewById(R.id.et_input_phone_number);
         et_phone_input.addTextChangedListener(new TextWatcher() {
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

         et_photo_verificaotion_code_input = findViewById(R.id.et_input_verification_code);
         et_photo_verificaotion_code_input.addTextChangedListener(new TextWatcher() {
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

         et_sms_verification_code_input = findViewById(R.id.et_input_sms_verification_code);
         et_sms_verification_code_input.addTextChangedListener(new TextWatcher() {
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
            case R.id.iv_verification_code:
                Log.e(TAG, "onClick: 刷新图形验证码" );
                break;
            case R.id.btn_get_sms_verification_code:
                Log.e(TAG, "onClick: 获取短信验证码" );
                break;
            case R.id.tv_get_sms_help:
                Log.e(TAG, "onClick: 获取不到短信验证码" );
                break;
            case R.id.iv_clean:
                Log.e(TAG, "onClick: 清空手机号" );
                break;
            case R.id.rl_login:
                Log.e(TAG, "onClick: 登录" );
                break;
            case R.id.rl_try_pw:
                Log.e(TAG, "onClick: 切换到密码登录" );
                startActivity(new Intent(mContext, LoginByPWActivity.class));
                break;
        }

    }
}