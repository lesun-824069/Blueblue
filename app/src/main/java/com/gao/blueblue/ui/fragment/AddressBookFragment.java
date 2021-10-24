package com.gao.blueblue.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.addressbook.NewFriendApplyActivity;
import com.gao.blueblue.ui.activity.addressbook.PersonalInfoActivity;
import com.gao.blueblue.ui.view.SearchBuddyDialog;
import com.gao.blueblue.utils.DialogUtil;
import com.shehuan.nicedialog.NiceDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.LCFriendship;
import cn.leancloud.LCObject;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.chatkit.view.LCIMDividerItemDecoration;
import cn.leancloud.im.v2.LCIMClient;
import cn.leancloud.im.v2.LCIMException;
import cn.leancloud.im.v2.callback.LCIMClientCallback;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public class AddressBookFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener{

    /**
     * 添加好友
     */
    @BindView(R.id.iv_add_member)
    ImageView iv_add_member;
    @BindView(R.id.tv_new_friend_apply)
    TextView tv_new_friend_apply;
    @BindView(R.id.rv_address_book)
    RecyclerView rv_address_book;


    private static final String TAG = "AddressBookFragment";
    List<LCFriendship> friendships = new ArrayList<>();
    private FriendAdapter adapter;

    @OnClick({R.id.iv_add_member,R.id.tv_new_friend_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_member:
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                break;
            case R.id.tv_new_friend_apply:
                startActivity(new Intent(getActivity(), NewFriendApplyActivity.class));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_book, container, false);
        ButterKnife.bind(this, view);

        loadFriendData();
        adapter = new FriendAdapter(R.layout.item_friend,friendships);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_address_book.setLayoutManager(manager);
        rv_address_book.setAdapter(adapter);
        rv_address_book.addItemDecoration(new LCIMDividerItemDecoration(getContext()));
        adapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFriendData();
    }

    private void loadFriendData() {
        friendships.clear();
        LCQuery<LCFriendship> query = LCUser.getCurrentUser().friendshipQuery(false);
        query.whereEqualTo(LCFriendship.ATTR_FRIEND_STATUS,true);
        query.addDescendingOrder(LCObject.KEY_UPDATED_AT);
        query.findInBackground().subscribe(new Observer<List<LCFriendship>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }
            @Override
            public void onNext(@NonNull List<LCFriendship> lcFriendships) {
                Log.e(TAG, "onNext: " + lcFriendships);
                friendships.addAll(lcFriendships);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(@NonNull Throwable e) {
            }
            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Log.e(TAG, "onItemClick: 点击了" + friendships.get(position).getFollowee().getUsername());
        String friendId = friendships.get(position).getFollowee().getObjectId();
        String myId = LCUser.getCurrentUser().getObjectId();
        LCChatKit.getInstance().open(myId, new LCIMClientCallback() {
            @Override
            public void done(LCIMClient client, LCIMException e) {
                if (null == e) {
                    Intent intent = new Intent(mContext, LCIMConversationActivity.class);
                    intent.putExtra(LCIMConstants.PEER_ID, friendId);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class FriendAdapter extends BaseQuickAdapter<LCFriendship, BaseViewHolder>{

        public FriendAdapter(int layoutResId, @Nullable List<LCFriendship> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LCFriendship item) {
            Log.e(TAG, "convert: " + item.getFollowee().getUsername());
            helper.setText(R.id.tv_name,item.getFollowee().getUsername());
        }
    }


}