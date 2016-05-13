package com.yichang.kaku.home.ad;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.yichang.kaku.view.RiseNumberTextView;
import com.yichang.kaku.view.popwindow.YuEPopWindow;
import com.yichang.kaku.view.popwindow.YuYueWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class Add_IActivity extends BaseActivity implements OnClickListener {

    private TextView left, title;
    private TextView right;
    private ImageView iv_I_zhizhen, iv_I_state, iv_I_riliyou, iv_I_rilizuo, tv_I_tixian;
    private TextView tv_I_jinrishouyi, tv_I_shengyutianshu, tv_I_zhangfu, tv_I_totalmoney, tv_I_yue;
    private RiseNumberTextView tv_I_shouyi;
    private Button btn_I_paizhao;
    private RelativeLayout rela_gengduopingjia, tv_woyaopingjiachetie;
    private LinearLayout rela_chetiedaiyan;
    private GridView gv_calendar;
    private List<DayObj> list_calendar = new ArrayList<>();
    private int x1, x2;
    private int index_x;
    private Boolean isPwdPopWindowShow = false;
    private ImageView iv_Y_quanbushouyi, iv_Y_paizhaolishi,iv_Y_i;
    private String ketixian, buketixian;
    private ListView lv_Y_pingjia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_i);

    }

    @Override
    protected void onStart() {
        super.onStart();
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
        iv_Y_i = (ImageView) findViewById(R.id.iv_I_i);
        iv_Y_i.setOnClickListener(this);
        iv_I_zhizhen = (ImageView) findViewById(R.id.iv_I_zhizhen);
        iv_I_state = (ImageView) findViewById(R.id.iv_I_state);
        iv_I_riliyou = (ImageView) findViewById(R.id.iv_I_riliyou);
        iv_I_rilizuo = (ImageView) findViewById(R.id.iv_I_rilizuo);
        tv_I_shouyi = (RiseNumberTextView) findViewById(R.id.tv_I_shouyi);
        tv_I_zhangfu = (TextView) findViewById(R.id.tv_I_zhangfu);
        tv_I_totalmoney = (TextView) findViewById(R.id.tv_I_totalmoney);
        tv_I_yue = (TextView) findViewById(R.id.tv_I_yue);
        tv_I_tixian = (ImageView) findViewById(R.id.tv_I_tixian);
        tv_I_tixian.setOnClickListener(this);
        tv_woyaopingjiachetie = (RelativeLayout) findViewById(R.id.tv_woyaopingjiachetie);
        tv_woyaopingjiachetie.setOnClickListener(this);
        tv_I_jinrishouyi = (TextView) findViewById(R.id.tv_I_jinrishouyi);
        tv_I_shengyutianshu = (TextView) findViewById(R.id.tv_I_shengyutianshu);
        btn_I_paizhao = (Button) findViewById(R.id.btn_I_paizhao);
        btn_I_paizhao.setOnClickListener(this);
        rela_chetiedaiyan = (LinearLayout) findViewById(R.id.rela_chetiedaiyan);
        rela_chetiedaiyan.setOnClickListener(this);
        rela_gengduopingjia = (RelativeLayout) findViewById(R.id.rela_gengduopingjia);
        rela_gengduopingjia.setOnClickListener(this);
        gv_calendar = (GridView) findViewById(R.id.gv_calendar);
        iv_Y_quanbushouyi = (ImageView) findViewById(R.id.iv_I_quanbushouyi);
        iv_Y_quanbushouyi.setOnClickListener(this);
        iv_Y_paizhaolishi = (ImageView) findViewById(R.id.iv_I_paizhaolishi);
        iv_Y_paizhaolishi.setOnClickListener(this);
        lv_Y_pingjia= (ListView) findViewById(R.id.lv_Y_pingjia);

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
            MobclickAgent.onEvent(context, "CheTieList");
            startActivity(new Intent(this, CheTieListActivity.class));
        } else if (R.id.tv_I_tixian == id) {
            MobclickAgent.onEvent(context, "TiXian");
            startActivity(new Intent(this, YueActivity.class));
        } else if (R.id.btn_I_paizhao == id) {
            startActivity(new Intent(this, ImageHistoryActivity.class));
        } else if (R.id.rela_chetiedaiyan == id) {
            MobclickAgent.onEvent(context, "CheTieDaiLi");
            CheTieDaiLi();
        } else if (R.id.rela_gengduopingjia == id) {
            MobclickAgent.onEvent(context, "CheTiePingjiaList");
            AdPingJiaList();
        } else if (R.id.tv_woyaopingjiachetie == id) {
            MobclickAgent.onEvent(context, "CheTieWoYaoPingJia");
            PingJiaAd();
        } else if (R.id.iv_I_quanbushouyi == id) {
            MobclickAgent.onEvent(context, "QuanBuShouYi");
            Intent intent = new Intent(this, CalendarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (R.id.iv_I_paizhaolishi == id) {
            MobclickAgent.onEvent(context, "PaiZhaoLiShi");
            Intent intent = new Intent(this, ImageHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (R.id.iv_I_i == id) {
            ShowTixianPop();
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
                    LogUtil.E("day:" + t.calendars.get(Integer.parseInt(t.index_x)).week.get(Integer.parseInt(t.index_y)).getDate());
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        KaKuApplication.flag_recommended = t.advert.getFlag_recommended();
                        KaKuApplication.flag_jiashinum = t.advert.getNum_privilege();
                        KaKuApplication.flag_position = t.advert.getFlag_position();
                        KaKuApplication.flag_heart = t.advert.getFlag_show();
                        KaKuApplication.code_my = t.advert.getCode_recommended();
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
        iv_I_riliyou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index_x == t.calendars.size() - 1) {
                    return;
                }
                index_x++;
                LogUtil.E("date:" + t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate());
                String yue = t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate().substring(5, 7);
                tv_I_yue.setText(yue + "月");

                int index_y = Integer.parseInt(t.index_y);
                list_calendar = t.calendars.get(index_x).week;
                CalendarAdapter adapter_calendar = new CalendarAdapter(Add_IActivity.this, list_calendar, index_y);
                gv_calendar.setAdapter(adapter_calendar);
            }
        });

        iv_I_rilizuo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index_x == 0) {
                    return;
                }
                --index_x;
                LogUtil.E("date:" + t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate());
                String yue = t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate().substring(5, 7);
                tv_I_yue.setText(yue + "月");

                int index_y = Integer.parseInt(t.index_y);
                list_calendar = t.calendars.get(index_x).week;
                CalendarAdapter adapter_calendar = new CalendarAdapter(Add_IActivity.this, list_calendar, index_y);
                gv_calendar.setAdapter(adapter_calendar);
            }
        });


        ketixian = t.advert.getNow_earnings_0();
        buketixian = t.advert.getNow_earnings_1();

        String yue = t.calendars.get(index_x).week.get(Integer.parseInt(t.index_y)).getDate().substring(5, 7);
        tv_I_yue.setText(yue + "月");
        index_x = Integer.parseInt(t.index_x);
        int index_y = Integer.parseInt(t.index_y);
        list_calendar = t.calendars.get(index_x).week;
        CalendarAdapter adapter_calendar = new CalendarAdapter(Add_IActivity.this, list_calendar, index_y);
        gv_calendar.setAdapter(adapter_calendar);

        if ("Y".equals(t.advert.getFlag_advert())) {
            rela_chetiedaiyan.setVisibility(View.VISIBLE);
        } else {
            rela_chetiedaiyan.setVisibility(View.GONE);
        }
        if ("1".equals(t.advert.getFlag_agent())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("恭喜您获得车贴代理资格，每邀请好友奖励30元哦~");
            builder.setNegativeButton("去看看", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    CheTieDaiLi();
                }
            });

            builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else if ("2".equals(t.advert.getFlag_agent())) {
            showYuYueWindow();
        }

        if (t.advert.getNow_earnings().contains(".")) {
            tv_I_shouyi.withNumber(Float.parseFloat(t.advert.getNow_earnings())).start();
        } else {
            tv_I_shouyi.withNumber(Integer.parseInt(t.advert.getNow_earnings())).start();
        }
        title.setText(t.advert.getName_advert());
        tv_I_totalmoney.setText("¥ " + t.advert.getEarnings_total());
        tv_I_zhangfu.setText(t.advert.getDay_growth());

        String day = t.advert.getDay_remaining() + " 天";
        SpannableStringBuilder style_day = new SpannableStringBuilder(day);
        style_day.setSpan(new AbsoluteSizeSpan(22, true), 0, day.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_I_shengyutianshu.setText(style_day);

        String jinri = t.advert.getDay_earnings() + " 元";
        SpannableStringBuilder style_jinri = new SpannableStringBuilder(jinri);
        style_jinri.setSpan(new AbsoluteSizeSpan(22, true), 0, jinri.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_I_jinrishouyi.setText(style_jinri);

        float degree = Float.parseFloat(t.advert.getNow_earnings()) / Float.parseFloat(t.advert.getTotal_earnings());
        RotateAnimation animation = new RotateAnimation(0f, degree * 120f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        iv_I_zhizhen.setAnimation(animation);

        CheTiePingJiaAdapter adapter = new CheTiePingJiaAdapter(context, t.evals);
        lv_Y_pingjia.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(lv_Y_pingjia);

    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        startActivity(intent);
        finish();
    }

    private void PingJiaAd() {
        startActivity(new Intent(this, PingJiaAdActivity.class));
    }

    private void AdPingJiaList() {
        startActivity(new Intent(this, AdPingJiaListActivity.class));
    }

    private void CheTieDaiLi() {
        startActivity(new Intent(this, CheTieDaiLiActivity.class));
    }

    private void showYuYueWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                YuYueWindow popWindow = new YuYueWindow(Add_IActivity.this);
                popWindow.show();

            }
        }, 0);
    }

    private void ShowTixianPop() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                YuEPopWindow popWindow =
                        new YuEPopWindow(Add_IActivity.this, ketixian, buketixian);
                popWindow.show();

            }
        }, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

}
