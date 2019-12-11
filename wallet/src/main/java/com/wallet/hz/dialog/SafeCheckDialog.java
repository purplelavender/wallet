package com.wallet.hz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wallet.hz.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import share.exchange.framework.utils.StringUtil;
import share.exchange.framework.widget.CommonToast;

/**
 *
 * @ClassName:      PayDetailDialog
 * @Description:    安全验证弹层
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class SafeCheckDialog extends Dialog {

    @BindView(R.id.tv_dialog_cancel)
    TextView tvCancel;
    @BindView(R.id.et_dialog_money_password)
    EditText etMoneyPassword;
    @BindView(R.id.tv_dialog_sure)
    TextView tvSure;

    private Context mContext;
    private OnViewClicked mOnViewClicked;
    private String password = "";

    public SafeCheckDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public SafeCheckDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_safe_check, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
//        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 1.0); // 高度设置为屏幕的1.0
        dialogWindow.setAttributes(params);

        SafeCheckDialog.this.setCanceledOnTouchOutside(false);
        SafeCheckDialog.this.setCancelable(false);
    }

    public SafeCheckDialog build() {

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnViewClicked != null) {
                    mOnViewClicked.onClose();
                }
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(etMoneyPassword.getText().toString())) {
                    CommonToast.showToast(mContext, CommonToast.ToastType.TEXT, mContext.getString(R.string.check_password_empty));
                    return;
                }
                if (mOnViewClicked != null) {
                    mOnViewClicked.onSure(etMoneyPassword.getText().toString());
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

        void onSure(String password);

        void onClose();
    }

}
