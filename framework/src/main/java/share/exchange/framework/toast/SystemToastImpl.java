package share.exchange.framework.toast;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import share.exchange.framework.R;

/**
 * 系统toast 扩展实现方式
 */
public class SystemToastImpl implements IToast {

    @NonNull
    private final Toast mToast;
    @NonNull
    private final Context mContext;
    @StyledToast.Type
    private final int mType;
    private static final String TAG = StyledToast.class.getSimpleName();

    private SystemToastImpl(@NonNull Context context, @NonNull Toast toast, @StyledToast.Type int type) {
        mContext = context;
        mToast = toast;
        mType = type;
    }

    public static SystemToastImpl makeText(@NonNull Context context, @NonNull String text, @StyledToast.Duration int duration) {
        return makeText(context, text, duration, StyledToast.UNIVERSAL);
    }

    public static SystemToastImpl makeText(@NonNull Context context, @NonNull String text, @StyledToast.Duration int duration, @StyledToast.Type int type) {
        Toast toast = Toast.makeText(context, text, duration);
        int layoutId = R.layout.toast_universal;
        if (type == StyledToast.EMPHASIZE) {
            layoutId = R.layout.toast_emphasize;
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        toast.setView(view);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            setContext(view, new SafeToastContext(context));
        }
        return new SystemToastImpl(context, toast, type);
    }

    private static void setContext(@NonNull View view, @NonNull Context context) {
        try {
            Field field = View.class.getDeclaredField("mContext");
            field.setAccessible(true);
            field.set(view, context);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    @Override
    public IToast setDuration(int duration) {
        mToast.setDuration(duration);
        return this;
    }

    @Override
    public IToast setIcon(int resId) {
        ImageView imageView = (ImageView) mToast.getView().findViewById(R.id.icon);
        imageView.setImageResource(resId);
        imageView.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * @param animations A style resource defining the animations to use for this window.
     *                   This must be a system resource; it can not be an application resource
     *                   because the window manager does not have access to applications.
     */
    @Deprecated
    @Override
    public IToast setAnimations(int animations) {
        Log.d(TAG, "method:setAnimations is Deprecated , animations must be a system resource " +
                ", considering the window manager does not have access to applications.");
        try {
            Field tnField = mToast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            Object mTN = tnField.get(mToast);
            Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
            tnParamsField.setAccessible(true);
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) tnParamsField.get(mTN);
            params.windowAnimations = animations;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public IToast setColor(int colorRes) {
        GradientDrawable drawable = (GradientDrawable) mToast.getView().getBackground();
        drawable.setColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public IToast setBackground(Drawable drawable) {
        mToast.getView().findViewById(R.id.toast).setBackground(drawable);
        return this;
    }

    @Override
    public IToast setBackgroundColor(int color) {
        mToast.getView().findViewById(R.id.toast).setBackgroundColor(ContextCompat.getColor(mContext, color));
        return this;
    }

    /**
     * 直接通过修改Toast的View布局的父控件宽度是无法实现的，指定父控件内部的textview的宽度实现
     *
     * @param width
     * @param height
     * @return
     */
    @Override
    public IToast setBackgroundSize(int width, int height) {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(width, height);
        mToast.getView().findViewById(R.id.toast).setLayoutParams(mParams);
        return this;
    }

    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
        return this;
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        mToast.setMargin(horizontalMargin, verticalMargin);
        return this;
    }

    @Override
    public IToast setText(int resId) {
        mToast.setText(resId);
        return this;
    }

    @Override
    public IToast setText(@NonNull CharSequence charSequence) {
        mToast.setText(charSequence);
        return this;
    }

    @Override
    public IToast setTextSize(float size) {
        ((TextView) mToast.getView().findViewById(R.id.text)).setTextSize(size);
        return this;
    }

    @Override
    public IToast setTextColor(@ColorInt int color) {
        ((TextView) mToast.getView().findViewById(R.id.text)).setTextColor(color);
        return this;
    }

    @Override
    public void show() {
        mToast.show();
    }

    @Deprecated
    @Override
    public void cancel() {
        Log.e(TAG, "only CustomToastImpl can be canceled by user");
    }

    @Deprecated
    @Override
    public IToast setClickCallBack(@NonNull String text, int resId, @NonNull View.OnClickListener listener) {
        Log.e(TAG, "only CustomToastImpl has click callback");
        return this;
    }

}
