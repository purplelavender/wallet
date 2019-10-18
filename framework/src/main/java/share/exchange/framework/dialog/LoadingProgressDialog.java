package share.exchange.framework.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import share.exchange.framework.R;


/**
 * 自定义加载层
 */
public class LoadingProgressDialog extends Dialog {

	private TextView tvMsg;

	public LoadingProgressDialog(Activity mActivity) {
		this(mActivity, R.style.CustomProgressDialog);
	}

	public LoadingProgressDialog(Activity mActivity, int theme) {
		super(mActivity, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_custom_progress);
		tvMsg = (TextView) findViewById(R.id.tv_loading_tip);
	}

	/**
	 * 设置加载提示文本
	 * 
	 * @param strMessage
	 * @return
	 */
	public void setMessage(String strMessage) {
		if (tvMsg != null) {
			tvMsg.setText(strMessage);
			tvMsg.setVisibility(View.VISIBLE);
		}
	}

}
