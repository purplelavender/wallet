package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.WorkOrder;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.ResourcesUtil;

/**
 * @ClassName: WorkOrderListAdapter
 * @Description: 工单列表适配器
 * @Author: ZL
 * @CreateDate: 2019/10/30 14:05
 */
public class WorkOrderListAdapter extends BaseRecyclerViewAdapter<WorkOrder, WorkOrderListAdapter.WorkOrderHolder> {

    private Context mContext;

    public WorkOrderListAdapter(List<WorkOrder> dataList, Context context) {
        super(dataList);
        this.mContext = context;
    }

    @Override
    protected void bindDataToItemView(WorkOrderHolder workOrderHolder, WorkOrder item) {
        workOrderHolder.tvTitle.setText(item.getTitle());
        if (item.isEnd()) {
            workOrderHolder.tvState.setText(mContext.getString(R.string.state_order_end));
            workOrderHolder.tvState.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_04041D));
        } else {
            workOrderHolder.tvState.setText(mContext.getString(R.string.state_order_reply));
            workOrderHolder.tvState.setTextColor(ResourcesUtil.getColor(mContext, R.color.color_green));
        }
        workOrderHolder.tvTime.setText(item.getCreateTime());
    }

    @NonNull
    @Override
    public WorkOrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_order_list, viewGroup, false);
        WorkOrderHolder holder = new WorkOrderHolder(view);
        return holder;
    }

    public static class WorkOrderHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        TextView tvTitle;
        TextView tvState;
        TextView tvTime;

        public WorkOrderHolder(View itemView) {
            super(itemView);
            tvTitle = getView(R.id.tv_order_title);
            tvState = getView(R.id.tv_order_state);
            tvTime = getView(R.id.tv_order_time);
        }
    }
}
