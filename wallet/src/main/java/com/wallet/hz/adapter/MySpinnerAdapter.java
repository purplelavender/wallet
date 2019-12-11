package com.wallet.hz.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.wallet.hz.R;

import java.util.ArrayList;

import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.widget.AutoSizeTextView;

/**
 * @ClassName: MySpinnerAdapter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/11 18:48
 */
public class MySpinnerAdapter implements SpinnerAdapter {

    private ArrayList<String> mList;
    private Context mContext;

    public MySpinnerAdapter(ArrayList<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AutoSizeTextView textView = new AutoSizeTextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(14f);
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(ResourcesUtil.getDrawable(mContext, R.drawable.bg_whitegray_circle_corners));
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ResourcesUtil.getDrawable(mContext, R.drawable.icon_xiala), null);
        textView.setCompoundDrawablePadding(EnvironmentUtil.dip2px(mContext, 5));
        textView.setText(mList.get(position));
        textView.setHeight(EnvironmentUtil.dip2px(mContext, 30));
        textView.setWidth(EnvironmentUtil.dip2px(mContext, 100));
        textView.setPadding(EnvironmentUtil.dip2px(mContext, 4), 0, EnvironmentUtil.dip2px(mContext, 4), 0);
        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_xiala_text, null);
        AutoSizeTextView textView = convertView.findViewById(R.id.tv_spinner_name);
        textView.setText(mList.get(position));
        return convertView;
    }

}
