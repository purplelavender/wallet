package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.TuiDetailListAdapter;
import com.wallet.hz.model.GroupInfoData;
import com.wallet.hz.presenter.TuiDetailListContract;
import com.wallet.hz.presenter.TuiDetailListPresenter;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.widget.AutoSizeTextView;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: TuiDetailListActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/12/17 09:49
 */
public class TuiDetailListActivity extends BaseAppMVPActivity<TuiDetailListPresenter> implements TuiDetailListContract.View {

    @BindView(R.id.tv_detail_amount_tag)
    TextView tvAmountTag;
    @BindView(R.id.tv_detail_amount)
    AutoSizeTextView tvAmount;
    @BindView(R.id.rv_detail_list)
    XRecyclerView rvTuiDetail;

    private GroupInfoData mGroupInfoData;
    private int type;
    private TuiDetailListAdapter mAdapter;

    public static void startIntent(Activity activity, GroupInfoData infoData) {
        Intent intent = new Intent(activity, TuiDetailListActivity.class);
        intent.putExtra("info", infoData);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tuiguang_detail_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        if (type == 0) {
            tvAmountTag.setText(getString(R.string.group_tui_today_total));
            tvAmount.setText(BigDecimalUtils.formatServiceNumber(mGroupInfoData.getTamount()) + " USDT");
        } else if (type == 2) {
            tvAmountTag.setText(getString(R.string.group_node_today_total));
            tvAmount.setText(BigDecimalUtils.formatServiceNumber(mGroupInfoData.getTamount()) + " USDT ≈ " + BigDecimalUtils.formatServiceNumber(mGroupInfoData.getTamount()) + " HS");
        } else if (type == 1) {
            tvAmountTag.setText(getString(R.string.group_tui_today_total));
            tvAmount.setText(BigDecimalUtils.formatServiceNumber(mGroupInfoData.getTamount()) + " USDT ≈ " + BigDecimalUtils.formatServiceNumber(mGroupInfoData.getTamount()) + " HS");
        }
    }

    @Override
    protected void initEvent() {
        String title = getString(R.string.group_tui_usdt_title);
        if (type == 0) {
            title = getString(R.string.group_tui_usdt_title);
        } else if (type == 1) {
            title = getString(R.string.group_tui_hs_title);
        } else if (type == 2) {
            title = getString(R.string.group_node_hs_title);
        }
        setToolTitleClick(title, null, null, null);
    }

    @Override
    protected void initCurrentData() {
        if (type == 0) {
            getPresenter().getTuiUsdtList(mGroupInfoData.getDatas());
        } else if (type == 1) {
            getPresenter().getTuiHsList(mGroupInfoData.getDatas());
        } else if (type == 2) {
            getPresenter().getNodeHsList(mGroupInfoData.getDatas());
        }
    }

    @Override
    public TuiDetailListPresenter injectDependencies() {
        mGroupInfoData = (GroupInfoData) getIntent().getSerializableExtra("info");
        type = mGroupInfoData.getTuiType();
        return new TuiDetailListPresenter(this, mAppContext);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new TuiDetailListAdapter(getPresenter().getTuiDetails(), mAppContext, type);
            rvTuiDetail.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvTuiDetail.addItemDecoration(new LinearDividerItemDecoration(mAppContext));
            rvTuiDetail.setAdapter(mAdapter);
            rvTuiDetail.setLoadingMoreEnabled(false);
            rvTuiDetail.setPullRefreshEnabled(false);
        } else {
            mAdapter.notifyDataSetChanged();
            if (rvTuiDetail != null) {
                rvTuiDetail.refreshComplete();
                rvTuiDetail.loadMoreComplete();
            }
        }
        showEmpty();
    }

    @Override
    public void changeTotalAmount(double total) {
        if (type == 1 || type == 2) {
            tvAmount.setText(total + " USDT ≈ " + BigDecimalUtils.formatServiceNumber(mGroupInfoData.getTamount()) + " HS");
        }
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            showSuccessStateLayout();
        } else {
            showEmptyStateLayout();
        }
    }

}
