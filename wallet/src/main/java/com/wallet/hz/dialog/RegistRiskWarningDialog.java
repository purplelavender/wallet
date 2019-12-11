package com.wallet.hz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallet.hz.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @ClassName:      PayDetailDialog
 * @Description:    注册警告弹层
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class RegistRiskWarningDialog extends Dialog {

    @BindView(R.id.iv_dialog_close)
    ImageView ivClose;
    @BindView(R.id.tv_risk_warning)
    TextView tvRiskWarning;
    @BindView(R.id.tv_dialog_agree)
    TextView tvAgree;

    private Context mContext;
    private OnViewClicked mOnViewClicked;

    public RegistRiskWarningDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public RegistRiskWarningDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_regist_risk_warning, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的1.0
        dialogWindow.setAttributes(params);

        RegistRiskWarningDialog.this.setCanceledOnTouchOutside(false);
        RegistRiskWarningDialog.this.setCancelable(false);
    }

    public RegistRiskWarningDialog build(String warningString) {
        tvRiskWarning.setText(warningString);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewClicked != null) {
                    mOnViewClicked.onClose();
                }
                dismiss();
            }
        });
        tvAgree.setOnClickListener(new View.OnClickListener() {
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
