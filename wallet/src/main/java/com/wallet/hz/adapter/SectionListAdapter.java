package com.wallet.hz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.CountryBean;
import com.wallet.hz.model.CountryMenu;

import java.util.ArrayList;
import java.util.List;

import share.exchange.framework.base.SectionRecyclerAdapter;

/**
 * Created by MMM on 2018/1/12.
 */
public class SectionListAdapter extends SectionRecyclerAdapter<SectionListAdapter.HeaderHolder, SectionListAdapter.DescHolder, RecyclerView.ViewHolder> {

    public List<CountryMenu> mNewsMenus;
    private Context mContext;
    private LayoutInflater mInflater;

    private List showingDatas = new ArrayList<>();
    private List<List<CountryBean>> childDatas = new ArrayList<>();

    public SectionListAdapter(Context context, List<CountryMenu> newsMenus) {
        mNewsMenus = newsMenus;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        setShowingDatas();
    }

    public void setData(List<CountryMenu> newsMenus) {
        mNewsMenus = newsMenus;
        notifyDataSetChanged();
    }

    private void setShowingDatas() {
        showingDatas.clear();
        childDatas.clear();
        for (int i = 0; i < mNewsMenus.size(); i++) {
            String menuName = mNewsMenus.get(i).getMenuName();
            boolean isExpand = mNewsMenus.get(i).isExpand();
            List<CountryBean> newsDatas = mNewsMenus.get(i).getCountryBeen();
            showingDatas.add(menuName);
            if (isExpand) {
                showingDatas.addAll(newsDatas);
            }
        }
    }

    public int getPositionGroup(int groupPos) {
        for (int i = 0; i < showingDatas.size(); i++) {
            Object item = showingDatas.get(i);
            if (item instanceof String) {
                if (item.equals(mNewsMenus.get(groupPos).getMenuName())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getPositionChild(int section, int position) {
        for (int i = 0; i < showingDatas.size(); i++) {
            Object item = showingDatas.get(i);
            if (item instanceof CountryBean) {
                if (item.equals(mNewsMenus.get(section).getCountryBeen().get(position))) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void delete(int section, int position) {
        mNewsMenus.get(section).getCountryBeen().remove(position);
        setShowingDatas();
        notifyDataSetChanged();
//        notifyItemRemoved(position);
//        if (position != (mItems.size())) { // 如果移除的是最后一个，忽略
//            notifyItemRangeChanged(position, this.mItems.size() - position);
//        }
    }

    @Override
    protected int getSectionCount() {
        return mNewsMenus == null ? 0 : mNewsMenus.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = mNewsMenus.get(section).getCountryBeen().size();
        if (count >= 0 && !mNewsMenus.get(section).isExpand()) {
            count = 0;
        }
        return mNewsMenus.get(section).getCountryBeen() == null ? 0 : count;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.adapter_section_title, parent, false));
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.adapter_section_item, parent, false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        CountryMenu newsMenu = mNewsMenus.get(section);
        holder.tvGroup.setText(newsMenu.getMenuName());
        holder.tvExpand.setText(newsMenu.isExpand() ? mContext.getString(R.string.assets_section_close) : mContext.getString(R.string.assets_section_expend));
    }

    @Override
    protected void onBindItemViewHolder(final DescHolder holder, final int section, final int position) {
        CountryMenu newsMenu = mNewsMenus.get(section);
        List<CountryBean> newsDatas = newsMenu.getCountryBeen();
        CountryBean newsInfo = newsDatas.get(position);
        holder.tvChild.setText(newsInfo.name);
        holder.tvCodenum.setText(newsInfo.code);

        // 设置监听事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, section, position);
                }
            });
        }

        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mItemLongClickListener.onItemLongClick(holder.itemView, section, position);
                    return true;
                }
            });
        }
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int section, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int section, int position);
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        public TextView tvGroup;
        public TextView tvExpand;

        public HeaderHolder(View itemView) {
            super(itemView);
            tvGroup = (TextView) itemView.findViewById(R.id.tv_group);
            tvExpand = (TextView) itemView.findViewById(R.id.tv_expand);
        }
    }

    public static class DescHolder extends RecyclerView.ViewHolder {

        public TextView tvChild;
        public TextView tvCodenum;

        public DescHolder(View itemView) {
            super(itemView);
            tvChild = (TextView) itemView.findViewById(R.id.tv_child);
            tvCodenum = (TextView) itemView.findViewById(R.id.tv_codenum);
        }
    }
}
