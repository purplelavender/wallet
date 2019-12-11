package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wallet.hz.R;
import com.wallet.hz.model.GroupFriend;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: GroupFriendAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/08 02:18
 */
public class GroupFriendAdapter extends BaseRecyclerViewAdapter<GroupFriend, GroupFriendAdapter.GroupFriendHolder> {

    private Context mContext;
    private int type;

    /**
     * @param dataList the datas to attach the adapter
     */
    public GroupFriendAdapter(List<GroupFriend> dataList, Context mContext, int type) {
        super(dataList);
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    protected void bindDataToItemView(GroupFriendHolder groupFriendHolder, GroupFriend item) {
        groupFriendHolder.tvName.setText(item.getUsername());
        groupFriendHolder.tvToday.setText(BigDecimalUtils.formatServiceNumber(item.getTodayamount()));
        groupFriendHolder.tvTotal.setText(BigDecimalUtils.formatServiceNumber(item.getTotalusdt()));
        groupFriendHolder.tvTodayTotal.setText(BigDecimalUtils.formatServiceNumber(item.getSqamount()));
        groupFriendHolder.tvLook.setVisibility(type >= 3 ? View.GONE : View.VISIBLE);
    }

    @NonNull
    @Override
    public GroupFriendHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_group_frend_list, viewGroup, false);
        GroupFriendHolder groupFriendHolder = new GroupFriendHolder(view);
        return groupFriendHolder;
    }

    public int getType() {
        return type;
    }

    public static class GroupFriendHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        AutoSizeTextView tvName;
        AutoSizeTextView tvLook;
        AutoSizeTextView tvToday;
        AutoSizeTextView tvTodayTotal;
        AutoSizeTextView tvTotal;

        public GroupFriendHolder(View itemView) {
            super(itemView);
            tvName = getView(R.id.tv_group_my_code);
            tvLook = getView(R.id.tv_group_look_friend);
            tvToday = getView(R.id.tv_group_today_award);
            tvTotal = getView(R.id.tv_group_total_award);
            tvTodayTotal = getView(R.id.tv_group_today_total_award);
        }
    }
}
