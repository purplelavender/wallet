package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.GroupFriendAdapter;
import com.wallet.hz.model.GroupFriend;
import com.wallet.hz.presenter.GroupListContract;
import com.wallet.hz.presenter.GroupListPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: GroupListActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/29 15:17
 */
public class GroupListActivity extends BaseAppMVPActivity<GroupListPresenter> implements GroupListContract.View {

    @BindView(R.id.rv_order)
    XRecyclerView rvGroupList;

    private String name;
    private int type;
    private GroupFriendAdapter mAdapter;

    public static void startIntent(Activity activity, String name, int type) {
        Intent intent = new Intent(activity, GroupListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_work_order_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.group_friend), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getGroupFriendList(name);
    }

    @Override
    public GroupListPresenter injectDependencies() {
        name = getIntent().getStringExtra("name");
        type = getIntent().getIntExtra("type", 1);
        return new GroupListPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(GroupListActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new GroupFriendAdapter(getPresenter().getGroupFriends(), mAppContext, type);
            rvGroupList.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvGroupList.addItemDecoration(new LinearDividerItemDecoration(mAppContext));
            rvGroupList.setAdapter(mAdapter);
            rvGroupList.setPullRefreshEnabled(false);
            rvGroupList.setLoadingMoreEnabled(false);

            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    if (type < 3) {
                        GroupFriend groupFriend = (GroupFriend) item;
                        GroupListActivity.startIntent(GroupListActivity.this, groupFriend.getUsername(), mAdapter.getType() + 1);
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
        showEmpty();
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            showSuccessStateLayout();
        } else {
            showEmptyStateLayout();
        }
    }
}
