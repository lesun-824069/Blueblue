package com.gao.blueblue.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gao.blueblue.R;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.im.v2.LCIMClient;
import cn.leancloud.im.v2.LCIMException;
import cn.leancloud.im.v2.callback.LCIMClientCallback;


public class ChatFragment extends Fragment {

    private TextView tv_start_chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        tv_start_chat = view.findViewById(R.id.tv_start_chat);
        tv_start_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LCChatKit.getInstance().open("13052949949", new LCIMClientCallback() {
                    @Override
                    public void done(LCIMClient client, LCIMException e) {
                        if (null == e) {
                            Intent intent = new Intent(getContext(), LCIMConversationActivity.class);
                            intent.putExtra(LCIMConstants.PEER_ID, "18851790733");
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}