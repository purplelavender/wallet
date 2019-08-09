package share.exchange.framework.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import share.exchange.framework.R;
import share.exchange.framework.toast.StyledToast;
import share.exchange.framework.utils.EnvironmentUtil;
import share.exchange.framework.utils.ResourcesUtil;


/**
 * 自定义Toast提示控件
 */
public class CommonToast {

    private CommonToast() {
    }

    /**
     * Toast提示,仅显示文字
     *
     * @param context
     * @param message
     * @param toastType
     */
    public static void showToast(Context context, ToastType toastType, String message) {
        if (null == toastType) {
            toastType = ToastType.TEXT;
        }
        switch (toastType) {
            case TEXT:
                /**
                 * 纯文字提示
                 */
                showCustom(context, StyledToast.EMPHASIZE, 0, message);
                break;
            case SUCCESS:
                /**
                 * 成功提示
                 */
                showCustom(context, StyledToast.EMPHASIZE, R.drawable.common_ic_success_white, message);
                break;
            case SUCCESS2:
                /**
                 * 成功提示
                 */
                showCustomNew(context, StyledToast.UNIVERSAL, R.drawable.common_toast_success_bg, R.drawable.common_icon_success, message);
                break;
            case ERROR:
                /**
                 * 错误提示
                 */
                showCustom(context, StyledToast.EMPHASIZE, R.drawable.common_ic_clear_white_24dp, message);
                break;
            case ERROR2:
                /**
                 * 错误提示
                 */
                showCustomNew(context, StyledToast.UNIVERSAL, R.drawable.common_toast_warn_bg, R.drawable.common_icon_warn, message);
                break;
            case WARNING:
                /**
                 * 一般信息提示
                 */
                showCustom(context, StyledToast.EMPHASIZE, R.drawable.common_ic_error_white, message);
                break;
            default:
                break;
        }
    }

    public static void showCustom(Context context, @StyledToast.Type int type, @DrawableRes int icon, String text) {
        StyledToast.makeText(context, text, StyledToast.LENGTH_SHORT, type)
                .setIcon(icon)
                .show();
    }

    public static void showCustomNew(Context context, @StyledToast.Type int type, @DrawableRes int background, @DrawableRes int icon, String text) {
        StyledToast.makeText(context, text, StyledToast.LENGTH_LONG, type)
                .setIcon(icon)
                .setGravity(Gravity.TOP, 0, EnvironmentUtil.dip2px(context, 5))
                .setBackground(ResourcesUtil.getDrawable(context, background))
                .setBackgroundSize(EnvironmentUtil.screenWidth(context) - 100, 100)
                .setTextColor(ContextCompat.getColor(context, R.color.white))
                .setTextSize(15f)
                .show();
    }

    public enum ToastType {
        SUCCESS,
        ERROR,
        WARNING,
        TEXT,
        SUCCESS2,
        ERROR2
    }

}
