package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.ExchangeListAdapter;
import com.wallet.hz.presenter.ModelRecordListContract;
import com.wallet.hz.presenter.ModelRecordListPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: ModelRecordListActivity
 * @Description: 兑换，增值，参与，提取历史记录页面
 * @Author: ZL
 * @CreateDate: 2019/10/28 16:07
 */
public class ModelRecordListActivity extends BaseAppMVPActivity<ModelRecordListPresenter> implements ModelRecordListContract.View {

    @BindView(R.id.rv_order)
    XRecyclerView rvRecordList;

    // 0,兑换   1,增值    2,参与   3,提取
    private int type = 0;
    private ExchangeListAdapter mAdapter;

    public static void startIntent(Activity activity, int type) {
        Intent intent = new Intent(activity, ModelRecordListActivity.class);
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
        setToolTitleClick(getString(R.string.wallet_history_record), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        switch (type) {
            case 0:
                getPresenter().getExchangeList(true);
                break;
            case 1:
                getPresenter().getValueList(true);
                break;
            case 2:
                getPresenter().getJoinList(true);
                break;
            case 3:
                getPresenter().getExtractList(true);
                break;
            default:
                break;
        }
    }

    @Override
    public ModelRecordListPresenter injectDependencies() {
        type = getIntent().getIntExtra("type", 0);
        return new ModelRecordListPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ModelRecordListActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new ExchangeListAdapter(getPresenter().getDataList(), mAppContext, type);
            rvRecordList.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvRecordList.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvRecordList.setAdapter(mAdapter);

            rvRecordList.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    switch (type) {
                        case 0:
                            getPresenter().getExchangeList(true);
                            break;
                        case 1:
                            getPresenter().getValueList(true);
                            break;
                        case 2:
                            getPresenter().getJoinList(true);
                            break;
                        case 3:
                            getPresenter().getExtractList(true);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onLoadMore() {
                    switch (type) {
                        case 0:
                            getPresenter().getExchangeList(false);
                            break;
                        case 1:
                            getPresenter().getValueList(false);
                            break;
                        case 2:
                            getPresenter().getJoinList(false);
                            break;
                        case 3:
                            getPresenter().getExtractList(false);
                            break;
                        default:
                            break;
                    }
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            if (rvRecordList != null) {
                rvRecordList.loadMoreComplete();
                rvRecordList.refreshComplete();
            }
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
