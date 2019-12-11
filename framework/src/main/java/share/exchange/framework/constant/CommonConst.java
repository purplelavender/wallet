package share.exchange.framework.constant;

import android.Manifest;

/**
 *
 * @ClassName:      CommonConst
 * @Description:    公用常量
 * @Author:         ZL
 * @CreateDate:     2019/10/21 10:30
 */
public class CommonConst {

    //是否类型
    public static final int FLAG_YES = 1;
    public static final int FLAG_NO = 0;


    /********************* 动态权限相关[10001-19999] begin **********************************/
    public static final int REQUEST_PERMISSION_CALENDAR = 1001;
    public static final int REQUEST_PERMISSION_CAMERA = 1002;
    public static final int REQUEST_PERMISSION_CONTACTS = 1003;
    public static final int REQUEST_PERMISSION_LOCATION = 1004;
    public static final int REQUEST_PERMISSION_MICROPHONE = 1005;
    public static final int REQUEST_PERMISSION_PHONE = 1006;
    public static final int REQUEST_PERMISSION_SENSORS = 1007;
    public static final int REQUEST_PERMISSION_SMS = 1008;
    public static final int REQUEST_PERMISSION_STORAGE = 1009;
    public static final int REQUEST_PERMISSION_AUDIO = 1010;
    public static final int REQUEST_PERMISSION_INSTALL = 1012;
    //用户权限
    public static final int REQUEST_CODE_USER_PERMISSION = 1011;


    public static final String[] PERMISSION_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final String[] PERMISSION_AUDIO = new String[]{
            Manifest.permission.RECORD_AUDIO
    };

    public static final String[] PERMISSION_AUDIO_STORAGE = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static final String[] PERMISSION_CAMERA_STORAGE = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static final String[] PERMISSION_CAMERA_AUDIO_STORAGE = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static final String[] PERMISSION_CONTACTS = new String[]{
            Manifest.permission.READ_CONTACTS
    };

    public static final String[] PERMISSION_PHONE = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    public static final String[] PERMISSION_STORAGE_LOACTION_PHONE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };

    public static final String[] PERMISSION_STORAGE_PHONE = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE};

    public static final String[] PERMISSION_LOCATION = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final String[] PERMISSION_STORAGE_AUDIO = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public static final String[] PERMISSION_INSTALL = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };

    /********************* 动态权限相关 end **********************************/

}
