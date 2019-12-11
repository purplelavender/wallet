package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.hz.R;
import com.wallet.hz.model.GroupInfoData;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: FinancialDetailAdapter
 * @Description: 社群收益列表
 * @Author: ZL
 * @CreateDate: 2019/11/07 21:18
 */
public class GroupInfoDataAdapter extends BaseRecyclerViewAdapter<GroupInfoData, GroupInfoDataAdapter.GroupInfoDataHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public GroupInfoDataAdapter(List<GroupInfoData> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(GroupInfoDataHolder financialDetailHolder, GroupInfoData item) {
        financialDetailHolder.tvTime.setText(item.getDatas());
        financialDetailHolder.tvName.setText(item.getTypeText(mContext));
        financialDetailHolder.tvAmount.setText("+" + BigDecimalUtils.formatServiceNumber(item.getTamount()) + item.getCoinname());
    }

    @NonNull
    @Override
    public GroupInfoDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_financial_detail_list, viewGroup, false);
        GroupInfoDataHolder financialDetailHolder = new GroupInfoDataHolder(view);
        return financialDetailHolder;
    }

    public static class GroupInfoDataHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        AutoSizeTextView tvName;
        AutoSizeTextView tvTime;
        AutoSizeTextView tvAmount;

        public GroupInfoDataHolder(View itemView) {
            super(itemView);
            tvName = getView(R.id.tv_financial_name);
            tvTime = getView(R.id.tv_financial_time);
            tvAmount = getView(R.id.tv_financial_amount);
        }
    }
}
