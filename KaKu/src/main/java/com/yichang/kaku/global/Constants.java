package com.yichang.kaku.global;

public class Constants {

    public static final int TAB_POSITION_UNKONWN = -1;
    public static final int TAB_POSITION_HOME1 = 1;
    public static final int TAB_POSITION_HOME2 = 2;
    public static final int TAB_POSITION_HOME3 = 3;
    public static final int TAB_POSITION_HOME4 = 4;
    public static final int TAB_POSITION_HOME5 = 5;

    public final static int RESULT = 100;
    public static final String FRAGMENT_TAG_HOME1 = "home1";
    public static final String FRAGMENT_TAG_HOME2 = "home2";
    public static final String FRAGMENT_TAG_HOME3 = "home3";
    public static final String FRAGMENT_TAG_HOME4 = "home4";
    public static final String FRAGMENT_TAG_HOME5 = "home5";
    public static final String PHONE_KAKU = "4006867585";
    public static final String ICON_KAKU = "http://manage.360kaku.com/index.php?m=Img&a=imgAction&img=icon_share.png";

    public static final String GO_TO_TAB = "gototab";
    public static final String NAME_GUANGBO = "name_guangbo";
    public static final String NAME_JIEMU = "name_jiemu";

    /**
     * 当前tab序号
     */
    public static int current_tab = TAB_POSITION_UNKONWN;
    /**
     * 首页广告滚动时间间隔
     */
    public static final int AUTO_SCROLL_DURATION = 5 * 1000;

    public final static String RES = "0";
    public final static String RES_ONE = "1";
    public final static String RES_TWO = "2";
    public final static String RES_THREE = "3";
    public final static String RES_FOUR = "4";
    public final static String RES_SIX = "6";
    public final static String RES_EIGHT = "8";
    public final static String RES_NINE = "9";
    public final static String RES_TEN = "10";

    public final static int START = 0;

    public final static String ISLOGIN = "islogin";
    public final static String ISFIRST = "isfirst";
    public final static String PHONE = "phone";
    public final static String PASS = "pass";
    public final static String IDUSER = "iduser";
    public final static String CALL = "call";
    public final static String CALLNAME = "callname";
    public final static String CALL_SHENG = "callsheng";
    public final static String CALLTIME = "calltime";
    public final static String IDDRIVE = "iddrive";
    public final static String SID = "sid";
    public final static String IDADVERT = "idadvert";
    public final static String NAMEDRIVE = "namedrive";
    public final static String IDCAR = "id_car";
    public final static String NICKNAME = "nickname";
    public final static String SHOPCAR = "shopcar";
    public final static String TOTALPOINT = "totalpoint";
    public final static String TOTALNUM = "totalnum";
    public final static String ISGETREWARD = "isgetreward";
    public final static String IsShopToFirst = "isShopToFirst";
    public final static String ISSHOWDIALOG = "isShowDialog";
    public final static String IsModifyToMycenter = "isModifyToMycenter";
    public final static String IsAddrToShop = "IsAddrToShop";
    public final static String SCANFLAG = "scanFlag";
    public final static String ADDRESS = "address";
    public final static String HEAD = "head";
    public final static String SHOPNAME = "shopname";
    public final static String SHOPID = "shopid";
    public final static String FLAGFREEZE = "flagfreeze";

    //public final static String ADDRESSID = "addressid";
    public final static String ADDRESSOBJ = "addressobj";

    /**
     * 双击退出间隔,毫秒单位
     */
    public static final long DOUBLE_CLICK_TIME = 2000;
    /**
     * 最后点击时间
     */
    public static long lastClickMil = -1;

    public static final String MESSAGE_RECEIVED_ACTION = "com.kaku.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static final String KEY_MESSAGE_NUM = "num";

    public final static String ISSCAN = "isscan";
    public final static String PRODUCT_NAME = "product_name";
    public final static String PRODUCT_POINT = "product_point";
    public final static String PRIZE_INFO = "prizeinfo";
    public final static String TOTAL = "total";
    public final static String HOMETYPE = "hometype";
    public final static String LAT = "lat";
    public final static String LON = "lon";
    public final static String MSGKEY = "45hFR3fbi3fJhJ4fh0d2jfGHd0f2Hfs7";


	/*支付宝支付接口所需参数*/

    //商户PID
    public static final String PARTNER = "2088022080431983";
    //商户收款账号
    //使用签约商户账号：easyctech@easycgroup.com
    public static final String SELLER = "easyctech@easycgroup.com";
    //商户私钥，pkcs8格式，使用OPENSSL生成私钥后转码pkcs8
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOcCYrhzATPZMSXNr9ipWYYdV8RPKeBJtSrL9hQFONfJ4uT8TxvrYkh1JBifw3Z8pnV/89beGKs9epVVC0MhD4QjsHC6qpP0tlqN8zEQof9/aBEe05Ulqo+Pl5o2a+ckYsbmiKrwIvzjkpW8cJPGkyMMaAKNijCkzSzQkT8kDXAhAgMBAAECgYAOmLL+Lp4b1ZRqbBW5XfH/LGl4SPw/ZMjivGJ/H1lZITOJ/ntNK1FZfLVcLPQfz36BHCWfJQdwAoF1YBtHEZa2RwOWDUSZAjK4O304fUz43wmB0bIkHpi24JaDIkogt/sliSeQZZr9U8mWjtrFh8WwgHqDPq1Hk24vu6lOnIyL9QJBAPQifdvrPE5AAKyr8cZFlkqoT97P9zBkIYGSj3rAmxCVtGP9OvFupE3NM3ed+jswLY+PYah1VyWdbUowrETU/N8CQQDyPJakeNl8kIlYcNqdLB5HVW6VRHp9Ordq2bkEqdAesJpPyJsiqQl32uBLSyBnZKxIDOJZ88KEdLrl03tTvjL/AkBFjVoMAG2S4XsPtoDP+t1Bccgnc1o6CBzfnFCSKTNEO1JENk3HoLzgiXiDOJekqyofvzqUUrG8Pqh3PXyms4NDAkBnFxf5SSEt3QlGpUi0iRkdvKMCTZFfiESU6bHO5UIYPYp+l95GAvBvnKvF/2P7/KFBTJJsO8w1uMPtmXIr8o8bAkBvPbwRayvG2fHFiiaINGPMwmDJ09cffdwXZH0W/hyvCHmyu3tFRtm06mn3I3GbApzE3WrENDFeedywJXW8cg+l";
    //支付宝公钥 此项可以不填
    public static final String RSA_PUBLIC = "";

    public static final String APP_ID = "wx2b66d6fb76dc1aec";

    //  API密钥，在商户平台设置
    public static final String API_KEY = "pOYjSGe6H1ZgfKn6sLo7USC3YaWTU9xT";


}
