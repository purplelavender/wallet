package com.wallet.hz.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
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
 * @Description:    參與模式規則彈層
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class JoinTypeRuleDialog extends Dialog {

    @BindView(R.id.tv_join_known)
    TextView tvKnown;
    @BindView(R.id.tv_dialog_join_type)
    TextView tvType;

    private Context mContext;

    public JoinTypeRuleDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public JoinTypeRuleDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_join_type_rule, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.9
        params.height = (int) (d.heightPixels * 0.6); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(params);

        JoinTypeRuleDialog.this.setCanceledOnTouchOutside(false);
        JoinTypeRuleDialog.this.setCancelable(false);
    }

    public JoinTypeRuleDialog build() {
        tvType.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvKnown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }

}
