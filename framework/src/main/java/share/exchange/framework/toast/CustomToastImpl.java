package share.exchange.framework.toast;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
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

import share.exchange.framework.R;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

/**
 * 自定义window实现的toast，无notification权限或者是点击类型时强制采用这种
 */
public class CustomToastImpl implements IToast {

    private final TextView textView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private View mView;
    private int mDuration;
    private Handler mHandler;
    private View.OnClickListener mListener = null;
    @StyledToast.Type
    private final int mType;
    private static final int TIME_LONG = 3500;
    private static final int TIME_SHORT = 2000;
    private static final String TAG = StyledToast.class.getSimpleName();

    private CustomToastImpl(@NonNull Context context, @NonNull String text, @StyledToast.Duration int duration, @StyledToast.Type int type) {
        mType = type;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int layoutId = R.layout.toast_universal;
        switch (type) {
            case StyledToast.UNIVERSAL:
                break;
            case StyledToast.EMPHASIZE:
                layoutId = R.layout.toast_emphasize;
                break;
            case StyledToast.CLICKABLE:
                layoutId = R.layout.toast_clickable;
                break;
            default:
                break;
        }
        mView = LayoutInflater.from(context).inflate(layoutId, null);
        textView = (TextView) mView.findViewById(R.id.text);
        textView.setText(text);
        mDuration = (duration == StyledToast.LENGTH_LONG ? TIME_LONG : TIME_SHORT);
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = android.R.style.Animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static CustomToastImpl makeText(@NonNull Context context, @NonNull String text, @StyledToast.Duration int duration) {
        return makeText(context, text, duration, StyledToast.UNIVERSAL);
    }

    public static CustomToastImpl makeText(@NonNull Context context, @NonNull String text, @StyledToast.Duration int duration, @StyledToast.Type int type) {
        return new CustomToastImpl(context, text, duration, type);
    }

    @Override
    public IToast setDuration(int duration) {
        if (duration == StyledToast.LENGTH_SHORT) {
            mDuration = TIME_SHORT;
        } else if (duration == StyledToast.LENGTH_LONG) {
            mDuration = TIME_LONG;
        } else {
            mDuration = duration;
        }
        return this;
    }

    @Override
    public IToast setIcon(int resId) {
        ImageView imageView = (ImageView) mView.findViewById(R.id.icon);
        imageView.setImageResource(resId);
        imageView.setVisibility(View.VISIBLE);
        return this;
    }

    @Override
    public IToast setAnimations(int animations) {
        mParams.windowAnimations = animations;
        return this;
    }

    @Override
    public IToast setColor(int colorRes) {
        GradientDrawable drawable = (GradientDrawable) mView.getBackground();
        drawable.setColor(mView.getContext().getResources().getColor(colorRes));
        return this;
    }

    @TargetApi(JELLY_BEAN)
    @Override
    public IToast setBackground(Drawable drawable) {
        mView.findViewById(R.id.toast).setBackground(drawable);
        return this;
    }

    @Override
    public IToast setBackgroundColor(int color) {
        mView.findViewById(R.id.toast).setBackgroundColor(ContextCompat.getColor(mView.getContext(), color));
        return this;
    }

    @Override
    public IToast setBackgroundSize(int width, int height) {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(width, height);
        mView.findViewById(R.id.toast).setLayoutParams(mParams);
        return this;
    }

    @TargetApi(JELLY_BEAN_MR1)
    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        final Configuration config = mView.getContext().getResources().getConfiguration();
        final int g = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
        mParams.gravity = g;
        if ((g & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            mParams.horizontalWeight = 1.0f;
        }
        if ((g & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            mParams.verticalWeight = 1.0f;
        }
        mParams.x = xOffset;
        mParams.y = yOffset;
        return this;
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        mParams.verticalMargin = verticalMargin;
        mParams.horizontalMargin = horizontalMargin;
        return this;
    }

    @Override
    public IToast setText(int resId) {
        ((TextView) mView.findViewById(R.id.text)).setText(resId);
        return this;
    }

    @Override
    public IToast setText(@NonNull CharSequence charSequence) {
        ((TextView) mView.findViewById(R.id.text)).setText(charSequence);
        return this;
    }

    @Override
    public IToast setTextSize(float size) {
        ((TextView) mView.findViewById(R.id.text)).setTextSize(size);
        return this;
    }

    @Override
    public IToast setTextColor(@ColorInt int color) {
        ((TextView) mView.findViewById(R.id.text)).setTextColor(color);
        return this;
    }

    @Override
    public void show() {
        if (mType == StyledToast.CLICKABLE && mListener == null) {
            Log.e(TAG, "the listener of clickable toast is null,have you called method:setClickCallBack?");
            return;
        }
        try {
            if (mView.getParent() != null) {
                mWindowManager.removeView(mView);
            }
            Log.e(TAG, "addview");
            mWindowManager.addView(mView, mParams);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            }, mDuration);
        } catch (Exception e) {
//            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public void cancel() {
        try {
            if (mView.getWindowToken() != null) {
                mWindowManager.removeView(mView);
            }
        } catch (Exception e) {
//            Log.e(TAG, Log.getStackTraceString(e));
        } finally {
            mParams = null;
            mWindowManager = null;
            mView = null;
            mHandler = null;
            mListener = null;
        }
    }

    @Override
    public IToast setClickCallBack(@NonNull String text, @DrawableRes int resId, @NonNull View.OnClickListener listener) {
        if (mType != StyledToast.CLICKABLE) {
            Log.d(TAG, "only clickable toast has click callback!!!");
            return this;
        }
        mListener = listener;
        LinearLayout layout = (LinearLayout) mView.findViewById(R.id.btn);
        layout.setVisibility(View.VISIBLE);
        layout.setOnClickListener(listener);
        TextView textView = (TextView) layout.findViewById(R.id.btn_text);
        textView.setText(text);
        ImageView imageView = (ImageView) layout.findViewById(R.id.btn_icon);
        imageView.setBackgroundResource(resId);
        return this;
    }

}
