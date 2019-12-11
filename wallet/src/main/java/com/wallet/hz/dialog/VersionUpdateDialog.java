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
import share.exchange.framework.utils.ResourcesUtil;

/**
 *
 * @ClassName:      PayDetailDialog
 * @Description:    版本更新彈層
 * @Author:         ZL
 * @CreateDate:     2019/10/29 17:35
 */
public class VersionUpdateDialog extends Dialog {

    @BindView(R.id.tv_update_content)
    TextView tvContent;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_dialog_sure)
    TextView tvSure;

    private Context mContext;
    private OnViewClicked mOnViewClicked;

    public VersionUpdateDialog(Context context) {
        this(context, 0);
        this.mContext = context;
    }

    public VersionUpdateDialog(Context context, int themeResId) {
        super(context, R.style.BottomDialogStyle);
        this.mContext = context;

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_version_update, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽高
        params.width = (int) (d.widthPixels * 0.9); // 高度设置为屏幕的0.8
        params.height = (int) (d.heightPixels * 0.5); // 高度设置为屏幕的0.8
        dialogWindow.setAttributes(params);

        VersionUpdateDialog.this.setCanceledOnTouchOutside(false);
        VersionUpdateDialog.this.setCancelable(false);
    }

    public VersionUpdateDialog build(String content, boolean isQing) {
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvContent.setText(ResourcesUtil.fromHtml(content));
        // TODO 由于耕种原因，暂时是反的，下一版优化
        tvCancel.setVisibility(!isQing ? View.GONE : View.VISIBLE);
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
