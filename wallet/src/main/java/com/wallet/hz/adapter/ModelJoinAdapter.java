package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.ExchangeInfo;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;

/**
 * @ClassName: ModelJoinAdapter
 * @Description: 模式参与记录适配器
 * @Author: ZL
 * @CreateDate: 2019/11/04 10:51
 */
public class ModelJoinAdapter extends BaseRecyclerViewAdapter<ExchangeInfo, ModelJoinAdapter.ModelJoinHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public ModelJoinAdapter(List<ExchangeInfo> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(ModelJoinHolder modelJoinHolder, ExchangeInfo item) {
        if (item.isLocked()) {
            modelJoinHolder.ivState.setImageResource(R.drawable.moshi_icon_suoding);
            modelJoinHolder.tvState.setText(mContext.getString(R.string.state_join_lock));
        } else {
            modelJoinHolder.ivState.setImageResource(R.drawable.moshi_icon_shifang);
            modelJoinHolder.tvState.setText(mContext.getString(R.string.state_join_release));
        }
        modelJoinHolder.tvAmount.setText(BigDecimalUtils.formatServiceNumber(item.getAmount()) + item.getCoinName());
        modelJoinHolder.tvTime.setText(item.getCreateTime());
    }

    @NonNull
    @Override
    public ModelJoinHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_model_join_list, viewGroup, false);
        ModelJoinHolder modelJoinHolder = new ModelJoinHolder(view);
        return modelJoinHolder;
    }

    public static class ModelJoinHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        ImageView ivState;
        TextView tvState;
        TextView tvAmount;
        TextView tvTime;

        public ModelJoinHolder(View itemView) {
            super(itemView);
            ivState = getView(R.id.iv_join_state);
            tvState = getView(R.id.tv_join_state);
            tvAmount = getView(R.id.tv_join_amount);
            tvTime = getView(R.id.tv_join_time);
        }
    }
}
