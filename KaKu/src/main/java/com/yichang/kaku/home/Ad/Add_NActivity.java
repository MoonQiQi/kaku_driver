package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wly.android.widget.AdGalleryHelper;
import com.wly.android.widget.Advertising;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.member.recommend.MemberRecommendActivity;
import com.yichang.kaku.obj.RollsAddObj;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.request.StickerShareReq;
import com.yichang.kaku.request.TaskJumpReq;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.response.StickerShareResp;
import com.yichang.kaku.response.TaskJumpResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.CheTieYaoCodeWindow;
import com.yichang.kaku.view.popwindow.OneKeySharePopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class Add_NActivity extends BaseActivity implements OnClickListener {

    private TextView left, title;
    private TextView right;
    private Bundle bundle;
    private String name_advert, day_earnings, time_begin, time_end, num_driver, free_remind, image_advert,
            image_size, day_continue, day_remaining, total_earning, flag_type, now_earnings;
    private TextView tv_shouyi_name, tv_shouyi_time, tv_shouyi_zongshouyi, tv_shouyi_dangqianshouyiqian, tv_shouyi_canyu,
            tv_shouyi_meiriqian, tv_shouyi_shengyutian, tv_shuoming_1, tv_shuoming_4, tv_chetiexinxi_1, tv_shuoming_5, tv_shuoming_8, tv_shuoming_10;
    private TextView tv_shuoming_lottery;
    private ImageView iv_tietieyangshi, iv_shouqi_n;
    private RelativeLayout rela_chetiexinxi, rela_addn_button;
    private AdGalleryHelper mGalleryHelper;
    private List<RollsAddObj> rollsadd_list = new ArrayList<RollsAddObj>();
    private Button btn_add;
    private ScrollView scroll_add_n;
    private View renwushuoming_n, chetiexinxi_n;
    private boolean flag_zhankai = true;
    private Boolean isPwdPopWindowShow = false;


    private LinearLayout ll_yaoqing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_n);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("任务详情");
        right = (TextView) findViewById(R.id.tv_right);
        right.setOnClickListener(this);

        ll_yaoqing = (LinearLayout) findViewById(R.id.ll_yaoqing);
        ll_yaoqing.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if ("Y".equals(KaKuApplication.flag_mengban)){
            showPopWindow();
            KaKuApplication.flag_mengban = "N";
        }
        GetAdd();
    }

    public void GetAdd() {
        showProgressDialog();
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(this, GetAddResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    LogUtil.E("flag_recommended: " + t.advert.getFlag_recommended());
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.flag_recommended = t.advert.getFlag_recommended();
                        KaKuApplication.flag_jiashinum = t.advert.getNum_privilege();
                        KaKuApplication.flag_position = t.advert.getFlag_position();
                        KaKuApplication.flag_heart = t.advert.getFlag_show();
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
                        now_earnings = t.advert.getNow_earnings();
                        rollsadd_list = t.rolls;
                        SetText();
                        autoAdvance(rollsadd_list);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            goToHome();
        } else if (R.id.tv_right == id) {
            // TODO: 2016/1/6  分享
            startActivity(new Intent(this,CheTieListActivity.class));
            //getStickerShareInfo();
        } else if (R.id.btn_add_n == id) {
            MobclickAgent.onEvent(this, "ShenQingKaiQi");
            Jump();
        } else if (R.id.rela_addn_button_n == id) {
            MobclickAgent.onEvent(this, "ShenQingKaiQi");
            Jump();
        } else if (R.id.iv_shouqi_n == id) {
            if (flag_zhankai) {
                iv_shouqi_n.setImageResource(R.drawable.btn_gengduoxiangqing);
                iv_shouqi_n.setScaleType(ImageView.ScaleType.FIT_START);
                iv_shouqi_n.setAdjustViewBounds(true);
                renwushuoming_n.setVisibility(View.GONE);
                chetiexinxi_n.setVisibility(View.GONE);
                flag_zhankai = false;
            } else if (!flag_zhankai) {
                iv_shouqi_n.setImageResource(R.drawable.btn_shouqixiangqing);
                iv_shouqi_n.setScaleType(ImageView.ScaleType.FIT_START);
                iv_shouqi_n.setAdjustViewBounds(true);
                renwushuoming_n.setVisibility(View.VISIBLE);
                chetiexinxi_n.setVisibility(View.VISIBLE);
                flag_zhankai = true;
            }
        } else if (R.id.tv_shuoming_5 == id) {
            startActivity(new Intent(this, YueActivity.class));
        }  else if (R.id.tv_shuoming_10 == id) {
            Utils.Call(this, "400-6867585");
        } else if (R.id.tv_shuoming_lottery == id) {
            startActivity(new Intent(this, LotteryActivity.class));
        } else if (R.id.ll_yaoqing == id) {
            startActivity(new Intent(context, MemberRecommendActivity.class));
        }
    }

    public void SetText() {
        title.setText(name_advert);
        renwushuoming_n = findViewById(R.id.renwushuoming_n);
        chetiexinxi_n = findViewById(R.id.chetiexinxi_n);
        scroll_add_n = (ScrollView) findViewById(R.id.scroll_add_n);
        tv_shouyi_time = (TextView) findViewById(R.id.tv_shouyi_time);
        tv_shouyi_zongshouyi = (TextView) findViewById(R.id.tv_shouyi_zongshouyi);
        tv_shouyi_dangqianshouyiqian = (TextView) findViewById(R.id.tv_shouyi_dangqianshouyiqian);
        tv_shouyi_canyu = (TextView) findViewById(R.id.tv_shouyi_canyu);
        tv_shouyi_meiriqian = (TextView) findViewById(R.id.tv_shouyi_meiriqian);
        tv_shouyi_shengyutian = (TextView) findViewById(R.id.tv_shouyi_shengyutian);
        tv_shuoming_1 = (TextView) findViewById(R.id.tv_shuoming_1);
        tv_shuoming_4 = (TextView) findViewById(R.id.tv_shuoming_4);
        tv_shuoming_5 = (TextView) findViewById(R.id.tv_shuoming_5);
        tv_shuoming_10 = (TextView) findViewById(R.id.tv_shuoming_10);
        tv_shuoming_lottery = (TextView) findViewById(R.id.tv_shuoming_lottery);
        tv_shuoming_lottery.setOnClickListener(this);

        String strings = "收益可前往【我】-【我的余额】查看。";
        SpannableStringBuilder styles = new SpannableStringBuilder(strings);
        styles.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styles.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 5, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shuoming_5.setText(styles);
        tv_shuoming_5.setOnClickListener(this);

        String stringsss = "如有疑问，请拨打【400-6867585】。";
        SpannableStringBuilder stylesss = new SpannableStringBuilder(stringsss);
        stylesss.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 8, stringsss.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shuoming_10.setText(stylesss);
        tv_shuoming_10.setOnClickListener(this);

        iv_tietieyangshi = (ImageView) findViewById(R.id.iv_tietieyangshi);
        iv_shouqi_n = (ImageView) findViewById(R.id.iv_shouqi_n);
        iv_shouqi_n.setOnClickListener(this);
        rela_chetiexinxi = (RelativeLayout) findViewById(R.id.rela_chetiexinxi);
        rela_addn_button = (RelativeLayout) findViewById(R.id.rela_addn_button_n);
        rela_addn_button.setOnClickListener(this);
        btn_add = (Button) findViewById(R.id.btn_add_n);
        btn_add.setOnClickListener(this);

        scroll_add_n.smoothScrollTo(0, 0);
        tv_shouyi_time.setText(time_begin + "至" + time_end);
        String string = "预期总收益：¥ " + total_earning;
        SpannableStringBuilder style = new SpannableStringBuilder(string);
        style.setSpan(new AbsoluteSizeSpan(18, true), 6, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shouyi_zongshouyi.setText(style);
        tv_shouyi_dangqianshouyiqian.setText(now_earnings);
        tv_shouyi_canyu.setText(num_driver + "人参与");
        tv_shouyi_meiriqian.setText("¥ " + day_earnings);
        tv_shouyi_shengyutian.setText(day_remaining + "天");
        BitmapUtil.getInstance(context).download(iv_tietieyangshi, KaKuApplication.qian_zhui + image_advert);
    }

    private void autoAdvance(List<RollsAddObj> imgList) {
        // TODO Auto-generated method stub
        if (imgList == null) {
            return;
        }
        if (imgList.size() <= 0) {
            return;
        }
        List<Advertising> list = new ArrayList<Advertising>();

        for (RollsAddObj obj : imgList) {
            Advertising advertising = new Advertising(null, obj.getImage_roll(), null);
            advertising.setPicURL(KaKuApplication.qian_zhui + obj.getImage_roll());
            list.add(advertising);
        }

        if (list.size() > 0) {
            mGalleryHelper = new AdGalleryHelper(context, list, Constants.AUTO_SCROLL_DURATION, true, false, false);
            rela_chetiexinxi.addView(mGalleryHelper.getLayout());
            mGalleryHelper.startAutoSwitch();
        }
    }

    public void Jump() {
        showProgressDialog();
        TaskJumpReq req = new TaskJumpReq();
        req.code = "60012";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.TaskJump(req, new KakuResponseListener<TaskJumpResp>(this,TaskJumpResp.class) {

            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("taskjump res: " + t.res);
                    if (Constants.RES_ONE.equals(t.res)) {
                        showPopWindow();
                    } else if ("A".equals(KaKuApplication.flag_recommended)){
                        KaKuApplication.flag_nochetietv = "you";
                        KaKuApplication.flag_code = "60020";
                        Intent intent = new Intent(context,AdImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if ("B".equals(KaKuApplication.flag_recommended)){
                        KaKuApplication.flag_nochetietv = "wu";
                        KaKuApplication.flag_code = "60020";
                        Intent intent = new Intent(context,AdImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if ("C".equals(KaKuApplication.flag_recommended) || "D".equals(KaKuApplication.flag_recommended)){
                        KaKuApplication.flag_nochetietv = "wu";
                        KaKuApplication.flag_code = "60020";
                        Intent intent = new Intent(context,XingShiZhengImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if ("".equals(KaKuApplication.flag_recommended)){
                        KaKuApplication.flag_nochetietv = "wu";
                        KaKuApplication.flag_code = "60018";
                        Intent intent = new Intent(context,AdImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

    private void getStickerShareInfo() {

        Utils.NoNet(context);
        showProgressDialog();
        StickerShareReq req = new StickerShareReq();
        req.code = "60019";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getStickerShareInfo(req, new KakuResponseListener<StickerShareResp>(this,StickerShareResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getCalendarList res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        showShare(t);

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }
        });
    }

    private void showShare(StickerShareResp t) {
        if (oneKeySharePopWindow == null) {
            shareContent.url = t.url;
            shareContent.content = t.content;
            oneKeySharePopWindow = new OneKeySharePopWindow(this, shareContent);
            oneKeySharePopWindow.setIsShortUrl(false);
        }
        oneKeySharePopWindow.show();

    }

    private ShareContentObj shareContent = new ShareContentObj();
    private OneKeySharePopWindow oneKeySharePopWindow;

    private void showPopWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                CheTieYaoCodeWindow input =
                        new CheTieYaoCodeWindow(Add_NActivity.this);
                input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                input.setConfirmListener(new CheTieYaoCodeWindow.ConfirmListener() {
                    @Override
                    public void confirmPwd(Boolean isConfirmed) {
                        if (isConfirmed) {
                            isPwdPopWindowShow = false;
                        }
                    }

                    @Override
                    public void showDialog() {
                        showProgressDialog();
                    }

                    @Override
                    public void stopDialog() {
                        stopProgressDialog();
                    }

                });

                input.show();

            }
        }, 200);
    }

}
