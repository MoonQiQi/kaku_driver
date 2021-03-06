package com.yichang.kaku.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.home.ad.CheTieOrderListActivity;
import com.yichang.kaku.home.baoyang.BaoYangOrderListActivity;
import com.yichang.kaku.home.choujiang.MyPrizeActivity;
import com.yichang.kaku.home.faxian.DiscoveryDetailActivity;
import com.yichang.kaku.home.qiandao.DailySignActivity;
import com.yichang.kaku.home.shop.PinPaiFuWuZhanActivity;
import com.yichang.kaku.member.WoDeYouHuiQuanActivity;
import com.yichang.kaku.member.YouHuiQuanActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.member.settings.CommentListActivity;
import com.yichang.kaku.member.settings.MemberSettingsCommentActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.request.DiscoveryListReq;
import com.yichang.kaku.response.DiscoveryListResp;
import com.yichang.kaku.response.JPushResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.PushUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private String type;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JPushResp jPushResp = new Gson().fromJson(message, JPushResp.class);

            if (!Utils.getIdDriver().equals(jPushResp.id_driver)) {
                abortBroadcast();//如果不是当前的用户就不推送信息
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            if (!Utils.isLogin()) {
                Intent i = new Intent(context, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            } else {
                String typeJson = bundle.getString(JPushInterface.EXTRA_EXTRA);
                try {
                    JSONObject object = new JSONObject(typeJson);
                    type = object.getString("type");
                    LogUtil.E("------++++++" + type);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if ("1".equals(type)) {
                    GoToQuanZiDetail(context);
                } else if ("2".equals(type)) {
                    GoToShopList(context);
                } else if ("3".equals(type)) {
                    GoToTuCao(context);
                } else if ("11".equals(type)) {
                    GoToDaiShouHuo(context);
                } else if ("12".equals(type)) {
                    GoToTuiHuanHuo(context);
                } else if ("13".equals(type)) {
                    GoToMyPrize(context);
                } else if ("14".equals(type)) {
                    GoToMyYouHuiQuan(context);
                } else if ("15".equals(type)) {
                    //好友列表
                } else if ("16".equals(type)) {
                    GoToTuCaoLieBiao(context);
                } else if ("17".equals(type)) {
                    GoToYue(context);
                } else if ("18".equals(type)) {
                    GetAdd(context);
                } else if ("19".equals(type)) {
                    GoCheTieOrderList(context);
                } else if ("20".equals(type)) {
                    GoToYouHuiQuan(context);
                } else if ("21".equals(type)) {
                    GoToQianDao(context);
                } else if ("31".equals(type)) {
                    GoToBaoYangOrderList(context);
                } else {
                    GoHome(context);
                }

            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.d(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        int count = KaKuApplication.sp.getInt(Constants.KEY_MESSAGE_NUM, 0);
        count++;
        Editor edit = KaKuApplication.sp.edit();
        edit.putInt(Constants.KEY_MESSAGE_NUM, count);
        edit.commit();
        Intent msgIntent = new Intent(Constants.MESSAGE_RECEIVED_ACTION);
        msgIntent.putExtra(Constants.KEY_MESSAGE, message);
        if (!PushUtil.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    msgIntent.putExtra(Constants.KEY_EXTRAS, extras);
                }
            } catch (JSONException e) {

            }

        }
        context.sendBroadcast(msgIntent);
    }

    public void GoToQuanZiDetail(final Context context) {

        DiscoveryListReq req = new DiscoveryListReq();
        req.code = "70010";
        req.start = "0";
        req.len = "5";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.getDiscoveryList(req, new KakuResponseListener<DiscoveryListResp>(context, DiscoveryListResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(context, DiscoveryDetailActivity.class);
                        KaKuApplication.id_news = t.newss.get(0).getId_news();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void GoToShopList(Context context) {
        Intent intent = new Intent(context, PinPaiFuWuZhanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void GoToTuCao(Context context) {
        Intent intent = new Intent(context, MemberSettingsCommentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToDaiShouHuo(Context context) {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        KaKuApplication.truck_order_state = "B";
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToTuiHuanHuo(Context context) {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        KaKuApplication.truck_order_state = "Z";
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToMyPrize(Context context) {
        Intent intent = new Intent(context, MyPrizeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToMyYouHuiQuan(Context context) {
        Intent intent = new Intent(context, YouHuiQuanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToTuCaoLieBiao(Context context) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToYue(Context context) {
        Intent intent = new Intent(context, YueActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToYouHuiQuan(Context context) {
        Intent intent = new Intent(context, WoDeYouHuiQuanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoCheTieOrderList(Context context) {
        Intent intent = new Intent(context, CheTieOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToQianDao(Context context) {
        Intent intent = new Intent(context, DailySignActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private void GoToBaoYangOrderList(Context context) {
        Intent intent = new Intent(context, BaoYangOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void GetAdd(final Context context) {
        Utils.GetAdType();
    }

}
