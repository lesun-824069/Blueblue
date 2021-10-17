package com.gao.blueblue.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * @program: Blueblue
 * @description:
 * @author: wuzewen
 * @create: 2021-10-16 15:55
 **/
public class FragmentTabAdapter {
    // 一个tab页面对应一个Fragment
    private List<Fragment> fragments;
    // Fragment所属的Activity
    private FragmentActivity fragmentActivity;
    // Activity中所要被替换的区域的id
    private int fragmentContentId;
    // 当前Tab页面索引
    private int currentTab;
    public FragmentTabAdapter(FragmentActivity fragmentActivity, List<Fragment> fragments,
                              int fragmentContentId) {
        //参数赋值
        this.fragments = fragments;//所有的fragment
        this.fragmentActivity = fragmentActivity;//上下文传入
        this.fragmentContentId = fragmentContentId;//fragementID
    }
    /**
     * 初始化界面
     */
    public void init(){
        // 默认显示发布页面
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(2));
        ft.commit();
    }



    /**
     *
     * @param index 索引
     */
    public void init(int index){
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if(index<0||index>=fragments.size()){//越界则显示0
            ft.add(fragmentContentId, fragments.get(0));
        }else{
            ft.add(fragmentContentId, fragments.get(index));
        }
        ft.commit();
    }
    private int currentIndex = -1;
    public void checkedIndex(int checkedIndex) {
        //索引没有发生改变
        if(currentIndex == checkedIndex){
            return;
        }
        int count = (null != fragments ? fragments.size() : 0);
        //标记当前选中索引
        currentIndex = checkedIndex;
        for (int i=0; i<count; i++) {
            if (currentIndex == i) {
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);
                getCurrentFragment().onPause(); // 暂停当前tab
                // getCurrentFragment().onStop(); // 暂停当前tab
                if (fragment.isAdded()) {//如果可以添加
                    // fragment.onStart(); // 启动目标tab的onStart()
                    fragment.onResume(); // 启动目标tab的onResume()
                } else {
                    ft.add(fragmentContentId, fragment);
                }
                //刷新通讯录数据
                if (i == 3){
//                    ((ContactAndTeamContainerFragment) fragment).reloadData(2);
                }
                if (i == 0){
                    //刷新审批数据
                    //((ApprovalFragment) fragment).refreshCurrentPageData();
//                    ((ApprovalFragment) fragment).checkRedDotState();
                }
                // 显示目标tab
                showTab(i);
                ft.commitAllowingStateLoss();
            }
        }
    }
    /**
     * 切换tab
     * @param idx
     */
    public void showTab(int idx) {
        for (int i = 0;i < fragments.size();i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);
            if (idx == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
        currentTab = idx; // 更新目标tab为当前tab
    }

    /**
     * 展示指定索引的tab
     * @param index
     */
    public void show(int index){
        int count = (null != fragments ? fragments.size() : 0);
        //标记当前选中索引
        for (int i=0; i<count; i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(i);
            if (index == i) {

                ft.add(fragmentContentId, fragment);
            }else {
                ft.remove(fragment);
            }
        }
    }


    /**
     * 获取一个带动画的FragmentTransaction
     * @param index
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        // 设置切换动画
        // if(index > currentTab){
        // ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        // }else{
        // ft.setCustomAnimations(R.anim.slide_right_in,
        // R.anim.slide_right_out);
        // }
        return ft;
    }
    public int getCurrentTab() {
        return currentTab;
    }
    public Fragment getCurrentFragment() {
        return fragments.get(currentTab);
    }

    private static final String TAG = "FragmentTabAdapter";
    public void setUnClick(){
        Fragment temp=fragments.get(0);
        temp.setUserVisibleHint(false);
        Log.e(TAG, "setUnClick: 设置不可点击fragment" );
    }
    public Fragment getFragment(int index) {
        return fragments.get(index);
    }
    public <T extends Fragment> T getDetailFragment(int index) {
        return (T) fragments.get(index);
    }
}
