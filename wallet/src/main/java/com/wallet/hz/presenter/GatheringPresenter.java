package com.wallet.hz.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.wallet.hz.R;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import share.exchange.framework.base.BasePresenter;
import share.exchange.framework.executor.AsyncExecutor;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.widget.CommonToast;

/**
 * @ClassName: GatheringPresenter
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/24 15:11
 */
public class GatheringPresenter extends BasePresenter<GatheringContract.View> implements GatheringContract.Presenter {

    private Context mContext;

    public GatheringPresenter(GatheringContract.View view, Context context) {
        super(view);
        mContext = context;
    }

    @Override
    public void createView() {

    }

    @Override
    public void createImage(final String secret) {
        executor.execute(new AsyncExecutor.Worker<Bitmap>() {
            @Override
            protected Bitmap doInBackground() {
                int size = EnvironmentUtil.dip2px(mContext, 200);
                return QRCodeEncoder.syncEncodeQRCode(secret, size, Color.BLACK);
            }

            @Override
            protected void onPostExecute(Bitmap data) {
                if (data != null) {
                    getView().onCreateSuccess(data);
                } else {
                    getView().showToast(CommonToast.ToastType.TEXT, mContext.getString(R.string.bind_google_rqcode_error));
                }
            }
        });
    }
}
