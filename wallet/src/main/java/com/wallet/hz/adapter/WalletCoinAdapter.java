package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.WalletInfo;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.GlideImageUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: WalletCoinAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 15:04
 */
public class WalletCoinAdapter extends BaseRecyclerViewAdapter<WalletInfo, WalletCoinAdapter.WalletCoinHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public WalletCoinAdapter(List<WalletInfo> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(WalletCoinHolder walletCoinHolder, WalletInfo item) {
        GlideImageUtil.with(mContext, item.getIcons(), walletCoinHolder.ivCoin);
        walletCoinHolder.tvCoinName.setText(item.getCoinName());
        walletCoinHolder.tvAmount.setText(BigDecimalUtils.formatServiceNumber(item.getBalance()));
        walletCoinHolder.tvAmountUsdt.setText("≈ " + BigDecimalUtils.formatServiceNumber(item.getUsdtbalance()) + " USDT");
    }

    @NonNull
    @Override
    public WalletCoinHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_wallet_coin_list, viewGroup, false);
        WalletCoinHolder walletCoinHolder = new WalletCoinHolder(view);
        return walletCoinHolder;
    }

    public static class WalletCoinHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        ImageView ivCoin;
        TextView tvCoinName;
        AutoSizeTextView tvAmount;
        AutoSizeTextView tvAmountUsdt;

        public WalletCoinHolder(View itemView) {
            super(itemView);
            ivCoin = getView(R.id.iv_coin);
            tvCoinName = getView(R.id.tv_coin_name);
            tvAmount = getView(R.id.tv_coin_amount);
            tvAmountUsdt = getView(R.id.tv_coin_amount_usdt);
        }
    }
}
