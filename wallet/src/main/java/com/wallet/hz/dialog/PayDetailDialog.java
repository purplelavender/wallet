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

/**
 *
 * @ClassName:      PayDetailDialog
 * @Description:    支付详情弹层
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class PayDetailDialog extends Dialog {

    @BindView(R.id.tv_dialog_info)
    TextView tvInfo;
    @BindView(R.id.tv_dialog_gathering_address)
    TextView tvGatheringAddress;
    @BindView(R.id.tv_dialog_transfer_address)
    TextView tvTransferAddress;
    @BindView(R.id.tv_dialog_amount)
    TextView tvAmount;
    @BindView(R.id.tv_dialog_sure)
    TextView tvSure;

    private Context mContext;
    private OnViewClicked mOnViewClicked;

    public PayDetailDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public PayDetailDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay_detail, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
//        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 1.0); // 高度设置为屏幕的1.0
        dialogWindow.setAttributes(params);

        PayDetailDialog.this.setCanceledOnTouchOutside(false);
        PayDetailDialog.this.setCancelable(false);
    }

    public PayDetailDialog build(int type, String gatheringAddress, String transferAddress, String amount) {
        tvInfo.setText(type == 0 ? mContext.getString(R.string.wallet_in) : mContext.getString(R.string.wallet_out));
        tvGatheringAddress.setText(gatheringAddress);
        tvTransferAddress.setText(transferAddress);
        tvAmount.setText(amount);
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

        void onClose();
    }

}
