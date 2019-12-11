package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wallet.hz.R;
import com.wallet.hz.model.ModelReleaseInfo;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: ModelEarningAdapter
 * @Description: 模式主页收益列表适配器
 * @Author: ZL
 * @CreateDate: 2019/11/04 11:33
 */
public class ModelEarningAdapter extends BaseRecyclerViewAdapter<ModelReleaseInfo, ModelEarningAdapter.ModelEarningHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public ModelEarningAdapter(List<ModelReleaseInfo> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(ModelEarningHolder modelEarningHolder, ModelReleaseInfo item) {
        modelEarningHolder.ivState.setImageResource(item.getJoinTypeIcon(mContext));
        modelEarningHolder.tvTime.setText(item.getCreateTime());
        modelEarningHolder.tvAward.setText(BigDecimalUtils.formatServiceNumber(item.getWaamount()) + " USDT");
    }

    @NonNull
    @Override
    public ModelEarningHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_model_earning_list, viewGroup, false);
        ModelEarningHolder modelEarningHolder = new ModelEarningHolder(view);
        return modelEarningHolder;
    }

    public static class ModelEarningHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        ImageView ivState;
        AutoSizeTextView tvTime;
        AutoSizeTextView tvAward;

        public ModelEarningHolder(View itemView) {
            super(itemView);
            ivState = getView(R.id.iv_earning_state);
            tvTime = getView(R.id.tv_earning_time);
            tvAward = getView(R.id.tv_earning_award);
        }
    }
}
