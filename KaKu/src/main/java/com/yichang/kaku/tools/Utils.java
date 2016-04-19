package com.yichang.kaku.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.login.LoginActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Utils {

    private static Context mContext;

    public static void setmContext(Context context){
        mContext=context;
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

    public static int getTotal() {
        return KaKuApplication.sp.getInt(Constants.TOTAL, 0);
    }

    public static String getPhone() {
        return KaKuApplication.sp.getString(Constants.PHONE, "");
    }

    public static String getPass() {
        return KaKuApplication.sp.getString(Constants.PASS, "");
    }

    public static String getName() {
        return KaKuApplication.sp.getString(Constants.NAMEDRIVE, "");
    }

    public static String getIdCar() {
        return KaKuApplication.sp.getString(Constants.IDCAR, "");
    }

    public static String getNickname() {
        return KaKuApplication.sp.getString(Constants.NICKNAME, null);
    }

    public static String getShopid() {
        return KaKuApplication.sp.getString(Constants.SHOPID, null);
    }

    public static String getShopname() {
        return KaKuApplication.sp.getString(Constants.SHOPNAME, null);
    }

    public static String getSid() {
        return KaKuApplication.sp.getString(Constants.SID, "");
    }

    public static String getHomeType() {
        return KaKuApplication.sp.getString(Constants.HOMETYPE, null);
    }

    public static String getLat() {
        return KaKuApplication.sp.getString(Constants.LAT, "");
    }

    public static String getLon() {
        return KaKuApplication.sp.getString(Constants.LON, "");
    }

    public static boolean isGetReward() {
        return KaKuApplication.sp.getBoolean(Constants.ISGETREWARD, false);
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
        intent.putExtra("isOtherLogin",true);
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
        params.height += 5;

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

}
