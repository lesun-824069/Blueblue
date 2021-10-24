package com.gao.blueblue.ui.activity.start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.gao.blueblue.MainActivity;
import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.ui.activity.login.LoginActivity;
import com.gao.blueblue.ui.activity.login.LoginBySMSActivity;
import com.gao.blueblue.utils.PreferenceUtil;
import com.gao.blueblue.utils.ToastUtil;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class StartActivity extends BaseActivity {

    /**
     * 开始
     */
    private RelativeLayout rl_start;
    private static final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        autoLogin();

    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        boolean autoLogin = PreferenceUtil.getBoolean(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.AUTO_LOGIN);
        if (autoLogin){
            String session_token = PreferenceUtil.getString(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.SESSION_TOKEN);
            LCUser.becomeWithSessionTokenInBackground(session_token).subscribe(new Observer<LCUser>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.e(TAG, "onSubscribe: " );
                }

                @Override
                public void onNext(@NonNull LCUser lcUser) {
                    Log.e(TAG, "onNext: " + lcUser);
                    LCUser.changeCurrentUser(lcUser, true);
                    startActivity(new Intent(mContext, MainActivity.class));
                    ToastUtil.showSuccessedToast(R.string.auto_login_success);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.e(TAG, "onError: " );

                }

                @Override
                public void onComplete() {
                    Log.e(TAG, "onComplete: " );
                }
            });
        }else {
            startActivity(new Intent(mContext, LoginBySMSActivity.class));
            Log.e(TAG, "autoLogin: LoginBySMSActivity" );
        }
    }
}