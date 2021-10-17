package com.gao.blueblue.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * “我的”页面，使用ButterKnife进行控件绑定，减少代码量
 */
public class MineFragment extends BaseFragment {

    private static final String TAG = "MineFragment";

    /**
     *用户头像
     */
    @BindView(R.id.img_head)
    ImageView iv_head;

    /**
     *用户名
     */
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    /**
     *实名认证
     */
    @BindView(R.id.tv_authenticate_status)
    TextView tv_Authenticate_status;

     /**
      *设置
      */
     @BindView(R.id.iv_setting)
     ImageView iv_settings;


     @OnClick({R.id.iv_setting})
     public void onViewClicked(View view){
         switch (view.getId()){
             case R.id.iv_setting:
                 Log.e(TAG, "onViewClicked: 设置" );
                 startActivity(new Intent(mContext, SettingsActivity.class));
                 break;
         }
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
}