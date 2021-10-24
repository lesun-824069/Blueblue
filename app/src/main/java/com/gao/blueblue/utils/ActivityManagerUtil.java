package com.gao.blueblue.utils;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

/**
 * @program: Blueblue
 * @description: activity管理类
 * @author: wuzewen
 * @create: 2021-10-24 22:17
 **/
public class ActivityManagerUtil {

    private static Stack<Activity> activities;
    private static ActivityManagerUtil instance;

    public static ActivityManagerUtil getActivityManager() {
        if (instance == null) {
            instance = new ActivityManagerUtil();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activities == null) {
            activities = new Stack<>();
        }
        activities.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activities.lastElement();
        return activity;
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    private static final String TAG = "ActivityManagerUtil";

    public void showAllActivity() {
        String allLive = "当前所有存活的：";
        for (Activity activity : activities) {
            allLive += activity.toString() + "    ";
        }
        Log.e(TAG, "showAllActivity: " + allLive);
    }

    public boolean isAlive(Class<?> cls) {
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (null != activities.get(i)) {
                activities.get(i).finish();
            }
        }
        activities.clear();
    }


    public boolean removeActivity(Activity writeActivity) {
        for (Activity activity : activities) {
            if (activity.getClass().toString().equals(writeActivity.getClass().toString())) {
                Log.e(TAG, "removeActivity: 删除成功 要删除的activity:" + writeActivity);
                activities.remove(activity);
                return true;
            }
        }
        Log.e(TAG, "removeActivity: 删除失败 要删除的activity:" + writeActivity);
        return false;
    }
}
