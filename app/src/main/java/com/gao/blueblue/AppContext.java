package com.gao.blueblue;

import android.app.Application;

import cn.leancloud.LeanCloud;

/**
 * @program: Blueblue
 * @description:
 * @author: wuzewen
 * @create: 2021-10-15 23:51
 **/
public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LeanCloud.initialize(
                this,
                "ImIPa8yY06Mjs8WpsdllXXKk-gzGzoHsz",
                "fNmf8JB8cPQIkwmfu2VyYaUQ",
                "https://imipa8yy.lc-cn-n1-shared.com");

    }
}
