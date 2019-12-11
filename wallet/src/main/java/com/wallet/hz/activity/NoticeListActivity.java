package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.NoticeListAdapter;
import com.wallet.hz.presenter.NoticeListContract;
import com.wallet.hz.presenter.NoticeListPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.bean.NoticeBean;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: NoticeListActivity
 * @Description: 公告列表
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:28
 */
public class NoticeListActivity extends BaseAppMVPActivity<NoticeListPresenter> implements NoticeListContract.View {

    @BindView(R.id.rv_order)
    XRecyclerView rvNotice;

    private NoticeListAdapter mNoticeListAdapter;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, NoticeListActivity.class);
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
        setToolTitleClick(getString(R.string.market_tips), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getNoticeList(true);
    }

    @Override
    public NoticeListPresenter injectDependencies() {
        return new NoticeListPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(NoticeListActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mNoticeListAdapter == null) {
            mNoticeListAdapter = new NoticeListAdapter(getPresenter().getNoticeBeans(), mAppContext);
            rvNotice.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvNotice.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvNotice.setAdapter(mNoticeListAdapter);

            mNoticeListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    NoticeBean noticeBean = (NoticeBean) item;
                    NoticeDetailActivity.startIntent(NoticeListActivity.this, noticeBean);
                }
            });
            rvNotice.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getNoticeList(true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getNoticeList(false);
                }
            });
        } else {
            mNoticeListAdapter.notifyDataSetChanged();
            if (rvNotice != null) {
                rvNotice.loadMoreComplete();
                rvNotice.refreshComplete();
            }
        }
        showEmpty();
    }

    @Override
    public void onNoticeListFailed() {
        showEmpty();
    }

    private void showEmpty() {
        if (mNoticeListAdapter.getItemCount() > 0) {
            showSuccessStateLayout();
        } else {
            showEmptyStateLayout();
        }
    }
}
