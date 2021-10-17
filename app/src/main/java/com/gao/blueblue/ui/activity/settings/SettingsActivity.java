package com.gao.blueblue.ui.activity.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.ui.activity.login.LoginActivity;
import com.gao.blueblue.utils.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.LCUser;

public class SettingsActivity extends BaseActivity {

    private static final String TAG = "SettingsActivity";

    /**
     *
     */
    @BindView(R.id.tv_back)
    TextView tv_back;

    @BindView(R.id.rl_login_out)
    RelativeLayout rl_login_out;

    @OnClick({R.id.tv_back, R.id.rl_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                Log.e(TAG, "onViewClicked: 返回");
                finish();
                break;
            case R.id.rl_login_out:
                Log.e(TAG, "onViewClicked: 退出登录");
                logout();
        }
    }

     /**
      * 退出登录
      */
     private void logout() {
        LCUser.logOut();
        PreferenceUtil.putBoolean(PreferenceUtil.MapName.LOGIN, PreferenceUtil.PreKey.AUTO_LOGIN, false);
        startActivity(new Intent(mContext, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }
}