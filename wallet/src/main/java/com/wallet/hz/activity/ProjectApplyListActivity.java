package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.ProjectApplyAdapter;
import com.wallet.hz.presenter.ProjectApplyListContract;
import com.wallet.hz.presenter.ProjectApplyListPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: ProjectApplyListActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 17:26
 */
public class ProjectApplyListActivity extends BaseAppMVPActivity<ProjectApplyListPresenter> implements ProjectApplyListContract.View {

    @BindView(R.id.rv_project)
    XRecyclerView rvProject;

    private ProjectApplyAdapter mAdapter;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, ProjectApplyListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_apply_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_project_apply), null, null, null);
    }

    @Override
    protected void initCurrentData() {
        getPresenter().getProjectList(true);
    }

    @Override
    public ProjectApplyListPresenter injectDependencies() {
        return new ProjectApplyListPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(ProjectApplyListActivity.this);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new ProjectApplyAdapter(getPresenter().getProjectApplies(), mAppContext);
            rvProject.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvProject.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvProject.setAdapter(mAdapter);

            rvProject.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getProjectList(true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getProjectList(false);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            if (rvProject != null) {
                rvProject.loadMoreComplete();
                rvProject.refreshComplete();
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
