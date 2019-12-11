package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.presenter.MnemonicCheckContract;
import com.wallet.hz.presenter.MnemonicCheckPresenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppMVPActivity;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.ResourcesUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;
import share.exchange.framework.widget.WrapLinearLayout;

/**
 * @ClassName: MnemonicCheckActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/01 10:49
 */
public class MnemonicCheckActivity extends BaseAppMVPActivity<MnemonicCheckPresenter> implements MnemonicCheckContract.View {

    @BindView(R.id.ll_select_word)
    WrapLinearLayout llSelectWord;
    @BindView(R.id.ll_check_word)
    WrapLinearLayout llCheckWord;
    @BindView(R.id.tv_mnemonic_sure)
    TextView tvSure;

    private boolean isFindMoney = false, isFindPassword = false;
    private String mnemonicWord, name, password, inviteCode;
    private HashMap<String, TextView> viewMap = new HashMap<>();
    private HashMap<String, Boolean> selectMap = new HashMap<>();
    private HashMap<String, Boolean> checkMap = new HashMap<>();

    public static void startIntentResult(Activity activity, boolean isFindMoney, boolean isFindPassword, String mnemonicWord, String name, String
            password, String inviteCode, int requestCode) {
        Intent intent = new Intent(activity, MnemonicCheckActivity.class);
        intent.putExtra("isFindMoney", isFindMoney);
        intent.putExtra("isFindPassword", isFindPassword);
        intent.putExtra("mnemonic", mnemonicWord);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("inviteCode", inviteCode);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_check_mnemonic_word;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        showSuccessStateLayout();
        tvSure.setText(isFindMoney || isFindPassword ? getString(R.string.bind_sure) : getString(R.string.regist_sure));

        createMnemonicView();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMnemonicSelect();
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    public MnemonicCheckPresenter injectDependencies() {
        isFindMoney = getIntent().getBooleanExtra("isFindMoney", false);
        isFindPassword = getIntent().getBooleanExtra("isFindPassword", false);
        mnemonicWord = getIntent().getStringExtra("mnemonic");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        inviteCode = getIntent().getStringExtra("inviteCode");
        return new MnemonicCheckPresenter(this, mAppContext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onRegistSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void oncheckSuccess() {
        if (isFindMoney) {
            SetMoneyPasswordActivity.startIntentResult(MnemonicCheckActivity.this, false, false, mnemonicWord, 119);
        } else {
            
        }
    }

    /**
     * 生成点击取消显示的助记词小控件
     */
    private void createMnemonicView() {
        List<String> wordList = StringUtil.getUrlStrList(mnemonicWord, ",");
        // 随机打乱List
        Collections.shuffle(wordList);
        for (final String word : wordList) {
            TextView tv = new TextView(mAppContext);
            tv.setText(word);
            tv.setTextSize(16f);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(EnvironmentUtil.px2dip(mAppContext, 15), EnvironmentUtil.px2dip(mAppContext, 10), EnvironmentUtil.px2dip(mAppContext, 15)
                    , EnvironmentUtil.px2dip(mAppContext, 10));
            tv.setBackground(ResourcesUtil.getDrawable(mAppContext, R.drawable.bg_gray_corners));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView view = viewMap.get(word);
                    if (selectMap.get(word)) {
                        llSelectWord.removeView(view);
                        selectMap.put(word, false);

                        view.setBackground(ResourcesUtil.getDrawable(mAppContext, R.drawable.bg_gray_corners));
                        llCheckWord.addView(view);
                        checkMap.put(word, true);
                    } else {
                        llCheckWord.removeView(view);
                        checkMap.put(word, false);

                        view.setBackground(ResourcesUtil.getDrawable(mAppContext, R.drawable.bg_white_corners));
                        llSelectWord.addView(view);
                        selectMap.put(word, true);
                    }
                }
            });
            viewMap.put(word, tv);
            selectMap.put(word, false);
            checkMap.put(word, true);
            llCheckWord.addView(tv);
        }
    }

    /**
     * 验证助记词的选中情况
     */
    private void checkMnemonicSelect() {
        if (llCheckWord.getChildCount() > 0) {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mnemonic_select_empty));
            return;
        }
        int size = llSelectWord.getChildCount();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            TextView tv = (TextView) llSelectWord.getChildAt(i);
            String string = tv.getText().toString();
            stringBuilder.append(string);
            if (i != size - 1) {
                stringBuilder.append(",");
            }
        }
        if (StringUtil.isEqual(mnemonicWord, stringBuilder.toString())) {
            if (isFindMoney) {
                getPresenter().checkZhujici(mnemonicWord);
            } else if (isFindPassword) {
                getPresenter().checkZhujici(mnemonicWord);
            } else {
                getPresenter().regist(name, password, inviteCode, mnemonicWord);
            }
        } else {
            showToast(CommonToast.ToastType.TEXT, getString(R.string.check_mnenotic_select_error));
        }
    }
}
