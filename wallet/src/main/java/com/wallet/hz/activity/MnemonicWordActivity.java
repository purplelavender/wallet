package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wallet.hz.R;

import butterknife.BindView;
import share.exchange.framework.base.BaseAppActivity;

/**
 * @ClassName: MnemonicWordActivity
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/31 16:49
 */
public class MnemonicWordActivity extends BaseAppActivity {

    @BindView(R.id.tv_mnemonic_word)
    TextView tvMnemonic;
    @BindView(R.id.tv_mnemonic_next)
    TextView tvNext;

    private String mnemonicWord, name, password, inviteCode;

    public static void startIntentResult(Activity activity, String mnemonicWord, String name, String password, String inviteCode, int requestCode) {
        Intent intent = new Intent(activity, MnemonicWordActivity.class);
        intent.putExtra("mnemonic", mnemonicWord);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("inviteCode", inviteCode);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mark_mnemonic_word;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
        mnemonicWord = getIntent().getStringExtra("mnemonic");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        inviteCode = getIntent().getStringExtra("inviteCode");
        tvMnemonic.setText(mnemonicWord.replaceAll(",", "    "));
    }

    @Override
    protected void initEvent() {
        setToolTitleClick("", null, null, null);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MnemonicCheckActivity.startIntentResult(MnemonicWordActivity.this, false, false, mnemonicWord, name, password, inviteCode, 113);
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
