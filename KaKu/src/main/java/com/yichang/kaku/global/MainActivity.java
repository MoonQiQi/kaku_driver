package com.yichang.kaku.global;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.home.HomeFragment;
import com.yichang.kaku.home.discovery.FindFragment;
import com.yichang.kaku.logistics.ZoneFragment;
import com.yichang.kaku.member.MemberFragment;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.request.AutoLoginReq;
import com.yichang.kaku.response.AutoLoginResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.BottomTabFragment;
import com.yichang.kaku.view.BottomTabFragment.OnTabClickCallback;
import com.yichang.kaku.view.PrizePopWindow;
import com.yichang.kaku.view.TitleFragment;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseFragmentActivity implements OnTabClickCallback {

    private BottomTabFragment bottomTabFragment;
    public static TitleFragment titleFragment;
    private boolean isShow = false;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_main);
        KaKuApplication.initTraceService(this, Utils.getPhone());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Bundle bundle = intent.getExtras();
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AutoLogin();
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(context);
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
        ZoneFragment zoneFragment = (ZoneFragment) fragMgr.findFragmentByTag(Constants.FRAGMENT_TAG_ZONE);
        if (zoneFragment == null)
            zoneFragment = new ZoneFragment();
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
        KaKuApiProvider.autologin(req, new BaseCallback<AutoLoginResp>(AutoLoginResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, AutoLoginResp t) {
                if (t != null) {
                    LogUtil.E("antologinmain res: " + t.res);
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

                        if (!TextUtils.isEmpty(KaKuApplication.flag_show) && !TextUtils.equals("0", KaKuApplication.flag_show) && !isShow) {
                            getWindow().getDecorView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new PrizePopWindow(MainActivity.this, KaKuApplication.flag_show).show();
                                    isShow = true;
                                }
                            }, 500);
                        }
                    }
                }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {

            }
        });
    }

}
