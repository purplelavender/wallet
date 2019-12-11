package com.wallet.hz.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wallet.hz.R;
import com.wallet.hz.activity.CoinDetailActivity;
import com.wallet.hz.activity.WalletRecordActivity;
import com.wallet.hz.adapter.WalletCoinAdapter;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.model.WalletToatl;
import com.wallet.hz.presenter.WalletContract;
import com.wallet.hz.presenter.WalletPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.AutoSizeTextView;
import share.exchange.framework.widget.LinearDividerItemDecoration;

/**
 * @ClassName: WalletFragment
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:29
 */
public class WalletFragment extends BaseAppMVPFragment<WalletPresenter> implements WalletContract.View {

    @BindView(R.id.rv_wallet)
    XRecyclerView rvWallet;
    @BindView(R.id.tv_wallet_amount)
    AutoSizeTextView tvAmount;
    @BindView(R.id.cb_wallet_eye)
    CheckBox cbEye;
    @BindView(R.id.tv_history_record)
    TextView tvRecord;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;

    private String amount;
    private WalletCoinAdapter mAdapter;

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        showTitleBar(false);
        cbEye.setChecked(true);

        // 暂时不显示历史记录
        tvRecord.setVisibility(View.GONE);
    }

    @Override
    protected void initEvent() {
        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletRecordActivity.startIntent(getActivity());
            }
        });
        cbEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvAmount.setText(getString(R.string.wallet_about, amount));
                } else {
                    tvAmount.setText(getString(R.string.wallet_about, StringUtil.getStarString(amount)));
                }
            }
        });
    }

    @Override
    protected void processLogic() {
        getPresenter().getWalletInfo();
        getPresenter().getWalletCoinList();
    }

    @Override
    public void onResume() {
        super.onResume();
        processLogic();
    }

    @Override
    public WalletPresenter injectDependencies() {
        return new WalletPresenter(this, mAppContext);
    }

    @Override
    public void showLoginDialog() {
        if (isAdded()) {
            LoginDialogUtil.getInstance().showLoginDialog(getActivity());
        }
    }

    @Override
    public void onWalletSuccess(WalletToatl walletInfo) {
        if (walletInfo != null) {
            amount = BigDecimalUtils.decimalPoint(walletInfo.getUsdtto() + "", 8);
        } else {
            amount = "0.00000000";
        }
        tvAmount.setText(getString(R.string.wallet_about, amount));
    }

    @Override
    public void updateAdapter() {
        if (mAdapter == null) {
            mAdapter = new WalletCoinAdapter(getPresenter().getWalletInfos(), mAppContext);
            rvWallet.setLayoutManager(new LinearLayoutManager(mAppContext));
            rvWallet.addItemDecoration(new LinearDividerItemDecoration(mAppContext));
            rvWallet.setAdapter(mAdapter);
            rvWallet.setLoadingMoreEnabled(false);
            rvWallet.setPullRefreshEnabled(false);

            mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Object item) {
                    WalletInfo walletInfo = (WalletInfo) item;
                    CoinDetailActivity.startIntent(getActivity(), walletInfo);
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
        showEmpty();
    }

    private void showEmpty() {
        if (mAdapter.getItemCount() > 0) {
            rvWallet.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        } else {
            rvWallet.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void updateData(){
        processLogic();
    }
}
