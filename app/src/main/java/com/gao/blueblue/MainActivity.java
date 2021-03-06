package com.gao.blueblue;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gao.blueblue.adapter.FragmentTabAdapter;
import com.gao.blueblue.provider.UserProvider;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.ui.fragment.AddressBookFragment;
import com.gao.blueblue.ui.fragment.MineFragment;
import com.gao.blueblue.ui.fragment.NewsFragment;
import com.gao.blueblue.ui.fragment.PublishFragment;
import com.gao.blueblue.ui.view.TabView;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.im.v2.LCIMChatRoom;
import cn.leancloud.im.v2.LCIMConversation;
import cn.leancloud.im.v2.LCIMException;
import cn.leancloud.im.v2.callback.LCIMConversationCreatedCallback;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 新闻
     */
    private TabView tab_news;

    /**
     * 会话
     */
    private TabView tab_chat;

    /**
     * 发布
     */
    private TabView tab_publish;

    /**
     * 市场
     */
    private TabView tab_markets;

    /**
     * 我的
     */
    private TabView tab_mine;

    /**
     * 当前页
     */
    private TabView current_tab;

    /**
     * 中间的显示内容
     */
    private FrameLayout fl_content;

    /**
     * 页面适配器，实现点击底部tab切换界面
     */
    public FragmentTabAdapter tabAdapter;

    /**
     * 所有子页面的集合
     */
    private List<Fragment> fragments;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViewSetOnClickListener();
        fragments = getFragments();
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content);
        tabAdapter.init();//显示我导入

    }


    private void bindViewSetOnClickListener() {

        fl_content = findViewById(R.id.tab_content);//中间Content


        // 会话
        LinearLayout ll_chat = findViewById(R.id.ll_tab_chat);
        ImageView img_chat = findViewById(R.id.img_tab_chat);
        TextView tv_chat = findViewById(R.id.tv_tab_chat);
        tab_chat = new TabView();
        tab_chat.setView(img_chat, tv_chat, ll_chat);
        tab_chat.setIcon(R.mipmap.icon_chat_normal, R.mipmap.icon_chat_select);
        tab_chat.setTextColor(Color.parseColor("#FFFFFFFF"),Color.parseColor("#d81e06"));
        tab_chat.setOnClickListener(this);

        //通讯录
        LinearLayout ll_news = findViewById(R.id.ll_tab_address_book);
        ImageView img_news = findViewById(R.id.img_tab_address_book);
        TextView tv_news = findViewById(R.id.tv_tab_address_book);
        tab_news = new TabView();
        tab_news.setView(img_news, tv_news, ll_news);
        tab_news.setIcon(R.mipmap.icon_address_book_normal, R.mipmap.icon_address_book_select);
        tab_news.setTextColor(Color.parseColor("#FFFFFFFF"),Color.parseColor("#d81e06"));
        tab_news.setOnClickListener(this);

        // 发布
        ImageView img_publish = findViewById(R.id.img_tab_publish);
        TextView tv_publish = findViewById(R.id.tv_tab_publish);
        LinearLayout ll_publish = findViewById(R.id.ll_tab_publish);
        tab_publish = new TabView();
        tab_publish.setView(img_publish, tv_publish, ll_publish);
        tab_publish.setIcon(R.mipmap.icon_publish_normal, R.mipmap.icon_publish_select);
        tab_publish.setTextColor(Color.parseColor("#FFFFFFFF"),Color.parseColor("#d81e06"));
        tab_publish.setOnClickListener(this);

        // 市场
        LinearLayout ll_markets = findViewById(R.id.ll_tab_markets);
        ImageView img_markets = findViewById(R.id.img_tab_markets);
        TextView tv_markets = findViewById(R.id.tv_tab_markets);
        tab_markets = new TabView();
        tab_markets.setView(img_markets, tv_markets, ll_markets);
        tab_markets.setIcon(R.mipmap.icon_markets_normal, R.mipmap.icon_markets_select);
        tab_markets.setTextColor(Color.parseColor("#FFFFFFFF"),Color.parseColor("#d81e06"));
        tab_markets.setOnClickListener(this);

        // 我的主页
        LinearLayout ll_mine = findViewById(R.id.ll_tab_mine);
        ImageView img_mine = findViewById(R.id.img_tab_mine);
        TextView tv_mine = findViewById(R.id.tv_tab_mine);
        tab_mine = new TabView();
        tab_mine.setView(img_mine, tv_mine, ll_mine);
        tab_mine.setIcon(R.mipmap.icon_mine_normal,R.mipmap.icon_mine_select);
        tab_mine.setTextColor(Color.parseColor("#FFFFFFFF"),Color.parseColor("#d81e06"));
        tab_mine.setOnClickListener(this);

        // 默认显示第一个主页
        current_tab = tab_publish;
    }

     /**
      * 点击事件：切换页面
      */
     @Override
    public void onClick(View v) {
         switch (v.getId()) {

             case R.id.ll_tab_chat:
                 //首页
                 tabAdapter.checkedIndex(0);
                 selectTab(tab_chat);
                 //AppContext.getInstance().tabIndex = 1;
                 break;
             case R.id.ll_tab_address_book:
                 tabAdapter.checkedIndex(1);
                 selectTab(tab_news);
                 //AppContext.getInstance().tabIndex = 0;
                 break;
             case R.id.ll_tab_publish:
                 tabAdapter.checkedIndex(2);
                 selectTab(tab_publish);
                 //AppContext.getInstance().tabIndex = 2;
                 break;
             case R.id.ll_tab_markets:
                 tabAdapter.checkedIndex(3);
                 selectTab(tab_markets);
                 //AppContext.getInstance().tabIndex = 3;
                 break;
             case R.id.ll_tab_mine:
                 tabAdapter.checkedIndex(4);
                 selectTab(tab_mine);
                 //AppContext.getInstance().tabIndex = 4;
                 break;
         }
    }

    /**
     * 选中tabView
     *
     * @param tabView
     */
    private void selectTab(TabView tabView) {
        if (null != tabView && !tabView.equals(current_tab)) {
            current_tab.clickIn();
            tabView.clickOn();
            current_tab = tabView;
        }
    }

    /**
     * 得到所有的fragment
     */
    private List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<Fragment>();

        //会话
        list.add(new LCIMConversationListFragment());

        //通讯录
        list.add(new AddressBookFragment());

        //新闻
        list.add(new NewsFragment());

        //二手市场
        list.add(new PublishFragment());

        //我的
        list.add(new MineFragment());

        return list;
    }

}