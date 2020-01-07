package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.CoinDetail;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: CoinDetailAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 15:56
 */
public class CoinDetailAdapter extends BaseRecyclerViewAdapter<CoinDetail, CoinDetailAdapter.CoinDetailHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public CoinDetailAdapter(List<CoinDetail> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(CoinDetailHolder coinDetailHolder, CoinDetail item) {
        if (!item.isIn()) {
            coinDetailHolder.ivType.setImageResource(R.drawable.icon_zhuanchu);
            coinDetailHolder.tvAmount.setText("- " + BigDecimalUtils.formatServiceNumber(item.getAmount()));
            coinDetailHolder.tvAmount.setTextColor(ResourcesUtil.getColor(mContext, R.color.tab_selected_color));
            coinDetailHolder.tvAddress.setText(item.getFromaddress());
        } else {
            coinDetailHolder.ivType.setImageResource(R.drawable.icon_zhuanru);
            coinDetailHolder.tvAmount.setText("+ " + BigDecimalUtils.formatServiceNumber(item.getAmount()));
            coinDetailHolder.tvAmount.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_green));
            coinDetailHolder.tvAddress.setText(item.getAddress());
        }
        coinDetailHolder.tvTime.setText(item.getCreateTime());
        coinDetailHolder.tvStatus.setText(item.getTypeSuccessText(mContext));
        coinDetailHolder.tvStatus.setTextColor(item.getStatusTextColor(mContext));
        coinDetailHolder.tvStatus.setVisibility(item.isSpecialCoin() ? View.VISIBLE : View.GONE);
    }

    @NonNull
    @Override
    public CoinDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_wallet_coin_detail_list, viewGroup, false);
        CoinDetailHolder coinDetailHolder = new CoinDetailHolder(view);
        return coinDetailHolder;
    }

    public static class CoinDetailHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        ImageView ivType;
        AutoSizeTextView tvAmount;
        AutoSizeTextView tvTime;
        TextView tvAddress;
        AutoSizeTextView tvStatus;

        public CoinDetailHolder(View itemView) {
            super(itemView);
            ivType = getView(R.id.iv_record_type);
            tvAmount = getView(R.id.tv_record_amount);
            tvTime = getView(R.id.tv_record_time);
            tvAddress = getView(R.id.tv_record_address);
            tvStatus = getView(R.id.tv_record_status);
        }
    }
}
