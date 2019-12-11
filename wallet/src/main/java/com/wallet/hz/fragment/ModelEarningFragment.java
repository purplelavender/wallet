package com.wallet.hz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.activity.EarningDetailActivity;
import com.wallet.hz.adapter.ModelEarningAdapter;
import com.wallet.hz.model.ModelReleaseInfo;
import com.wallet.hz.presenter.ModelEarningContract;
import com.wallet.hz.presenter.ModelEarningPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: ModelEarningFragment
 * @Description: 模式主页收益记录页面
 * @Author: ZL
 * @CreateDate: 2019/11/04 10:23
 */
public class ModelEarningFragment extends BaseAppMVPFragment<ModelEarningPresenter> implements ModelEarningContract.View {

    @BindView(R.id.rv_join)
    XRecyclerView rvEarning;

    private ModelEarningAdapter mModelEarningAdapter;

    public static ModelEarningFragment newInstance() {
        Bundle args = new Bundle();
        ModelEarningFragment fragment = new ModelEarningFragment();
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
            if (mModelEarningAdapter.getItemCount() <= 0) {
                getPresenter().getModelEarningList(true);
            }
        }
    }

    @Override
    protected void processLogic() {
        getPresenter().getModelEarningList(true);
    }

    @Override
    public ModelEarningPresenter injectDependencies() {
        return new ModelEarningPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(getActivity());
    }

    @Override
    public void updateAdapter() {
        if (mModelEarningAdapter == null) {
            mModelEarningAdapter = new ModelEarningAdapter(getPresenter().getModelReleaseInfos(), mAppContext);
            rvEarning.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvEarning.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvEarning.setAdapter(mModelEarningAdapter);

            rvEarning.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getModelEarningList(true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getModelEarningList(false);
                }
            });

            mModelEarningAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    ModelReleaseInfo modelReleaseInfo = (ModelReleaseInfo) item;
                    EarningDetailActivity.startIntent(getActivity(), modelReleaseInfo);
                }
            });
        } else {
            mModelEarningAdapter.notifyDataSetChanged();
            rvEarning.loadMoreComplete();
            rvEarning.refreshComplete();
        }
        showEmpty();
    }

    private void showEmpty() {
        if (mModelEarningAdapter.getItemCount() <= 0) {
            showEmptyStateLayout();
        } else {
            showSuccessStateLayout();
        }
    }
}
