package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wallet.hz.R;
import com.wallet.hz.model.TuiDetail;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: TuiDetailListAdapter
 * @Description: 推广详情列表
 * @Author: ZL
 * @CreateDate: 2019/12/17 09:35
 */
public class TuiDetailListAdapter extends BaseRecyclerViewAdapter<TuiDetail, TuiDetailListAdapter.TuiDetailHolder> {

    private Context mContext;
    private int type;

    /**
     * @param dataList the datas to attach the adapter
     */
    public TuiDetailListAdapter(List<TuiDetail> dataList, Context mContext, int type) {
        super(dataList);
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    protected void bindDataToItemView(TuiDetailHolder viewHolder, TuiDetail item) {
        viewHolder.tvName.setText(item.getFrommobile());
        if (type == 0) {
            viewHolder.llHs.setVisibility(View.GONE);
            viewHolder.tvAmountUsdt.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_green));
            viewHolder.tvAmountUsdt.setText("+ " + BigDecimalUtils.formatServiceNumber(item.getUsdtamount())+ " USDT");
        } else if (type == 1) {
            viewHolder.llHs.setVisibility(View.VISIBLE);
            viewHolder.tvAmountUsdt.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_black));
            viewHolder.tvAmountUsdt.setText(BigDecimalUtils.formatServiceNumber(item.getPcsamount())+ " USDT");
            viewHolder.tvAmountHs.setText("+ " + BigDecimalUtils.formatServiceNumber(item.getHsamount())+ " HS");
            viewHolder.tvRate.setText("1 HS ≈ " + BigDecimalUtils.formatServiceNumber(item.getHuilv()) + " USDT");
        } else if (type == 2) {
            viewHolder.llHs.setVisibility(View.VISIBLE);
            viewHolder.tvAmountUsdt.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_black));
            viewHolder.tvAmountUsdt.setText(BigDecimalUtils.formatServiceNumber(item.getJdusdt())+ " USDT");
            viewHolder.tvAmountHs.setText("+ " + BigDecimalUtils.formatServiceNumber(item.getJdamount())+ " HS");
            viewHolder.tvRate.setText("1 HS ≈ " + BigDecimalUtils.formatServiceNumber(item.getHuilv()) + " USDT");
        }
    }

    @NonNull
    @Override
    public TuiDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_tuiguang_detail_list, viewGroup, false);
        TuiDetailHolder viewHolder = new TuiDetailHolder(view);
        return viewHolder;
    }

    public static class TuiDetailHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        AutoSizeTextView tvName;
        AutoSizeTextView tvAmountUsdt;
        AutoSizeTextView tvRate;
        AutoSizeTextView tvAmountHs;
        LinearLayout llHs;

        public TuiDetailHolder(View itemView) {
            super(itemView);
            tvName = getView(R.id.tv_tuiguang_name);
            tvAmountUsdt = getView(R.id.tv_tuiguang_amount_usdt);
            tvRate = getView(R.id.tv_tuiguang_rate);
            tvAmountHs = getView(R.id.tv_tuiguang_amount_hs);
            llHs = getView(R.id.ll_tuiguang_hs);
        }
    }
}
