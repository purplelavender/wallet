package share.exchange.framework.toast;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;

/**
 *
 * @ClassName:      IToast
 * @Description:    Toast公共接口
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:34
 */
public interface IToast {
    /**
     * 设置duration
     *
     * @param duration 显示时长
     * @return IToast
     */
    IToast setDuration(int duration);

    /**
     * 设置icon
     *
     * @param resId drawableId
     * @return IToast
     */
    IToast setIcon(@DrawableRes int resId);

    /**
     * 设置动画
     *
     * @param animations style id
     * @return IToast
     */
    IToast setAnimations(@StyleRes int animations);

    /**
     * 设置背景颜色
     *
     * @param colorRes color res id
     * @return IToast
     */
    IToast setColor(@ColorRes int colorRes);

    /**
     * 设置背景drawable
     *
     * @param drawable drawable
     * @return IToast
     */
    IToast setBackground(Drawable drawable);

    /**
     * 设置背景drawable
     *
     * @param color
     * @return IToast
     */
    IToast setBackgroundColor(@ColorRes int color);

    /**
     * 设置背景宽高
     *
     * @param width
     * @param height
     * @return
     */
    IToast setBackgroundSize(int width, int height);

    /**
     * 设置gravity
     *
     * @param gravity gravity
     * @param xOffset x偏移
     * @param yOffset y 偏移
     */
    IToast setGravity(int gravity, int xOffset, int yOffset);

    /**
     * 设置margin
     *
     * @param horizontalMargin 水平margin
     * @param verticalMargin   垂直margin
     * @return IToast
     */
    IToast setMargin(float horizontalMargin, float verticalMargin);

    /**
     * 设置文字
     *
     * @param resId string id
     * @return IToast
     */
    IToast setText(@StringRes int resId);

    /**
     * 设置文字
     *
     * @param charSequence 字符串
     * @return IToast
     */
    IToast setText(@NonNull CharSequence charSequence);

    /**
     * 设置字体大小
     *
     * @param size
     * @return
     */
    IToast setTextSize(float size);

    /**
     * 设置字体颜色
     *
     * @param color
     * @return
     */
    IToast setTextColor(@ColorInt int color);

    /**
     * 显示方法
     */
    void show();

    /**
     * 取消显示
     */
    void cancel();

    /**
     * 设置点击事件
     *
     * @param text     按钮文字，建议俩字
     * @param resId    按钮图标
     * @param listener 点击listener
     * @return IToast
     */
    IToast setClickCallBack(@NonNull String text, @DrawableRes int resId, @NonNull View.OnClickListener listener);
}
