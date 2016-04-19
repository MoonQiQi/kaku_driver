package com.yichang.kaku.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.marswin89.marsdaemon.DaemonClient;
import com.marswin89.marsdaemon.DaemonConfigurations;
import com.tencent.bugly.crashreport.CrashReport;
import com.yichang.kaku.obj.Addr2Obj;
import com.yichang.kaku.obj.QuestionObj;
import com.yichang.kaku.tools.CaptchaDownTimer;
import com.yichang.kaku.tools.CrashHandler;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yolanda.nohttp.NoHttp;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class KaKuApplication extends Application {

    public static final String SHARED_NAME = "KaKu";
    private DaemonClient mDaemonClient;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mDaemonClient = new DaemonClient(createDaemonConfigurations());
        mDaemonClient.onAttachBaseContext(base);
    }


    //登录后返回的保留数据
    public static String id_user;
    public static String id_driver;
    public static String name;
    public static String id_car;
    public static String flag_freeze;
    //所有图片的前缀
    public static String qian_zhui = "http://manage.360kaku.com/index.php?m=Img&a=imgAction&img=";
    public static String qian_zhuikong = "";
    //判断是否是从收货地址跳回MyCenter
    public static boolean isShopToFirst;
    //到车服务费
    public static float carmoney = 0.00f;
    //保养花费
    public static float money;
    //保养，循环ID
    public static String id_string;
    //保养，循环数量
    public static String num_string;
    //保养，循环单价
    public static String price_string;
    //保养，地址
    public static String addr_string;
    public static String flag_shop = "0";
    public static String flag_show;
    //订单状态
    public static String state_order;
    public static String color_order = "";
    //订单号
    public static String id_orderA;
    public static String id_orderB;
    public static String id_orderC;
    public static String id_orderD;
    public static String id_orderE;
    public static String id_orderF;
    public static String id_orderG;
    public static String id_orderZ;
    public static String fanxiu_order;
    public static String id_drop;
    public static String id_bill_chetie;
    public static boolean flag_IsDetailToPingJia;

    public static String id_advert = "";
    public static String WhichFrag;
    //判断是否弹出升级对话框
    public static boolean isShowDialog;
    public static boolean scanFlag;

    /**
     * 注册验证码用计时器
     */
    public static CaptchaDownTimer timer;

    //区域相关
    public static String province_addr = "";
    public static String province_addrname = "";
    public static String city_addr = "";
    public static String city_addrname = "";
    public static String county_addrid;
    //地址
    public static String name_addr = "";
    public static String phone_addr = "";
    public static String county_addr = "";
    public static String dizhi_addr = "";
    public static String id_dizhi = "";
    public static String name_connect = "";
    public static String phone_connect = "";
    public static String flag_addr = "";
    public static String hongbao_title = "";
    public static String hongbao_content = "";
    public static String hongbao_url = "";
    /*标识是否为修改地址*/
    public static Boolean isEditAddr = false;
    public static int itemPosition = 0;

    //用户自动登录
    public static String sid;
    public static boolean isLogin;
    public static String address;
    public static String name_shop = "";
    public static String addr_shop = "";
    public static String image_shop = "";
    public static String pingjia_shop = "";
    public static String id_shop;
    public static String head;
    public static String lat = "";
    public static String lon = "";
    public static int position;

    public static String type_item;
    public static String no_item;
    public static String reason_upload;
    public static String flag_image = "wu";

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    //抢车贴订单
    public static String flag_one_qiang;
    public static String money_balance_qiang;
    public static String flag_pay_qiang;
    public static String price_advert_qiang;
    public static String breaks_money_qiang;
    public static String image_advert_qiang;
    public static String name_advert_qiang;
    public static String id_advert_qiang;
    public static Addr2Obj addr_qiang;

    //车贴详情弹不弹蒙板
    public static String flag_mengban = "N";
    public static String flag_recommended = "";
    public static String flag_code = "";
    public static String flag_nochetietv = "";
    public static String flag_position = "L";
    public static String flag_heart = "";
    public static String flag_jiashinum = "N";
    public static String phone_driver = "";

    //服务订单评价
    public static Bitmap picture;
    public static Bitmap picture_fanxiu;
    //车品订单 退货图片
    public static Bitmap picture_return;
    //    车品订单评价
    public static Bitmap pictureTruck;
    public static List<String> ssls_list;
    //    车品订单状态
    public static String truck_order_state;

    /*上传头像*/
    public static Bitmap ImageHead;
    /*上传驾照*/
    public static Bitmap cartInfoIcon;

    /*上传车品订单评论照片*/
    public static Bitmap OrderCommentIcon;
    public static Bitmap ImageZhong;
    public static Bitmap ImageJiaShiZheng;
    public static Bitmap ImageBen;
    public static Bitmap ImageChe;
    public static Bitmap ImageXingShiZheng;
    /*微信支付结果*/
    public static String WXPayResult = "";
    public static String APP_ID;

    /*支付回调地址*/
    public static String notify_url = "http://www.99kaku.com/public/alipay/notify_url.php";
    public static String id_bill = "";
    public static String id_order = "";
    //    订单类型 服务：SERVICE，车品：TRUCK
    public static String payType = "";

    //    订单支付金额
    public static String realPayment = "";

    //    评价订单内容
    public static String pingjiaOrderContent = "";
    public static List<Bitmap> mBimpList = new ArrayList<>();
    public static String id_goods_shopcars;
    public static String discoveryId;
    //    我的爱车 添写爱车信息，用于存放编辑类型，在编辑页面中判断
    public static String editCarInfoType;
    //    我的爱车 编辑页面标题
    public static String editCarInfoPageTitle;
    //    我的爱车 编辑完成的信息
    public static String editCarInfo;
    //发现页面是否展示删除按钮
    public static boolean isShowRemoveImg_discovery = false;
    //页面滑动状态标识
    public static int scroll_flag = -1;
    public static String city;
    private LocationClient mLocationClient;
    private LocationListner locationListner;
    //问题详情列表 Question实体
    public static QuestionObj question;
    /*private static Trace mLocalTrace;
    private LBSTraceClient localLBSTraceClient;
    private static LBSTraceClient mLocalLBSTraceClient1;*/
    public static String chetie_order_to = "";

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        OkHttpUtil.init(new Handler());
        NoHttp.init(this);
        SDKInitializer.initialize(getApplicationContext());
        JPushInterface.init(this);
        CrashHandler.getInstance().init(this);//捕获全局异常
        sp = getSharedPreferences(SHARED_NAME, Context.MODE_WORLD_WRITEABLE);
        editor = sp.edit();

        initLocation();
        //LeakCanary.install(this);
        Fresco.initialize(this);
        CrashReport.initCrashReport(getApplicationContext(), "900022764", false);

        Utils.setmContext(getApplicationContext());

    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        locationListner = new LocationListner();

        initLocationInfo(mLocationClient);
    }

    /**
     * 开始定位
     */
    public void startLocate() {
        mLocationClient.registerLocationListener(locationListner);
        mLocationClient.start();
    }

    /**
     * 结束定位
     */
    public void stopLocate() {
        mLocationClient.start();
        mLocationClient.unRegisterLocationListener(locationListner);
    }

    /*public static void initTraceService(Context paramContext, String paramString) {
        LBSTraceClient localLBSTraceClient = new LBSTraceClient(paramContext.getApplicationContext());
        Trace localTrace = new Trace(paramContext.getApplicationContext(), 104890L, paramString, 2);
        OnStartTraceListener local1 = new OnStartTraceListener() {
            public void onTraceCallback(int paramInt, String paramString) {
                Log.e("kaku", "arg0==" + paramInt + "-----" + "arg1==" + paramString);
            }

            public void onTracePushCallback(byte paramByte, String paramString) {
            }
        };
        localLBSTraceClient.setInterval(21600, 21600);
        localLBSTraceClient.startTrace(localTrace, local1);
    }

    public static void startTrace(Context paramContext, String phone) {

        if (mLocalLBSTraceClient1 == null) {

            mLocalLBSTraceClient1 = new LBSTraceClient(paramContext.getApplicationContext());
        }
        if (mLocalTrace==null){

            mLocalTrace = new Trace(paramContext.getApplicationContext(), 104890L, phone, 2);
        }
        OnStartTraceListener local = new OnStartTraceListener() {
            public void onTraceCallback(int paramInt, String paramString) {
                LogUtil.E("kaku arg0==" + paramInt + "-----" + "arg1==" + paramString);
                //上传成功后及时关闭
                if (paramInt == 0) {
                    stopTrace();
                    mLocalLBSTraceClient1.onDestroy();
                    mLocalTrace=null;

                }
            }

            public void onTracePushCallback(byte paramByte, String paramString) {
                LogUtil.E("kaku arg0==" + paramByte + "-----" + "arg1==" + paramString);
            }
        };
        mLocalLBSTraceClient1.setInterval(2, 4);
        mLocalLBSTraceClient1.startTrace(mLocalTrace, local);


    }

    public static void stopTrace() {
        if (mLocalLBSTraceClient1 != null) {
            mLocalLBSTraceClient1.stopTrace(mLocalTrace, new OnStopTraceListener() {
                @Override
                public void onStopTraceSuccess() {
                    LogUtil.E("结束上传");
                }

                @Override
                public void onStopTraceFailed(int i, String s) {

                }
            });
        }
    }*/

    private void initLocationInfo(LocationClient mLocationClient) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setPriority(LocationClientOption.GpsFirst);//设置定位优先级
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setScanSpan(50000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClient.setLocOption(option);
    }

    private DaemonConfigurations createDaemonConfigurations(){
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration("com.yichang.kaku:process1", Service1.class.getCanonicalName(), Receiver1.class.getCanonicalName());
        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration("com.yichang.kaku:process2", Service2.class.getCanonicalName(), Receiver2.class.getCanonicalName());
        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        //return new DaemonConfigurations(configuration1, configuration2);//listener can be null
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }

    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDaed() {
        }
    }

}
