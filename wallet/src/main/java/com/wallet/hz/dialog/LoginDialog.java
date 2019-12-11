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
 * @Description:    帐号被顶调弹层
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class LoginDialog extends Dialog {

    @BindView(R.id.tv_dialog_sure)
    TextView tvSure;

    private Context mContext;
    private OnViewClicked mOnViewClicked;

    public LoginDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public LoginDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_login_other, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.8
        dialogWindow.setAttributes(params);

        LoginDialog.this.setCanceledOnTouchOutside(false);
        LoginDialog.this.setCancelable(false);
    }

    public LoginDialog build() {
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
