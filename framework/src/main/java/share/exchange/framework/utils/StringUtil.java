package share.exchange.framework.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ClassName:      StringUtil
 * @Description:    字符串操作工具包
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:35
 */
public class StringUtil {

    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static final String URL_PATTERN = "([Hh]ttps?|[Ff]tp|[Ff]ile)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    private StringUtil() {
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     *
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组是否
     *
     * @param paramArrayOfObject
     *
     * @return
     *
     * @author yangxd
     */
    public static boolean isEmpty(Object[] paramArrayOfObject) {
        return (paramArrayOfObject == null) || (paramArrayOfObject.length == 0) || ((paramArrayOfObject.length == 1) && (paramArrayOfObject[ 0 ] == null));
    }

    /**
     * 判断是否为空
     *
     * @param paramCollection
     *
     * @return
     *
     * @author yangxd
     */
    public static <T> boolean isEmpty(Collection<T> paramCollection) {
        return (paramCollection == null) || (paramCollection.isEmpty());
    }

    /**
     * 验证联系电话【手机号码或者座机】
     *
     * @param phone 手机号码或者座机
     *
     * @return
     *
     * @author wragony
     */
    public static boolean checkPhone(String phone) {
        if (phone.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)")) {
            return true;
        } else return phone.matches("^[1][1,2,3,4,5,6,7,8,9]+\\d{9}");
    }

    /**
     * 是否是手机号码
     *
     * @param mobile
     *
     * @return
     *
     * @author wragony
     */
    public static boolean isMobile(String mobile) {
        return mobile.matches("^[1][1,2,3,4,5,6,7,8,9]+\\d{9}");
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static boolean isEmail(String email) {
        if (email == null || "".equals(email) || email.trim().length() == 0)
            return false;
        email = email.toLowerCase();
        if (email.endsWith(".con"))
            return false;
        if (email.endsWith(".cm"))
            return false;
        Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return emailer.matcher(email).matches();
    }

    /**
     * 是否是有效的密码[可以是纯数字，也可以是纯字母，也可以是数字+字母,6-16 位]
     *
     * @param password
     *
     * @return
     *
     * @author wragony
     */
    public static boolean isPasswordValid(String password) {
        return Pattern.matches("^[0-9a-zA-Z]{6,20}", password);
    }

    /**
     * 判断密码是否符合规定，只支持数字+字母的6~20位字符
     *
     * @param password
     *
     * @return
     */
    public static boolean isPasswordWake(String password) {
        return Pattern.compile(".*[a-zA-Z]+.*").matcher(password).matches() && Pattern.compile(".*[0-9]+.*").matcher(password).matches();
    }

    /**
     * 取得字符串的实际长度（考虑了汉字的情况）
     *
     * @param srcStr 源字符串
     *
     * @return 字符串的实际长度
     */
    public static int getStringLen(String srcStr) {
        int return_value = 0;
        if (srcStr != null) {
            char[] theChars = srcStr.toCharArray();
            for (char theChar : theChars) {
                return_value += (theChar <= 255) ? 1 : 2;
            }
        }
        return return_value;
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     *
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     *
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     *
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     *
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 组装 http url
     *
     * @param p_url
     * @param params
     *
     * @return
     *
     * @author wragony
     */
    @SuppressWarnings(
            { "unused", "deprecation" })
    private static String makeURL(String p_url, Map<String, Object> params) {
        StringBuilder url = new StringBuilder(p_url);
        if (url.indexOf("?") < 0)
            url.append('?');
        for (String name : params.keySet()) {
            url.append('&');
            url.append(URLEncoder.encode(name));
            url.append('=');
            url.append(URLEncoder.encode(String.valueOf(params.get(name))));
        }
        return url.toString().replace("?&", "?");
    }

    /**
     * MD5加密
     *
     * @param password
     *
     * @return
     *
     * @auther
     */
    public static String encrypt(String password) {
        String result = null;
        if (password != null) {
            try {
                MessageDigest ca = MessageDigest.getInstance("md5");
                result = "";
                byte mess[] = password.getBytes();
                ca.reset();
                byte[] hash = ca.digest(mess);
                result = byte2hex(hash);
            } catch (Exception e) {
                Log.e("encrypt", "加密失败!");
            }
        }
        return result;
    }

    /**
     * 加密调用的方法
     *
     * @param b 字符
     *
     * @return
     *
     * @auther
     */
    @SuppressLint("DefaultLocale")
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp;
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    /**
     * byteArr转hexString
     * <p>例如：</p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     *
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[ len << 1 ];
        for (int i = 0, j = 0; i < len; i++) {
            ret[ j++ ] = hexDigits[ bytes[ i ] >>> 4 & 0x0f ];
            ret[ j++ ] = hexDigits[ bytes[ i ] & 0x0f ];
        }
        return new String(ret);
    }

    /**
     * 去除字符串中转义符。
     *
     * @param str
     *
     * @return
     *
     * @author yh
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }

    /**
     * 解析类似： "[\"www.baidu.com\",\"www.xinlang.com\",\"www.xinlang.com\"]";
     *
     * @param jsonStr
     *
     * @return
     *
     * @author wragony
     */
    public static String[] parseStringArray(String jsonStr) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> strings = JSONArray.parseArray(jsonStr, String.class);
            if (null != strings && strings.size() > 0) {
                String result = "";
                for (String string : strings) {
                    sb.append(string).append(",");
                }
                if (sb.toString().endsWith(",")) {
                    result = sb.toString().substring(0, sb.toString().length() - 1);
                }
                return result.split(",");
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 解析类似： "[0,1,2,3]";
     *
     * @param jsonStr
     *
     * @return
     *
     * @author wragony
     */
    public static Integer[] parseIntArray(String jsonStr) {
        Integer[] results = null;
        try {
            List<Integer> strings = JSONArray.parseArray(jsonStr, Integer.class);
            if (null != strings && strings.size() > 0) {
                results = new Integer[ strings.size() ];
                for (int i = 0; i < strings.size(); i++) {
                    results[ i ] = strings.get(i);
                }
            }
        } catch (Exception e) {
        }
        return results;
    }

    /**
     * 是否是数字
     *
     * @param str
     *
     * @return
     *
     * @author wragony
     */
    public static boolean isDigtal(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        return match.matches() != false;
    }

    /**
     * 是否是数字[正数、负数、和小数]
     *
     * @param str
     *
     * @return
     *
     * @author wragony
     */
    public static boolean isNumber2(String str) {
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
        Matcher match = pattern.matcher(str);
        return match.matches() != false;
    }

    /**
     * 是否是数字［］
     *
     * @param str
     *
     * @return
     *
     * @author wragony
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("-?[1-9][0-9]*");
        Matcher match = pattern.matcher(str);
        return match.matches() != false;
    }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     *
     * @return
     */
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * @param src 源字符串
     *
     * @return 结果
     *
     * @description 去掉字符串最后一个字符
     * @author wragony
     */
    public static String removeLast(String src) {
        String result = src;
        if (!isEmpty(src)) {
            result = src.substring(0, src.length() - 1);
        }
        return result;
    }

    /**
     * @param content 源字符串
     *
     * @return 截取后的数字
     *
     * @description 截取字符串中的数字
     * @author wragony
     */
    public static String getNumbersOfString(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * @param url
     *
     * @return
     *
     * @description 是否是网络地址
     * @author wragony
     */
    public static boolean isHttpAddr(String url) {
        try {
            if (!isEmpty(url)) {
                String pattern = "([Hh]ttps?|[Ff]tp)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
                Pattern patt = Pattern.compile(pattern);
                Matcher matcher = patt.matcher(url);
                return matcher.matches();
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * 从一段文字中，获取完整的url地址
     *
     * @param text 待查找的文本
     *
     * @return 多个匹配到的结果, null 表示一个也没匹配到
     */
    public static String[] getCompleteUrl(String text) {
        try {
            if (!isEmpty(text)) {
                ArrayList<String> results = new ArrayList<>();
                Pattern p = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
                Matcher matcher = p.matcher(text);
                while (matcher.find()) {
                    results.add(matcher.group());
                }
                if (results.size() > 0) {
                    return results.toArray(new String[ 0 ]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 四舍五入取整
     *
     * @param sourceNumber
     *
     * @return
     *
     * @author wragony
     */

    public static int roundingNumber(String sourceNumber) {
        BigDecimal bigDecimal = new BigDecimal(sourceNumber).setScale(0, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.intValue();
    }

    /**
     * @param sourceNumber
     * @param newScale
     * @param roundingMode
     *
     * @return
     *
     * @description 浮点数小数点处理
     * @author wragony
     */
    public static BigDecimal formatNumber(String sourceNumber, int newScale, int roundingMode) {
        BigDecimal bigDecimal = new BigDecimal(sourceNumber).setScale(newScale, roundingMode);
        return bigDecimal;
    }

    /**
     * @param s
     *
     * @return
     *
     * @description 使用正则表达式去掉多余的.与0
     * @author wragony
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * List 去重复
     *
     * @param dataList
     *
     * @return
     *
     * @author wragony
     */
    @SuppressWarnings(
            { "rawtypes", "unchecked" })
    public static List removeDuplicate(List dataList) {
        HashSet set = new HashSet(dataList);
        dataList.clear();
        dataList.addAll(set);
        return dataList;
    }

    @SuppressWarnings(
            { "rawtypes", "unchecked" })
    public static List removeDuplicate2(List dataList) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Object element : dataList) {
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /**
     * @param stringList
     * @param seperator
     *
     * @return
     *
     * @description List转为指定符号隔开的String字符串
     * @author wragony
     * @modifier
     * @modifier_date
     */
    public static <T> String listToString(List<T> stringList, String seperator) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (T t : stringList) {
            if (flag) {
                result.append(seperator);
            } else {
                flag = true;
            }
            result.append(t.toString());
        }
        return result.toString();
    }

    /**
     * List转成字符串 分隔符|
     *
     * @param imageurl
     *
     * @return
     */
    public static String getUrlStr(List<String> imageurl) {
        String fileUrl = "";
        if (imageurl.size() > 0) {
            for (int i = 0; i < imageurl.size(); i++) {
                if (fileUrl.equals("")) {
                    fileUrl = "file://" + imageurl.get(i);
                } else {
                    fileUrl = fileUrl + "|" + "file://" + imageurl.get(i);
                }
            }
        }
        return fileUrl;
    }

    /**
     * @param seperator 分割符
     * @param imageUrl  以分隔符隔开的字符串
     *
     * @return
     *
     * @description 将以分隔符隔开的字符串转换为List
     * @author wragony
     */
    public static ArrayList<String> getUrlStrList(String imageUrl, String seperator) {
        ArrayList<String> results = new ArrayList<>();
        if (!isEmpty(imageUrl)) {
            try {
                String[] imageUrls = imageUrl.split(seperator);
                for (String image : imageUrls) {
                    results.add(image);
                }
            } catch (Exception e) {
            }
        }
        return results;
    }

    /**
     * 数组转String
     *
     * @param srcArray
     * @param seperator
     *
     * @return
     */
    public static String array2String(String[] srcArray, String seperator) {
        String result = null;
        if (null != srcArray) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < srcArray.length; i++) {
                sb.append(srcArray[ i ]);
                if (i != srcArray.length - 1) {
                    sb.append(seperator);
                }
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * 字符串转为指定符号隔开的数组
     *
     * @param string
     * @param seperator
     *
     * @return
     *
     * @modifier_date
     * @author 李平
     */
    public static String[] stringToArray(String string, String seperator) {
        if (StringUtil.isEmpty(string)) {
            return null;
        }
        String[] stringArray = string.split(seperator);
        return stringArray;
    }

    /**
     * 字符串数组合并
     *
     * @param a
     * @param b
     *
     * @return
     *
     * @author wragony
     */
    public static String[] concat(String[] a, String[] b) {
        String[] c = new String[ a.length + b.length ];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 转换成百分比
     *
     * @param total   总基数
     * @param num     要转换的数
     * @param maximum 精确到小数点后第几位
     *
     * @return
     *
     * @description TODO
     * @modifier_date
     * @author 李平
     */
    public static String getPercent(int total, int num, int maximum) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) num / (float) total * 100) + "%";
        return result;
    }

    public static String getApiURL(String apiURL, Map<String, String> paramMap) {
        Iterator<Entry<String, String>> iter = paramMap.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(apiURL);
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            if (sb.indexOf("?") != -1) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    /**
     * 是否是需要模拟手机号码
     *
     * @param mobile
     * @return
     * @author wragony
     */
    public static boolean isSimulateLoginMobile(String mobile) {
        return mobile.matches("^[mM][nN][1][1,2,3,4,5,6,7,8,9]+\\d{9}");
    }

    /**
     * 判断是否包含
     * @param value
     * @param value2
     * @return
     */
    public static boolean isContain(String value, String value2) {
        return value != null && value.contains(value2);
    }
}
