package share.exchange.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * @ClassName:      SolveViewPager
 * @Description:    实现自由控制禁止滑动，禁止左滑，禁止右滑
 * @Author:         ZL
 * @CreateDate:     2019/08/13 11:49
 */
public class SolveViewPager extends HackyViewPager {

    /**
     * 上一次x坐标
     */
    private float lastX;
    /**
     * 上一次y坐标
     */
    private float lastY;
    /**
     * 向左滑动事件  默认true
     */
    boolean leftable = true;
    /**
     * 向右滑动事件  默认true
     */
    boolean rightable = true;

    /**
     * 垂直方向上滑动 默认false 不允许
     */
    boolean isVerticalScroll = false;

    public SolveViewPager(Context context) {
        super(context);
        init(context);
    }

    public SolveViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 是否左滑
     *
     * @param leftable
     */
    public void setLeftable(boolean leftable) {
        this.leftable = leftable;
    }

    /**
     * 是否右滑
     *
     * @param rightable
     */
    public void setRightable(boolean rightable) {
        this.rightable = rightable;
    }

    /**
     * 是否可以垂直滑动
     * @param verticalScroll
     */
    public void setVerticalScroll(boolean verticalScroll) {
        isVerticalScroll = verticalScroll;
    }

    private void init(Context context) {}

    /**
     * 重写ViewPager的事件分发，以实现控制禁止左右滑动的效果，并且兼容ViewPager内其他滑动控件的上下滑动
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取起始坐标值
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX() - lastX;
                float moveY = event.getY() - lastY;
                if (!leftable) {
                    if (moveX < 0 && (!isVerticalScroll || (Math.abs(moveX) > Math.abs(moveY) && Math.abs(moveX) / Math.abs(moveY) > Math.tan(30)))) { //禁止左滑
                        return true;
                    }
                    //重新对起始坐标赋值
                    lastX = event.getX();
                }
                if (!rightable) {
                    if (moveX > 0 && (!isVerticalScroll || (Math.abs(moveX) > Math.abs(moveY) && Math.abs(moveX) / Math.abs(moveY) > Math.tan(30)))) { //禁止右滑
                        return true;
                    }
                    //重新对起始坐标赋值
                    lastX = event.getX();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
