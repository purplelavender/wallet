package share.exchange.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import share.exchange.framework.R;
import share.exchange.framework.bean.NoticeBean;
import share.exchange.framework.utils.EnvironmentUtil;

/**
 * Created by MMM on 2018/1/28.
 * 仿淘宝首页的 淘宝头条滚动的自定义View
 */
public class NoticeView extends ViewFlipper {
    /**
     * 是否设置动画时间间隔
     */
    private boolean isSetAnimDuration = false;

    /**
     * 是否单行显示
     */
    private boolean isSingleLine = false;

    /**
     * 轮播间隔
     */
    private int interval = 3000;

    /**
     * 动画时间
     */
    private int animDuration = 1000;
    private int textSize = 15;
    private int textColor = Color.parseColor("#888888");
    /**
     * 一次性显示多少个
     */
    private int itemCount = 2;

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoticeView, defStyleAttr, 0);
        if (typedArray != null) {
            isSetAnimDuration = typedArray.getBoolean(R.styleable.NoticeView_isSetAnimDuration, false);
            isSingleLine = typedArray.getBoolean(R.styleable.NoticeView_isSingleLine, false);
            interval = typedArray.getInteger(R.styleable.NoticeView_marquee_interval, interval);
            animDuration = typedArray.getInteger(R.styleable.NoticeView_marquee_animDuration, animDuration);
            if (typedArray.hasValue(R.styleable.NoticeView_marquee_textSize)) {
                textSize = (int) typedArray.getDimension(R.styleable.NoticeView_marquee_textSize, textSize);
                textSize = (int) EnvironmentUtil.sp2px(context, textSize);
            }
            textColor = typedArray.getColor(R.styleable.NoticeView_marquee_textColor, textColor);
            typedArray.recycle();
        }
        itemCount = isSingleLine ? 1 : 2;
        Animation animIn = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in);
        Animation animOut = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out);
        if (isSetAnimDuration) {
            animIn.setDuration(animDuration);
            animOut.setDuration(animDuration);
        }
        setInAnimation(animIn);
        setOutAnimation(animOut);
        setFlipInterval(interval);
    }

    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    private void setViews(final List<View> views) {
        if (views == null || views.size() == 0) {
            return;
        }
        removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            final int position = i;
            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, views.get(position));
                    }
                }
            });
            ViewGroup viewGroup = (ViewGroup) views.get(i).getParent();
            if (viewGroup != null) {
                viewGroup.removeAllViews();
            }
            addView(views.get(i));
        }
        startFlipping();
    }

    public void setData(List<NoticeBean> data) {
        setData(R.layout.layout_notice_view, data);
    }

    public void setData(@LayoutRes int layoutId, List<NoticeBean> data) {
        if (data.isEmpty()) {
            return;
        }
        int currentIndex = 0;
        List<View> viewList = new ArrayList<>();
        int loopconunt = data.size() % itemCount == 0 ? data.size() / itemCount : data.size() / itemCount + 1;
        for (int i = 0; i < loopconunt; i++) {
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(layoutId, null);
            AutoSizeTextView tvOne = (AutoSizeTextView) moreView.findViewById(R.id.marquee_tv_one);
            TextView tvTwo = (TextView) moreView.findViewById(R.id.marquee_tv_two);
            if (tvOne != null) {
                tvOne.setTextSize(textSize);
                tvOne.setTextColor(textColor);
                tvOne.setText(data.get(currentIndex).getTitle());
            } else {
                throw new RuntimeException("Please set the first TextView Id With marquee_tv_one !");
            }
            if (tvTwo != null) {
                tvTwo.setTextSize(textSize);
                tvTwo.setTextColor(textColor);
                if (isSingleLine) {
                    tvTwo.setVisibility(GONE);
                } else {
                    if (currentIndex == data.size() - 1 && currentIndex % 2 == 0) {
                        tvTwo.setText(data.get(0).getTitle());
                    } else {
                        tvTwo.setText(data.get(currentIndex + 1).getTitle());
                    }
                }
            } else {
                throw new RuntimeException("Please set the second TextView Id With marquee_tv_two !");
            }
            viewList.add(moreView);
            currentIndex = currentIndex + itemCount;
        }
        setViews(viewList);
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
