package share.exchange.framework.utils;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: MobileUtil
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/08/20 11:49
 */
public class MobileUtil {

    /**
     * Print telephone info.
     */
    public static String printMobileInfo(Context context) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("系统时间：").append(time).append("\n");
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = tm.getSubscriberId();
        // IMSI前面三位460是国家号码，其次的两位是运营商代号，00、02是中国移动，01是联通，03是电信。
        String providerName = null;
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providerName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providerName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providerName = "中国电信";
            }
        }
        sb.append(providerName).append("\n")
                .append(getNativePhoneNumber(context)).append("\n网络模式：")
                .append(EnvironmentUtil.getNetType(context)).append("\nIMSI是：").append(IMSI);
        sb.append("\nDeviceID(IMEI)       :").append(tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion:").append(
                tm.getDeviceSoftwareVersion());
        sb.append("\ngetLine1Number       :").append(tm.getLine1Number());
        sb.append("\nNetworkCountryIso    :").append(tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator      :").append(tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName  :").append(
                tm.getNetworkOperatorName());
        sb.append("\nNetworkType          :").append(tm.getNetworkType());
        sb.append("\nPhoneType            :").append(tm.getPhoneType());
        sb.append("\nSimCountryIso        :").append(tm.getSimCountryIso());
        sb.append("\nSimOperator          :").append(tm.getSimOperator());
        sb.append("\nSimOperatorName      :").append(tm.getSimOperatorName());
        sb.append("\nSimSerialNumber      :").append(tm.getSimSerialNumber());
        sb.append("\ngetSimState          :").append(tm.getSimState());
        sb.append("\nSubscriberId         :").append(tm.getSubscriberId());
        sb.append("\nVoiceMailNumber      :").append(tm.getVoiceMailNumber());

        return sb.toString();
    }

    /**
     * 打印系统信息
     *
     * @return
     */
    public static String printSystemInfo() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        StringBuilder sb = new StringBuilder();
        sb.append("_______  系统信息  ").append(time).append(" ______________");
        sb.append("\nID                 :").append(Build.ID);
        sb.append("\nBRAND              :").append(Build.BRAND);
        sb.append("\nMODEL              :").append(Build.MODEL);
        sb.append("\nRELEASE            :").append(Build.VERSION.RELEASE);
        sb.append("\nSDK                :").append(Build.VERSION.SDK);

        sb.append("\n_______ OTHER _______");
        sb.append("\nBOARD              :").append(Build.BOARD);
        sb.append("\nPRODUCT            :").append(Build.PRODUCT);
        sb.append("\nDEVICE             :").append(Build.DEVICE);
        sb.append("\nFINGERPRINT        :").append(Build.FINGERPRINT);
        sb.append("\nHOST               :").append(Build.HOST);
        sb.append("\nTAGS               :").append(Build.TAGS);
        sb.append("\nTYPE               :").append(Build.TYPE);
        sb.append("\nTIME               :").append(Build.TIME);
        sb.append("\nINCREMENTAL        :").append(Build.VERSION.INCREMENTAL);

        sb.append("\n_______ CUPCAKE-3 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            sb.append("\nDISPLAY            :").append(Build.DISPLAY);
        }

        sb.append("\n_______ DONUT-4 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            sb.append("\nSDK_INT            :").append(Build.VERSION.SDK_INT);
            sb.append("\nMANUFACTURER       :").append(Build.MANUFACTURER);
            sb.append("\nBOOTLOADER         :").append(Build.BOOTLOADER);
            sb.append("\nCPU_ABI            :").append(Build.CPU_ABI);
            sb.append("\nCPU_ABI2           :").append(Build.CPU_ABI2);
            sb.append("\nHARDWARE           :").append(Build.HARDWARE);
            sb.append("\nUNKNOWN            :").append(Build.UNKNOWN);
            sb.append("\nCODENAME           :").append(Build.VERSION.CODENAME);
        }

        sb.append("\n_______ GINGERBREAD-9 _______");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sb.append("\nSERIAL             :").append(Build.SERIAL);
        }
        return sb.toString();
    }

    /**
     * 获取手机号码
     *
     * @param c
     * @return
     */
    public static String getNativePhoneNumber(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = tm.getLine1Number();
        if (null != tel && !"".equals(tel)) {
            if (tel.startsWith("+86")) {
                tel = tel.replace("+86", "").trim();
            } else if (tel.startsWith("+")) {
                tel = tel.replace("+", "").trim();
            }
        }
        return tel;
    }

    /**
     * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
     * IMSI共有15位，其结构如下： <br>
     * MCC+MNC+MIN <br>
     * MCC：Mobile Country Code，移动国家码，共3位，中国为460;<br>
     * MNC:Mobile NetworkCode，移动网络码，共2位 <br>
     * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03<br>
     * 合起来就是（也是Android手机中APN配置文件中的代码）： <br>
     * 中国移动：46000 46002 <br>
     * 中国联通：46001 <br>
     * 中国电信：46003 <br>
     * 举例，一个典型的IMSI号码为460030912121001 <br>
     */
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    /**
     * IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称
     * IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的 其组成为：
     * 1.前6位数(TAC)是”型号核准号码”，一般代表机型 <br>
     * 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地 <br>
     * 3.之后的6位数(SNR)是”串号”，一般代表生产顺序号 <br>
     * 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用<br>
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取移动网络运营商名称
     * 如中国联通、中国移动、中国电信
     *
     * @param context 上下文
     * @return 移动网络运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * 获取 开机时间
     */
    public static String getBootTimeString() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        int h = (int) ((ut / 3600));
        int m = (int) ((ut / 60) % 60);

        return h + ":" + m;
    }

    /**
     * Return pseudo unique ID
     * 获取一个唯一标志码
     *
     * @return ID
     */
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE
                .length() % 10) + (Build.MANUFACTURER.length() % 10) + (
                Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 获取设备DEVICEID
     *
     * @param c
     * @return
     */
    @Deprecated
    public static String getDEVICEID(Context c) {
        String deviceid = "";
        try {
            TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            deviceid = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceid;
    }

    /**
     * 获取设备SUBSCRIBERID
     *
     * @param c
     * @return
     */
    public static String getSUBSCRIBERID(Context c) {
        TelephonyManager phoneMgr = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        return phoneMgr.getSubscriberId();
    }
}
