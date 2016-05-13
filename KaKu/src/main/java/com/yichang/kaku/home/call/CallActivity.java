package com.yichang.kaku.home.call;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaolafm.sdk.core.mediaplayer.BroadcastRadioPlayerManager;
import com.kaolafm.sdk.core.mediaplayer.IPlayerStateListener;
import com.kaolafm.sdk.core.mediaplayer.PlayItem;
import com.kaolafm.sdk.core.mediaplayer.PlayerManager;
import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.CallListObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.CallListResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.InputPhoneWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.ECVoIPSetupManager;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.OnMeetingListener;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.VideoRatio;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
import com.yuntongxun.ecsdk.meeting.intercom.ECInterPhoneMeetingMsg;
import com.yuntongxun.ecsdk.meeting.video.ECVideoMeetingMsg;
import com.yuntongxun.ecsdk.meeting.voice.ECVoiceMeetingMsg;

import java.util.ArrayList;
import java.util.List;

public class CallActivity extends BaseActivity implements OnClickListener, ECDevice.OnECDeviceConnectListener {

    private TextView left, right, title;
    private ListView lv_call;
    private TextView tv_call_zuijin;
    private List<CallListObj> list_call = new ArrayList<>();
    private ImageView iv_call_right, iv_call_left;
    private Boolean isPwdPopWindowShow = false;
    private String mCurrentCallId, time;
    private RelativeLayout rela_call_liaojie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("免费电话");
        tv_call_zuijin = (TextView) findViewById(R.id.tv_call_zuijin);
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("更多");
        right.setOnClickListener(this);
        lv_call = (ListView) findViewById(R.id.lv_call);
        Utils.setListViewHeightBasedOnChildren(lv_call);
        iv_call_left = (ImageView) findViewById(R.id.iv_call_left);
        iv_call_left.setOnClickListener(this);
        iv_call_right = (ImageView) findViewById(R.id.iv_call_right);
        iv_call_right.setOnClickListener(this);
        rela_call_liaojie = (RelativeLayout) findViewById(R.id.rela_call_liaojie);
        rela_call_liaojie.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!ECDevice.isInitialized()) {
            ECDevice.initial(context, new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    LogUtil.E("SDK已经初始化成功");
                    initsdk();
                }

                @Override
                public void onError(Exception exception) {
                    LogUtil.E("SDK初始化失败" + exception.toString());
                }
            });
        }

        getInfo();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        } else if (R.id.tv_right == id) {
            MobclickAgent.onEvent(context, "callmore");
            Intent intent = new Intent(this, CallMoreActivity.class);
            startActivity(intent);
        } else if (R.id.iv_call_left == id) {
            if (Utils.getCallSheng() <= 0) {
                LogUtil.showShortToast(context, "剩余通话时长不足");
                return;
            }
            PlayerManager.getInstance(context).pause();
            BroadcastRadioPlayerManager.getInstance().pause();
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
        } else if (R.id.iv_call_right == id) {
            if (Utils.getCallSheng() <= 0) {
                LogUtil.showShortToast(context, "剩余通话时长不足");
                return;
            }
            ShowPop();
        } else if (R.id.rela_call_liaojie == id) {
            MobclickAgent.onEvent(context, "callliaojie");
            startActivity(new Intent(this, CallIntroActivity.class));
        }
    }

    public void getInfo() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "60053";
        KaKuApiProvider.CallList(req, new KakuResponseListener<CallListResp>(context, CallListResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("calllist res: " + t.res);
                    LogUtil.E("time : " + t.duration_now);
                    if (Constants.RES.equals(t.res)) {
                        time = t.duration_now;
                        int abs = Math.abs(Integer.parseInt(time));
                        SharedPreferences.Editor editor = KaKuApplication.editor;
                        editor.putInt(Constants.CALL_SHENG, abs);
                        editor.commit();
                        list_call = t.duration;
                        if (list_call.size() == 0) {
                            tv_call_zuijin.setVisibility(View.INVISIBLE);
                        } else {
                            tv_call_zuijin.setVisibility(View.VISIBLE);
                            CallListAdapter adapter = new CallListAdapter(context, list_call);
                            lv_call.setAdapter(adapter);
                            Utils.setListViewHeightBasedOnChildren(lv_call);
                        }
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void ShowPop() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                InputPhoneWindow popWindow = new InputPhoneWindow(CallActivity.this);
                popWindow.setConfirmListener(new InputPhoneWindow.ConfirmListener() {
                    @Override
                    public void Jump() {
                        PlayerManager.getInstance(context).pause();
                        BroadcastRadioPlayerManager.getInstance().pause();
                        Call();
                    }
                });
                popWindow.show();

            }
        }, 0);
    }

    public void Call() {
        startActivity(new Intent(this, VoIPCallActivity.class));
    }

    public void initsdk() {
        // 构建注册所需要的参数信息
//5.0.3的SDK初始参数的方法：ECInitParams params = new ECInitParams();
//5.1.*以上版本如下：
        ECInitParams params = ECInitParams.createParams();
        //自定义登录方式：
        //测试阶段Userid可以填写手机
        params.setUserid(Utils.getPhone());
        params.setAppKey("8a216da85624dadc01562f87f2c50a43");
        params.setToken("7110fbaef7c7bdeb503becdbf08d9e18");
        // 设置登陆验证模式（是否验证密码）NORMAL_AUTH-自定义方式
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        // 1代表用户名+密码登陆（可以强制上线，踢掉已经在线的设备）
        // 2代表自动重连注册（如果账号已经在其他设备登录则会提示异地登陆）
        // 3 LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO）
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);

        //voip账号+voip密码方式：
        params.setUserid(Utils.getPhone());
        //params.setPwd("voip密码");
        params.setAppKey("8a216da85624dadc01562f87f2c50a43");
        // 设置登陆验证模式（是否验证密码）PASSWORD_AUTH-密码登录方式
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        // 1代表用户名+密码登陆（可以强制上线，踢掉已经在线的设备）
        // 2代表自动重连注册（如果账号已经在其他设备登录则会提示异地登陆）
        // 3 LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO）
        params.setMode(ECInitParams.LoginMode.AUTO);

        // 如果是v5.1.8r开始版本建议使用
        ECDevice.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            @Override
            public void onConnect() {

            }

            @Override
            public void onDisconnect(ECError ecError) {

            }

            @Override
            public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {
                LogUtil.E("eccode:" + ecError.errorCode);
            }
        });

        // 如果是v5.1.8r以前版本设置登陆状态回调如下
        params.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            public void onConnect() {
                // 兼容4.0，5.0可不必处理
            }

            @Override
            public void onDisconnect(ECError error) {
                // 兼容4.0，5.0可不必处理
            }

            @Override
            public void onConnectState(ECDevice.ECConnectState state, ECError error) {
                LogUtil.E("code:" + error.errorCode);
                if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
                    if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                        //账号异地登陆
                    } else {
                        //连接状态失败
                    }
                    return;
                } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                    // 登陆成功
                    LogUtil.E("AAAAAAAAAA");
                }
            }
        });

        // 如果是v5.1.8r版本
        ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage ecMessage) {

            }

            @Override
            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage ecGroupNoticeMessage) {

            }

            @Override
            public void onOfflineMessageCount(int i) {

            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List<ECMessage> list) {

            }

            @Override
            public void onReceiveOfflineMessageCompletion() {

            }

            @Override
            public void onServicePersonVersion(int i) {

            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });
        // 5.1.7r及以前版本设置SDK接收消息回调
        params.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage msg) {
                // 收到新消息
            }

            @Override
            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage notice) {
                // 收到群组通知消息（有人加入、退出...）
                // 可以根据ECGroupNoticeMessage.ECGroupMessageType类型区分不同消息类型
            }

            @Override
            public void onOfflineMessageCount(int count) {
                // 登陆成功之后SDK回调该接口通知账号离线消息数
            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List msgs) {
                // SDK根据应用设置的离线消息拉去规则通知应用离线消息
            }

            @Override
            public void onReceiveOfflineMessageCompletion() {
                // SDK通知应用离线消息拉取完成
            }

            @Override
            public void onServicePersonVersion(int version) {
                // SDK通知应用当前账号的个人信息版本号
            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });

        if (params.validate()) {
            LogUtil.E("注册SDK成功");
            ECDevice.login(params);
        }

        // 获得SDKVoIP呼叫接口
        // 注册VoIP呼叫事件回调监听
        ECVoIPCallManager callInterface = ECDevice.getECVoIPCallManager();
        if (callInterface != null) {
            callInterface.setOnVoIPCallListener(new ECVoIPCallManager.OnVoIPListener() {
                @Override
                public void onVideoRatioChanged(VideoRatio videoRatio) {

                }

                @Override
                public void onSwitchCallMediaTypeRequest(String s, ECVoIPCallManager.CallType callType) {

                }

                @Override
                public void onSwitchCallMediaTypeResponse(String s, ECVoIPCallManager.CallType callType) {

                }

                @Override
                public void onDtmfReceived(String s, char c) {

                }

                @Override
                public void onCallEvents(ECVoIPCallManager.VoIPCall voipCall) {
                    // 处理呼叫事件回调
                    if (voipCall == null) {
                        return;
                    }
                    // 根据不同的事件通知类型来处理不同的业务
                    ECVoIPCallManager.ECCallState callState = voipCall.callState;
                    switch (callState) {
                        case ECCALL_PROCEEDING:
                            // 正在连接服务器处理呼叫请求
                            break;
                        case ECCALL_ALERTING:
                            // 呼叫到达对方客户端，对方正在振铃
                            break;
                        case ECCALL_ANSWERED:
                            // 对方接听本次呼叫
                            break;
                        case ECCALL_FAILED:
                            // 本次呼叫失败，根据失败原因播放提示音
                            break;
                        case ECCALL_RELEASED:
                            // 通话释放[完成一次呼叫]
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        // 注册会议消息处理监听
        if (ECDevice.getECMeetingManager() != null) {
            ECDevice.getECMeetingManager().setOnMeetingListener(new OnMeetingListener() {
                @Override
                public void onVideoRatioChanged(VideoRatio videoRatio) {

                }

                @Override
                public void onReceiveInterPhoneMeetingMsg(ECInterPhoneMeetingMsg msg) {
                    // 处理实时对讲消息Push
                }

                @Override
                public void onReceiveVoiceMeetingMsg(ECVoiceMeetingMsg msg) {
                    // 处理语音会议消息push
                }

                @Override
                public void onReceiveVideoMeetingMsg(ECVideoMeetingMsg msg) {
                    // 处理视频会议消息Push（暂未提供）
                }
            });
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect(ECError ecError) {

    }

    @Override
    public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {

    }

    /**
     * VoIP呼叫接口
     *
     * @return
     */
    public static ECVoIPCallManager getVoIPCallManager() {
        return ECDevice.getECVoIPCallManager();
    }

    public static ECVoIPSetupManager getVoIPSetManager() {
        return ECDevice.getECVoIPSetupManager();
    }

    private String username, usernumber;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                if ("+86".equals(usernumber.substring(0, 3))) {
                    //去掉空格
                    usernumber = usernumber.replaceAll(" ", "");
                    usernumber = usernumber.replaceAll("-", "");
                } else {
                    //去掉空格
                    usernumber = usernumber.replaceAll(" ", "");
                    usernumber = usernumber.replaceAll("-", "");
                }
                SharedPreferences.Editor editor = KaKuApplication.editor;
                editor.putString(Constants.CALL, usernumber);
                editor.putString(Constants.CALLNAME, username);
                editor.commit();
                startActivity(new Intent(this, VoIPCallActivity.class));
            }

        }
    }

    IPlayerStateListener mIplayerStateListener = new IPlayerStateListener() {
        @Override
        public void onIdle(PlayItem playItem) {

        }

        @Override
        public void onPlayerPreparing(PlayItem playItem) {
            if (playItem == null) {
                return;
            }
        }

        @Override
        public void onPlayerPlaying(PlayItem playItem) {
        }

        @Override
        public void onPlayerPaused(PlayItem playItem) {
        }

        @Override
        public void onProgress(String s, int i, int i1, boolean b) {
        }

        @Override
        public void onPlayerFailed(PlayItem playItem, int i, int i1) {

        }

        @Override
        public void onPlayerEnd(PlayItem playItem) {

        }

        @Override
        public void onSeekStart(String s) {
        }

        @Override
        public void onSeekComplete(String s) {
        }

        @Override
        public void onBufferingStart(PlayItem playItem) {

        }

        @Override
        public void onBufferingEnd(PlayItem playItem) {

        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
