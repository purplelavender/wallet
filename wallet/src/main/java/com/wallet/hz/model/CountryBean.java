package com.wallet.hz.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wallet.hz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import share.exchange.framework.widget.CommonToast;

/**
 * Created by MMM on 2018/1/12.
 * 国家地区代码
 */
public class CountryBean implements Comparable<CountryBean>, Serializable {

    public String code;
    public String name;
    public String locale;
    public String pinyin;

    private static List<CountryBean> countries = null;

    public CountryBean(String code, String name, String locale) {
        this.code = code;
        this.name = name;
        this.locale = locale;
    }

    public static List<CountryBean> getAll(@NonNull Context context) {
        if (countries != null) return countries;
        countries = new ArrayList<>();
        boolean isEnglish = isEnglish(context);
        boolean isTraditionalChinese = isTraditionalChinese(context);
        BufferedReader br = null;
        try {
            InputStream country = context.getResources().getAssets().open("country.json");
            br = new BufferedReader(new InputStreamReader(country));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            JSONArray ja = new JSONArray(sb.toString());
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String locale = jo.getString("locale");
                if (isEnglish) {
                    countries.add(new CountryBean("+" + jo.getInt("code"), jo.getString("en"), locale));
                } else if (isTraditionalChinese) {
                    countries.add(new CountryBean("+" + jo.getInt("code"), jo.getString("zh-Hant"), locale));
                } else {
                    countries.add(new CountryBean("+" + jo.getInt("code"), jo.getString("zh"), locale));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonToast.showToast(context, CommonToast.ToastType.TEXT, context.getResources().getString(R.string.no_result));
        }
        return countries;
    }

    public static void destroy() {
        countries = null;
    }

    public static boolean isEnglish(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return Locale.US.equals(locale);
    }

    public static boolean isTraditionalChinese(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return Locale.TRADITIONAL_CHINESE.equals(locale);
    }

    @Override
    public int compareTo(CountryBean countryBean) {
        return this.pinyin.compareTo(countryBean.pinyin);
    }
}
