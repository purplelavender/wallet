package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.bean.NoticeBean;

/**
 * @ClassName: NoticeListAdapter
 * @Description: 公告列表适配器
 * @Author: ZL
 * @CreateDate: 2019/10/31 14:37
 */
public class NoticeListAdapter extends BaseRecyclerViewAdapter<NoticeBean, NoticeListAdapter.NoticeHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public NoticeListAdapter(List<NoticeBean> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(NoticeHolder noticeHolder, NoticeBean item) {
        if (item.isRead()) {
            noticeHolder.ivRead.setImageResource(R.drawable.point_gray);
        } else {
            noticeHolder.ivRead.setImageResource(R.drawable.point_red);
        }
        noticeHolder.tvTitle.setText(item.getTitle());
        noticeHolder.tvTime.setText(item.getCreateTime());
    }

    @NonNull
    @Override
    public NoticeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_notice_list, viewGroup, false);
        NoticeHolder noticeHolder = new NoticeHolder(view);
        return noticeHolder;
    }

    public static class NoticeHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        ImageView ivRead;
        TextView tvTitle;
        TextView tvTime;

        public NoticeHolder(View itemView) {
            super(itemView);
            ivRead = getView(R.id.iv_notice_read);
            tvTitle = getView(R.id.tv_notice_title);
            tvTime = getView(R.id.tv_notice_time);
        }
    }
}
