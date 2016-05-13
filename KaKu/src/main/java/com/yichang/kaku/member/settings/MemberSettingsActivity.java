package com.yichang.kaku.member.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import com.yichang.kaku.global.UpdateAppManager;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.member.driver.DriverInfoActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.request.CheckUpdateReq;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.CheckUpdateResp;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.UpDatePopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class MemberSettingsActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;

    private RelativeLayout rela_setting_update, rela_setting_version, rela_setting_address, rela_setting_aboutus;
    private RelativeLayout rela_setting_edit_pwd, rela_setting_info;

    private Button btn_setting_exit;

    private TextView tv_setting_update;

    //    版本信息
    private String android_url;
    private UpdateAppManager updateManager;
    private String versionName;
    private String version_introduction;
    private Boolean isPwdPopWindowShow = false;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_settings);

        init();
    }

    private void init() {
        initTitleBar();

        initRelativeLayout();
//退出
        btn_setting_exit = (Button) findViewById(R.id.btn_setting_exit);
        btn_setting_exit.setOnClickListener(this);
//显示版本更新、版本信息
        tv_setting_update = (TextView) findViewById(R.id.tv_setting_update);
    }

    private void initRelativeLayout() {
        rela_setting_update = (RelativeLayout) findViewById(R.id.rela_setting_update);
        rela_setting_update.setOnClickListener(this);
        rela_setting_address = (RelativeLayout) findViewById(R.id.rela_setting_address);
        rela_setting_address.setOnClickListener(this);
        rela_setting_aboutus = (RelativeLayout) findViewById(R.id.rela_setting_aboutus);
        rela_setting_aboutus.setOnClickListener(this);
        rela_setting_edit_pwd = (RelativeLayout) findViewById(R.id.rela_setting_edit_pwd);
        rela_setting_edit_pwd.setOnClickListener(this);
        rela_setting_info = (RelativeLayout) findViewById(R.id.rela_setting_info);
        rela_setting_info.setOnClickListener(this);

    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("设置");
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(getApplicationContext());
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.rela_setting_update == id) {
            /*检查更新*/
            MobclickAgent.onEvent(context, "Update");
            Update();

        } else if (R.id.rela_setting_address == id) {
            /*收货地址*/
            KaKuApplication.IsOrderToAddr = false;
            Intent intent = new Intent(this, AddrActivity.class);
            startActivity(intent);
        } else if (R.id.rela_setting_aboutus == id) {
            /*关于我们*/
            startActivity(new Intent(this, AboutKakuIntroduceActivity.class));
        } else if (R.id.rela_setting_edit_pwd == id) {
            /*TODO 跳转到支付密码编辑页面*/
            startActivity(new Intent(context, SetWithDrawCodeActivity.class).putExtra("flag_next_activity", "NONE"));
        } else if (R.id.btn_setting_exit == id) {
            /*退出登陆*/

            if (Utils.isLogin()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否选择退出？");
                builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Exit();
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
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        } else if (R.id.rela_setting_info == id) {
            startActivity(new Intent(this, DriverInfoActivity.class));
        }
    }

    public void Update() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CheckUpdateReq req = new CheckUpdateReq();
        req.code = "10010";
        req.version_android = versionName;
        KaKuApiProvider.checkUpdate(req, new KakuResponseListener<CheckUpdateResp>(context, CheckUpdateResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("update res: " + t.res);
                    if (Constants.RES_ONE.equals(t.res)) {
                        android_url = t.android_url;
                        version_introduction = t.version_introduction;
                        showPopWindow(true, android_url);
                    } else if (Constants.RES_NINE.equals(t.res)) {
                        android_url = t.android_url;
                        showPopWindow(false, android_url);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void showPopWindow(final boolean flag, final String android_url) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                UpDatePopWindow input =
                        new UpDatePopWindow(MemberSettingsActivity.this, flag, android_url, version_introduction);
                input.show();
            }
        }, 200);
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        startActivity(intent);
        finish();
    }

    private void Exit() {
        Utils.NoNet(context);

        ExitReq req = new ExitReq();
        req.code = "1000";

        KaKuApiProvider.exit(req, new KakuResponseListener<ExitResp>(this, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("exit res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SharedPreferences.Editor editor = KaKuApplication.editor;
                        editor.putBoolean(Constants.ISLOGIN, false);
                        editor.putString(Constants.PHONE, "");
                        editor.putString(Constants.IDDRIVE, "");
                        editor.putString(Constants.IDCAR, "");
                        editor.putString(Constants.NAMEDRIVE, "");
                        editor.putString(Constants.SID, "");
                        editor.commit();

                        PlayerManager.getInstance(context).pause();
                        BroadcastRadioPlayerManager.getInstance().pause();
                        goToHome();
                    }

                    LogUtil.showShortToast(context, t.msg);
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
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

}
