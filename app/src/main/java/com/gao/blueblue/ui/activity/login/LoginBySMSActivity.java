package com.gao.blueblue.ui.activity.login;

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

import com.gao.blueblue.MainActivity;
import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.utils.PreferenceUtil;
import com.gao.blueblue.utils.StringUtil;
import com.gao.blueblue.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.LCUser;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class LoginBySMSActivity extends BaseActivity {

    /**
     * 头像
     */
    @BindView(R.id.iv_head_image)
    CircleImageView iv_head_image;
    /**
     * 输入账号
     */
    @BindView(R.id.et_account_input)
    EditText et_account_input;
    /**
     * 清除账号
     */
    @BindView(R.id.iv_account_clear)
    ImageView iv_account_clear;
    /**
     * 输入密码
     */
    @BindView(R.id.et_password_input)
    EditText et_password_input;
    /**
     * 清除密码
     */
    @BindView(R.id.iv_password_clear)
    ImageView iv_password_clear;
    /**
     * 登录
     */
    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    /**
     * 手机号登录
     */
    @BindView(R.id.tv_login_by_phone)
    TextView tv_login_by_phone;
    /**
     * 找回密码
     */
    @BindView(R.id.tv_retrieve_password)
    TextView tv_retrieve_password;
    /**
     * 注册账号
     */
    @BindView(R.id.tv_register)
    TextView tv_register;
    /**
     * 账号格式是否正确
     */
    private boolean is_account_ok = false;
    /**
     * 密码格式是否正确
     */
    private boolean is_password_ok = false;
    /**
     * 获取输入框内容
     */
    private String account;
    private String password;

    private static final String TAG = "LoginBySMSActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_sms);
        ButterKnife.bind(this);
        init();
        setLOginButtonClickAble();
    }

    /**
     * 初始化
     */
    private void init() {

        et_account_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean is_empty = StringUtil.isEmpty(s.toString().trim());
                boolean is_emoji = StringUtil.isEmoji(s.toString().trim());
                is_account_ok = !is_empty && !is_emoji;
                iv_account_clear.setVisibility(StringUtil.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
                setLOginButtonClickAble();
            }
        });

        et_password_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean is_empty = StringUtil.isEmpty(s.toString().trim());
                is_password_ok = !is_empty && s.toString().length() >= 6;
                setLOginButtonClickAble();
            }
        });

    }

    /**
     * 初始化登录按钮的状态，如果账号或密码格式不正确，按钮不响应点击事件
     */
    private void setLOginButtonClickAble() {
        account = et_account_input.getText().toString().trim();
        password = et_password_input.getText().toString().trim();
        if (is_account_ok && is_password_ok) {
            rl_login.setBackgroundResource(R.drawable.shape_login_btn_enable);
            rl_login.setClickable(true);
        } else {
            rl_login.setBackgroundResource(R.drawable.shape_login_btn_dis_enable);
            rl_login.setClickable(false);
        }
    }

    /**
     * 登录
     */
    private void onLogin() {
        LCUser.logIn(account, password).subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull LCUser lcUser) {
                //登录成功
                Log.e(TAG, "onNext: 登录成功");
                ToastUtil.showSuccessedToast(R.string.login_success);
                startActivity(new Intent(mContext, MainActivity.class));
                LCUser currentUser = LCUser.getCurrentUser();
                String token = currentUser.getSessionToken();
                PreferenceUtil.putString(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.SESSION_TOKEN, token);
                PreferenceUtil.putBoolean(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.AUTO_LOGIN, true);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onNext: 登录失败");
                ToastUtil.showErrorToast(R.string.login_failed);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @OnClick({R.id.iv_account_clear, R.id.iv_password_clear, R.id.rl_login, R.id.tv_login_by_phone, R.id.tv_retrieve_password, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_account_clear:
                Log.e(TAG, "onViewClicked: 清除账号");
                et_account_input.setText("");
                break;
            case R.id.iv_password_clear:
                Log.e(TAG, "onViewClicked: 清除密码");
                et_password_input.setText("");
                break;
            case R.id.rl_login:
                Log.e(TAG, "onViewClicked: 登录");
                onLogin();
                break;
            case R.id.tv_login_by_phone:
                Log.e(TAG, "onViewClicked: 使用手机号登录");
                break;
            case R.id.tv_retrieve_password:
                Log.e(TAG, "onViewClicked: 找回密码");
                break;
            case R.id.tv_register:
                Log.e(TAG, "onViewClicked: 注册新用户");
                break;
        }

    }
}