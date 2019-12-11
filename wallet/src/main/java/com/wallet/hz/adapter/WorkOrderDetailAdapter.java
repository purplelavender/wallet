package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.WorkOrder;

import java.util.ArrayList;
import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.GlideImageUtil;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: WorkOrderDetailAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/31 10:18
 */
public class WorkOrderDetailAdapter extends BaseRecyclerViewAdapter<WorkOrder, WorkOrderDetailAdapter.OrderDetailHolder> {

    private Context mContext;
    private OnViewClickedListener mViewClickedListener;

    /**
     * @param dataList the datas to attach the adapter
     */
    public WorkOrderDetailAdapter(List<WorkOrder> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(OrderDetailHolder orderDetailHolder, final WorkOrder item) {
        int position = dataList.indexOf(item);
        if (item.isCustom()) {
            orderDetailHolder.llOrder.setVisibility(View.GONE);
            orderDetailHolder.llCustom.setVisibility(View.VISIBLE);
            orderDetailHolder.tvCustomTime.setText(item.getAnswerTime());
            orderDetailHolder.tvCustomContent.setText(ResourcesUtil.fromHtml(item.getContents()));
            orderDetailHolder.tvCustomReply.setVisibility(item.isEnd() ? View.GONE : View.VISIBLE);
            orderDetailHolder.tvCustomReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewClickedListener != null) {
                        mViewClickedListener.onReply(item);
                    }
                }
            });
        } else {
            orderDetailHolder.llOrder.setVisibility(View.VISIBLE);
            orderDetailHolder.llCustom.setVisibility(View.GONE);
            orderDetailHolder.tvOrderTitle.setText(item.getTitle());
            orderDetailHolder.tvOrderTime.setText(item.getCreateTime());
            orderDetailHolder.tvOrderContent.setText(ResourcesUtil.fromHtml(item.getContents()));
            if (StringUtil.isEmpty(item.getImgs())) {
                orderDetailHolder.llPhotos.setVisibility(View.GONE);
            } else {
                orderDetailHolder.llPhotos.setVisibility(View.VISIBLE);
                calculateImage(orderDetailHolder);
                ArrayList<String> imageList = new ArrayList<>();
                imageList.addAll(StringUtil.getUrlStrList(item.getImgs(), ","));
                changeBottomPhotoView(orderDetailHolder, imageList);
            }
            orderDetailHolder.llPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewClickedListener != null) {
                        mViewClickedListener.onPhotoView(item);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public OrderDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_order_detail, viewGroup, false);
        OrderDetailHolder orderDetailHolder = new OrderDetailHolder(view);
        return orderDetailHolder;
    }

    /**
     * 重新计算图片控件的宽高
     */
    private void calculateImage(OrderDetailHolder orderDetailHolder) {
        int wight = (EnvironmentUtil.screenWidth(mContext) - EnvironmentUtil.dip2px(mContext, 50)) / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wight, wight);
        params.setMargins(EnvironmentUtil.dip2px(mContext, 5), 0, 0, 0);
        orderDetailHolder.ivOne.setLayoutParams(params);
        orderDetailHolder.ivTwo.setLayoutParams(params);
        orderDetailHolder.ivThree.setLayoutParams(params);
        orderDetailHolder.ivFour.setLayoutParams(params);
        orderDetailHolder.ivFive.setLayoutParams(params);
    }

    /**
     * 根据选中的图片来更改底部图片控件的显示状况
     */
    private void changeBottomPhotoView(OrderDetailHolder orderDetailHolder, ArrayList<String> imageList) {
        int size = imageList.size();
        switch (size) {
            case 1:
                GlideImageUtil.with(mContext, imageList.get(0), orderDetailHolder.ivOne);
                orderDetailHolder.ivOne.setVisibility(View.VISIBLE);
                orderDetailHolder.ivTwo.setVisibility(View.INVISIBLE);
                orderDetailHolder.ivThree.setVisibility(View.INVISIBLE);
                orderDetailHolder.ivFour.setVisibility(View.INVISIBLE);
                orderDetailHolder.ivFive.setVisibility(View.INVISIBLE);
                break;
            case 2:
                GlideImageUtil.with(mContext, imageList.get(0), orderDetailHolder.ivOne);
                orderDetailHolder.ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(1), orderDetailHolder.ivTwo);
                orderDetailHolder.ivTwo.setVisibility(View.VISIBLE);
                orderDetailHolder.ivThree.setVisibility(View.INVISIBLE);
                orderDetailHolder.ivFour.setVisibility(View.INVISIBLE);
                orderDetailHolder.ivFive.setVisibility(View.INVISIBLE);
                break;
            case 3:
                GlideImageUtil.with(mContext, imageList.get(0), orderDetailHolder.ivOne);
                orderDetailHolder.ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(1), orderDetailHolder.ivTwo);
                orderDetailHolder.ivTwo.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(2), orderDetailHolder.ivThree);
                orderDetailHolder.ivThree.setVisibility(View.VISIBLE);
                orderDetailHolder.ivFour.setVisibility(View.INVISIBLE);
                orderDetailHolder.ivFive.setVisibility(View.INVISIBLE);
                break;
            case 4:
                GlideImageUtil.with(mContext, imageList.get(0), orderDetailHolder.ivOne);
                orderDetailHolder.ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(1), orderDetailHolder.ivTwo);
                orderDetailHolder.ivTwo.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(2), orderDetailHolder.ivThree);
                orderDetailHolder.ivThree.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(3), orderDetailHolder.ivFour);
                orderDetailHolder.ivFour.setVisibility(View.VISIBLE);
                orderDetailHolder.ivFive.setVisibility(View.INVISIBLE);
                break;
            case 5:
                GlideImageUtil.with(mContext, imageList.get(0), orderDetailHolder.ivOne);
                orderDetailHolder.ivOne.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(1), orderDetailHolder.ivTwo);
                orderDetailHolder.ivTwo.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(2), orderDetailHolder.ivThree);
                orderDetailHolder.ivThree.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(3), orderDetailHolder.ivFour);
                orderDetailHolder.ivFour.setVisibility(View.VISIBLE);
                GlideImageUtil.with(mContext, imageList.get(4), orderDetailHolder.ivFive);
                orderDetailHolder.ivFive.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public static class OrderDetailHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        LinearLayout llOrder;
        TextView tvOrderTitle;
        TextView tvOrderTime;
        TextView tvOrderContent;
        LinearLayout llPhotos;
        ImageView ivOne;
        ImageView ivTwo;
        ImageView ivThree;
        ImageView ivFour;
        ImageView ivFive;
        LinearLayout llCustom;
        TextView tvCustomTime;
        TextView tvCustomContent;
        TextView tvCustomReply;

        public OrderDetailHolder(View itemView) {
            super(itemView);
            llOrder = getView(R.id.ll_order_user);
            tvOrderTitle = getView(R.id.tv_order_title);
            tvOrderTime = getView(R.id.tv_order_time);
            tvOrderContent = getView(R.id.tv_order_content);
            llPhotos = getView(R.id.ll_photos);
            ivOne = getView(R.id.image_one);
            ivTwo = getView(R.id.image_two);
            ivThree = getView(R.id.image_three);
            ivFour = getView(R.id.image_four);
            ivFive = getView(R.id.image_five);
            llCustom = getView(R.id.ll_order_custom);
            tvCustomTime = getView(R.id.tv_custom_time);
            tvCustomContent = getView(R.id.tv_custom_content);
            tvCustomReply = getView(R.id.tv_custom_reply);
        }
    }

    public void setViewClickedListener(OnViewClickedListener viewClickedListener) {
        mViewClickedListener = viewClickedListener;
    }

    public interface OnViewClickedListener {

        void onPhotoView(WorkOrder workorder);

        void onReply(WorkOrder workOrder);
    }
}
