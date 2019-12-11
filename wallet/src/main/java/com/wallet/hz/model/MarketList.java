package com.wallet.hz.model;

import java.io.Serializable;
import java.util.ArrayList;

import share.exchange.framework.utils.StringUtil;

/**
 * @ClassName: MarketList
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/11/07 19:20
 */
public class MarketList implements Serializable {

    /**
     * "adpairs":[
     * <p>
     * ],
     * "ads":[
     * "MXC;https://www.mxc.co/auth/signup?inviteCode=1Zfr;https://s1.bqiapp.com/image/20190308/mexc_mid.png",
     * "A网;https://aofex.com/#/;https://s1.bqiapp.com/image/20190611/aofex_mid.png"
     * ],
     * "change_percent":-9.17,
     * "changerate_utc":-5.05,
     * "changerate_utc8":-6.2,
     * "code":"stellar",
     * "current_price":0.5083,
     * "current_price_usd":0.072959,
     * "drop_ath":-92.22,
     * "fullname":"恒星币",
     * "high_price":0.9381,
     * "high_time":"2018-01-04",
     * "isifo":0,
     * "ismineable":0,
     * "kline_data":"0.063468,0.063962,0.064100,0.070279,0.070374,0.069045,0.068802,0.069514,0.069888,0.070066,0.070051,0.069384,
     * 0.068083,0.068528,0.068731,0.067751,0.069254,0.069640,0.069494,0.081314,0.081320,0.081141,0.082993,0.080410,0.080596,
     * 0.077580,0.077196,0.074306",
     * "logo":"https://s1.bqiapp.com/coin/20181030_72_webp/stellar_200_200.webp",
     * "low_price":0.001227,
     * "low_time":"2014-11-18",
     * "market_value":9972736589,
     * "market_value_usd":1431239913,
     * "marketcap":7674082005,
     * "name":"XLM",
     * "rank":10,
     * "star_level":1,
     * "supply":19616918913,
     * "turnoverrate":12.4,
     * "vol":1239046756,
     * "vol_usd":177822121
     */

    private ArrayList<String> adpairs;
    private ArrayList<String> ads;
    private double change_percent;
    private double changerate_utc;
    private double changerate_utc8;
    private String code;
    private double current_price;
    private double current_price_usd;
    private double drop_ath;
    private String fullname;
    private double high_price;
    private String high_time;
    private int isifo;
    private int ismineable;
    private String kline_data;
    private String logo;
    private double low_price;
    private String low_time;
    private long market_value;
    private long market_value_usd;
    private long marketcap;
    private String name;
    private int rank;
    private int star_level;
    private long supply;
    private double turnoverrate;
    private long vol;
    private long vol_usd;

    public ArrayList<String> getAdpairs() {
        return adpairs;
    }

    public void setAdpairs(ArrayList<String> adpairs) {
        this.adpairs = adpairs;
    }

    public ArrayList<String> getAds() {
        return ads;
    }

    public void setAds(ArrayList<String> ads) {
        this.ads = ads;
    }

    public double getChange_percent() {
        return change_percent;
    }

    public void setChange_percent(double change_percent) {
        this.change_percent = change_percent;
    }

    public double getChangerate_utc() {
        return changerate_utc;
    }

    public void setChangerate_utc(double changerate_utc) {
        this.changerate_utc = changerate_utc;
    }

    public double getChangerate_utc8() {
        return changerate_utc8;
    }

    public void setChangerate_utc8(double changerate_utc8) {
        this.changerate_utc8 = changerate_utc8;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }

    public double getCurrent_price_usd() {
        return current_price_usd;
    }

    public void setCurrent_price_usd(double current_price_usd) {
        this.current_price_usd = current_price_usd;
    }

    public double getDrop_ath() {
        return drop_ath;
    }

    public void setDrop_ath(double drop_ath) {
        this.drop_ath = drop_ath;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public double getHigh_price() {
        return high_price;
    }

    public void setHigh_price(double high_price) {
        this.high_price = high_price;
    }

    public String getHigh_time() {
        return high_time;
    }

    public void setHigh_time(String high_time) {
        this.high_time = high_time;
    }

    public int getIsifo() {
        return isifo;
    }

    public void setIsifo(int isifo) {
        this.isifo = isifo;
    }

    public int getIsmineable() {
        return ismineable;
    }

    public void setIsmineable(int ismineable) {
        this.ismineable = ismineable;
    }

    public String getKline_data() {
        return kline_data;
    }

    public void setKline_data(String kline_data) {
        this.kline_data = kline_data;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getLow_price() {
        return low_price;
    }

    public void setLow_price(double low_price) {
        this.low_price = low_price;
    }

    public String getLow_time() {
        return low_time;
    }

    public void setLow_time(String low_time) {
        this.low_time = low_time;
    }

    public String getName() {
        return StringUtil.isEmpty(name) ? name : name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getStar_level() {
        return star_level;
    }

    public void setStar_level(int star_level) {
        this.star_level = star_level;
    }

    public double getTurnoverrate() {
        return turnoverrate;
    }

    public void setTurnoverrate(double turnoverrate) {
        this.turnoverrate = turnoverrate;
    }

    public long getMarket_value() {
        return market_value;
    }

    public void setMarket_value(long market_value) {
        this.market_value = market_value;
    }

    public long getMarket_value_usd() {
        return market_value_usd;
    }

    public void setMarket_value_usd(long market_value_usd) {
        this.market_value_usd = market_value_usd;
    }

    public long getMarketcap() {
        return marketcap;
    }

    public void setMarketcap(long marketcap) {
        this.marketcap = marketcap;
    }

    public long getSupply() {
        return supply;
    }

    public void setSupply(long supply) {
        this.supply = supply;
    }

    public long getVol() {
        return vol;
    }

    public void setVol(long vol) {
        this.vol = vol;
    }

    public long getVol_usd() {
        return vol_usd;
    }

    public void setVol_usd(long vol_usd) {
        this.vol_usd = vol_usd;
    }
}
