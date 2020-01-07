package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.GroupInfoDataAdapter;
import com.wallet.hz.model.GroupInfo;
import com.wallet.hz.model.GroupInfoData;
import com.wallet.hz.presenter.MyGroupContract;
import com.wallet.hz.presenter.MyGroupPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.LinearDividerItemDecoration;
import share.exchange.framework.widget.MyScrollView;

/**
 * @ClassName: MyGroupActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/29 15:23
 */
public class MyGroupActivity extends BaseAppMVPActivity<MyGroupPresenter> implements MyGroupContract.View {

    @BindView(R.id.scroll_view)
    MyScrollView mScrollView;
    @BindView(R.id.tv_group_node)
    TextView tvGroupNode;
    @BindView(R.id.tv_group_persion_join)
    TextView tvPersionalJoin;
    @BindView(R.id.tv_node_total)
    TextView tvNodeTotal;
    @BindView(R.id.tv_tuiguang_total)
    TextView tvTuiGuangTotal;
    @BindView(R.id.tv_group_total_usdt)
    TextView tvTotalUsdt;
    @BindView(R.id.tv_group_my_code)
    TextView tvMyCode;
    @BindView(R.id.tv_group_look_friend)
    TextView tvLookFriend;
    @BindView(R.id.tv_group_today_award)
    TextView tvTodayAward;
    @BindView(R.id.tv_group_total_award)
    TextView tvTotalAward;
    @BindView(R.id.tv_group_total_num)
    TextView tvTotalNum;
    @BindView(R.id.rv_group_earning)
    XRecyclerView rvGroupEarning;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;

    private String userName;
    private GroupInfoDataAdapter mAdapter;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, MyGroupActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_group;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();

        tvMyCode.setText(userName);
        changeNodeView();
        mScrollView.scrollTo(0, 0);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        tvLookFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupListActivity.startIntent(MyGroupActivity.this, userName, 1);
            }
        });
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getGroupInfo();
    }

    @Override
    public MyGroupPresenter injectDependencies() {
        userName = AppSpUtil.getUserName(mAppContext);
        return new MyGroupPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(MyGroupActivity.this);
    }

    @Override
    public void onGroupSuccess(GroupInfo groupInfo) {
        if (groupInfo != null) {
            tvPersionalJoin.setText(groupInfo.getGramount() + "");
            tvNodeTotal.setText(groupInfo.getJdamount() + "");
            tvTuiGuangTotal.setText(groupInfo.getPcsamount() + "");
            tvTotalUsdt.setText(groupInfo.getUsdtamount() + "");
            tvTodayAward.setText(groupInfo.getTodayamount() + "");
            tvTotalAward.setText(groupInfo.getTotalusdt() + "");
            tvTotalNum.setText(groupInfo.getCount() + "");
        } else {
            tvPersionalJoin.setText("0");
            tvNodeTotal.setText("0");
            tvTuiGuangTotal.setText("0");
            tvTotalUsdt.setText("0");
            tvTodayAward.setText("0");
            tvTotalAward.setText("0");
            tvTotalNum.setText("0");
        }
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new GroupInfoDataAdapter(getPresenter().getGroupInfoData(), mAppContext);
            rvGroupEarning.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvGroupEarning.addItemDecoration(new LinearDividerItemDecoration(mAppContext));
            rvGroupEarning.setAdapter(mAdapter);
            rvGroupEarning.setLoadingMoreEnabled(false);
            rvGroupEarning.setPullRefreshEnabled(false);

            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    GroupInfoData infoData = (GroupInfoData) item;
                    if (infoData.getTuiType() != 2) {
                        TuiDetailListActivity.startIntent(MyGroupActivity.this, infoData);
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            if (rvGroupEarning != null) {
                rvGroupEarning.refreshComplete();
                rvGroupEarning.loadMoreComplete();
            }
        }
        showEmpty();
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            rvGroupEarning.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        } else {
            rvGroupEarning.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 修改用户节点等级信息
     */
    private void changeNodeView() {
        int level = AppSpUtil.getUserLevel(mAppContext);
        String text = "";
        switch (level) {
            case 1:
                text = getString(R.string.group_node_one);
                break;
            case 2:
                text = getString(R.string.group_node_two);
                break;
            case 3:
                text = getString(R.string.group_node_three);
                break;
            case 4:
                text = getString(R.string.group_node_four);
                break;
            case 5:
                text = getString(R.string.group_node_five);
                break;
            case 0:
            default:
                text = "";
                break;
        }
        tvGroupNode.setText(text);
        tvGroupNode.setVisibility(StringUtil.isEmpty(text) ? View.GONE : View.VISIBLE);
    }
}
