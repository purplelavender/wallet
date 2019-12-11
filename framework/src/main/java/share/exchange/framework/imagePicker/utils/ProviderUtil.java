package share.exchange.framework.imagePicker.utils;

import android.content.Context;

/**
 * 用于解决provider冲突的util
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-03-22  18:55
 */

public class ProviderUtil {

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".provider";
    }
}
