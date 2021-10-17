package com.gao.blueblue.ui.activity.start;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.gao.blueblue.MainActivity;
import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.ui.activity.login.LoginActivity;
import com.gao.blueblue.utils.PreferenceUtil;

import cn.leancloud.LCUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class StartActivity extends BaseActivity {

    /**
     * 开始
     */
    private RelativeLayout rl_start;

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

                }

                @Override
                public void onNext(@NonNull LCUser lcUser) {
                    startActivity(new Intent(mContext, MainActivity.class));
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }else {
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }
}