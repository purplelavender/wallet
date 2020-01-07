package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.wallet.hz.R;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.constant.LanguageEnums;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.SpUtil;
import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: GatheringPhotoActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2020/01/06 15:44
 */
public class GatheringPhotoActivity extends BaseAppActivity {

    @BindView(R.id.iv_gathering_lizi)
    ImageView ivLiZi;

    private String name = "";

    public static void startIntent(Activity activity, String name) {
        Intent intent = new Intent(activity, GatheringPhotoActivity.class);
        intent.putExtra("name", name);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gathering_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
        name = getIntent().getStringExtra("name");
        initLiZiPhoto();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    private void initLiZiPhoto() {
        int language = SpUtil.getLanguage(mAppContext);
        if (language == LanguageEnums.ENGLISH.getKey()) {
            if (StringUtil.isEqual("VDS", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_yingwen));
            } else if (StringUtil.isEqual("DDAM", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
            }
        } else if (language == LanguageEnums.SIMPLIFIED_CHINESE.getKey()) {
            if (StringUtil.isEqual("VDS", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_lizi));
            } else if (StringUtil.isEqual("DDAM", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
            }
        } else if (language == LanguageEnums.TRADITIONAL_CHINESE.getKey()) {
            if (StringUtil.isEqual("VDS", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_fanti));
            } else if (StringUtil.isEqual("DDAM", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
            }
        } else {
            if (StringUtil.isEqual("VDS", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.vds_yingwen));
            } else if (StringUtil.isEqual("DDAM", name)) {
                ivLiZi.setImageDrawable(ResourcesUtil.getDrawable(mAppContext, R.drawable.ddam_lizi));
            }
        }
    }
}
