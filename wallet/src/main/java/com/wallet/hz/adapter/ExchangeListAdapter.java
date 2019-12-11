package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.ExchangeInfo;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: ExchangeListAdapter
 * @Description: 兑换增值记录列表适配器
 * @Author: ZL
 * @CreateDate: 2019/11/04 17:27
 */
public class ExchangeListAdapter extends BaseRecyclerViewAdapter<ExchangeInfo, ExchangeListAdapter.ExchangeListHolder> {

    private Context mContext;
    private int type; // 0,兑换   1,增值    2,参与   3,提取

    public ExchangeListAdapter(List<ExchangeInfo> dataList, Context mContext, int type) {
        super(dataList);
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    protected void bindDataToItemView(ExchangeListHolder exchangeListHolder, ExchangeInfo item) {
        exchangeListHolder.tvTime.setText(item.getCreateTime());
        if (type == 0) {
            exchangeListHolder.tvType.setText(mContext.getString(R.string.modle_exchange));
            exchangeListHolder.tvRateTag.setText(mContext.getString(R.string.modle_exchange_rate));
            exchangeListHolder.tvPcsTag.setText(mContext.getString(R.string.modle_list_count, item.getCoinName()));
            exchangeListHolder.tvUsdtTag.setText(mContext.getString(R.string.modle_list_exchange_count));
            exchangeListHolder.tvRate.setText("1" + item.getCoinName() + " ≈ " + item.getRetes() + " USDT");
            exchangeListHolder.tvPcs.setText(BigDecimalUtils.formatServiceNumber(item.getExchangeCount()));
            exchangeListHolder.tvUsdt.setText(BigDecimalUtils.formatServiceNumber(item.getIncomeCount()));
        } else if (type == 1) {
            exchangeListHolder.tvType.setText(mContext.getString(R.string.modle_value_add));
            exchangeListHolder.tvRateTag.setText(mContext.getString(R.string.modle_exchange_rate));
            exchangeListHolder.tvPcsTag.setText(mContext.getString(R.string.modle_list_buy_count, item.getCoinName()));
            exchangeListHolder.tvUsdtTag.setText(mContext.getString(R.string.modle_list_value_count));
            exchangeListHolder.tvRate.setText("1" + item.getCoinName() + " ≈ " + item.getRetes() + " USDT");
            exchangeListHolder.tvPcs.setText(BigDecimalUtils.formatServiceNumber(item.getExchangeCount()));
            exchangeListHolder.tvUsdt.setText(BigDecimalUtils.formatServiceNumber(item.getIncomeCount()));
        } else if (type == 2) {
            exchangeListHolder.tvType.setText(mContext.getString(R.string.state_mingxi_type_join));
            exchangeListHolder.tvRateTag.setText(mContext.getString(R.string.modle_list_join_type));
            exchangeListHolder.tvPcsTag.setText(mContext.getString(R.string.modle_list_join_count));
            exchangeListHolder.tvUsdtTag.setText(mContext.getString(R.string.modle_list_dig_count));
            exchangeListHolder.tvRate.setText(item.getJoinTypeText(mContext));
            exchangeListHolder.tvPcs.setText(BigDecimalUtils.formatServiceNumber(item.getAmount()));
            exchangeListHolder.tvUsdt.setText(BigDecimalUtils.formatServiceNumber(item.getWaamount()));
        } else if (type == 3) {
            exchangeListHolder.tvType.setText(mContext.getString(R.string.modle_extract));
            exchangeListHolder.tvRateTag.setText(mContext.getString(R.string.modle_exchange_rate));
            exchangeListHolder.tvPcsTag.setText(mContext.getString(R.string.modle_list_extract_count));
            exchangeListHolder.tvUsdtTag.setText(mContext.getString(R.string.modle_list_account_count, "HS"));
            exchangeListHolder.tvRate.setText("1" + item.getCoinName() + " ≈ " + item.getHuilv() + " USDT");
            exchangeListHolder.tvPcs.setText(BigDecimalUtils.formatServiceNumber(item.getTamount()));
            exchangeListHolder.tvUsdt.setText(BigDecimalUtils.formatServiceNumber(item.getDamount()));
        }

    }

    @NonNull
    @Override
    public ExchangeListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_exchange_list, viewGroup, false);
        ExchangeListHolder exchangeListHolder = new ExchangeListHolder(view);
        return exchangeListHolder;
    }

    public static class ExchangeListHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        TextView tvType;
        TextView tvTime;
        AutoSizeTextView tvRateTag;
        AutoSizeTextView tvPcsTag;
        AutoSizeTextView tvUsdtTag;
        AutoSizeTextView tvRate;
        AutoSizeTextView tvPcs;
        AutoSizeTextView tvUsdt;

        public ExchangeListHolder(View itemView) {
            super(itemView);
            tvType = getView(R.id.tv_type);
            tvTime = getView(R.id.tv_time);
            tvRateTag = getView(R.id.tv_rate_tag);
            tvPcsTag = getView(R.id.tv_pcs_tag);
            tvUsdtTag = getView(R.id.tv_usdt_tag);
            tvRate = getView(R.id.tv_rate);
            tvPcs = getView(R.id.tv_pcs);
            tvUsdt = getView(R.id.tv_usdt);
        }
    }
}
