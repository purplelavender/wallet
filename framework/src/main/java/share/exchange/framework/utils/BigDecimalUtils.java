package share.exchange.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @ClassName:      BigDecimalUtils
 * @Description:    资金运算工具类
 * @Author:         ZL
 * @CreateDate:     2019/08/12 09:21
 */
public class BigDecimalUtils {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 8;

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isEqual("" + v1, "NaN") ? "0" : Double.toString(v1));
        BigDecimal b2 = new BigDecimal(StringUtil.isEqual("" + v2, "NaN") ? "0" : Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return b1.add(b2).toString();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留scale 位小数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return format(b1.add(b2).setScale(scale, BigDecimal.ROUND_DOWN), scale);
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isEqual("" + v1, "NaN") ? "0" : Double.toString(v1));
        BigDecimal b2 = new BigDecimal(StringUtil.isEqual("" + v2, "NaN") ? "0" : Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return b1.subtract(b2).toString();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return format(b1.subtract(b2).setScale(scale, BigDecimal.ROUND_DOWN), scale);
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isEqual("" + v1, "NaN") ? "0" : Double.toString(v1));
        BigDecimal b2 = new BigDecimal(StringUtil.isEqual("" + v2, "NaN") ? "0" : Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return b1.multiply(b2).toString();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return format(b1.multiply(b2).setScale(scale, BigDecimal.ROUND_DOWN), scale);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(double v1, double v2) {
        return div(v1 + "", v2 + "", DEF_DIV_SCALE);
    }

    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        if(StringUtil.isZero(v1) || StringUtil.isZero(v2)) {
            return "0";
        } else {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return format(b1.divide(b2, scale, BigDecimal.ROUND_DOWN), scale);
        }
    }

    /**
     * v1占v2的百分比
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static float percent(String v1, String v2, int scale) {
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        BigDecimal b3 = new BigDecimal("100");
        BigDecimal decimal = b1.multiply(b3);
        return decimal.divide(b2, scale, BigDecimal.ROUND_DOWN).floatValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String decimalPoint(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b = new BigDecimal(StringUtil.isZero(v) ? "0" : v);
        return format(b.setScale(scale, BigDecimal.ROUND_DOWN), scale);
    }

    /**
     * 取余数
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 小数点后保留几位
     * @return 余数
     */
    public static String remainder(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        return format(b1.remainder(b2).setScale(scale, BigDecimal.ROUND_DOWN), scale);
    }

    /**
     * 比较大小
     *
     * @param v1 被比较数
     * @param v2 比较数
     * @return 如果v1 大于v2 则 返回true 否则false
     */
    public static boolean compare(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(StringUtil.isZero(v1) ? "0" : v1);
        BigDecimal b2 = new BigDecimal(StringUtil.isZero(v2) ? "0" : v2);
        int bj = b1.compareTo(b2);
        return bj > 0;
    }

    /**
     * 预防计算过后因为小数点的关系变成0.00000000之类的情况
     * 若计算过后正常，则直接返回原数字
     * 若精确位数后为0，则去相应为数的0数字返回
     * @param decimal
     * @param scale
     * @return
     */
    public static String format(BigDecimal decimal, int scale) {
        if (decimal.compareTo(new BigDecimal("0.00000001")) >= 0) {
            return decimal.toString();
        } else {
            DecimalFormat format = new DecimalFormat("0.00000000");
            String result = format.format(decimal);
            switch (scale) {
                case 0:
                    format = new DecimalFormat("0");
                    result = format.format(decimal);
                    break;
                case 1:
                    format = new DecimalFormat("0.0");
                    result = format.format(decimal);
                    break;
                case 2:
                    format = new DecimalFormat("0.00");
                    result = format.format(decimal);
                    break;
                case 3:
                    format = new DecimalFormat("0.000");
                    result = format.format(decimal);
                    break;
                case 4:
                    format = new DecimalFormat("0.0000");
                    result = format.format(decimal);
                    break;
                case 5:
                    format = new DecimalFormat("0.00000");
                    result = format.format(decimal);
                    break;
                case 6:
                    format = new DecimalFormat("0.000000");
                    result = format.format(decimal);
                    break;
                case 7:
                    format = new DecimalFormat("0.0000000");
                    result = format.format(decimal);
                    break;
                case 8:
                    format = new DecimalFormat("0.00000000");
                    result = format.format(decimal);
                    break;
            }
            return formatServiceNumber(result);
        }
    }

    /**
     * 将科学计数法转成正常显示的数字
     * @param number
     * @return
     */
    public static String formatServiceNumber(double number) {
        return formatServiceNumber(number + "");
    }

    public static String formatServiceNumber(String number) {
        BigDecimal b = new BigDecimal(number + "");
        return b.toPlainString();
    }

    /**
     * 判断输入的是否是小数并且位数大于8
     * @param number
     * @return
     */
    public static boolean isEightPoint(String number) {
        int length = number.length();
        int index = number.indexOf(".");
        return index > 0 && length - index > 9;
    }
}
