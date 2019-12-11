package com.wallet.hz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.adapter.MarketListAdapter;
import com.wallet.hz.presenter.MarketContract;
import com.wallet.hz.presenter.MarketPresenter;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: MarketFragment
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:14
 */
public class MarketFragment extends BaseAppMVPFragment<MarketPresenter> implements MarketContract.View {

    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.rv_market)
    XRecyclerView rvMarket;

    private MarketListAdapter mAdapter;

    public static MarketFragment newInstance() {
        Bundle args = new Bundle();
        MarketFragment fragment = new MarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showTitleBar(false);
        showSuccessStateLayout();
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void processLogic() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getMarketList();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().stopTimer();
    }

    @Override
    public MarketPresenter injectDependencies() {
        return new MarketPresenter(this, mAppContext);
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new MarketListAdapter(getPresenter().getMarketLists(), mAppContext);
            rvMarket.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvMarket.addItemDecoration(new LinearDividerItemDecoration(mAppContext));
            rvMarket.setAdapter(mAdapter);

            rvMarket.setLoadingMoreEnabled(false);
            rvMarket.setPullRefreshEnabled(false);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        showEmpty();
    }

    private void showEmpty() {
        if (rvMarket != null) {
            if (mAdapter.getItemCount() > 0) {
                rvMarket.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.GONE);
            } else {
                rvMarket.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.VISIBLE);
            }
        }
    }
}
