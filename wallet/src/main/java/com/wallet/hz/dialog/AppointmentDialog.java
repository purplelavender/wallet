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
 * @Description:    预约搜索弹层
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class AppointmentDialog extends Dialog {

    @BindView(R.id.tv_dialog_count)
    TextView tvCount;
    @BindView(R.id.tv_dialog_ranking)
    TextView tvRanking;
    @BindView(R.id.tv_dialog_sure)
    TextView tvSure;

    private Context mContext;

    public AppointmentDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public AppointmentDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_appointment_search, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的1.0
        dialogWindow.setAttributes(params);

        AppointmentDialog.this.setCanceledOnTouchOutside(false);
        AppointmentDialog.this.setCancelable(false);
    }

    public AppointmentDialog build(String count, String ranking) {
        tvCount.setText(count);
        tvRanking.setText(ranking);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }

}
