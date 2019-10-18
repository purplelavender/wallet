package share.exchange.framework.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

/**
 *
 * @ClassName:      ResourcesUtil
 * @Description:    资源文件处理工具，后续有需要在添加
 * @Author:         ZL
 * @CreateDate:     2019/08/14 09:46
 */
@SuppressWarnings("deprecation")
public class ResourcesUtil {

    private ResourcesUtil() {
    }

    public static int getColor(String colorValue) {
        if (!TextUtils.isEmpty(colorValue) && colorValue.startsWith("#")) {
            return Color.parseColor(colorValue);
        }
        return 0;
    }

    public static int getColor(@NonNull Context context, @ColorRes int colorResId) {
        return ContextCompat.getColor(context, colorResId);
    }

    public static int getColor(@NonNull Context context, @ColorRes int id, @Nullable Resources.Theme theme)
            throws Resources.NotFoundException {
        return ResourcesCompat.getColor(context.getResources(), id, theme);
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int resId) {
        return ContextCompat.getColorStateList(context, resId);
    }

    public static ColorStateList getColorStateList(@NonNull Context context, @ColorRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getColorStateList(context.getResources(), id, theme);
    }

    /**
     * 获取drawable
     * @param context
     * @param drawableResId
     * @return
     */
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int drawableResId) {
        return ContextCompat.getDrawable(context, drawableResId);
    }

    public static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 兼容处理Html.fromHtml
     *
     * @param html
     * @return
     */
    public static Spanned fromHtml(@NonNull String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

}
