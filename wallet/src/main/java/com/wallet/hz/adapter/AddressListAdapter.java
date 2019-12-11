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
import com.wallet.hz.model.CoinAddress;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;

/**
 * @ClassName: AddressListAdapter
 * @Description: 地址本列表适配器
 * @Author: ZL
 * @CreateDate: 2019/11/05 10:52
 */
public class AddressListAdapter extends BaseRecyclerViewAdapter<CoinAddress, AddressListAdapter.AddressHolder> {

    private Context mContext;
    private OnClickedListener mOnClickedListener;

    /**
     * @param dataList the datas to attach the adapter
     */
    public AddressListAdapter(List<CoinAddress> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(AddressHolder addressHolder, final CoinAddress item) {
        addressHolder.tvMark.setText(item.getRemarks());
        addressHolder.tvAddress.setText(item.getAddress());
        addressHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickedListener != null) {
                    mOnClickedListener.onDelete(item);
                }
            }
        });
        addressHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickedListener != null) {
                    mOnClickedListener.onItemClick(item);
                }
            }
        });
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_address_list, viewGroup, false);
        AddressHolder addressHolder = new AddressHolder(view);
        return addressHolder;
    }

    public static class AddressHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        TextView tvMark;
        TextView tvAddress;
        ImageView ivDelete;
        LinearLayout llItem;

        public AddressHolder(View itemView) {
            super(itemView);
            tvMark = getView(R.id.tv_address_mark);
            tvAddress = getView(R.id.tv_address);
            ivDelete = getView(R.id.iv_delete);
            llItem = getView(R.id.ll_item);
        }
    }

    public void setOnClickedListener(OnClickedListener onClickedListener) {
        mOnClickedListener = onClickedListener;
    }

    public interface OnClickedListener{

        void onDelete(CoinAddress coinAddress);

        void onItemClick(CoinAddress coinAddress);
    }
}
