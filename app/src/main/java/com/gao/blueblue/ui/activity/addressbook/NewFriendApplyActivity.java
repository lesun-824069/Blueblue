package com.gao.blueblue.ui.activity.addressbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gao.blueblue.R;
import com.gao.blueblue.ui.activity.base.BaseActivity;
import com.gao.blueblue.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.LCFriendshipRequest;
import cn.leancloud.LCQuery;
import cn.leancloud.LCUser;
import cn.leancloud.chatkit.view.LCIMDividerItemDecoration;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static cn.leancloud.Messages.OpType.subscribe;

public class NewFriendApplyActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {


    /**
     * {"className":"_FriendshipRequest",
     * "serverData":{
     * "createdAt":"2021-10-24T09:23:34.160Z",
     * "friend":{"className":"_User",
     * "serverData":{
     * "objectId":"616be8e8f83d85335add7859"}},
     * "user":{"className":"_User",
     * "serverData":{"createdAt":"2021-10-17T10:00:15.484Z",
     * "emailVerified":false,
     * "mobilePhoneVerified":false,
     * "objectId":"616bf42f5dcf8f23dc1ffa1b",
     * "updatedAt":"2021-10-23T11:13:05.534Z",
     * "username":"18851790733"}},
     * "objectId":"617526165285185c60a95d53",
     * "status":"pending",
     * "updatedAt":"2021-10-24T09:23:34.160Z"}}
     */

    @BindView(R.id.rv_new_friend_apply)
    RecyclerView rv_new_friend_apply;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @OnClick(R.id.tv_back)
    public void onViewClicked(View view){
        finish();
    }

    private static final String TAG = "NewFriendApplyActivity";
    private List<LCFriendshipRequest> friendshipRequests = new ArrayList<>();
    private NewFriendApplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_apply);
        ButterKnife.bind(this);

        friendshipRequestQuery();
        adapter = new NewFriendApplyAdapter(R.layout.item_new_friend_apply, friendshipRequests);
        adapter.setOnItemChildClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_new_friend_apply.setLayoutManager(manager);
        rv_new_friend_apply.setAdapter(adapter);
        rv_new_friend_apply.addItemDecoration(new LCIMDividerItemDecoration(mContext));

    }

    private void friendshipRequestQuery() {
        friendshipRequests.clear();
        LCQuery<LCFriendshipRequest> lcFriendshipRequestLCQuery = LCUser.getCurrentUser().friendshipRequestQuery(LCFriendshipRequest.STATUS_ANY, true, true);
        lcFriendshipRequestLCQuery.findInBackground().subscribe(new Observer<List<LCFriendshipRequest>>() {
            @Override
            public void onSubscribe(@NotNull Disposable disposable) {
            }

            @Override
            public void onNext(@NotNull List<LCFriendshipRequest> lcFriendshipRequests) {
                friendshipRequests.addAll(lcFriendshipRequests);
                Log.e(TAG, "onNext: " + lcFriendshipRequests);
                adapter.notifyDataSetChanged();
            }

            public void onError(Throwable throwable) {
            }

            public void onComplete() {
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_agree://同意好友申请
                LCUser.getCurrentUser().acceptFriendshipRequest(friendshipRequests.get(position), null)
                        .subscribe(new Observer<LCFriendshipRequest>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull LCFriendshipRequest lcFriendshipRequest) {
                                Log.e(TAG, "onNext: " + lcFriendshipRequest);
                                ToastUtil.showSuccessedToast(R.string.add_friend_success);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.tv_reject://拒绝好友申请

        }
    }

    private void acceptNewFriendApply() {
    }


    class NewFriendApplyAdapter extends BaseQuickAdapter<LCFriendshipRequest, BaseViewHolder> {

        public NewFriendApplyAdapter(int layoutResId, @Nullable List<LCFriendshipRequest> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, LCFriendshipRequest item) {
            helper.setText(R.id.tv_applicant_name, item.getSourceUser().getUsername());
            helper.getView(R.id.tv_agree).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LCUser.getCurrentUser().acceptFriendshipRequest(item, null)
                            .subscribe(new Observer<LCFriendshipRequest>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                }

                                @Override
                                public void onNext(@NonNull LCFriendshipRequest lcFriendshipRequest) {
                                    Log.e(TAG, "onNext: " + lcFriendshipRequest);
                                    ToastUtil.showSuccessedToast(R.string.add_friend_success);
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            });
            helper.getView(R.id.tv_reject).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LCUser.getCurrentUser().declineFriendshipRequest(item)
                            .subscribe(new Observer<LCFriendshipRequest>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                }

                                @Override
                                public void onNext(@NonNull LCFriendshipRequest lcFriendshipRequest) {
                                    Log.e(TAG, "onNext: " + lcFriendshipRequest);
                                    ToastUtil.showCAWarningToast(getString(R.string.reject_friend_apply));
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                }

                                @Override
                                public void onComplete() {
                                }
                            });

                }
            });
            Log.e(TAG, "convert: " + item.getSourceUser());
        }
    }

}