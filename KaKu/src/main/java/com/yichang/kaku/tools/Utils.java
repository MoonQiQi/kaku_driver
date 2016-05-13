package com.yichang.kaku.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.ad.Add_FActivity;
import com.yichang.kaku.home.ad.Add_IActivity;
import com.yichang.kaku.home.ad.Add_NActivity;
import com.yichang.kaku.home.ad.Add_PActivity;
import com.yichang.kaku.home.ad.Add_YActivity;
import com.yichang.kaku.home.ad.CheTieListActivity;
import com.yichang.kaku.home.ad.ZongJieNewJieShuActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.request.AdTypeReq;
import com.yichang.kaku.response.AdTypeResp;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

    private static Context mContext;

    public static void setmContext(Context context) {
        mContext = context;
    }

    private static long lastClickTime;

    private static DecimalFormat dfs = null;

    public static boolean isLogin() {
        if (KaKuApplication.isLogin) {
            return KaKuApplication.isLogin;
        }
        return KaKuApplication.sp.getBoolean(Constants.ISLOGIN, false);
    }

    public static String getIdDriver() {
        return KaKuApplication.sp.getString(Constants.IDDRIVE, "");
    }

    public static String getPhone() {
        return KaKuApplication.sp.getString(Constants.PHONE, "");
    }


    public static String getName() {
        return KaKuApplication.sp.getString(Constants.NAMEDRIVE, "");
    }

    public static String getGuangboName() {
        return KaKuApplication.sp.getString(Constants.NAME_GUANGBO, "高速广播");
    }

    public static String getJiemuName() {
        return KaKuApplication.sp.getString(Constants.NAME_JIEMU, "");
    }

    public static String getCall() {
        return KaKuApplication.sp.getString(Constants.CALL, "");
    }

    public static int getCallSheng() {
        return KaKuApplication.sp.getInt(Constants.CALL_SHENG, 0);
    }

    public static String getCallName()

    {
        return KaKuApplication.sp.getString(Constants.CALLNAME, "");
    }

    public static String getCityName() {
        return KaKuApplication.sp.getString("city_name", "");
    }

    public static float getCallTime() {
        return KaKuApplication.sp.getFloat(Constants.CALLTIME, 0.00f);
    }

    public static String getCarNumber() {
        return KaKuApplication.sp.getString("carnumber", "");
    }

    public static String getCarCode() {
        return KaKuApplication.sp.getString("carcode", "");
    }

    public static String getCarDriveNumber() {
        return KaKuApplication.sp.getString("cardrivenumber", "");
    }

    public static String getCityCode() {
        return KaKuApplication.sp.getString("city_code", "");
    }

    public static String getCityJian() {
        return KaKuApplication.sp.getString("city_jian", "");
    }

    public static String getHandCode() {
        return KaKuApplication.sp.getString("FLAG_CODE", "");
    }


    public static String getSid() {
        return KaKuApplication.sp.getString(Constants.SID, "");
    }

    public static String getLat() {
        if ("4.9E-324".equals(KaKuApplication.sp.getString(Constants.LAT, ""))) {
            return "0.00";
        } else {
            return KaKuApplication.sp.getString(Constants.LAT, "");
        }
    }

    public static String getLon() {
        if ("4.9E-324".equals(KaKuApplication.sp.getString(Constants.LON, ""))) {
            return "0.00";
        } else {
            return KaKuApplication.sp.getString(Constants.LON, "");
        }
    }

    public static boolean isFirst() {
        return KaKuApplication.sp.getBoolean(Constants.ISFIRST, true);
    }

    public static boolean isShopToFirst() {
        if (KaKuApplication.isShopToFirst) {
            return KaKuApplication.isShopToFirst;
        }
        return KaKuApplication.sp.getBoolean(Constants.IsShopToFirst, false);
    }

    public static boolean isShowDialog() {
        if (KaKuApplication.isShowDialog) {
            return KaKuApplication.isShowDialog;
        }
        return KaKuApplication.sp.getBoolean(Constants.ISSHOWDIALOG, false);
    }

    public static boolean isScanFlag() {
        if (KaKuApplication.scanFlag) {
            return KaKuApplication.scanFlag;
        }
        return KaKuApplication.sp.getBoolean(Constants.SCANFLAG, false);
    }


    //生成二维码
    public static Bitmap createQRCode(String str) {
        try {
            return EncodingHandler.createQRCode(str, 300);
        } catch (WriterException e) {
            LogUtil.E(e.toString());
        }
        return null;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][345678]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    /**
     * 检测是否有网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetworkConnection(Context context) {
        boolean flag = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi != null && wifi.isConnectedOrConnecting()) {
            flag = true;
        } else if (mobile != null && mobile.isConnectedOrConnecting()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 检测设备识别信息
     *
     * @param context
     * @return
     */

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void Exit(Context context) {
        KaKuApplication.isLogin = false;
        KaKuApplication.editor.putString(Constants.SID, "");
        KaKuApplication.editor.putString(Constants.IDDRIVE, "");
        KaKuApplication.editor.putString(Constants.PHONE, "");
        KaKuApplication.editor.putString(Constants.NAMEDRIVE, "");
        KaKuApplication.editor.putBoolean(Constants.ISLOGIN, false);
        KaKuApplication.editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void Exit() {
        KaKuApplication.isLogin = false;
        KaKuApplication.editor.putString(Constants.SID, "");
        KaKuApplication.editor.putString(Constants.IDDRIVE, "");
        KaKuApplication.editor.putString(Constants.PHONE, "");
        KaKuApplication.editor.putString(Constants.NAMEDRIVE, "");
        KaKuApplication.editor.putBoolean(Constants.ISLOGIN, false);
        KaKuApplication.editor.commit();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("isOtherLogin", true);
        mContext.startActivity(intent);

    }


    public static void NoNet(Context context) {
        if (!Utils.checkNetworkConnection(context)) {
            Toast.makeText(context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public static boolean Many() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void Call(final Context context, final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("确认拨打:" + phone + "？");
        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });

        builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }

    public static DecimalFormat format(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }

    public static String numdouble(String s) {
        float ss = Float.parseFloat(s);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(ss);//format 返回的是字符串

    }

    public static SpannableStringBuilder ChangeTextColor(String s, int start, int end, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(s);
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    public static String TimeChange(String time_now) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            Date d1 = df.parse(time_now);
            Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            if (days == 0) {
                if (hours == 0) {
                    if (minutes == 0 || minutes == 1) {
                        time = "刚刚";
                    } else if (minutes > 1 && minutes <= 60) {
                        time = minutes + "分钟前";
                    }
                } else {
                    time = hours + "小时前";
                }

            } else if (days > 0 && days <= 3) {
                time = days + "天之前";
            } else {
                time = time_now.substring(5, 10);
            }

        } catch (Exception e) {
        }
        return time;
    }

    public static void GetAdType(final BaseActivity activity) {
        activity.showProgressDialog();
        AdTypeReq req = new AdTypeReq();
        req.code = "600110";
        KaKuApiProvider.GetAdType(req, new KakuResponseListener<AdTypeResp>(activity, AdTypeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadtype res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.id_advert;
                        String flag_type = t.flag_type;
                        Intent intent = new Intent();
                        if ("N".equals(flag_type)) {
                            intent.setClass(activity, Add_NActivity.class);
                        } else if ("Y".equals(flag_type)) {
                            intent.setClass(activity, Add_YActivity.class);
                        } else if ("E".equals(flag_type)) {
                            KaKuApplication.id_advert_daili = t.id_advert;
                            intent.setClass(activity, ZongJieNewJieShuActivity.class);
                        } else if ("I".equals(flag_type)) {
                            intent.setClass(activity, Add_IActivity.class);
                        } else if ("F".equals(flag_type)) {
                            intent.setClass(activity, Add_FActivity.class);
                        } else if ("P".equals(flag_type)) {
                            intent.setClass(activity, Add_PActivity.class);
                        } else if ("A".equals(flag_type)) {
                            intent.setClass(activity, CheTieListActivity.class);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.stopProgressDialog();
                        activity.startActivity(intent);
                        activity.finish();
                    } else {
                        LogUtil.showShortToast(activity, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    public static void GetAdType() {
        AdTypeReq req = new AdTypeReq();
        req.code = "600110";
        KaKuApiProvider.GetAdType(req, new KakuResponseListener<AdTypeResp>(mContext, AdTypeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadtype res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.id_advert;
                        KaKuApplication.id_advert_daili = t.id_advert;
                        String flag_type = t.flag_type;
                        Intent intent = new Intent();
                        if ("N".equals(flag_type)) {
                            intent.setClass(mContext, Add_NActivity.class);
                        } else if ("Y".equals(flag_type)) {
                            intent.setClass(mContext, Add_YActivity.class);
                        } else if ("E".equals(flag_type)) {
                            KaKuApplication.id_advert_daili = t.id_advert;
                            intent.setClass(mContext, ZongJieNewJieShuActivity.class);
                        } else if ("I".equals(flag_type)) {
                            intent.setClass(mContext, Add_IActivity.class);
                        } else if ("F".equals(flag_type)) {
                            intent.setClass(mContext, Add_FActivity.class);
                        } else if ("P".equals(flag_type)) {
                            intent.setClass(mContext, Add_PActivity.class);
                        } else if ("A".equals(flag_type)) {
                            intent.setClass(mContext, CheTieListActivity.class);
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } else {
                        LogUtil.showShortToast(mContext, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public static String getHmacMd5Str(String price_balance) {
        String sEncodedString = null;
        String hmac_key = KaKuApplication.hmac_key;
        String s = Utils.getIdDriver() + price_balance + Utils.getSid();
        LogUtil.E("SSSSS:" + s);
        try {
            SecretKeySpec key = new SecretKeySpec((hmac_key).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return sEncodedString;
    }

    public static String TwoMD5Big(String name, String value) {
        StringBuilder sb = new StringBuilder();
        //拼接签名字符串
        sb.append("id_driver=");
        sb.append(Utils.getIdDriver());

        sb.append("&" + name + "=");
        sb.append(value);

        sb.append("&key=");
        sb.append(Constants.MSGKEY);

        String appSign1 = MD5.getMessageDigest(sb.toString().getBytes());
        String appSign = MD5.getMessageDigest(appSign1.getBytes()).toUpperCase();
        return appSign;
    }

    public static void GotoMain(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        context.startActivity(intent);
        context.finish();
    }

}
