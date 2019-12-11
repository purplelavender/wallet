package com.wallet.hz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.ModelJoinAdapter;
import com.wallet.hz.presenter.ModelJoinContract;
import com.wallet.hz.presenter.ModelJoinPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: ModelJoinFragment
 * @Description: 模式主页参与记录
 * @Author: ZL
 * @CreateDate: 2019/11/04 10:23
 */
public class ModelJoinFragment extends BaseAppMVPFragment<ModelJoinPresenter> implements ModelJoinContract.View {

    @BindView(R.id.rv_join)
    XRecyclerView rvJoin;

    private ModelJoinAdapter mModelJoinAdapter;

    public static ModelJoinFragment newInstance() {
        Bundle args = new Bundle();
        ModelJoinFragment fragment = new ModelJoinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_model_join_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isAdded()) {
            if (mModelJoinAdapter.getItemCount() <= 0) {
                getPresenter().getModelJoinList(true);
            }
        }
    }

    @Override
    protected void processLogic() {
        getPresenter().getModelJoinList(true);
    }

    @Override
    public ModelJoinPresenter injectDependencies() {
        return new ModelJoinPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(getActivity());
    }

    @Override
    public void updateAdapter() {
        if (mModelJoinAdapter == null) {
            mModelJoinAdapter = new ModelJoinAdapter(getPresenter().getModelJoinInfos(), mAppContext);
            rvJoin.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvJoin.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvJoin.setAdapter(mModelJoinAdapter);

            rvJoin.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getModelJoinList(true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getModelJoinList(false);
                }
            });
        } else {
            mModelJoinAdapter.notifyDataSetChanged();
            rvJoin.loadMoreComplete();
            rvJoin.refreshComplete();
        }
        showEmpty();
    }

    private void showEmpty() {
        if (mModelJoinAdapter.getItemCount() <= 0) {
            showEmptyStateLayout();
        } else {
            showSuccessStateLayout();
        }
    }

}
