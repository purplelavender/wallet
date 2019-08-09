package share.exchange.framework.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * @ClassName: AutoSizeTextView
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/07/09 14:57
 */
public class AutoSizeTextView extends TextView {
    public AutoSizeTextView(Context context) {
        super(context);
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int line=getLineCount();
        float textsize=getTextSize();
        if(line>1){
            textsize--;
            setTextSize(TypedValue.COMPLEX_UNIT_PX,textsize);
        }
    }
}
