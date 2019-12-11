package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import com.wallet.hz.R;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 *
 * @ClassName:      QRCodeActivity
 * @Description:    二维码扫描页面
 * @Author:         ZL
 * @CreateDate:     2019/11/05 10:22
 */
public class QRCodeActivity extends BaseAppActivity {

    @BindView(R.id.zxing_view)
    ZXingView mQRCodeView;
    
    public static void startIntentResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, QRCodeActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();

        initListener();
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.qrcode_text), null, null, null);
    }

    @Override
    protected void initCurrentData() {

    }

    private void initListener() {
        // 二维码扫描结果监听
        mQRCodeView.setDelegate(new QRCodeView.Delegate() {

            @Override
            public void onScanQRCodeSuccess(String result) {
                // 手机震动
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                if (StringUtil.isContain(result, "UID:")) {// 我的名片
                    result = result.replace("UID:", "");
                    Intent intent = new Intent().putExtra("code", result);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent().putExtra("code", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                showToast(CommonToast.ToastType.TEXT, getString(R.string.qrcode_decode));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mQRCodeView != null) {
            mQRCodeView.startCamera();
            mQRCodeView.showScanRect();
            mQRCodeView.startSpot();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQRCodeView != null) {
            mQRCodeView.stopCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mQRCodeView != null) {
            mQRCodeView.onDestroy();
        }
    }
}