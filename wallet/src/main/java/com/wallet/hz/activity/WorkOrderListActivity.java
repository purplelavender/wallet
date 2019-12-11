package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.WorkOrderListAdapter;
import com.wallet.hz.model.WorkOrder;
import com.wallet.hz.presenter.WorkOrderListContract;
import com.wallet.hz.presenter.WorkOrderListPresenter;
import com.wallet.hz.utils.AppSpUtil;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.ResourcesUtil;

/**
 * @ClassName: WorkOrderListActivity
 * @Description: 工单列表
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:23
 */
public class WorkOrderListActivity extends BaseAppMVPActivity<WorkOrderListPresenter> implements WorkOrderListContract.View {

    @BindView(R.id.rv_order)
    XRecyclerView rvOrder;

    private WorkOrderListAdapter mListAdapter;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, WorkOrderListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_work_order_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_work_order), null, null, null);
        setToolRightClick("", null, ResourcesUtil.getDrawable(mAppContext, R.drawable.icon_tianjia), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkOrderAddActivity.startIntentResult(WorkOrderListActivity.this, null, 107);
            }
        });
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getOrderList(AppSpUtil.getUserId(mAppContext));
    }

    @Override
    public WorkOrderListPresenter injectDependencies() {
        return new WorkOrderListPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initCurrentData();
        }
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(WorkOrderListActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mListAdapter == null) {
            mListAdapter = new WorkOrderListAdapter(getPresenter().getWorkOrders(), mAppContext);
            rvOrder.setLayoutManager(new LinearLayoutManager(this));
            rvOrder.setAdapter(mListAdapter);
            rvOrder.setPullRefreshEnabled(false);
            rvOrder.setLoadingMoreEnabled(false);

            mListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    WorkOrder workOrder = (WorkOrder) item;
                    WorkOrderDetailActivity.startIntentResult(WorkOrderListActivity.this, 109, workOrder);
                }
            });
        } else {
            mListAdapter.notifyDataSetChanged();
            showEmptyLayout();
        }
    }

    @Override
    public void onOrderListFailed() {
        showEmptyLayout();
    }

    private void showEmptyLayout() {
        if (mListAdapter.getItemCount() <= 0) {
            showEmptyStateLayout();
        } else {
            showSuccessStateLayout();
        }
    }
}
