package com.yichang.kaku.home.qiandao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.home.choujiang.ChouJiangActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.request.QianDaoTiXingReq;
import com.yichang.kaku.response.DailySignResp;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class DailySignActivity extends BaseActivity implements OnClickListener {

    private TextView left, title, right;
    private TextView tv_qiandao_day, tv_qiandao_lianxutianshu, tv_qiandao_content, tv_qiandao_wodejifen;
    private ImageView iv_qiandao_duihuan, tv_qiandao_left, tv_qiandao_right, tv_qiandao_tixing;
    private TextView tv_qiandao_5, tv_qiandao_10, tv_qiandao_20, tv_qiandao_30;
    private TextView iv_qiandao_quan1, iv_qiandao_quan2, iv_qiandao_quan3, iv_qiandao_quan4, iv_qiandao_quan5,
            iv_qiandao_quan6, iv_qiandao_quan7, iv_qiandao_quan8, iv_qiandao_quan9, iv_qiandao_quan10,
            iv_qiandao_quan11, iv_qiandao_quan12, iv_qiandao_quan13, iv_qiandao_quan14, iv_qiandao_quan15,
            iv_qiandao_quan16, iv_qiandao_quan17, iv_qiandao_quan18, iv_qiandao_quan19, iv_qiandao_quan20,
            iv_qiandao_quan21, iv_qiandao_quan22, iv_qiandao_quan23, iv_qiandao_quan24, iv_qiandao_quan25,
            iv_qiandao_quan26, iv_qiandao_quan27, iv_qiandao_quan28, iv_qiandao_quan29, iv_qiandao_quan30;
    private List<TextView> textViewList = new ArrayList<>();
    private String flag_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sign);
        init();
    }


    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("每日签到");
        tv_qiandao_day = (TextView) findViewById(R.id.tv_qiandao_day);
        tv_qiandao_lianxutianshu = (TextView) findViewById(R.id.tv_qiandao_lianxutianshu);
        tv_qiandao_content = (TextView) findViewById(R.id.tv_qiandao_content);
        tv_qiandao_wodejifen = (TextView) findViewById(R.id.tv_qiandao_wodejifen);
        iv_qiandao_duihuan = (ImageView) findViewById(R.id.iv_qiandao_duihuan);
        iv_qiandao_duihuan.setOnClickListener(this);
        tv_qiandao_left = (ImageView) findViewById(R.id.tv_qiandao_left);
        tv_qiandao_left.setOnClickListener(this);
        tv_qiandao_right = (ImageView) findViewById(R.id.tv_qiandao_right);
        tv_qiandao_right.setOnClickListener(this);
        tv_qiandao_5 = (TextView) findViewById(R.id.tv_qiandao_5);
        tv_qiandao_10 = (TextView) findViewById(R.id.tv_qiandao_10);
        tv_qiandao_20 = (TextView) findViewById(R.id.tv_qiandao_20);
        tv_qiandao_30 = (TextView) findViewById(R.id.tv_qiandao_30);
        tv_qiandao_tixing = (ImageView) findViewById(R.id.tv_qiandao_tixing);
        tv_qiandao_tixing.setOnClickListener(this);
        findDay();
        getQianDao();
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.iv_qiandao_duihuan == id) {
            startActivity(new Intent(this, ShopMallActivity.class));
        } else if (R.id.tv_qiandao_left == id) {
            startActivity(new Intent(this, ChouJiangActivity.class));
        } else if (R.id.tv_qiandao_right == id) {
            startActivity(new Intent(this, GongZhongHaoActivity.class));
        } else if (R.id.tv_qiandao_tixing == id) {
            if ("Y".equals(flag_sign)) {
                flag_sign = "N";
                tv_qiandao_tixing.setImageResource(R.drawable.toggle_off);
            } else {
                flag_sign = "Y";
                tv_qiandao_tixing.setImageResource(R.drawable.toggle_on);
            }
            TiXing();
        }
    }

    public void getQianDao() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "8002";
        KaKuApiProvider.getDailySignInfo(req, new KakuResponseListener<DailySignResp>(context, DailySignResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("qiandao res: " + t.res);
                    if (Constants.RES_ONE.equals(t.res)) {
                        showPopWindow(t.point);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                    SetText(t);
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }
        });
    }

    private void showPopWindow(String point) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.popwindow_daily_sign, null, false);
        final PopupWindow popWindow = new PopupWindow(vPopWindow, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);

        ImageView iv_qiandao_cha = (ImageView) vPopWindow.findViewById(R.id.iv_qiandao_cha);
        RelativeLayout rela_qiandao_pop = (RelativeLayout) vPopWindow.findViewById(R.id.rela_qiandao_pop);
        ImageView iv_qiandao_qiankuang = (ImageView) vPopWindow.findViewById(R.id.iv_qiandao_qiankuang);
        TextView tv_qiandao_poppoint = (TextView) vPopWindow.findViewById(R.id.tv_qiandao_poppoint);
        tv_qiandao_poppoint.setText("+ " + point + "积分");
        rela_qiandao_pop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

        iv_qiandao_cha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        iv_qiandao_qiankuang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailySignActivity.this, ChouJiangActivity.class));
                popWindow.dismiss();
            }
        });


        popWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    public void SetText(DailySignResp t) {
        LogUtil.E("day_qiandao:" + t.day_sign);
        int day_continue = Integer.parseInt(t.day_sign);
        if (day_continue < 10) {
            tv_qiandao_day.setText("0" + t.day_sign);
        } else {
            tv_qiandao_day.setText(t.day_sign);
        }

        String strings = "我的积分 " + t.point_now;
        SpannableStringBuilder styles = new SpannableStringBuilder(strings);
        styles.setSpan(new ForegroundColorSpan(Color.rgb(225, 0, 0)), 4, strings.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_qiandao_wodejifen.setText(styles);

        tv_qiandao_5.setText("+" + t.point_sign_five);
        tv_qiandao_10.setText("+" + t.point_sign_ten);
        tv_qiandao_20.setText("+" + t.point_sign_twenty);
        tv_qiandao_30.setText("+" + t.point_sign_thirty);
        flag_sign = t.flag_sign;

        if ("Y".equals(t.flag_sign)) {
            flag_sign = "Y";
            tv_qiandao_tixing.setImageResource(R.drawable.toggle_on);
        } else {
            flag_sign = "N";
            tv_qiandao_tixing.setImageResource(R.drawable.toggle_off);
        }

        int day_num = Integer.parseInt(t.day_sign);
        if (day_num == 1) {
            textViewList.get(0).setBackgroundResource(R.drawable.qiandao_heiyouhui);
        } else {
            for (int i = 0; i < day_num; i++) {
                if (i == 30) {
                    textViewList.get(i).setBackgroundResource(R.drawable.qiandao_zuohei);
                } else {
                    textViewList.get(i).setBackgroundResource(R.drawable.qiandao_heihei);
                }
            }
            textViewList.get(day_num).setBackgroundResource(R.drawable.qiandao_heihui);
            textViewList.get(0).setBackgroundResource(R.drawable.qiandao_youhei);
        }
    }

    public void TiXing() {
        showProgressDialog();
        QianDaoTiXingReq req = new QianDaoTiXingReq();
        req.flag_sign = flag_sign;
        req.code = "8008";
        KaKuApiProvider.qiandaotixing(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("qiandaotixing res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }
        });
    }

    public void findDay() {
        iv_qiandao_quan1 = (TextView) findViewById(R.id.iv_qiandao_quan1);
        iv_qiandao_quan2 = (TextView) findViewById(R.id.iv_qiandao_quan2);
        iv_qiandao_quan3 = (TextView) findViewById(R.id.iv_qiandao_quan3);
        iv_qiandao_quan4 = (TextView) findViewById(R.id.iv_qiandao_quan4);
        iv_qiandao_quan5 = (TextView) findViewById(R.id.iv_qiandao_quan5);
        iv_qiandao_quan6 = (TextView) findViewById(R.id.iv_qiandao_quan6);
        iv_qiandao_quan7 = (TextView) findViewById(R.id.iv_qiandao_quan7);
        iv_qiandao_quan8 = (TextView) findViewById(R.id.iv_qiandao_quan8);
        iv_qiandao_quan9 = (TextView) findViewById(R.id.iv_qiandao_quan9);
        iv_qiandao_quan10 = (TextView) findViewById(R.id.iv_qiandao_quan10);
        iv_qiandao_quan11 = (TextView) findViewById(R.id.iv_qiandao_quan11);
        iv_qiandao_quan12 = (TextView) findViewById(R.id.iv_qiandao_quan12);
        iv_qiandao_quan13 = (TextView) findViewById(R.id.iv_qiandao_quan13);
        iv_qiandao_quan14 = (TextView) findViewById(R.id.iv_qiandao_quan14);
        iv_qiandao_quan15 = (TextView) findViewById(R.id.iv_qiandao_quan15);
        iv_qiandao_quan16 = (TextView) findViewById(R.id.iv_qiandao_quan16);
        iv_qiandao_quan17 = (TextView) findViewById(R.id.iv_qiandao_quan17);
        iv_qiandao_quan18 = (TextView) findViewById(R.id.iv_qiandao_quan18);
        iv_qiandao_quan19 = (TextView) findViewById(R.id.iv_qiandao_quan19);
        iv_qiandao_quan20 = (TextView) findViewById(R.id.iv_qiandao_quan20);
        iv_qiandao_quan21 = (TextView) findViewById(R.id.iv_qiandao_quan21);
        iv_qiandao_quan22 = (TextView) findViewById(R.id.iv_qiandao_quan22);
        iv_qiandao_quan23 = (TextView) findViewById(R.id.iv_qiandao_quan23);
        iv_qiandao_quan24 = (TextView) findViewById(R.id.iv_qiandao_quan24);
        iv_qiandao_quan25 = (TextView) findViewById(R.id.iv_qiandao_quan25);
        iv_qiandao_quan26 = (TextView) findViewById(R.id.iv_qiandao_quan26);
        iv_qiandao_quan27 = (TextView) findViewById(R.id.iv_qiandao_quan27);
        iv_qiandao_quan28 = (TextView) findViewById(R.id.iv_qiandao_quan28);
        iv_qiandao_quan29 = (TextView) findViewById(R.id.iv_qiandao_quan29);
        iv_qiandao_quan30 = (TextView) findViewById(R.id.iv_qiandao_quan30);
        textViewList.add(iv_qiandao_quan1);
        textViewList.add(iv_qiandao_quan1);
        textViewList.add(iv_qiandao_quan2);
        textViewList.add(iv_qiandao_quan3);
        textViewList.add(iv_qiandao_quan4);
        textViewList.add(iv_qiandao_quan5);
        textViewList.add(iv_qiandao_quan6);
        textViewList.add(iv_qiandao_quan7);
        textViewList.add(iv_qiandao_quan8);
        textViewList.add(iv_qiandao_quan9);
        textViewList.add(iv_qiandao_quan10);
        textViewList.add(iv_qiandao_quan11);
        textViewList.add(iv_qiandao_quan12);
        textViewList.add(iv_qiandao_quan13);
        textViewList.add(iv_qiandao_quan14);
        textViewList.add(iv_qiandao_quan15);
        textViewList.add(iv_qiandao_quan16);
        textViewList.add(iv_qiandao_quan17);
        textViewList.add(iv_qiandao_quan18);
        textViewList.add(iv_qiandao_quan19);
        textViewList.add(iv_qiandao_quan20);
        textViewList.add(iv_qiandao_quan21);
        textViewList.add(iv_qiandao_quan22);
        textViewList.add(iv_qiandao_quan23);
        textViewList.add(iv_qiandao_quan24);
        textViewList.add(iv_qiandao_quan25);
        textViewList.add(iv_qiandao_quan26);
        textViewList.add(iv_qiandao_quan27);
        textViewList.add(iv_qiandao_quan28);
        textViewList.add(iv_qiandao_quan29);
        textViewList.add(iv_qiandao_quan30);
    }

}
