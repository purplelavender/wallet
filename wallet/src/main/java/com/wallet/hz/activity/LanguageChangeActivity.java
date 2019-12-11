package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wallet.hz.R;

import butterknife.BindView;
import butterknife.OnClick;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.manager.AppManager;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: LanguageChangeActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/22 17:09
 */
public class LanguageChangeActivity extends BaseAppActivity {

    @BindView(R.id.iv_simplified)
    ImageView ivSimplified;
    @BindView(R.id.iv_traditional)
    ImageView ivTraditional;
    @BindView(R.id.iv_english)
    ImageView ivEnglish;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, LanguageChangeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_language;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();

        int language = SpUtil.getLanguage(mAppContext);
        ivSimplified.setVisibility(language == LanguageEnums.SIMPLIFIED_CHINESE.getKey() ? View.VISIBLE : View.GONE);
        ivTraditional.setVisibility(language == LanguageEnums.TRADITIONAL_CHINESE.getKey() ? View.VISIBLE : View.GONE);
        ivEnglish.setVisibility(language == LanguageEnums.ENGLISH.getKey() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_language), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    @OnClick({R.id.ll_simplified, R.id.ll_traditional, R.id.ll_english, R.id.ll_japan, R.id.ll_fayu, R.id.ll_hanyu, R.id.ll_eluosi})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_simplified:
                changeLanguageUI(LanguageEnums.SIMPLIFIED_CHINESE.getKey());
                break;
            case R.id.ll_traditional:
                changeLanguageUI(LanguageEnums.TRADITIONAL_CHINESE.getKey());
                break;
            case R.id.ll_english:
                changeLanguageUI(LanguageEnums.ENGLISH.getKey());
                break;
            case R.id.ll_japan:
            case R.id.ll_hanyu:
            case R.id.ll_fayu:
            case R.id.ll_eluosi:
                showToast(CommonToast.ToastType.TEXT, getString(R.string.language_wait));
                break;
            default:
                changeLanguageUI(LanguageEnums.ENGLISH.getKey());
                break;
        }
    }

    /**
     * 修改语言切换页面的UI显示
     * @param language
     */
    private void changeLanguageUI(int language) {
        ivSimplified.setVisibility(language == LanguageEnums.SIMPLIFIED_CHINESE.getKey() ? View.VISIBLE : View.GONE);
        ivTraditional.setVisibility(language == LanguageEnums.TRADITIONAL_CHINESE.getKey() ? View.VISIBLE : View.GONE);
        ivEnglish.setVisibility(language == LanguageEnums.ENGLISH.getKey() ? View.VISIBLE : View.GONE);
        SpUtil.setLanguage(mAppContext, language);
        EnvironmentUtil.changeAppLanguage(mAppContext, LanguageEnums.getLanguageByKye(language));

        AppManager.getAppManager().appExit();
        AppStartActivity.startIntent(LanguageChangeActivity.this);
    }
}
