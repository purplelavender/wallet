package share.exchange.framework.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

    /**
     * 用MD5算法进行加密
     *
     * @param str 需要加密的字符串
     * @return MD5加密后的结果
     */
    public static String encodeMD5(String str) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] decodeBase64(final byte[] bytes) {
        return Base64.decode(bytes, Base64.NO_WRAP);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encodeBase64(final byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param srcStr
     * @return
     * @throws Exception
     */
    public static String encodeBase64(final String srcStr) {
        if (null != srcStr && !"".equals(srcStr)) {
            return encodeBase64(srcStr.getBytes());
        }
        return srcStr;
    }

    /**
     * HMAC-SHA1
     *
     * @param s
     * @param keyString
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String sha1(String s, String keyString) {
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));
            return new String(encodeBase64(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyString;
    }

}
