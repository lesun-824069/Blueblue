package com.gao.blueblue;

import android.app.Application;
import android.content.Context;

import com.gao.blueblue.provider.UserProvider;
import com.gao.blueblue.utils.PreferenceUtil;
import com.google.gson.Gson;

import cn.leancloud.LeanCloud;
import cn.leancloud.chatkit.LCChatKit;

/**
 * @program: Blueblue
 * @description:
 * @author: wuzewen
 * @create: 2021-10-15 23:51
 **/
public class AppContext extends Application {

    private final String APP_ID = "ImIPa8yY06Mjs8WpsdllXXKk-gzGzoHsz";
    private final String APP_KEY = "fNmf8JB8cPQIkwmfu2VyYaUQ";
    private final String SERVER_URL = "https://imipa8yy.lc-cn-n1-shared.com";

    public static AppContext appContext;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        LeanCloud.initialize(this, APP_ID, APP_KEY, SERVER_URL);
        LCChatKit.getInstance().setProfileProvider(UserProvider.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY,SERVER_URL);

    }

    public static Context getContext() {
        return mContext;
    }

    public static AppContext getInstance() {
        return appContext;
    }

}
