package com.wallet.hz.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.activity.ExchangeActivity;
import com.wallet.hz.activity.ExtractActivity;
import com.wallet.hz.activity.JoinActivity;
import com.wallet.hz.activity.NoticeDetailActivity;
import com.wallet.hz.activity.NoticeListActivity;
import com.wallet.hz.activity.ValueAddActivity;
import com.wallet.hz.adapter.TabLayoutViewPagerAdapter;
import com.wallet.hz.model.ModelInfo;
import com.wallet.hz.presenter.ModelContract;
import com.wallet.hz.presenter.ModelPresenter;
import com.wallet.hz.utils.LoginDialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppMVPFragment;
import share.exchange.framework.bean.NoticeBean;
import share.exchange.framework.utils.BigDecimalUtils;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.HackyViewPager;
import share.exchange.framework.widget.MyScrollView;
import share.exchange.framework.widget.NoticeView;

/**
 * @ClassName: ModelFragment
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/21 14:29
 */
public class ModelFragment extends BaseAppMVPFragment<ModelPresenter> implements ModelContract.View {

    @BindView(R.id.scroll_model)
    MyScrollView mScrollView;
    @BindView(R.id.notice_view)
    NoticeView mNoticeView;
    @BindView(R.id.cb_model_eye)
    CheckBox cbEye;
    @BindView(R.id.tv_model_available)
    TextView tvAvailable;
    @BindView(R.id.tv_model_principal)
    TextView tvPrincipal;
    @BindView(R.id.tv_model_amount)
    TextView tvAmount;
    @BindView(R.id.tv_model_dig)
    TextView tvDig;
    @BindView(R.id.tab_model_record)
    TabLayout tabRecord;
    @BindView(R.id.vp_model_record)
    HackyViewPager vpRecord;

    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private ModelInfo mModelInfo;

    public static ModelFragment newInstance() {
        Bundle args = new Bundle();
        ModelFragment fragment = new ModelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_model;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        mScrollView.scrollTo(0, 0);
        setToolBarBackground(R.drawable.moshi_beijing);
        cbEye.setChecked(true);
        initFragment();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.modle_project), null, null, null);
        setToolLeftClick("", ResourcesUtil.getDrawable(mAppContext, R.drawable.shouye_icon_gonggao), null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeListActivity.startIntent(getActivity());
            }
        });
        // TODO  当前版本暂时不显示奖金池相关信息
//        setToolRightClick(getString(R.string.modle_award_pool), null, null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AwardDestructionActivity.startIntent(getActivity());
//            }
//        });
        setTitleTextColor(ResourcesUtil.getColor(mAppContext, R.color.white));
        setRightTextColor(ResourcesUtil.getColor(mAppContext, R.color.white));
        mNoticeView.setOnItemClickListener(new NoticeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                NoticeBean noticeBean = getPresenter().getNoticeBeans().get(position);
                NoticeDetailActivity.startIntent(getActivity(), noticeBean);
            }
        });
        cbEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text = tvAmount.getText().toString();
                if (isChecked) {
                    tvAmount.setText(text);
                } else {
                    tvAmount.setText(StringUtil.getStarString(text));
                }
            }
        });
    }

    @Override
    protected void processLogic() {
        getPresenter().getModelInfo();
//        getPresenter().getHsRate();
        getPresenter().getNoticeList();
    }

    @Override
    public void onResume() {
        super.onResume();
        processLogic();
    }

    @Override
    public ModelPresenter injectDependencies() {
        return new ModelPresenter(this, mAppContext);
    }

    @OnClick({R.id.ll_model_exchange, R.id.ll_model_value_add, R.id.ll_model_join, R.id.ll_model_extract})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_model_exchange:
                ExchangeActivity.startIntent(getActivity());
                break;
            case R.id.ll_model_value_add:
                ValueAddActivity.startIntent(getActivity());
                break;
            case R.id.ll_model_join:
                JoinActivity.startIntent(getActivity(), mModelInfo);
                break;
            case R.id.ll_model_extract:
                ExtractActivity.startIntent(getActivity(), mModelInfo == null ? "0" : mModelInfo.getCapital() + "");
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoginDialog() {
        if (isAdded()) {
            LoginDialogUtil.getInstance().showLoginDialog(getActivity());
        }
    }

    @Override
    public void onNoticeSuccess(ArrayList<NoticeBean> noticeList) {
        if (noticeList.size() > 0) {
            mNoticeView.setVisibility(View.VISIBLE);
            mNoticeView.setData(noticeList);
        } else {
            mNoticeView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNoticeFailed() {
        mNoticeView.setVisibility(View.GONE);
    }

    @Override
    public void onModelInfoSuccess(ModelInfo modelInfo) {
        this.mModelInfo = modelInfo;
        if (modelInfo != null) {
            tvAmount.setText(BigDecimalUtils.formatServiceNumber(modelInfo.getReleased()));
            String digText = getString(R.string.modle_dig_earning, BigDecimalUtils.formatServiceNumber(modelInfo.getMinables()));
            SpannableString spannableString = new SpannableString(digText);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_yellow));
            spannableString.setSpan(foregroundColorSpan, SpUtil.getLanguage(mAppContext) == 0 ? 21 : 6, digText.lastIndexOf(" "), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(EnvironmentUtil.sp2px(mAppContext, 18));
            spannableString.setSpan(absoluteSizeSpan, SpUtil.getLanguage(mAppContext) == 0 ? 21 : 6, digText.lastIndexOf(" "), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvDig.setText(spannableString);
            tvAvailable.setText(BigDecimalUtils.formatServiceNumber(modelInfo.getAvailabs()));
            tvPrincipal.setText(BigDecimalUtils.formatServiceNumber(modelInfo.getCapital()));
        } else {
            tvAmount.setText("0.0000");
            tvDig.setText(getString(R.string.modle_dig_earning, "0"));
            tvAvailable.setText("0.0000");
            tvPrincipal.setText("0.0000");
        }
    }

    private void initFragment() {
        titleList.clear();
        fragmentList.clear();

        titleList.add(getString(R.string.modle_join_record));
        titleList.add(getString(R.string.modle_release_record));

        fragmentList.add(ModelJoinFragment.newInstance());
        fragmentList.add(ModelEarningFragment.newInstance());

        TabLayoutViewPagerAdapter adapter = new TabLayoutViewPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        vpRecord.setAdapter(adapter);
        vpRecord.setOffscreenPageLimit(fragmentList.size());
        tabRecord.setupWithViewPager(vpRecord);
        tabRecord.getTabAt(0).select();
    }

    public void updateData(){
        processLogic();
    }
}
