package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.obj.DayObj;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class Add_EActivity extends BaseActivity implements OnClickListener {

    private TextView left, title;
    private TextView right;
    private ImageView iv_E_zhizhen, iv_E_state, iv_E_riliyou, iv_E_rilizuo;
    private TextView tv_E_shouyi, tv_E_tixian, tv_E_jinrishouyi, tv_E_shengyutianshu, tv_E_zhangfu, tv_E_totalmoney, tv_E_yue;
    private Button btn_E_paizhao;
    private RelativeLayout rela_gengduopingjia, tv_woyaopingjiachetie;
    private LinearLayout rela_chetiedaiyan;
    private ListView lv_chetiepingjia;
    private GridView gv_calendar;
    private List<DayObj> list_calendar = new ArrayList<>();
    private int x1, x2;
    private int index_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_y);
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
        iv_E_zhizhen = (ImageView) findViewById(R.id.iv_E_zhizhen);
        iv_E_state = (ImageView) findViewById(R.id.iv_E_state);
        iv_E_riliyou = (ImageView) findViewById(R.id.iv_E_riliyou);
        iv_E_rilizuo = (ImageView) findViewById(R.id.iv_E_rilizuo);
        tv_E_shouyi = (TextView) findViewById(R.id.tv_E_shouyi);
        tv_E_zhangfu = (TextView) findViewById(R.id.tv_E_zhangfu);
        tv_E_totalmoney = (TextView) findViewById(R.id.tv_E_totalmoney);
        tv_E_yue = (TextView) findViewById(R.id.tv_E_yue);
        tv_E_tixian = (TextView) findViewById(R.id.tv_E_tixian);
        tv_E_tixian.setOnClickListener(this);
        tv_woyaopingjiachetie = (RelativeLayout) findViewById(R.id.tv_woyaopingjiachetie);
        tv_woyaopingjiachetie.setOnClickListener(this);
        tv_E_jinrishouyi = (TextView) findViewById(R.id.tv_E_jinrishouyi);
        tv_E_shengyutianshu = (TextView) findViewById(R.id.tv_E_shengyutianshu);
        btn_E_paizhao = (Button) findViewById(R.id.btn_E_paizhao);
        btn_E_paizhao.setOnClickListener(this);
        rela_chetiedaiyan = (LinearLayout) findViewById(R.id.rela_chetiedaiyan);
        rela_chetiedaiyan.setOnClickListener(this);
        rela_gengduopingjia = (RelativeLayout) findViewById(R.id.rela_gengduopingjia);
        rela_gengduopingjia.setOnClickListener(this);
        lv_chetiepingjia = (ListView) findViewById(R.id.lv_chetiepingjia);
        lv_chetiepingjia.setFocusable(false);
        gv_calendar = (GridView) findViewById(R.id.gv_calendar);

        GetAdd();
    }


    @Override
    public void onClick(View v) {
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            goToHome();
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, CheTieListActivity.class));
        } else if (R.id.tv_E_tixian == id) {
            startActivity(new Intent(this, YueActivity.class));
        } else if (R.id.btn_E_paizhao == id) {
            MobclickAgent.onEvent(context, "ShenQingXiaYiLun");
            ShenQingXiaYiLun();
        } else if (R.id.rela_chetiedaiyan == id) {

        } else if (R.id.rela_gengduopingjia == id) {

        } else if (R.id.tv_woyaopingjiachetie == id) {

        }
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
                    LogUtil.E("dddddddd:" + t.calendars.get(Integer.parseInt(t.index_x)).week.get(Integer.parseInt(t.index_y)).getDate());
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        KaKuApplication.flag_recommended = t.advert.getFlag_recommended();
                        KaKuApplication.flag_jiashinum = t.advert.getNum_privilege();
                        KaKuApplication.flag_position = t.advert.getFlag_position();
                        KaKuApplication.flag_heart = t.advert.getFlag_show();
                        SetText(t);
                    } else {
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

    public void SetText(final GetAddResp t) {

        index_x = Integer.parseInt(t.index_x);
        iv_E_riliyou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                index_x++;
                LogUtil.E("date:" + t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate());
                String yue = t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate().substring(5, 7);
                tv_E_yue.setText(yue + "月");

                int index_y = Integer.parseInt(t.index_y);
                list_calendar = t.calendars.get(index_x).week;
                CalendarAdapter adapter_calendar = new CalendarAdapter(Add_EActivity.this, list_calendar, index_y);
                gv_calendar.setAdapter(adapter_calendar);
            }
        });

        iv_E_rilizuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                --index_x;
                LogUtil.E("date:" + t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate());
                String yue = t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate().substring(5, 7);
                tv_E_yue.setText(yue + "月");

                int index_y = Integer.parseInt(t.index_y);
                list_calendar = t.calendars.get(index_x).week;
                CalendarAdapter adapter_calendar = new CalendarAdapter(Add_EActivity.this, list_calendar, index_y);
                gv_calendar.setAdapter(adapter_calendar);
            }
        });

        String yue = t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate().substring(5, 7);
        tv_E_yue.setText(yue + "月");
        index_x = Integer.parseInt(t.index_x);
        int index_y = Integer.parseInt(t.index_y);
        list_calendar = t.calendars.get(index_x).week;
        CalendarAdapter adapter_calendar = new CalendarAdapter(Add_EActivity.this, list_calendar, index_y);
        gv_calendar.setAdapter(adapter_calendar);

        tv_E_totalmoney.setText("¥ " + t.advert.getTotal_earnings());
        tv_E_zhangfu.setText("x " + t.advert.getDay_growth());

        String day = t.advert.getDay_earnings() + " 天";
        SpannableStringBuilder style_day = new SpannableStringBuilder(day);
        style_day.setSpan(new AbsoluteSizeSpan(22, true), 0, day.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_E_shengyutianshu.setText(style_day);

        String jinri = t.advert.getDay_earnings() + " 元";
        SpannableStringBuilder style_jinri = new SpannableStringBuilder(jinri);
        style_jinri.setSpan(new AbsoluteSizeSpan(22, true), 0, jinri.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_E_jinrishouyi.setText(style_jinri);

        String shouyi = t.advert.getNow_earnings() + " 元";
        SpannableStringBuilder style_shouyi = new SpannableStringBuilder(shouyi);
        style_shouyi.setSpan(new AbsoluteSizeSpan(36, true), 0, shouyi.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_E_shouyi.setText(style_shouyi);

        CheTiePingJiaAdapter adapter = new CheTiePingJiaAdapter(Add_EActivity.this, t.evals);
        lv_chetiepingjia.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(lv_chetiepingjia);

        float degree = Float.parseFloat(t.advert.getNow_earnings()) / Float.parseFloat(t.advert.getTotal_earnings());
        RotateAnimation animation = new RotateAnimation(0f, degree * 120f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        iv_E_zhizhen.setAnimation(animation);

    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        startActivity(intent);
        finish();
    }

    public void ShenQingXiaYiLun() {
        if ("A".equals(KaKuApplication.flag_recommended) || "B".equals(KaKuApplication.flag_recommended)) {
            LogUtil.showShortToast(Add_EActivity.this, "今日拍照已生效，无需重复拍照");
        } else {
            KaKuApplication.flag_nochetietv = "wu";
            KaKuApplication.flag_code = "60018";
            Intent intent = new Intent(context, AdImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

}
