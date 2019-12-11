package share.exchange.framework.constant;

import java.util.Locale;

/**
 * @ClassName: LanguageEnums
 * @Description: java类作用描述
 * @Author: Administrator
 * @CreateDate: 2019/05/05 15:26
 */
public enum LanguageEnums {

    DEFAULT{
        @Override
        public String getValues() {
            return "简体中文";
        }

        @Override
        public int getKey() {
            return 3;
        }

        @Override
        public Locale getLocalLanguage() {
            return Locale.SIMPLIFIED_CHINESE;
        }
    },
    SIMPLIFIED_CHINESE{
        @Override
        public String getValues() {
            return "简体中文";
        }

        @Override
        public int getKey() {
            return 1;
        }

        @Override
        public Locale getLocalLanguage() {
            return Locale.SIMPLIFIED_CHINESE;
        }
    },
    TRADITIONAL_CHINESE{
        @Override
        public String getValues() {
            return "繁體中文";
        }

        @Override
        public int getKey() {
            return 2;
        }

        @Override
        public Locale getLocalLanguage() {
            return Locale.TRADITIONAL_CHINESE;
        }
    },
    ENGLISH{
        @Override
        public String getValues() {
            return "English";
        }

        @Override
        public int getKey() {
            return 0;
        }

        @Override
        public Locale getLocalLanguage() {
            return Locale.US;
        }
    };

    public abstract String getValues();

    public abstract int getKey();

    public abstract Locale getLocalLanguage();

    /**
     * 根据给定的key值获取语言类型
     * @param key
     * @return
     */
    public static String getValueByKey(int key) {
        for (LanguageEnums en : LanguageEnums.values()) {
            if (en.getKey() == key){
                return en.getValues();
            }
        }
        return DEFAULT.getValues();
    }

    /**
     * 根据给定的key值获取语言类型
     * @param key
     * @return
     */
    public static Locale getLanguageByKye(int key) {
        for (LanguageEnums en : LanguageEnums.values()) {
            if (en.getKey() == key){
                return en.getLocalLanguage();
            }
        }
        return DEFAULT.getLocalLanguage();
    }
}
