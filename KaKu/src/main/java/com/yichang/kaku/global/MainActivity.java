package com.yichang.kaku.global;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.home.HomeFragment;
import com.yichang.kaku.home.faxian.Home4Fragment;
import com.yichang.kaku.member.Home5Fragment;
import com.yichang.kaku.request.AutoLoginReq;
import com.yichang.kaku.request.CheckUpdateReq;
import com.yichang.kaku.response.AutoLoginResp;
import com.yichang.kaku.response.CheckUpdateResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.BottomTabFragment;
import com.yichang.kaku.view.BottomTabFragment.OnTabClickCallback;
import com.yichang.kaku.view.TitleFragment;
import com.yichang.kaku.view.popwindow.MainPopWindow;
import com.yichang.kaku.view.popwindow.UpDatePopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.Home2Fragment;
import com.yolanda.nohttp.rest.Response;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseFragmentActivity implements OnTabClickCallback {

    private BottomTabFragment bottomTabFragment;
    public static TitleFragment titleFragment;
    private boolean isShow = false;
    private String android_url;
    private String versionName;
    private String version_introduction;
    private Boolean isPwdPopWindowShow = false;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        //KaKuApplication.initTraceService(this, Utils.getPhone());
        bottomTabFragment = (BottomTabFragment) fragMgr.findFragmentById(R.id.bottom_tabs);
        int index = getIntent().getIntExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_UNKONWN);
        switch (index) {
            case Constants.TAB_POSITION_UNKONWN:
            case Constants.TAB_POSITION_HOME1:
                onHomeTabClick1();
                bottomTabFragment.setTab(Constants.TAB_POSITION_HOME1);
                break;
            case Constants.TAB_POSITION_HOME2:
                onHomeTabClick2();
                bottomTabFragment.setTab(Constants.TAB_POSITION_HOME2);
                break;
            case Constants.TAB_POSITION_HOME4:
                onHomeTabClick4();
                bottomTabFragment.setTab(Constants.TAB_POSITION_HOME4);
                break;
            case Constants.TAB_POSITION_HOME5:
                onHomeTabClick5();
                bottomTabFragment.setTab(Constants.TAB_POSITION_HOME5);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.startService(new Intent(this, Service1.class));
        Update();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = false;
        JPushInterface.onResume(context);

        if (!"Home2".equals(KaKuApplication.WhichFrag) &&
                !"Home4".equals(KaKuApplication.WhichFrag) &&
                !"Home5".equals(KaKuApplication.WhichFrag)) {
            AutoLogin();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(context);
    }


    @Override
    public void onHomeTabClick1() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Home1");
        HomeFragment homeFragment = (HomeFragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_HOME1);
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, homeFragment, Constants.FRAGMENT_TAG_HOME1);
        KaKuApplication.WhichFrag = "Home1";
    }

    @Override
    public void onHomeTabClick2() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Home2");
        Home4Fragment homeFragment2 = (Home4Fragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_HOME2);
        if (homeFragment2 == null)
            homeFragment2 = new Home4Fragment();
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, homeFragment2, Constants.FRAGMENT_TAG_HOME2);
        KaKuApplication.WhichFrag = "Home2";
    }

    @Override
    public void onHomeTabClick4() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Home4");
        Home2Fragment homeFragment4 = (Home2Fragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_HOME4);
        if (homeFragment4 == null)
            homeFragment4 = new Home2Fragment();
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, homeFragment4, Constants.FRAGMENT_TAG_HOME4);
        KaKuApplication.WhichFrag = "Home4";
    }

    @Override
    public void onHomeTabClick5() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Home5");

        Home5Fragment homeFragment5 = (Home5Fragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_HOME5);
        if (homeFragment5 == null)
            homeFragment5 = new Home5Fragment();
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, homeFragment5, Constants.FRAGMENT_TAG_HOME5);
        KaKuApplication.WhichFrag = "Home5";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    MyActivityManager.getInstance().appExit();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    public void AutoLogin() {
        AutoLoginReq req = new AutoLoginReq();
        req.code = "1001";
        req.var_lon = Utils.getLon();
        req.var_lat = Utils.getLat();
        try {
            req.version_android = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        KaKuApiProvider.autologin(req, new KakuResponseListener<AutoLoginResp>(this, AutoLoginResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("antologinmain res: " + t.res);
                    LogUtil.E("flag_show：" + t.flag_show);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.flag_show = t.flag_show;
                        KaKuApplication.hongbao_title = t.title;
                        KaKuApplication.hongbao_content = t.content;
                        KaKuApplication.hongbao_url = t.url;

                        SharedPreferences.Editor editor = KaKuApplication.editor;
                        editor.putBoolean(Constants.ISLOGIN, true);
                        editor.putString(Constants.IDDRIVE, t.driver.getId_driver());
                        editor.putString(Constants.IDCAR, t.id_car);
                        editor.putString(Constants.NAMEDRIVE, t.driver.getName_driver());
                        editor.putString(Constants.SID, t.driver.getSid());
                        editor.commit();
                        if ((!"0".equals(KaKuApplication.flag_show)) && (!isShow)) {
                            getWindow().getDecorView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!"Zone".equals(KaKuApplication.WhichFrag) &&
                                            !"Member".equals(KaKuApplication.WhichFrag) &&
                                            !"Find".equals(KaKuApplication.WhichFrag)) {
                                        new MainPopWindow(MainActivity.this).show();
                                        isShow = true;
                                    }
                                }
                            }, 0);
                        }

                    } else if (Constants.RES_ONE.equals(t.res)) {

                    } else {
                        SharedPreferences.Editor editor = KaKuApplication.editor;
                        editor.putBoolean(Constants.ISLOGIN, false);
                        editor.putString(Constants.IDDRIVE, "");
                        editor.putString(Constants.IDCAR, "");
                        editor.putString(Constants.NAMEDRIVE, "");
                        editor.putString(Constants.SID, "");
                        editor.commit();
                        LogUtil.showShortToast(MainActivity.this, t.msg);
                    }
                }
            }

        });
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
                    LogUtil.E("TTTT:" + t.version_introduction);
                    LogUtil.E("update res: " + t.res);
                    if (Constants.RES_ONE.equals(t.res)) {
                        android_url = t.android_url;
                        version_introduction = t.version_introduction;
                        if (!"Zone".equals(KaKuApplication.WhichFrag) &&
                                !"Member".equals(KaKuApplication.WhichFrag) &&
                                !"Find".equals(KaKuApplication.WhichFrag)) {
                            showPopWindow(true, android_url, version_introduction);
                        }

                    } else if (Constants.RES_NINE.equals(t.res)) {
                        android_url = t.android_url;
                        version_introduction = t.version_introduction;
                        if (!"Zone".equals(KaKuApplication.WhichFrag) &&
                                !"Member".equals(KaKuApplication.WhichFrag) &&
                                !"Find".equals(KaKuApplication.WhichFrag)) {
                            showPopWindow(false, android_url, version_introduction);
                        }
                    } else if (Constants.RES.equals(t.res)) {

                    } else {
                        Utils.Exit();
                    }
                }
            }

        });
    }

    private void showPopWindow(final boolean flag, final String android_url, final String version_introduction) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                UpDatePopWindow input =
                        new UpDatePopWindow(MainActivity.this, flag, android_url, version_introduction);
                input.show();
            }
        }, 0);
    }
}
