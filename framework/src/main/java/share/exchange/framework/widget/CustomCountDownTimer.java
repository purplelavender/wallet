package share.exchange.framework.widget;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;

/**
 * @ClassName: CustomCountDownTimer
 * @Description: 倒计时计时器，用于解决5.0以下版本cancle之后onTick方法仍然执行的问题
 * @Author: ZL
 * @CreateDate: 2019/04/16 10:04
 */
public abstract class CustomCountDownTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    private CountDownHandler countDownHandler;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
        countDownHandler  = new CountDownHandler(this);
    }

    /**
     * Cancel the countdown.-
     */
    public synchronized final void cancel() {
        mCancelled = true;
        countDownHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CustomCountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        countDownHandler.sendMessage(countDownHandler.obtainMessage(MSG));
        return this;
    }


    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    static class CountDownHandler extends Handler {

        private WeakReference<CustomCountDownTimer> myCountDownTimerReference;

        public CountDownHandler(CustomCountDownTimer myCountDownTimer) {
            myCountDownTimerReference = new WeakReference<>(myCountDownTimer);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CustomCountDownTimer countDownTimer = myCountDownTimerReference.get();
            if (countDownTimer != null){
                countDownTimer.transMessage(this, msg);
            }
        }
    }

    private void transMessage(CountDownHandler countDownHandler, Message msg) {
        synchronized (CustomCountDownTimer.this) {
            if (mCancelled) {
                return;
            }

            final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

            if (millisLeft <= 0) {
                onFinish();
            } else if (millisLeft < mCountdownInterval) {
                // no tick, just delay until done
                countDownHandler.sendMessageDelayed(countDownHandler.obtainMessage(MSG), millisLeft);
            } else {
                long lastTickStart = SystemClock.elapsedRealtime();
                onTick(millisLeft);

                // take into account user's onTick taking time to execute
                long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                // special case: user's onTick took more than interval to
                // complete, skip to next interval
                while (delay < 0) {
                    delay += mCountdownInterval;
                }

                countDownHandler.sendMessageDelayed(countDownHandler.obtainMessage(MSG), delay);
            }
        }
    }

}
