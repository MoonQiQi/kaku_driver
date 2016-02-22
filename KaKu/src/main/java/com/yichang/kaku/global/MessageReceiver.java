package com.yichang.kaku.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.home.Ad.Add_EActivity;
import com.yichang.kaku.home.Ad.Add_FActivity;
import com.yichang.kaku.home.Ad.Add_IActivity;
import com.yichang.kaku.home.Ad.Add_NActivity;
import com.yichang.kaku.home.Ad.Add_PActivity;
import com.yichang.kaku.home.Ad.Add_YActivity;
import com.yichang.kaku.home.MyPrizeActivity;
import com.yichang.kaku.home.PinPaiFuWuZhanActivity;
import com.yichang.kaku.home.discovery.DiscoveryDetailActivity;
import com.yichang.kaku.member.MemberCouponsActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.member.settings.CommentListActivity;
import com.yichang.kaku.member.settings.MemberSettingsCommentActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;
import com.yichang.kaku.obj.RollsAddObj;
import com.yichang.kaku.request.DiscoveryListReq;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.response.DiscoveryListResp;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.JPushResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.PushUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MessageReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private String type;

	private String name_advert;
	private String now_earnings;
	private String total_earning;
	private String day_remaining;
	private String day_continue;
	private String image_size;
	private String image_advert;
	private String free_remind;
	private String num_driver;
	private String time_end;
	private String time_begin;
	private String day_earnings;
	private String approve_opinions;
	private List<RollsAddObj> rollsadd_list;

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
				Intent i = new Intent(context,LoginActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
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
					GetAdd(context);
				} else {
					GoHome(context);
				}

			}

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.d(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
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
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
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

	public void GoToQuanZiDetail(final Context context){

		DiscoveryListReq req = new DiscoveryListReq();
		req.code = "70010";
		req.start = "0";
		req.len = "5";
		req.id_driver = Utils.getIdDriver();
		KaKuApiProvider.getDiscoveryList(req, new BaseCallback<DiscoveryListResp>(DiscoveryListResp.class) {

			@Override
			public void onSuccessful(int statusCode, Header[] headers, DiscoveryListResp t) {
				// TODO Auto-generated method stub
				if (t != null) {
					if (Constants.RES.equals(t.res)) {
						Intent intent = new Intent(context, DiscoveryDetailActivity.class);
						intent.putExtra("id_news",  t.newss.get(0).getId_news());
						intent.putExtra("is_collection",  t.newss.get(0).getIs_collection());
						intent.putExtra("num_collection",  t.newss.get(0).getNum_collection());
						intent.putExtra("num_comments",  t.newss.get(0).getNum_comments());
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(intent);
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg,
								  Throwable error) {
				// TODO Auto-generated method stub
			}
		});
	}
	public void GoToShopList(Context context){
		Intent intent = new Intent(context, PinPaiFuWuZhanActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	public void GoToTuCao(Context context){
		Intent intent = new Intent(context, MemberSettingsCommentActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoHome(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoToDaiShouHuo(Context context) {
		Intent intent = new Intent(context, TruckOrderListActivity.class);
		KaKuApplication.truck_order_state = "B";
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoToTuiHuanHuo(Context context) {
		Intent intent = new Intent(context, TruckOrderListActivity.class);
		KaKuApplication.truck_order_state = "Z";
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoToMyPrize(Context context) {
		Intent intent = new Intent(context, MyPrizeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoToMyYouHuiQuan(Context context) {
		Intent intent = new Intent(context, MemberCouponsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoToTuCaoLieBiao(Context context) {
		Intent intent = new Intent(context, CommentListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
	private void GoToYue(Context context) {
		Intent intent = new Intent(context, YueActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}

	public void GetAdd(final Context context){
		GetAddReq req = new GetAddReq();
		req.code = "60011";
		req.id_driver = Utils.getIdDriver();
		req.id_advert = "1";
		KaKuApiProvider.GetAdd(req, new BaseCallback<GetAddResp>(GetAddResp.class) {

			@Override
			public void onSuccessful(int statusCode, Header[] headers, GetAddResp t) {
				if (t != null) {
					LogUtil.E("getadd res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						name_advert = t.advert.getName_advert();
						day_earnings = t.advert.getDay_earnings();
						time_begin = t.advert.getTime_begin();
						time_end = t.advert.getTime_end();
						num_driver = t.advert.getNum_driver();
						free_remind = t.advert.getFree_remind();
						image_advert = t.advert.getImage_advert();
						image_size = t.advert.getImage_size();
						day_continue = t.advert.getDay_continue();
						day_remaining = t.advert.getDay_remaining();
						total_earning = t.advert.getTotal_earnings();
						approve_opinions = t.advert.getApprove_opinions();
						now_earnings = t.advert.getNow_earnings();
						rollsadd_list = t.rolls;
						GoToAdd(t.advert.getFlag_type(),context);
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
			}
		});
	}

	public void GoToAdd(String flag_type,Context context){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		LogUtil.E("flag:"+flag_type);
		if ("N".equals(flag_type)){
			intent.setClass(context,Add_NActivity.class);
		} else if ("Y".equals(flag_type)){
			intent.setClass(context,Add_YActivity.class);
		} else if ("E".equals(flag_type)){
			intent.setClass(context,Add_EActivity.class);
		} else if ("I".equals(flag_type)){
			intent.setClass(context,Add_IActivity.class);
		} else if ("F".equals(flag_type)){
			intent.setClass(context,Add_FActivity.class);
		} else if ("P".equals(flag_type)){
			intent.setClass(context,Add_PActivity.class);
		}
		bundle.putString("name_advert",name_advert);
		bundle.putString("day_earnings",day_earnings);
		bundle.putString("time_begin",time_begin);
		bundle.putString("time_end",time_end);
		bundle.putString("free_remind",free_remind);
		bundle.putString("num_driver",num_driver);
		bundle.putString("image_advert",image_advert);
		bundle.putString("image_size",image_size);
		bundle.putString("day_continue",day_continue);
		bundle.putString("day_remaining",day_remaining);
		bundle.putString("total_earning",total_earning);
		bundle.putString("now_earnings",now_earnings);
		bundle.putString("approve_opinions",approve_opinions);
		bundle.putString("flag_type",flag_type);
		bundle.putSerializable("rollsadd_list", (Serializable) rollsadd_list);
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		context.startActivity(intent);
	}
}
