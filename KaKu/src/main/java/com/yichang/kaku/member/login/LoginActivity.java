package com.yichang.kaku.member.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.payhelper.wxpay.net.sourceforge.simcpux.MD5;
import com.yichang.kaku.request.LoginReq;
import com.yichang.kaku.request.MobileReq;
import com.yichang.kaku.response.LoginResp;
import com.yichang.kaku.response.MobileResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.login_name)
    private EditText login_name;

    @ViewInject(R.id.login_psw)
    private EditText login_psw;

    @ViewInject(R.id.bt_check)
    private Button bt_check;

    @ViewInject(R.id.tv_protocal)
    private TextView tv_protocal;

    @ViewInject(R.id.bt_login)
    private Button bt_login;

    private boolean isWaiting = false;
    private TextView login_back;

    private int tempTime = 60;
    private String MobileInfo = "";

    private boolean isOtherLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ViewUtils.inject(this);
        bt_login.setEnabled(true);
        bt_login.setOnClickListener(this);
        bt_check.setOnClickListener(this);
        login_back = (TextView) findViewById(R.id.login_back);
        login_back.setOnClickListener(this);

        initReceiver();
        getInfo();
        initProtocal();

        isOtherLogin=getIntent().getBooleanExtra("isOtherLogin",false);
        if(isOtherLogin){
           // Toast.makeText(this, "您的账号已在其他设备登陆 chaih", Toast.LENGTH_LONG);
            LogUtil.showShortToast(this,"当前账号已在其他设备上登录，如非本人操作，请重新登录或联系客服");
        }
    }

    private void initProtocal() {

        String protocal = tv_protocal.getText().toString();
        SpannableString ss_protocal = new SpannableString(protocal);

        ss_protocal.setSpan(new ForegroundColorSpan(Color.parseColor("#F6F6F6")), 13, 17,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ss_protocal.setSpan(new NoUnderLineClickableSpan() {

            @Override
            public void onClick(View widget) {
                //todo 跳转到协议界面
                startActivity(new Intent(LoginActivity.this, ZhuCeXieYiActivity.class));
            }
        }, 13, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        tv_protocal.setText(ss_protocal);
        tv_protocal.setMovementMethod(LinkMovementMethod.getInstance());
    }

    abstract class NoUnderLineClickableSpan extends ClickableSpan {

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.setColor(tp.linkColor);
            tp.setUnderlineText(false);

        }

        @Override
        public abstract void onClick(View widget);

    }

    private void initReceiver() {
        smsReceiver = new SmsReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, filter);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    if (tempTime == 0) {
                        // 状态重置
                        resetCheckButton();
                        return;
                    }

                    bt_check.setText(tempTime + "秒后重新获取");
                    tempTime--;

                    handler.sendEmptyMessageDelayed(0, 1000);
                    break;

                default:

                    break;
            }
        }

    };

    private SmsReceiver smsReceiver;

    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler = null;
        unregisterReceiver(smsReceiver);
    }

    class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            showShortToast("接收到短信");

            Object[] messages = (Object[]) intent.getSerializableExtra("pdus");

            byte[][] pduObjs = new byte[messages.length][];

            for (int i = 0; i < messages.length; i++) {

                pduObjs[i] = (byte[]) messages[i];

            }

            byte[][] pdus = new byte[pduObjs.length][];

            int pduCount = pdus.length;

            SmsMessage[] msgs = new SmsMessage[pduCount];

            for (int i = 0; i < pduCount; i++) {

                pdus[i] = pduObjs[i];
                msgs[i] = SmsMessage.createFromPdu(pdus[i]);

                if (SmsMessage.createFromPdu(pdus[i]).getDisplayOriginatingAddress().endsWith("106904700027")) {
                    String messageBody = SmsMessage.createFromPdu(pdus[i]).getDisplayMessageBody();
                    Log.d("xiaosu", messageBody);
                    login_psw.setText(messageBody.subSequence(13, 17));
                }

            }
        }
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        String checkCode = login_psw.getText().toString().trim();
        String phone = login_name.getText().toString().trim();
        switch (v.getId()) {
            case R.id.bt_login:

                MobclickAgent.onEvent(context, "Login");
                bt_login.setEnabled(false);
                // 登录
                login();

                break;
            case R.id.bt_check:

                if (11 != phone.length()) {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isWaiting) {

                } else {
                    // 获取验证码
                    getCaptcha();

                    bt_check.setBackgroundResource(R.drawable.login_bt_waiting);
                    bt_check.setTextColor(Color.parseColor("#999999"));
                    isWaiting = true;
                    bt_check.setEnabled(false);

                    handler.sendEmptyMessage(0);
                }

                break;
            case R.id.login_back:
                GoHome(null);
        }
    }

    private void getCaptcha() {

        Utils.NoNet(this);
        showProgressDialog();
        String phone = login_name.getText().toString().trim();
        String appSign = genAppSign(phone, JPushInterface.getRegistrationID(context));
        MobileReq req = new MobileReq();
        req.code = "1004";
        req.phone_driver = phone;
        req.id_equipment = JPushInterface.getRegistrationID(context);
        req.sign = appSign;

        KaKuApiProvider.getCaptcha(req, new BaseCallback<MobileResp>(MobileResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers, MobileResp t) {
                if (t != null) {
                    LogUtil.E("getCaptcha res: " + t.res);

                    if (Constants.RES.equals(t.res)) {

                    } else if (t.res.endsWith("1")) {
                        handler.removeMessages(0);
                        resetCheckButton();
                    }

                    Toast.makeText(LoginActivity.this, t.msg, Toast.LENGTH_SHORT).show();
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private void login() {
        Utils.NoNet(this);
        showProgressDialog();

        LoginReq req = new LoginReq();
        req.code = "1002";
        req.driver_equipment = "A";
        req.version_equipment = MobileInfo;
        req.var_lat = Utils.getLat();
        req.var_lon = Utils.getLon();
        req.phone_driver = login_name.getText().toString().trim();
        req.id_equipment = JPushInterface.getRegistrationID(this);
        req.vcode = login_psw.getText().toString().trim();

        KaKuApiProvider.login(req, new BaseCallback<LoginResp>(LoginResp.class) {

            @Override
            public void onSuccessful(int statusCode, Header[] headers, LoginResp t) {
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        SharedPreferences.Editor editor = KaKuApplication.editor;
                        editor.putBoolean(Constants.ISLOGIN, true);
                        editor.putString(Constants.PHONE, login_name.getText().toString().trim());
                        editor.putString(Constants.IDDRIVE, t.driver.getId_driver());
                        editor.putString(Constants.IDCAR, t.id_car);
                        editor.putString(Constants.NAMEDRIVE, t.driver.getName_driver());
                        editor.putString(Constants.SID, t.driver.getSid());
                        editor.commit();
                        GoHome(t);
                    } else {
                        bt_login.setEnabled(true);
                    }

                    Toast.makeText(LoginActivity.this, t.msg, Toast.LENGTH_SHORT).show();
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private void resetCheckButton() {
        tempTime = 60;
        bt_check.setBackgroundResource(R.drawable.login_bt);
        bt_check.setTextColor(Color.parseColor("#FFFFFF"));
        bt_check.setEnabled(true);
        bt_check.setText("获取验证码");
        isWaiting = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            GoHome(null);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void GoHome(LoginResp t) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    private void getInfo() {
        TelephonyManager mTm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String model = Build.MODEL;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String fingerprint = Build.FINGERPRINT;
        String id = Build.ID;
        String product = Build.PRODUCT;
        String manufacturer = Build.MANUFACTURER;
        int versionsdk = Build.VERSION.SDK_INT;
        MobileInfo = "brand:" + brand + ",device:" + device + ",manufacturer:" + manufacturer + ",versionsdk:" + versionsdk;
    }

    private String genAppSign(String phone, String id_equipment) {
        StringBuilder sb = new StringBuilder();
        //拼接签名字符串
        sb.append("id_equipment=");
        sb.append(id_equipment);

        sb.append("&phone_driver=");
        sb.append(phone);

        sb.append("&key=");
        sb.append(Constants.MSGKEY);
        LogUtil.E("sb:" + sb);

        String appSign1 = MD5.getMessageDigest(sb.toString().getBytes());
        String appSign = MD5.getMessageDigest(appSign1.getBytes()).toUpperCase();
        return appSign;
    }
}
