package com.wallet.hz.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;
import com.wallet.hz.utils.AppSpUtil;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import share.exchange.framework.base.BaseAppActivity;
import share.exchange.framework.utils.BitmapUtil;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: InviteShareActivity
 * @Description: 邀请分享页面
 * @Author: ZL
 * @CreateDate: 2019/10/23 10:56
 */
public class InviteShareActivity extends BaseAppActivity {

    @BindView(R.id.iv_invite_down)
    ImageView ivDown;
    @BindView(R.id.tv_invite_join)
    TextView tvJoin;
    @BindView(R.id.tv_invite_code)
    TextView tvCode;
    @BindView(R.id.tv_invite_copy)
    TextView tvCopy;

    public static void startIntent(Activity activity) {
        Intent intent = new Intent(activity, InviteShareActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_share;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        showSuccessStateLayout();
        tvCode.setText(AppSpUtil.getUserCode(mAppContext));
        createInviteBitmap();
        tvJoin.setText(getString(R.string.invite_join, AppSpUtil.getUserName(mAppContext)));
    }

    @Override
    protected void initEvent() {
        setToolTitleClick(getString(R.string.mine_invite), null, null, null);
        setToolRightClick(getString(R.string.invite_save_qrcode), null, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapUtil.screenShot(InviteShareActivity.this);
            }
        });
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnvironmentUtil.copyTextContent(mAppContext, tvCode.getText().toString());
                showToast(CommonToast.ToastType.TEXT, getString(R.string.interface_copy_success));
            }
        });
    }

    @Override
    protected void initCurrentData() {

    }

    private void createInviteBitmap() {
        String url = AppSpUtil.getUserInviteUrl(mAppContext) + "?Invitationcode=" + AppSpUtil.getUserCode(mAppContext);
        int size = EnvironmentUtil.dip2px(mAppContext, 100);
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(StringUtil.isEmpty(url) ? tvCode.getText().toString() : url, size, Color.BLACK);
        ivDown.setImageBitmap(bitmap);
    }
}
