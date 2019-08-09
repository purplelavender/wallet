package share.exchange.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 *
 * @ClassName:      SpUtil
 * @Description:    SharedPreferences 操作工具类
 * @Author:         ZL
 * @CreateDate:     2019/08/06 10:31
 */
public class SpUtil {

    private SpUtil() {
    }

    public static SharedPreferences getSP(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 保存数据的方法，根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static <T> boolean put(Context context, String key, T value) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sp.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            }
            return editor.commit();
        } catch (Exception e) {
            Log.d("SpUtil", Log.getStackTraceString(e));
            return false;
        }
    }

    /**
     * 得到数据的方法，根据默认值得到保存的数据的具体类型
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static <T> T get(Context context, String key, T defaultValue) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            if (defaultValue instanceof String) {
                String str = sp.getString(key, (String) defaultValue);
                defaultValue = (T) str;
            } else if (defaultValue instanceof Integer) {
                Integer in = sp.getInt(key, (Integer) defaultValue);
                defaultValue = (T) in;
            } else if (defaultValue instanceof Long) {
                Long lon = sp.getLong(key, (Long) defaultValue);
                defaultValue = (T) lon;
            } else if (defaultValue instanceof Float) {
                Float fl = sp.getFloat(key, (Float) defaultValue);
                defaultValue = (T) fl;
            } else if (defaultValue instanceof Boolean) {
                Boolean bl = sp.getBoolean(key, (Boolean) defaultValue);
                defaultValue = (T) bl;
            }
            return defaultValue;
        } catch (Exception e) {
            Log.d("SpUtil", Log.getStackTraceString(e));
            return null;
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static boolean remove(Context context, String key) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.edit().remove(key).commit();
        } catch (Exception e) {
            Log.d("SpUtil", Log.getStackTraceString(e));
            return false;
        }
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static boolean clear(Context context) {
        try {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.edit().clear().commit();
        } catch (Exception e) {
            Log.d("SpUtil", Log.getStackTraceString(e));
            return false;
        }
    }


}
