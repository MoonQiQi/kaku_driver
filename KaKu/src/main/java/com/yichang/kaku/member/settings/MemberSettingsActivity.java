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

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.UpdateAppManager;
import com.yichang.kaku.member.address.AddrActivity;
import com.yichang.kaku.member.cash.SetWithDrawCodeActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.request.CheckUpdateReq;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.CheckUpdateResp;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class MemberSettingsActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;

    private RelativeLayout rela_setting_update, rela_setting_version, rela_setting_address, rela_setting_aboutus;
    private RelativeLayout rela_setting_edit_pwd;

    private Button btn_setting_exit;

    private TextView tv_setting_update, tv_setting_version;

    private UpdateAppManager updateAppManager;
    //    版本信息
    private String versionName;
    private String android_url;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_settings);

        init();
    }

    private void init() {
        versionName = getVersionName();
        initTitleBar();

        initRelativeLayout();
//退出
        btn_setting_exit = (Button) findViewById(R.id.btn_setting_exit);
        btn_setting_exit.setOnClickListener(this);
//显示版本更新、版本信息
        tv_setting_update = (TextView) findViewById(R.id.tv_setting_update);
        tv_setting_version = (TextView) findViewById(R.id.tv_setting_version);
        tv_setting_version.setText("V" + versionName);

    }

    private void initRelativeLayout() {
        rela_setting_update = (RelativeLayout) findViewById(R.id.rela_setting_update);
        rela_setting_update.setOnClickListener(this);
        rela_setting_version = (RelativeLayout) findViewById(R.id.rela_setting_version);
        rela_setting_version.setOnClickListener(this);
        rela_setting_address = (RelativeLayout) findViewById(R.id.rela_setting_address);
        rela_setting_address.setOnClickListener(this);
        rela_setting_aboutus = (RelativeLayout) findViewById(R.id.rela_setting_aboutus);
        rela_setting_aboutus.setOnClickListener(this);
        rela_setting_edit_pwd = (RelativeLayout) findViewById(R.id.rela_setting_edit_pwd);
        rela_setting_edit_pwd.setOnClickListener(this);

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
            checkUpdate();

        } else if (R.id.rela_setting_version == id) {
            /*版本*/

        } else if (R.id.rela_setting_address == id) {
            /*收货地址*/
            Intent intent = new Intent(this, AddrActivity.class);
            startActivity(intent);
        } else if (R.id.rela_setting_aboutus == id) {
            /*关于我们*/
            startActivity(new Intent(this, AboutKakuActivity.class));
        }else if (R.id.rela_setting_edit_pwd == id) {
            /*TODO 跳转到支付密码编辑页面*/
            startActivity(new Intent(context, SetWithDrawCodeActivity.class).putExtra("flag_next_activity","NONE"));
        } else if (R.id.btn_setting_exit == id) {
            /*退出登陆*/

            if (Utils.isLogin()) {
                /*MobclickAgent.onEvent(context, "08");*/
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否选择退出？");
                builder.setNegativeButton("是", new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Exit();
                    }
                });

                builder.setPositiveButton("否", new android.content.DialogInterface.OnClickListener() {

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
        }
    }

    private void checkUpdate() {
        Utils.NoNet(context);
        showProgressDialog();

        CheckUpdateReq req = new CheckUpdateReq();
        req.code = "10010";
        req.version_android = versionName;

        KaKuApiProvider.checkUpdate(req, new BaseCallback<CheckUpdateResp>(CheckUpdateResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CheckUpdateResp t) {
                if (t != null) {
                    LogUtil.E("exit res: " + t.res);
                    android_url = t.android_url;
                    switch (t.res) {
                        case Constants.RES:
                            tv_setting_update.setText("当前已是最新版本");
                            break;
                        case Constants.RES_ONE:
                            tv_setting_update.setText("有新版本待更新");
                            AlertDialog.Builder builder = new AlertDialog.Builder(MemberSettingsActivity.this);
                            builder.setTitle("发现新版本！");
                            //builder.setMessage("新版本特性");
                            builder.setNegativeButton("立即更新", new android.content.DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateAppManager = new UpdateAppManager(context,android_url);

                                    updateAppManager.checkUpdateInfo();
                                }
                            });

                            builder.setPositiveButton("稍后再说", new android.content.DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();

                            break;

                        case Constants.RES_NINE:
                            tv_setting_update.setText("有新版本待更新");
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MemberSettingsActivity.this);
                            builder1.setTitle("发现新版本！");
                            //builder1.setMessage("新版本特性");
                            builder1.setNegativeButton("立即更新", new android.content.DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    updateAppManager = new UpdateAppManager(context,android_url);

                                    updateAppManager.checkUpdateInfo();
                                }
                            });
                            builder1.setCancelable(false);
                            builder1.create().show();

                            break;
                    }

                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private String getVersionName() {
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    private void Exit() {
        Utils.NoNet(context);
        showProgressDialog();

        ExitReq req = new ExitReq();
        req.code = "1000";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.exit(req, new BaseCallback<ExitResp>(ExitResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ExitResp t) {
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
                        goToHome();
                    }

                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }
}
