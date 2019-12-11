package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.hz.R;
import com.wallet.hz.model.FinancialDetailInfo;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: FinancialDetailAdapter
 * @Description: 财务明细列表适配器
 * @Author: ZL
 * @CreateDate: 2019/11/07 21:18
 */
public class FinancialDetailAdapter extends BaseRecyclerViewAdapter<FinancialDetailInfo, FinancialDetailAdapter.FinancialDetailHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public FinancialDetailAdapter(List<FinancialDetailInfo> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(FinancialDetailHolder financialDetailHolder, FinancialDetailInfo item) {
        financialDetailHolder.tvTime.setText(item.getCreateTime());
        setTypeText(financialDetailHolder, item);
    }

    @NonNull
    @Override
    public FinancialDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_financial_detail_list, viewGroup, false);
        FinancialDetailHolder financialDetailHolder = new FinancialDetailHolder(view);
        return financialDetailHolder;
    }

    private void setTypeText(FinancialDetailHolder financialDetailHolder, FinancialDetailInfo item) {
        int type = item.getType();
        String name = "";
        String amount = "";
        int color = StringUtil.isEqual("+", item.getCommas()) ? ResourcesUtil.getColor(mContext, R.color.color_green) : ResourcesUtil.getColor(mContext, R.color.tab_selected_color);
        switch (type) {
            case 1:
                name = mContext.getString(R.string.state_mingxi_type_release);
                amount = BigDecimalUtils.formatServiceNumber(item.getSfamount());
                break;
            case 2:
                name = mContext.getString(R.string.state_mingxi_type_join);
                amount = BigDecimalUtils.formatServiceNumber(item.getCyamount());
                break;
            case 3:
                name = mContext.getString(R.string.state_mingxi_type_exchange);
                amount = StringUtil.isEqual("+", item.getCommas()) ? BigDecimalUtils.formatServiceNumber(item.getDhamount()) : BigDecimalUtils.formatServiceNumber(item.getDamount());
                break;
            case 4:
                name = mContext.getString(R.string.state_mingxi_type_extract);
                amount = StringUtil.isEqual("+", item.getCommas()) ? BigDecimalUtils.formatServiceNumber(item.getDzamount()) : BigDecimalUtils.formatServiceNumber(item.getTqamount());
                break;
            case 5:
                name = mContext.getString(R.string.state_mingxi_type_value);
                amount = StringUtil.isEqual("+", item.getCommas()) ? BigDecimalUtils.formatServiceNumber(item.getBzamount()) : BigDecimalUtils.formatServiceNumber(item.getGmamount());
                break;
            case 6:
                name = mContext.getString(R.string.state_mingxi_type_dig);
                amount = BigDecimalUtils.formatServiceNumber(item.getWkamount());
                break;
            case 7:
                name = mContext.getString(R.string.state_mingxi_type_tui);
                amount = BigDecimalUtils.formatServiceNumber(item.getWkamount());
                break;
            case 8:
                name = mContext.getString(R.string.state_mingxi_type_node);
                amount = BigDecimalUtils.formatServiceNumber(item.getWkamount());
                break;
            case 0:
            default:
                break;
        }
        financialDetailHolder.tvName.setText(name);
        financialDetailHolder.tvAmount.setText(item.getCommas() + amount + item.getCoinname());
        financialDetailHolder.tvAmount.setTextColor(color);
    }

    public static class FinancialDetailHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        AutoSizeTextView tvName;
        AutoSizeTextView tvTime;
        AutoSizeTextView tvAmount;

        public FinancialDetailHolder(View itemView) {
            super(itemView);
            tvName = getView(R.id.tv_financial_name);
            tvTime = getView(R.id.tv_financial_time);
            tvAmount = getView(R.id.tv_financial_amount);
        }
    }
}
