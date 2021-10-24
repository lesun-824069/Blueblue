package com.gao.blueblue.ui.activity.addressbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.ui.view.SearchBuddyDialog;
import com.gao.blueblue.utils.ToastUtil;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.LCException;
import cn.leancloud.LCFriendship;
import cn.leancloud.LCFriendshipRequest;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import cn.leancloud.callback.FollowersAndFolloweesCallback;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.http.GET;

public class PersonalInfoActivity extends BaseActivity {

    /**
     * {"className":"_User",
     * "serverData":{
     * "createdAt":"2021-10-17T09:12:08.279Z",
     * "emailVerified":false,"
     * mobilePhoneVerified":false,
     * "objectId":"616be8e8f83d85335add7859",
     * "updatedAt":"2021-10-17T09:12:08.279Z",
     * "username":"1002304490"}}
     */

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.iv_search_buddy)
    ImageView iv_search_buddy;
    @BindView(R.id.tv_add_contact)
    TextView tv_add_contact;
    @BindView(R.id.tv_info_name)
    TextView tv_info_name;
    @BindView(R.id.tv_info_id)
    TextView tv_info_id;
    @BindView(R.id.tv_info_phone)
    TextView tv_info_phone;
    @BindView(R.id.tv_info_email)
    TextView tv_info_email;
    @BindView(R.id.ll_buddy_info)
    LinearLayout ll_buddy_info;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;

    private static final String TAG = "PersonalInfoActivity";
    public static final int GET_INFO_SUCCESS = 1;
    private int searchResult = 0;
    private LCUser user = new LCUser();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_INFO_SUCCESS){
                updateData();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        updateData();

    }

    private void updateData() {
        if (searchResult != GET_INFO_SUCCESS){
            ll_empty.setVisibility(View.VISIBLE);
            ll_buddy_info.setVisibility(View.GONE);
        }else {
            ll_empty.setVisibility(View.GONE);
            ll_buddy_info.setVisibility(View.VISIBLE);
            tv_info_name.setText(user.getUsername());
            tv_info_id.setText(user.getObjectId());
            if (user.getMobilePhoneNumber() == null){
                tv_info_phone.setText(R.string.unknown);
            }else {
                tv_info_phone.setText(user.getMobilePhoneNumber());
            }
            if (user.getEmail() == null){
                tv_info_email.setText(R.string.unknown);
            }else {
                tv_info_email.setText(user.getEmail());
            }
        }
        searchResult = 0;
    }

    @OnClick({R.id.tv_back,R.id.iv_search_buddy,R.id.tv_add_contact})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_search_buddy:
                SearchBuddyDialog searchBuddyDialog = new SearchBuddyDialog(mContext);
                searchBuddyDialog.show(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchBuddyDialog.cancel();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchKey = searchBuddyDialog.inputString;
                        LCQuery<LCUser> query = new LCQuery<>("_User");
                        query.whereEqualTo("username", searchKey);
                        query.findInBackground().subscribe(new Observer<List<LCUser>>() {
                            public void onSubscribe(Disposable disposable) {
                                Log.e(TAG, "onSubscribe: " + disposable.toString());
                            }
                            public void onNext(List<LCUser> lcUsers) {
                                // students 是包含满足条件的 Student 对象的数组
                                Log.e(TAG, "onNext: " + lcUsers.size());
                                Log.e(TAG, "onNext: " + lcUsers.get(0));
                                user = lcUsers.get(0);
                                searchResult = GET_INFO_SUCCESS;
                                mHandler.sendEmptyMessage(GET_INFO_SUCCESS);
                                searchBuddyDialog.cancel();
                            }
                            public void onError(Throwable throwable) {
                                Log.e(TAG, "onError: " + throwable.getMessage());
                            }
                            public void onComplete() {
                                Log.e(TAG, "onComplete: " );
                            }
                        });
                    }
                });
                break;
            case R.id.tv_add_contact:
                Log.e(TAG, "onViewClicked: 添加好友" );
                addContact();
                break;

        }
    }

    private void addContact(){
        LCUser friend = null;
        try {
            friend = LCUser.createWithoutData(LCUser.class,user.getObjectId());
        } catch (LCException e) {
            e.printStackTrace();
        }
        LCUser.getCurrentUser().applyFriendshipInBackground(friend,null).subscribe(new Observer<LCFriendshipRequest>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                Log.e(TAG, "onSubscribe: " );
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull LCFriendshipRequest lcFriendshipRequest) {
                Log.e(TAG, "onNext: " +lcFriendshipRequest.getRequestMethod());
                Log.e(TAG, "onNext: " );
                ToastUtil.showSuccessedToast(R.string.send_request_success);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        });

    }

}