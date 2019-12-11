package com.wallet.hz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.model.ProjectApply;

import java.util.List;

import share.exchange.framework.base.BaseRecyclerViewAdapter;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: ProjectApplyAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 23:53
 */
public class ProjectApplyAdapter extends BaseRecyclerViewAdapter<ProjectApply, ProjectApplyAdapter.ProjectApplyHolder> {

    private Context mContext;

    /**
     * @param dataList the datas to attach the adapter
     */
    public ProjectApplyAdapter(List<ProjectApply> dataList, Context mContext) {
        super(dataList);
        this.mContext = mContext;
    }

    @Override
    protected void bindDataToItemView(ProjectApplyHolder projectApplyHolder, ProjectApply item) {
        projectApplyHolder.tvName.setText(item.getNames());
        projectApplyHolder.tvWeb.setText(item.getGuanwang());
        projectApplyHolder.tvState.setText(item.getStateText(mContext));
        projectApplyHolder.tvState.setTextColor(item.getStateTextColor(mContext));
    }

    @NonNull
    @Override
    public ProjectApplyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_project_apply_list, viewGroup, false);
        ProjectApplyHolder projectApplyHolder = new ProjectApplyHolder(view);
        return projectApplyHolder;
    }

    public static class ProjectApplyHolder extends BaseRecyclerViewAdapter.SparseArrayViewHolder {

        AutoSizeTextView tvName;
        AutoSizeTextView tvState;
        TextView tvWeb;

        public ProjectApplyHolder(View itemView) {
            super(itemView);
            tvName = getView(R.id.tv_project_name);
            tvState = getView(R.id.tv_project_state);
            tvWeb = getView(R.id.tv_project_web);
        }
    }
}
