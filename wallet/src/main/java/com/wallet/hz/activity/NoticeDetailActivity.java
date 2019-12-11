package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wallet.hz.R;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.bean.NoticeBean;
import share.exchange.framework.utils.ResourcesUtil;

/**
 * @ClassName: NoticeDetailActivity
 * @Description: 公告详情页面
 * @Author: ZL
 * @CreateDate: 2019/10/28 15:48
 */
public class NoticeDetailActivity extends BaseAppActivity {

    @BindView(R.id.tv_notice_title)
    TextView tvTitle;
    @BindView(R.id.tv_notice_content)
    TextView tvContent;
    @BindView(R.id.tv_notice_time)
    TextView tvTime;

    public static void startIntent(Activity activity, NoticeBean noticeBean) {
        Intent intent = new Intent(activity, NoticeDetailActivity.class);
        intent.putExtra("notice", noticeBean);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
        NoticeBean noticeBean = (NoticeBean) getIntent().getSerializableExtra("notice");
//        tvTitle.setText(noticeBean.getTitle());
        tvContent.setText(ResourcesUtil.fromHtml(noticeBean.getContents()));
//        tvTime.setText(noticeBean.getCreateTime());
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.market_tips), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }
}
