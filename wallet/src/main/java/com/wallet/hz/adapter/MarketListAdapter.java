package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.MarketList;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.GlideImageUtil;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: MarketListAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 19:40
 */
public class MarketListAdapter extends BaseRecyclerViewAdapter<MarketList, MarketListAdapter.MarketListHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public MarketListAdapter(List<MarketList> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(MarketListHolder marketListHolder, MarketList item) {
        marketListHolder.tvName.setText(item.getName());
        marketListHolder.tvPrice.setText("$" + BigDecimalUtils.formatServiceNumber(item.getCurrent_price_usd()));
        String fu = BigDecimalUtils.decimalPoint(item.getChange_percent() + "", 2);
        if (BigDecimalUtils.compare("0", fu)) {
            marketListHolder.tvUp.setText(fu + "%");
            marketListHolder.tvUp.setBackground(ResourcesUtil.getDrawable(mContext, R.drawable.bg_red_corners));
        } else {
            marketListHolder.tvUp.setText("+" + fu + "%");
            marketListHolder.tvUp.setBackground(ResourcesUtil.getDrawable(mContext, R.drawable.bg_green_corners));
        }
        if (StringUtil.isContain("HS", item.getName())) {
            marketListHolder.ivCoin.setImageResource(R.drawable.hangqing_hs);
        } else {
            GlideImageUtil.with(mContext, item.getLogo(), marketListHolder.ivCoin);
        }
    }

    @NonNull
    @Override
    public MarketListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_market_list, viewGroup, false);
        MarketListHolder marketListHolder = new MarketListHolder(view);
        return marketListHolder;
    }

    public static class MarketListHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        ImageView ivCoin;
        TextView tvName;
        AutoSizeTextView tvPrice;
        TextView tvUp;

        public MarketListHolder(View itemView) {
            super(itemView);
            ivCoin = getView(R.id.iv_market_coin);
            tvName = getView(R.id.tv_market_coin_name);
            tvPrice = getView(R.id.tv_market_coin_price);
            tvUp = getView(R.id.tv_market_coin_up);
        }
    }
}
