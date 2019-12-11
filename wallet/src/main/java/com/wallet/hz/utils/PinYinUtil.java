package com.wallet.hz.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: PinYinUtil
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/09 01:39
 */
public class PinYinUtil {

    public static String hanZiToPinyin(String hanZi) {
        StringBuilder sb = new StringBuilder();
        HanyuPinyinOutputFormat format = null;
        //如果该汉字为空
        if (StringUtil.isEmpty(hanZi)) {
            sb.append("#");
            return sb.toString();
        }
        //取出字符串里面的每一个字符
        char[] chars = hanZi.toCharArray();
        if (format == null) {
            //设置输出的一个配置
            format = new HanyuPinyinOutputFormat();
            //设置大写
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            //取消声调
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }
        for (char aChar : chars) {
            //去掉空格
            if (Character.isWhitespace(aChar)) {
                continue;
            }
            if (Character.toString(aChar).matches("[\\u4E00-\\u9FA5]")) {
                //是中文
                try {
                    //把汉字转换成拼音
                    String pinyin = PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0];
                    sb.append(pinyin);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                //不是文字
                if (Character.isLetter(aChar)) {
                    //是字母
                    sb.append(Character.toUpperCase(aChar));
                } else {
                    //非法字符，看不懂：#$%$%#^$
                    sb.append("#");
                }
            }
        }
        return sb.toString();
    }
}
