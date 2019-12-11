package com.wallet.hz.constant;

/**
 * @ClassName: ApiConstant
 * @Description: java类作用描述
 * @Author: ZL
 * @CreateDate: 2019/10/30 09:44
 */
public class ApiConstant {

    // 测试地址
//    public static final String WEB_URL = "http://39.98.170.113:9200";
    //长连接获取行情列表
//    public static final String URL_MARKET_LIST = "ws://18.140.30.55:8080/api/v1/wallet/getMarket";

    // 正式地址
    public static final String WEB_URL = "https://jeapi.hilbertspace.vip";
    //长连接获取行情列表
    public static final String URL_MARKET_LIST = "ws://3.0.126.85:8080/api/v1/wallet/getMarket";
    //获取行情列表
    public static final String URL_REQUEST_MARKET_LIST = "http://3.0.126.85:8080/api/v1/wallet/market";

    /*********  其他接口去要用到的数据 **************/
    //分页数量
    public static final int PAGE_LIMIT = 15;

    /********* 接口 **************/
    //版本更新
    public static final String URL_CHECK_VERSION = WEB_URL + "/api/version";
    //登录
    public static final String URL_LOGIN = WEB_URL + "/api/login";
    //登出
    public static final String URL_LOGOUT = WEB_URL + "/api/logout";
    //上传
    public static final String URL_UPLOAD = WEB_URL + "/api/upload?files";
    //结束工单
    public static final String URL_ORDER_END = WEB_URL + "/api/workorder/end";
    //提交工单
    public static final String URL_ORDER_INSERT = WEB_URL + "/api/workorder/insert";
    //工单列表
    public static final String URL_ORDER_LIST = WEB_URL + "/api/workorder/list";
    //回复工单
    public static final String URL_ORDER_REPLY = WEB_URL + "/api/workorder/reply";
    //公告列表
    public static final String URL_ANNOUNCEMENT_LIST = WEB_URL + "/api/announcement/list";
    //获取用户信息
    public static final String URL_USERINFO = WEB_URL + "/api/mine";
    //获取财务明细列表
    public static final String URL_FINANCIAL_DETAIL_LIST = WEB_URL + "/api/caiwu/list";
    //提交项目申请
    public static final String URL_PROJECT_SUBMIT = WEB_URL + "/api/xiangmu";
    //获取项目申请列表
    public static final String URL_PROJECT_LIST = WEB_URL + "/api/xianmgu/list";
    //获取社群主页信息
    public static final String URL_GROUP_INFO = WEB_URL + "/api/shequ";
    //获取邀请人收益列表
    public static final String URL_INVITE_EARNING_LIST = WEB_URL + "/api/shequ/star";
    //获取服務協議
    public static final String URL_SERVICE_AGREEMENT = WEB_URL + "/api/agreement";

    /*************************************** 钱包相关 ****************************************/
    //删除充币地址
    public static final String URL_ADDRESS_DELETE = WEB_URL + "/api/address/delete";
    //充币地址列表
    public static final String URL_ADDRESS_LIST = WEB_URL + "/api/address/list";
    //保存充币地址
    public static final String URL_ADDRESS_SAVE = WEB_URL + "/api/address/save";
    //获取钱包首页的总资产
    public static final String URL_WALLET_TOTAL = WEB_URL + "/api/totalusdt";
    //获取钱包首页底部的货币列表
    public static final String URL_WALLET_INFO = WEB_URL + "/api/walletinfo";
    //获取某个货币的汇率,余额
    public static final String URL_WALLET_COIN_BALANCE = WEB_URL + "/api/coinbalance";
    //获取钱包的历史记录
    public static final String URL_WALLET_HISTORY_RECORD = WEB_URL + "/api/wallet/list";
    //获取货币交易列表
    public static final String URL_WALLET_COIN_DETAIL_LIST = WEB_URL + "/api/coin/list";
    //转账
    public static final String URL_WALLET_TRANSFER = WEB_URL + "/api/tibi";

    /*************************************** 注册绑定找回相关 ****************************************/
    //绑定手机
    public static final String URL_BING_PHONE = WEB_URL + "/api/bingphone";
    //设置交易密码
    public static final String URL_JY_PASSWORD = WEB_URL + "/api/jypassword";
    //获取手机验证码
    public static final String URL_PHONECODES = WEB_URL + "/api/phonecodes";
    //修改绑定手机获取手机验证码
    public static final String URL_PHONECODES_CHANGE = WEB_URL + "/api/updatephonecode";
    //找回密码获取手机验证码
    public static final String URL_PHONECODES_ZHAOHUI = WEB_URL + "/api/phonezhaohui";
    //设置资金密码获取手机验证码
    public static final String URL_PHONECODES_JY = WEB_URL + "/api/zjphonecode";
    //忘记资金密码获取手机验证码
    public static final String URL_PHONECODES_FORGOT_JY = WEB_URL + "/api/wjzjphonecode";
    //绑定谷歌获取手机验证码
    public static final String URL_PHONECODES_GUGE = WEB_URL + "/api/bdgugephonecode";
    //获取邮箱验证码
    public static final String URL_MAILCODES = WEB_URL + "/api/emailcodes";
    //找回密码获取邮箱验证码
    public static final String URL_MAILCODES_ZHAOHUI = WEB_URL + "/api/emailzhaohui";
    //设置资金密码获取邮箱验证码
    public static final String URL_MAILCODES_JY = WEB_URL + "/api/zjemailcodes";
    //忘记资金密码获取邮箱验证码
    public static final String URL_MAILCODES_FORGOT_JY = WEB_URL + "/api/wjzjemailcodes";
    //绑定谷歌获取邮箱验证码
    public static final String URL_MAILCODES_GUGE = WEB_URL + "/api/bdgugeemailcodes";
    //注册
    public static final String URL_REGISTER = WEB_URL + "/api/register";
    //修改交易密码
    public static final String URL_UPJY_PASSWORD = WEB_URL + "/api/upjypassword";
    //修改登录密码
    public static final String URL_UPLOGIN_PASSWORD = WEB_URL + "/api/uploginpassword";
    //获取助记词
    public static final String URL_ZHUJICI = WEB_URL + "/api/zhujici";
    //验证用户名
    public static final String URL_YANZHENG = WEB_URL + "/api/yanzheng";
    //验证邀请码
    public static final String URL_YANZHENG_CODE = WEB_URL + "/api/yanzhengcode";
    //绑定邮箱
    public static final String URL_BING_MAIL = WEB_URL + "/api/bingemail";
    //绑定谷歌
    public static final String URL_BING_GUGE = WEB_URL + "/api/bingguge";
    //获取谷歌码
    public static final String URL_GUGE_CODE = WEB_URL + "/api/guge";
    //修改绑定手机确认
    public static final String URL_UPDATE_PHONE = WEB_URL + "/api/updatenewphone";
    //修改绑定手机号
    public static final String URL_UPDATE_PHONE_SURE = WEB_URL + "/api/updatephone";
    //风险提示
    public static final String URL_POINTS = WEB_URL + "/api/points";
    //手机邮箱找回密码
    public static final String URL_PHONEMAIL_ZHAOHUI_PASSWORD = WEB_URL + "/api/peupdatepassword";
    //谷歌找回密码
    public static final String URL_GUGE_ZHAOHUI_PASSWORD = WEB_URL + "/api/gugeupdatepassword";
    //助记词找回密码
    public static final String URL_ZHUJICI_ZHAOHUI_PASSWORD = WEB_URL + "/api/upzhujicipassword";
    //校验助记词
    public static final String URL_ZHUJICI_CHECK = WEB_URL + "/api/jyzhujici";
    //忘记资金密码
    public static final String URL_FORGOT_JY_PASSWORD = WEB_URL + "/api/wjjypassword";
    //助记词找回资金密码
    public static final String URL_ZHUJICI_FIND_JY = WEB_URL + "/api/upzijinpassword";

    /******************       模式接口相关       ************************/
    //模式主页信息
    public static final String URL_MODEL_INFO = WEB_URL + "/api/moshi";
    //兑换
    public static final String URL_MODEL_EXCHANGE = WEB_URL + "/api/exchange";
    //兑换-增值列表
    public static final String URL_EXCHANGE_LIST = WEB_URL + "/api/exchange/list";
    //增值
    public static final String URL_MODEL_ADD_VALUE = WEB_URL + "/api/appreciation";
    //参与
    public static final String URL_MODEL_JOIN = WEB_URL + "/api/participate";
    //参与列表
    public static final String URL_JOIN_LIST = WEB_URL + "/api/participate/list";
    //提取
    public static final String URL_MODEL_EXTRACT = WEB_URL + "/api/capital";
    //提取列表
    public static final String URL_MODEL_EXTRACT_LIST = WEB_URL + "/api/tiqu/list";
    //汇率
    public static final String URL_MODEL_RATE = WEB_URL + "/api/huilv";
    //模式主页收益列表
    public static final String URL_MODEL_EARNING_LIST = WEB_URL + "/api/shouyi/list";
    //奖金池、销毁池
    public static final String URL_MODEL_AWARD_DESTRUCTION_POOL = WEB_URL + "/api/pools";
    //参与页面的预约
    public static final String URL_MODEL_PAIDUI = WEB_URL + "/api/paidui";
    //参与页面的预约排队查询
    public static final String URL_MODEL_LOOK_PAIDUI = WEB_URL + "/api/chakanpaidui";







    /***      接口请求错误的错误信息      ***/
    public static String yichang = "未知异常，请联系管理员";
    public static String nocunzai = "用户不存在";
    public static String passworderror = "密码错误";
    public static String success = "请求成功";
    public static String noworkorder = "不是自己的工单,不能结束";
    public static String nooldpassword = "原密码错误";
    public static String nopassword = "密码错误";
    public static String nologinpassword = "登录密码错误";
    public static String nocodes = "邀请码填写错误,请确认邀请码";
    public static String noaddress = "该地址与自己不匹配,请确认";
    public static String exchange = "兑换成功";
    public static String zeroparticipate = "参与金额不能为0";
    public static String noavailabs = "可用资金不足";
    public static String nominables = "可挖取数量不足";
    public static String nocode = "该邀请码不存在";
    public static String zerocapital = "提取金额不能为0";
    public static String nocapital = "可提取资金不足";
    public static String getcodes = "请先获取验证码";
    public static String codesno = "验证码不正确,请重新输入";
    public static String cophone = "原手机号输入错误";
    public static String nozhujici= "助记词错误";
    public static String oldnewpassword= "新密码不能跟原密码一样";
    public static String gucodes= "谷歌验证码错误";
    public static String phonecodes= "手机验证码错误";
    public static String emailcodes= "邮箱验证码错误";
    public static String duishuliang= "兑换数量不能为0";
    public static String duixiaoyu= "兑换数量不能小于0";
    public static String yue= "您的余额不足";
    public static String tibierror= "提币失败";
    public static String yuyue= "您已存在预约,不能继续预约";
    public static String yuyuejine= "预约金额不能为0";
    public static String sendcodefail= "发送验证码失败";
    public static String phonealreadbind= "该手机号已经绑定";
    public static String mailalreadbind= "该邮箱已经绑定";
    public static String yuyuetime= "节点全网广播中，请稍候参与";
}
