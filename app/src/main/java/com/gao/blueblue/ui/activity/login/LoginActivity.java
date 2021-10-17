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
import android.widget.Toast;

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
import cn.leancloud.sms.LCSMS;
import cn.leancloud.sms.LCSMSOption;
import cn.leancloud.types.LCNull;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 登录和注册界面
 */
public class LoginActivity extends BaseActivity {

    public static final int LOGIN_BY_PASSWORD = 1;
    public static final int LOGIN_BY_SMS = 2;
    public static final int REGISTER_BY_PASSWORD = 3;
    public static final int REGISTER_BY_SMS = 4;
    private static final String TAG = "LoginActivity";

    /**
     * 根据传入的参数type的值判断当前页面处理什么业务
     * type == 1:账号密码登录
     * type == 2:短信验证码登录
     * type == 3:账号密码注册
     * type == 4:手机号码注册
     */
    private int type;

    /**
     * 清空当前输入
     */
    @BindView(R.id.iv_clean)
    ImageView iv_clean_user_name;

    /**
     * 用户名输入框
     */
    @BindView(R.id.et_input_user_name)
    EditText et_user_name_input;

    /**
     * 短信验证码输入栏，使用账号密码登录时隐藏
     */
    @BindView(R.id.rl_sms_root)
    RelativeLayout rl_sms_verify;

    /**
     * 短信验证码输入框
     */
    @BindView(R.id.et_input_sms_verification_code)
    EditText et_sms_input;

    /**
     * 获取短信验证码
     */
    @BindView(R.id.tv_get_sms_verification_code)
    TextView tv_get_sms;

    /**
     * 密码输入栏，使用短信验证码登录时隐藏
     */
    @BindView(R.id.rl_pw_root)
    RelativeLayout rl_pw_verify;

    /**
     * 密码输入框
     */
    @BindView(R.id.et_input_password)
    EditText et_pw_input;

    /**
     * 设置密码是否可见
     */
    @BindView(R.id.iv_pw_visibility)
    ImageView iv_pw_visibility;

    /**
     * 登录或注册按钮
     */
    @BindView(R.id.rl_login)
    RelativeLayout rl_login_or_register;

    /**
     * 登录，注册时将文本内容改为"注册"
     */
    @BindView(R.id.tv_login)
    TextView tv_login_or_register;

    /**
     * 忘记密码，短信验证码登录时修改文本为"获取不到验证码"
     */
    @BindView(R.id.tv_forget_pw)
    TextView tv_forget_password;

    /**
     * 切换为注册界面
     */
    @BindView(R.id.tv_register)
    TextView tv_register;

    /**
     * 切换到短信验证码登录或注册
     */
    @BindView(R.id.tv_change_type)
    TextView tv_change_type;

    /**
     * 微信登录
     */
    @BindView(R.id.iv_wechat_login)
    ImageView iv_wechat_login;

    /**
     * QQ登录
     */
    @BindView(R.id.iv_qq_login)
    ImageView iv_qq_login;

    /**
     * 微薄登录
     */
    @BindView(R.id.iv_sina_login)
    ImageView iv_sina_login;

    /**
     * 第三方登录，注册时隐藏
     */
    @BindView(R.id.rl_other_type_login)
    RelativeLayout rl_other_type_login;

    /**
     * 忘记密码、注册账号，注册时隐藏
     */
    @BindView(R.id.rl_help)
    RelativeLayout rl_help;

    /**
     * 账号格式是否正确
     */
    private boolean is_user_name_ok = false;

    /**
     * 密码格式是否正确
     */
    private boolean is_pass_word_ok = false;

    /**
     * 短信验证码格式是否正确
     */
    private boolean is_smm_code_ok = false;

    /**
     * 获取输入框内容
     */
    private String user_name;
    private String pass_word;
    private String sms_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();
        setLOginButtonClickAble();
        //获取从其它页面传入的type,根据type设置UI
        type = getIntent().getIntExtra("type", LOGIN_BY_PASSWORD);
        updateUI(type);

    }

    /**
     * 初始化
     */
    private void init() {

        et_user_name_input.addTextChangedListener(new TextWatcher() {
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
                is_user_name_ok = !is_empty && !is_emoji;
                iv_clean_user_name.setVisibility(StringUtil.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
                setLOginButtonClickAble();
            }
        });

        et_pw_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean is_empty = StringUtil.isEmpty(s.toString().trim());
                is_pass_word_ok = !is_empty && s.toString().length() >= 6;
                setLOginButtonClickAble();
            }
        });

        et_sms_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean is_empty = StringUtil.isEmpty(s.toString().trim());
                is_smm_code_ok = !is_empty;
                setLOginButtonClickAble();
            }
        });
    }

    /**
     * 除非账号和密码输入格式都正确，否则登录按钮不可点击
     */
    private void setLOginButtonClickAble() {
        user_name = et_user_name_input.getText().toString().trim();
        pass_word = et_pw_input.getText().toString().trim();
        sms_code = et_sms_input.getText().toString().trim();
        switch (type) {
            case LOGIN_BY_PASSWORD:
            case REGISTER_BY_PASSWORD:
                if (is_user_name_ok && is_pass_word_ok) {
                    rl_login_or_register.setAlpha(1.0f);
                    rl_login_or_register.setClickable(true);
                } else {
                    rl_login_or_register.setAlpha(0.3f);
                    rl_login_or_register.setClickable(false);
                }
                break;
            case LOGIN_BY_SMS:
            case REGISTER_BY_SMS:
                if (is_user_name_ok && is_smm_code_ok) {
                    rl_login_or_register.setAlpha(1.0f);
                    rl_login_or_register.setClickable(true);
                } else {
                    rl_login_or_register.setAlpha(0.3f);
                    rl_login_or_register.setClickable(false);
                }
                break;
        }
    }

    /**
     * 处理不同业务时显示对应视图
     */
    private void updateUI(int view_type) {
        type = view_type;
        switch (view_type) {
            case LOGIN_BY_PASSWORD://显示为账号密码登录的视图
                rl_sms_verify.setVisibility(View.GONE);
                rl_pw_verify.setVisibility(View.VISIBLE);
                tv_change_type.setText(R.string.try_sms_login);
                rl_other_type_login.setVisibility(View.VISIBLE);
                tv_login_or_register.setText(R.string.login);
                rl_help.setVisibility(View.VISIBLE);
                tv_forget_password.setText(R.string.forget_password);
                break;
            case LOGIN_BY_SMS://显示为短信验证码登录的视图
                rl_sms_verify.setVisibility(View.VISIBLE);
                rl_pw_verify.setVisibility(View.GONE);
                tv_change_type.setText(R.string.try_pw_login);
                rl_other_type_login.setVisibility(View.VISIBLE);
                tv_login_or_register.setText(R.string.login);
                rl_help.setVisibility(View.VISIBLE);
                tv_forget_password.setText(R.string.cant_get_vercode);
                break;
            case REGISTER_BY_PASSWORD://显示为账号密码注册的视图
                rl_sms_verify.setVisibility(View.GONE);
                rl_pw_verify.setVisibility(View.VISIBLE);
                tv_change_type.setText(R.string.try_sms_register);
                tv_login_or_register.setText(R.string.register);
                rl_other_type_login.setVisibility(View.GONE);
                rl_help.setVisibility(View.GONE);
                break;
            case REGISTER_BY_SMS://显示为短信验证码注册的视图
                rl_sms_verify.setVisibility(View.VISIBLE);
                rl_pw_verify.setVisibility(View.GONE);
                tv_change_type.setText(R.string.try_pw_register);
                tv_login_or_register.setText(R.string.register);
                rl_other_type_login.setVisibility(View.GONE);
                rl_help.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 绑定点击事件
     */
    @OnClick({R.id.iv_clean,
            R.id.tv_get_sms_verification_code,
            R.id.rl_login,
            R.id.tv_forget_pw,
            R.id.tv_register,
            R.id.tv_change_type,
            R.id.iv_wechat_login,
            R.id.iv_qq_login,
            R.id.iv_sina_login})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_clean:
                et_user_name_input.setText("");
                break;
            case R.id.tv_get_sms_verification_code:
                Log.e(TAG, "onViewClick: 获取验证码" );
                getSmsVerifyCode();
                break;
            case R.id.rl_login:
                onLoginClicked();
                break;
            case R.id.tv_forget_pw:
                onForgetPassword();
                break;
            case R.id.tv_register:
                onJumpRegister();
                break;
            case R.id.tv_change_type:
                onChangeType();
                break;
            case R.id.iv_wechat_login:
                onWechatLogin();
                break;
            case R.id.iv_qq_login:
                onQQLogin();
                break;
            case R.id.iv_sina_login:
                onSinaLogin();
                break;
        }
    }


    /**
     * 切换登录或注册方式，短信登录切换为密码登录，短信注册切换为密码注册
     */
    private void onChangeType() {
        if (type == LOGIN_BY_PASSWORD) {
            updateUI(LOGIN_BY_SMS);
        } else if (type == LOGIN_BY_SMS) {
            updateUI(LOGIN_BY_PASSWORD);
        } else if (type == REGISTER_BY_PASSWORD) {
            updateUI(REGISTER_BY_SMS);
        } else {
            updateUI(REGISTER_BY_PASSWORD);
        }
    }

    /**
     * 注册账号点击事件处理，若当前为账号密码登录界面，切换为注册账号密码
     * 若当前为短信验证码登录，切换为手机号注册
     */
    private void onJumpRegister() {
        if (type == LOGIN_BY_PASSWORD) {
            updateUI(REGISTER_BY_PASSWORD);
        } else {
            updateUI(REGISTER_BY_SMS);
        }
    }

    /**
     * 忘记密码点击事件处理，根据type的值判断当前处理的是什么业务，执行对应逻辑
     */
    private void onForgetPassword() {

    }

    /**
     * 获取短信验证码
     */
    private void getSmsVerifyCode() {
        if (type == REGISTER_BY_SMS){
            LCSMSOption option = new LCSMSOption();
            option.setSignatureName("sign_name"); //设置短信签名名称
            LCSMS.requestSMSCodeInBackground("+86"+user_name, option).subscribe(new Observer<LCNull>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                }
                @Override
                public void onNext(LCNull avNull) {
                    Log.d("TAG","Result: succeed to request SMSCode.");
                    ToastUtil.showSuccessedToast(R.string.sms_send_success);
                }
                @Override
                public void onError(Throwable throwable) {
                    Log.d("TAG","Result: failed to request SMSCode. cause:" + throwable.getMessage());
                }
                @Override
                public void onComplete() {
                }
            });
        }else {
            LCUser.requestLoginSmsCodeInBackground("+86"+user_name).subscribe(new Observer<LCNull>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }
                @Override
                public void onNext(@NonNull LCNull lcNull) {
                    ToastUtil.showSuccessedToast(R.string.sms_send_success);
                }
                @Override
                public void onError(@NonNull Throwable e) {
                }
                @Override
                public void onComplete() {

                }
            });
        }
    }

    /**
     * 登录按钮点击事件处理，根据type的值判断当前处理的是什么业务，执行对应逻辑
     */
    private void onLoginClicked() {
        switch (type) {
            case LOGIN_BY_PASSWORD:
                onPasswordLogin();
                break;
            case LOGIN_BY_SMS:
                onSMSLogin();
                break;
            case REGISTER_BY_PASSWORD:
                onPasswordRegister();
                break;
            case REGISTER_BY_SMS:
                onSMSRegister();
                break;

        }
    }

    /**
     * 手机号注册
     */
    private void onSMSRegister() {
        LCUser.signUpOrLoginByMobilePhoneInBackground("+86"+user_name, sms_code).subscribe(new Observer<LCUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(LCUser user) {
                // 注册成功
                Log.e(TAG, "onNext: 注册成功" );
                ToastUtil.showSuccessedToast(R.string.register_success);
                updateUI(LOGIN_BY_SMS);
            }
            public void onError(Throwable throwable) {
                // 验证码不正确
            }
            public void onComplete() {}
        });

    }

    /**
     * 密码注册
     */
    private void onPasswordRegister() {

        LCUser user = new LCUser();
        user.setUsername(user_name);
        user.setPassword(pass_word);
        user.signUpInBackground().subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull LCUser lcUser) {
                //注册成功
                Log.e(TAG, "onNext: 注册成功" );
                ToastUtil.showSuccessedToast(R.string.register_success);
                updateUI(LOGIN_BY_PASSWORD);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: 注册失败" );
                ToastUtil.showErrorToast(R.string.register_failed);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 短信验证码登录
     */
    private void onSMSLogin() {

        LCUser.signUpOrLoginByMobilePhoneInBackground("+86"+user_name, sms_code).subscribe(new Observer<LCUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(LCUser user) {
                // 登录成功
                onLoginSuccess();
            }
            public void onError(Throwable throwable) {
                // 验证码不正确
                ToastUtil.showErrorToast(R.string.error_sms_code);
            }
            public void onComplete() {}
        });
    }

    /**
     * 账号密码登录
     */
    private void onPasswordLogin() {
        LCUser.logIn(user_name,pass_word).subscribe(new Observer<LCUser>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull LCUser lcUser) {
                //登录成功
                Log.e(TAG, "onNext: 登录成功" );
                onLoginSuccess();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onNext: 登录失败" );
                ToastUtil.showErrorToast(R.string.login_failed);
            }

            @Override
            public void onComplete() {
            }
        });
    }



    /**
     * 使用微薄账号登录
     */
    private void onSinaLogin() {
    }

    /**
     * 使用QQ账号登录
     */
    private void onQQLogin() {
    }

    /**
     * 使用微信账号登录
     */
    private void onWechatLogin() {
    }

     /**
      * 登录成功后的处理
      */
     private void onLoginSuccess() {
        ToastUtil.showSuccessedToast(R.string.login_success);
        startActivity(new Intent(mContext, MainActivity.class));
        LCUser user = LCUser.getCurrentUser();
        String token = user.getSessionToken();
        PreferenceUtil.putString(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.SESSION_TOKEN, token);
        PreferenceUtil.putBoolean(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.AUTO_LOGIN, true);
    }

}