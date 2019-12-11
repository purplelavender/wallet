package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.hz.R;
import com.wallet.hz.model.CoinDetail;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.widget.AutoSizeTextView;
import share.exchange.framework.widget.CircleImageView;

/**
 * @ClassName: WalletRecordAdapter
 * @Description: 钱包页面的历史记录
 * @Author: ZL
 * @CreateDate: 2019/11/07 14:33
 */
public class WalletRecordAdapter extends BaseRecyclerViewAdapter<CoinDetail, WalletRecordAdapter.WalletRecordHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public WalletRecordAdapter(List<CoinDetail> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(WalletRecordHolder walletRecordHolder, CoinDetail item) {
        if (item.getType() == 1) {
            walletRecordHolder.ivType.setImageResource(R.drawable.icon_zhuanchu);
            walletRecordHolder.tvAmount.setText("- " + BigDecimalUtils.formatServiceNumber(item.getAmount()));
            walletRecordHolder.tvAmount.setTextColor(ResourcesUtil.getColor(mContext, R.color.tab_selected_color));
        } else {
            walletRecordHolder.ivType.setImageResource(R.drawable.icon_zhuanru);
            walletRecordHolder.tvAmount.setText("+ " + BigDecimalUtils.formatServiceNumber(item.getAmount()));
            walletRecordHolder.tvAmount.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_green));
        }
        walletRecordHolder.tvName.setText(item.getCoinname());
        walletRecordHolder.tvTime.setText(item.getCreateTime());
    }

    @NonNull
    @Override
    public WalletRecordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_wallet_record_list, viewGroup, false);
        WalletRecordHolder walletRecordHolder = new WalletRecordHolder(view);
        return walletRecordHolder;
    }

    public static class WalletRecordHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        CircleImageView ivType;
        AutoSizeTextView tvName;
        AutoSizeTextView tvAmount;
        AutoSizeTextView tvTime;

        public WalletRecordHolder(View itemView) {
            super(itemView);
            ivType = getView(R.id.iv_record_type);
            tvName = getView(R.id.tv_record_name);
            tvAmount = getView(R.id.tv_record_amount);
            tvTime = getView(R.id.tv_record_time);
        }
    }
}
