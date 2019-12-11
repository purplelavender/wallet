package com.wallet.hz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.activity.CoinRecordDetailActivity;
import com.wallet.hz.adapter.CoinDetailAdapter;
import com.wallet.hz.model.CoinDetail;
import com.wallet.hz.presenter.CoinFragmentContract;
import com.wallet.hz.presenter.CoinFragmentPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: CoinDetailFragemt
 * @Description: 货币交易列表
 * @Author: ZL
 * @CreateDate: 2019/10/24 14:11
 */
public class CoinDetailFragemt extends BaseAppMVPFragment<CoinFragmentPresenter> implements CoinFragmentContract.View {

    @BindView(R.id.rv_wallet)
    XRecyclerView rvWallet;

    private int position = 0;
    private String name = "";
    private CoinDetailAdapter mAdapter;

    public static CoinDetailFragemt newInstance(int position, String name) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("name", name);
        CoinDetailFragemt fragment = new CoinDetailFragemt();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wallet_recycler;
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
    protected void processLogic() {
        getPresenter().getCoinDetailList(position, name, true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isAdded()) {
            if (mAdapter.getItemCount() <= 0) {
                getPresenter().getCoinDetailList(position, name, true);
            }
        }
    }

    @Override
    public CoinFragmentPresenter injectDependencies() {
        position = getArguments().getInt("position");
        name = getArguments().getString("name");
        return new CoinFragmentPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        LoginDialogUtil.getInstance().showLoginDialog(getActivity());
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new CoinDetailAdapter(getPresenter().getCoinDetails(), mAppContext);
            rvWallet.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvWallet.addItemDecoration(new LinearDividerItemDecoration(mAppContext, 0, false, true));
            rvWallet.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    CoinDetail coinDetail = (CoinDetail) item;
                    CoinRecordDetailActivity.startIntent(getActivity(), coinDetail);
                }
            });

            rvWallet.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getPresenter().getCoinDetailList(position, name, true);
                }

                @Override
                public void onLoadMore() {
                    getPresenter().getCoinDetailList(position, name, false);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
            rvWallet.loadMoreComplete();
            rvWallet.refreshComplete();
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
