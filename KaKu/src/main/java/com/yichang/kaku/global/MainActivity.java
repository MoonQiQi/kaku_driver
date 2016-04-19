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
import com.yichang.kaku.home.faxian.FindFragment;
import com.yichang.kaku.member.MemberFragment;
import com.yichang.kaku.member.login.LoginActivity;
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
import com.yichang.kaku.zhaohuo.ZhaoFragment;
import com.yolanda.nohttp.Response;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseFragmentActivity implements OnTabClickCallback {

    private BottomTabFragment bottomTabFragment;
    public static TitleFragment titleFragment;
    private boolean isShow = false;
    private String android_url;
    private String versionName;
    private Boolean isPwdPopWindowShow = false;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        //KaKuApplication.initTraceService(this, Utils.getPhone());
        bottomTabFragment = (BottomTabFragment) fragMgr.findFragmentById(R.id.bottom_tabs);
        int index = getIntent().getIntExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_UNKONWN);
        switch (index) {
            case Constants.TAB_POSITION_UNKONWN:
            case Constants.TAB_POSITION_HOME:
                onHomeTabClick();
                bottomTabFragment.setTab(Constants.TAB_POSITION_HOME);
                break;
            case Constants.TAB_POSITION_ZONE:
                onZoneTabClick();
                bottomTabFragment.setTab(Constants.TAB_POSITION_ZONE);
                break;
            case Constants.TAB_POSITION_FIND:
                onFindTabClick();
                bottomTabFragment.setTab(Constants.TAB_POSITION_FIND);
                break;
            case Constants.TAB_POSITION_MEMBER:
                onMemberTabClick();
                bottomTabFragment.setTab(Constants.TAB_POSITION_MEMBER);
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

        if (!"Zone".equals(KaKuApplication.WhichFrag) &&
                !"Member".equals(KaKuApplication.WhichFrag) &&
                !"Find".equals(KaKuApplication.WhichFrag)) {
            AutoLogin();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(context);
    }


    @Override
    public void onHomeTabClick() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Home");
        HomeFragment homeFragment = (HomeFragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_HOME);
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        /*
         * setTitleName(R.id.frgmt_title, LungPoonApplication.titleName);
		 * setTitleImage(R.id.frgmt_title, 0);
		 * setTitleLeftImage(R.id.frgmt_title, 0);
		 */
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, homeFragment, Constants.FRAGMENT_TAG_HOME);
        KaKuApplication.WhichFrag = "Home";
    }

    @Override
    public void onZoneTabClick() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Zone");
        ZhaoFragment zoneFragment = (ZhaoFragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_ZONE);
        if (zoneFragment == null)
            zoneFragment = new ZhaoFragment();
        /*
         * setTitleName(R.id.frgmt_title, "兑换"); setTitleImage(R.id.frgmt_title,
		 * R.drawable.gouwucheicon); setTitleLeftImage(R.id.frgmt_title,
		 * R.drawable.wujiaoxing);
		 */
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, zoneFragment, Constants.FRAGMENT_TAG_ZONE);
        KaKuApplication.WhichFrag = "Zone";
    }

    @Override
    public void onMemberTabClick() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Member");
        if (!Utils.isLogin()) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        MemberFragment memberFragment = (MemberFragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_MEMBER);
        if (memberFragment == null)
            memberFragment = new MemberFragment();
        /*
         * setTitleName(R.id.frgmt_title, "我"); setTitleImage(R.id.frgmt_title,
		 * 0); setTitleLeftImage(R.id.frgmt_title, 0);
		 */
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, memberFragment, Constants.FRAGMENT_TAG_MEMBER);
        KaKuApplication.WhichFrag = "Member";
    }

    @Override
    public void onFindTabClick() {
        // TODO Auto-generated method stub
        MobclickAgent.onEvent(context, "Find");

        FindFragment knowFragment = (FindFragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_KNOW);
        if (knowFragment == null)
            knowFragment = new FindFragment();
        /*
         * setTitleName(R.id.frgmt_title, "发现");
		 * setTitleImage(R.id.frgmt_title,0);
		 * setTitleLeftImage(R.id.frgmt_title, 0);
		 */
        FragmentTransaction ft = fragMgr.beginTransaction();
        switchFragment(ft, R.id.layout_content, knowFragment, Constants.FRAGMENT_TAG_KNOW);
        KaKuApplication.WhichFrag = "Know";
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

                    } else if (Constants.RES_TWO.equals(t.res)) {
                        LogUtil.showShortToast(MainActivity.this, t.msg);
                    } else {
                        SharedPreferences.Editor editor = KaKuApplication.editor;
                        editor.putBoolean(Constants.ISLOGIN, false);
                        editor.putString(Constants.IDDRIVE, "");
                        editor.putString(Constants.IDCAR, "");
                        editor.putString(Constants.NAMEDRIVE, "");
                        editor.putString(Constants.SID, "");
                        editor.commit();
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
                    LogUtil.E("update res: " + t.res);
                    if (Constants.RES_ONE.equals(t.res)) {
                        android_url = t.android_url;
                        if (!"Zone".equals(KaKuApplication.WhichFrag) &&
                                !"Member".equals(KaKuApplication.WhichFrag) &&
                                !"Find".equals(KaKuApplication.WhichFrag)) {
                            showPopWindow(true, android_url);
                        }

                    } else if (Constants.RES_NINE.equals(t.res)) {
                        android_url = t.android_url;
                        if (!"Zone".equals(KaKuApplication.WhichFrag) &&
                                !"Member".equals(KaKuApplication.WhichFrag) &&
                                !"Find".equals(KaKuApplication.WhichFrag)) {
                            showPopWindow(false, android_url);
                        }
                    }
                }
            }
        });
    }

    private void showPopWindow(final boolean flag, final String android_url) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                UpDatePopWindow input =
                        new UpDatePopWindow(MainActivity.this, flag, android_url);
                input.show();
            }
        }, 0);
    }
}
