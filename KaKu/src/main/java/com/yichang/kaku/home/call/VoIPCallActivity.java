package com.yichang.kaku.home.call;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.widget.ImageButton;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.CallSubmitReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECVoIPCallManager.CallType;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.VideoRatio;

import java.util.Timer;
import java.util.TimerTask;

/**
 * com.yuntongxun.ecdemo.ui.voip in ECDemo_Android
 * Created by Jorstin on 2015/7/3.
 */
public class VoIPCallActivity extends BaseActivity implements VoIPCallHelper.OnCallEventNotifyListener, ECCallControlUILayout.OnCallControlDelegate, ECCallHeadUILayout.OnSendDTMFDelegate      /*extends ECVoIPBaseActivity*/ /*implements ECVoIPCallManager.OnCallProcessMultiDataListener*/ {

    private static final String TAG = "ECSDK_Demo.VoIPCallActivity";
    private boolean isCallBack;
    protected String mCallId, mCallName, mCallNumber;
    protected ECCallHeadUILayout mCallHeaderView;
    protected ECCallControlUILayout mCallControlUIView;
    boolean isConnect = false;
    /**
     * 屏幕资源
     */
    private KeyguardManager.KeyguardLock mKeyguardLock = null;
    private KeyguardManager mKeyguardManager = null;
    private PowerManager.WakeLock mWakeLock;

    /**
     * 是否来电
     */
    protected boolean mIncomingCall = false;
    Timer timer = new Timer();
    private int time_sheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ec_call_interface);

        initCall();
        isCreated = true;
        initProwerManager();
        timer.schedule(task, 1000, 1000);

    }


    private void initCall() {
        // 呼出
        mCallId = Utils.getPhone();
        mCallName = Utils.getCallName();
        mCallNumber = Utils.getCall();
        time_sheng = Utils.getCallSheng();
        isCallBack = false;

        initView();
        // 处理呼叫逻辑
        if (TextUtils.isEmpty(mCallNumber)) {
            finish();
            return;
        }

        if (isCallBack) {
            VoIPCallHelper.makeCallBack(CallType.VOICE, mCallNumber);
        } else {
            //ECDevice.getECVoIPCallManager().setProcessDataEnabled(null , true , true , this);
            mCallId = VoIPCallHelper.makeCall(CallType.DIRECT, mCallNumber);
            if (TextUtils.isEmpty(mCallId)) {
                finish();
                return;
            }
        }
        mCallHeaderView.setCallTextMsg(R.string.ec_voip_call_connecting_server);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (!isCreated) {
            super.onNewIntent(intent);
            initCall();
        }
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time_sheng--;
                    if (time_sheng < 0) {
                        VoIPCallHelper.releaseCall(mCallId);
                        timer.cancel();
                    }
                }
            });
        }
    };

    private boolean isCreated = false;

    private void initView() {
        mCallHeaderView = (ECCallHeadUILayout) findViewById(R.id.call_header_ll);
        mCallControlUIView = (ECCallControlUILayout) findViewById(R.id.call_control_ll);
        mCallControlUIView.setOnCallControlDelegate(this);
        mCallHeaderView.setCallName(mCallName);
        mCallHeaderView.setCallNumber(Utils.getCall());
        mCallHeaderView.setCalling(false);

        ECCallControlUILayout.CallLayout callLayout = mIncomingCall ? ECCallControlUILayout.CallLayout.INCOMING
                : ECCallControlUILayout.CallLayout.OUTGOING;
        mCallControlUIView.setCallDirect(callLayout);

        mCallHeaderView.setSendDTMFDelegate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCreated = false;

    }

    /**
     * 连接到服务器
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallProceeding(String callId) {
        if (mCallHeaderView == null || !needNotify(callId)) {
            return;
        }
        mCallHeaderView.setCallTextMsg("正在呼叫对方，请稍后&#8230");
    }

    /**
     * 连接到对端用户，播放铃音
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallAlerting(String callId) {
        if (!needNotify(callId) || mCallHeaderView == null) {
            return;
        }
        mCallHeaderView.setCallTextMsg("等待对方接听&#8230");
        mCallControlUIView.setCallDirect(ECCallControlUILayout.CallLayout.ALERTING);
    }

    /**
     * 对端应答，通话计时开始
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallAnswered(final String callId) {
        if (!needNotify(callId) || mCallHeaderView == null) {
            return;
        }
        mCallHeaderView.setCalling(true);
        isConnect = true;


    }

    @Override
    public void onMakeCallFailed(String callId, int reason) {
        if (mCallHeaderView == null || !needNotify(callId)) {
            return;
        }
        mCallHeaderView.setCalling(false);
        isConnect = false;
        mCallHeaderView.setCallTextMsg(CallFailReason.getCallFailReason(reason));
        if (reason != SdkErrorCode.REMOTE_CALL_BUSY && reason != SdkErrorCode.REMOTE_CALL_DECLINED) {
            VoIPCallHelper.releaseCall(mCallId);
            finish();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        VoIPCallHelper.setOnCallEventNotifyListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseWakeLock();
    }

    /**
     * 初始化资源
     */
    protected void initProwerManager() {
        mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "CALL_ACTIVITY#" + super.getClass().getName());
        mKeyguardManager = ((KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE));
    }

    /**
     * 释放资源
     */
    protected void releaseWakeLock() {
        try {
            if (this.mWakeLock.isHeld()) {
                if (this.mKeyguardLock != null) {
                    this.mKeyguardLock.reenableKeyguard();
                    this.mKeyguardLock = null;
                }
                this.mWakeLock.release();
            }
            return;
        } catch (Exception e) {
        }
    }

    /**
     * 通话结束，通话计时结束
     *
     * @param callId 通话的唯一标识
     */
    @Override
    public void onCallReleased(String callId) {
        if (mCallHeaderView == null || !needNotify(callId)) {
            return;
        }
        mCallHeaderView.setCalling(false);
        isConnect = false;
        mCallHeaderView.setCallTextMsg("通话结束");
        mCallControlUIView.setControlEnable(false);
        SubmitCall();
        //finish();
    }

    @Override
    public void onVideoRatioChanged(VideoRatio videoRatio) {

    }

    protected boolean needNotify(String callId) {
        return !(isFinishing() || !isEqualsCall(callId));
    }

    /**
     * 收到的VoIP通话事件通知是否与当前通话界面相符
     *
     * @return 是否正在进行的VoIP通话
     */
    protected boolean isEqualsCall(String callId) {
        return (!TextUtils.isEmpty(callId) && callId.equals(mCallId));
    }

    @Override
    public void onMakeCallback(ECError ecError, String caller, String called) {
        if (!TextUtils.isEmpty(mCallId)) {
            return;
        }
        if (ecError.errorCode != SdkErrorCode.REQUEST_SUCCESS) {
            mCallHeaderView.setCallTextMsg("回拨呼叫失败[" + ecError.errorCode + "]");
        } else {
            mCallHeaderView.setCallTextMsg("回拨呼叫成功，请注意接听系统来电!");
        }
        mCallHeaderView.setCalling(false);
        isConnect = false;
        mCallControlUIView.setControlEnable(false);
        finish();
    }


    @Override
    public void sendDTMF(char c) {
        ECDevice.getECVoIPCallManager().sendDTMF(mCallId, c);
    }

    @Override
    public void onViewAccept(ECCallControlUILayout controlPanelView, ImageButton view) {
        if (controlPanelView != null) {///
            controlPanelView.setControlEnable(false);
        }
        VoIPCallHelper.acceptCall(mCallId);
        mCallControlUIView.setCallDirect(ECCallControlUILayout.CallLayout.INCALL);
        mCallHeaderView.setCallTextMsg("正在接听...");
    }

    @Override
    public void onViewReject(ECCallControlUILayout controlPanelView, ImageButton view) {
        if (controlPanelView != null) {
            controlPanelView.setControlEnable(false);
        }
        VoIPCallHelper.rejectCall(mCallId);
    }

    @Override
    public void onViewRelease(ECCallControlUILayout controlPanelView, ImageButton view) {
        if (controlPanelView != null) {
            controlPanelView.setControlEnable(false);
        }
        VoIPCallHelper.releaseCall(mCallId);
    }

    @Override
    public void setDialerpadUI() {

    }

    public void SubmitCall() {
        CallSubmitReq req = new CallSubmitReq();
        req.code = "60054";
        req.phone_people = Utils.getCall();
        req.call_duration = Utils.getCallTime() + "";
        KaKuApiProvider.CallSubmit(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("callsubmit res: " + t.res);
                    SharedPreferences.Editor editor = KaKuApplication.editor;
                    editor.putFloat(Constants.CALLTIME, 0.00f);
                    editor.commit();
                    finish();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }
}



