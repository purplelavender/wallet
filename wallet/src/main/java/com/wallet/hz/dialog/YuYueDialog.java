package com.wallet.hz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wallet.hz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import share.exchange.framework.utils.BigDecimalUtils;

/**
 *
 * @ClassName:      PayDetailDialog
 * @Description:    预约弹层
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class YuYueDialog extends Dialog {

    @BindView(R.id.tv_yuyue_content)
    TextView tvContent;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_dialog_sure)
    TextView tvSure;

    private Context mContext;
    private OnViewClicked mOnViewClicked;

    public YuYueDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public YuYueDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_join_yuyue, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.8
        dialogWindow.setAttributes(params);

        YuYueDialog.this.setCanceledOnTouchOutside(false);
        YuYueDialog.this.setCancelable(false);
    }

    public YuYueDialog build(String successCount, String failCount) {
        tvContent.setText(mContext.getString(R.string.dialog_yuyue_notice, BigDecimalUtils.formatServiceNumber(successCount), BigDecimalUtils.formatServiceNumber(failCount)));
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewClicked != null) {
                    mOnViewClicked.onSure();
                }
                dismiss();
            }
        });
        return this;
    }

    public void setOnViewClicked(OnViewClicked onViewClicked) {
        mOnViewClicked = onViewClicked;
    }

    public interface OnViewClicked{

        void onSure();
    }
}
