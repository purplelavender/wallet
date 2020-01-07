package share.exchange.framework.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

/**
 * @ClassName: LinearGradientFontSpan
 * @Description: 文字渐变的Span类
 * @Author: ZL
 * @CreateDate: 2019/09/23 09:58
 */
public class LinearGradientFontSpan extends ReplacementSpan {

    private int mSize;
    private int startColor = Color.BLUE;
    private int endColor = Color.RED;
    private boolean isLeftToRight = true;

    public LinearGradientFontSpan() {
    }

    public LinearGradientFontSpan(int startColor, int endColor, boolean leftToRight) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.isLeftToRight = leftToRight;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end));
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        // 修改y1的值从上到下渐变， 修改x1的值从左到右渐变
        LinearGradient lg;
        if (isLeftToRight) {
            lg = new LinearGradient(0, 0, mSize, 0,
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);
        } else {
            lg = new LinearGradient(0, 0, 0, paint.descent() - paint.ascent(),
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);
        }
        paint.setShader(lg);

        canvas.drawText(text, start, end, x, y, paint);//绘制文字
    }

    public void setLeftToRight(boolean leftToRight) {
        isLeftToRight = leftToRight;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }
}
