package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.adapter.TabLayoutViewPagerAdapter;
import com.wallet.hz.fragment.CoinDetailFragemt;
import com.wallet.hz.model.CoinBalance;
import com.wallet.hz.model.WalletInfo;
import com.wallet.hz.presenter.CoinDetailContract;
import com.wallet.hz.presenter.CoinDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.GlideImageUtil;
import share.exchange.framework.widget.CircleImageView;
import share.exchange.framework.widget.HackyViewPager;

/**
 * @ClassName: CoinDetailActivity
 * @Description: 货币交易情况页面
 * @Author: ZL
 * @CreateDate: 2019/10/24 14:16
 */
public class CoinDetailActivity extends BaseAppMVPActivity<CoinDetailPresenter> implements CoinDetailContract.View {

    @BindView(R.id.iv_coin)
    CircleImageView ivCoin;
    @BindView(R.id.tv_coin_amount)
    TextView tvAmount;
    @BindView(R.id.tv_coin_amount_usdt)
    TextView tvAmountUsdt;
    @BindView(R.id.tab_amount)
    TabLayout tabAmount;
    @BindView(R.id.vp_amount)
    HackyViewPager vpAmount;

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private WalletInfo mWalletInfo;

    public static void startIntent(Activity activity, WalletInfo walletInfo) {
        Intent intent = new Intent(activity, CoinDetailActivity.class);
        intent.putExtra("wallet", walletInfo);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_coin_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        initFragment();

        GlideImageUtil.with(mAppContext, mWalletInfo.getIcons(), ivCoin);
        tvAmount.setText(BigDecimalUtils.formatServiceNumber(mWalletInfo.getBalance()));
        tvAmountUsdt.setText("≈ " + BigDecimalUtils.formatServiceNumber(mWalletInfo.getUsdtbalance()) + "USDT");
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(mWalletInfo.getCoinName(), null, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getCoinBalance(mWalletInfo.getCoinName());
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public CoinDetailPresenter injectDependencies() {
        mWalletInfo = (WalletInfo) getIntent().getSerializableExtra("wallet");
        return new CoinDetailPresenter(this, mAppContext);
    }

    @OnClick({R.id.tv_in, R.id.tv_out})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_in:
                GatheringActivity.startIntent(CoinDetailActivity.this, mWalletInfo);
                break;
            case R.id.tv_out:
                TransferActivity.startIntent(CoinDetailActivity.this, mWalletInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化底部Tab栏
     */
    private void initFragment() {
        titles.add(getString(R.string.wallet_all));
        titles.add(getString(R.string.wallet_in));
        titles.add(getString(R.string.wallet_out));

        fragments.add(CoinDetailFragemt.newInstance(0, mWalletInfo.getCoinName()));
        fragments.add(CoinDetailFragemt.newInstance(1, mWalletInfo.getCoinName()));
        fragments.add(CoinDetailFragemt.newInstance(2, mWalletInfo.getCoinName()));

        TabLayoutViewPagerAdapter adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpAmount.setAdapter(adapter);
        vpAmount.setOffscreenPageLimit(fragments.size());
        tabAmount.setupWithViewPager(vpAmount);
        tabAmount.getTabAt(0).select();
    }

    @Override
    public void onchangeUI(CoinBalance coinBalance) {
        if (coinBalance != null) {
            mWalletInfo.setBalance(coinBalance.getBalance());
            tvAmount.setText(BigDecimalUtils.formatServiceNumber(coinBalance.getBalance()));
            tvAmountUsdt.setText("≈ " + BigDecimalUtils.mul(coinBalance.getBalance() + "", coinBalance.getHuilv() + "", 8) + "USDT");
        }
    }
}
