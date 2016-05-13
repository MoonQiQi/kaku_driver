package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.CheTieZongJieReq;
import com.yichang.kaku.response.CheTieZongJieResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class ZongJieYuYueZhongActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_E_day;
    private Button btn_EZongJie_chetiedaili;
    private TextView tv_EZongJie_sijishouyi;
    private TextView tv_EZongJie_canyurenshu;
    private TextView tv_EZongJie_biaozhun;
    private TextView tv_EZongJie_rijun;
    private TextView tv_MZongJie_tian;
    private String flag_jump;
    private ImageView iv_YZongJie_image;
    private ImageView iv_YZongJie_shoutie0yuan;
    private TextView tv_YZongJie_tuijianhaoyou;
    private TextView tv_YZongJie_remark;
    private TextView tv_YZongJie_shouyiqixian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zongjieyuyuezhong);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("车贴详情");
        tv_E_day = (TextView) findViewById(R.id.tv_MZongJie_day);
        tv_EZongJie_sijishouyi = (TextView) findViewById(R.id.tv_MZongJie_sijishouyi);
        tv_EZongJie_canyurenshu = (TextView) findViewById(R.id.tv_MZongJie_canyurenshu);
        tv_EZongJie_biaozhun = (TextView) findViewById(R.id.tv_MZongJie_biaozhun);
        tv_EZongJie_rijun = (TextView) findViewById(R.id.tv_MZongJie_rijun);
        tv_MZongJie_tian = (TextView) findViewById(R.id.tv_MZongJie_tian);
        btn_EZongJie_chetiedaili = (Button) findViewById(R.id.btn_MZongJie_chetiedaili);
        btn_EZongJie_chetiedaili.setOnClickListener(this);
        iv_YZongJie_image = (ImageView) findViewById(R.id.iv_MZongJie_image);
        iv_YZongJie_shoutie0yuan = (ImageView) findViewById(R.id.iv_MZongJie_shoutie0yuan);
        tv_YZongJie_remark = (TextView) findViewById(R.id.tv_MZongJie_remark);
        tv_YZongJie_tuijianhaoyou = (TextView) findViewById(R.id.tv_MZongJie_tuijianhaoyou);
        tv_YZongJie_shouyiqixian = (TextView) findViewById(R.id.tv_MZongJie_shouyiqixian);
        getZongJie();
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
        } else if (R.id.btn_MZongJie_chetiedaili == id) {
            DaiLi();
        }
    }

    public void getZongJie() {
        showProgressDialog();
        CheTieZongJieReq req = new CheTieZongJieReq();
        req.code = "60010";
        req.id_advert = KaKuApplication.id_advert_daili;
        KaKuApiProvider.CheTieZongJie(req, new KakuResponseListener<CheTieZongJieResp>(this, CheTieZongJieResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("chetiezongjie res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
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

    public void SetText(CheTieZongJieResp t) {

        if ("1".equals(KaKuApplication.flag_advert_sign)) {
            KaKuApplication.flag_dory = "D";
            flag_jump = "Y";
            btn_EZongJie_chetiedaili.setVisibility(View.VISIBLE);
            btn_EZongJie_chetiedaili.setText("买车贴  做任务");
            tv_YZongJie_tuijianhaoyou.setVisibility(View.GONE);
        } else if ("2".equals(KaKuApplication.flag_advert_sign)) {
            KaKuApplication.flag_dory = "D";
            flag_jump = "N";
            btn_EZongJie_chetiedaili.setVisibility(View.VISIBLE);
            btn_EZongJie_chetiedaili.setText("代理买车贴");
            iv_YZongJie_shoutie0yuan.setImageResource(R.drawable.lingyuandaili);
            tv_YZongJie_tuijianhaoyou.setVisibility(View.VISIBLE);
        } else if ("3".equals(KaKuApplication.flag_advert_sign)) {
            KaKuApplication.flag_dory = "Y";
            flag_jump = "Y";
            btn_EZongJie_chetiedaili.setVisibility(View.VISIBLE);
            btn_EZongJie_chetiedaili.setText("预约买车贴");
            iv_YZongJie_shoutie0yuan.setImageResource(0);
            tv_YZongJie_tuijianhaoyou.setVisibility(View.GONE);
        } else if ("4".equals(KaKuApplication.flag_advert_sign)) {
            KaKuApplication.flag_dory = "D";
            btn_EZongJie_chetiedaili.setVisibility(View.GONE);
            iv_YZongJie_shoutie0yuan.setVisibility(View.GONE);
            tv_YZongJie_tuijianhaoyou.setVisibility(View.GONE);
        }


        KaKuApplication.code_my = t.advert.getCode_recommended();
        String day_remark = "任务于 " + t.advert.getDate_remark() + " " + t.advert.getDate_remark1();
        SpannableStringBuilder style_dayremark = new SpannableStringBuilder(day_remark);
        style_dayremark.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), 3, day_remark.length() - t.advert.getDate_remark1().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_E_day.setText(style_dayremark);

        String siji_shouyi = t.advert.getEarnings_total() + "元";
        SpannableStringBuilder style_sijishouyi = new SpannableStringBuilder(siji_shouyi);
        style_sijishouyi.setSpan(new AbsoluteSizeSpan(22, true), 0, siji_shouyi.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_EZongJie_sijishouyi.setText(style_sijishouyi);

        String month_shouyi = t.advert.getMonth_continue() + "个月";
        SpannableStringBuilder style_month = new SpannableStringBuilder(month_shouyi);
        style_month.setSpan(new AbsoluteSizeSpan(21, true), 0, month_shouyi.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_YZongJie_shouyiqixian.setText(style_month);

        String people_num = t.advert.getEarnings_average() + "元";
        SpannableStringBuilder style_numpeople = new SpannableStringBuilder(people_num);
        style_numpeople.setSpan(new AbsoluteSizeSpan(22, true), 0, people_num.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_EZongJie_canyurenshu.setText(style_numpeople);

        title.setText(t.advert.getName_advert());
        tv_YZongJie_remark.setText(t.advert.getRemark_advert());
        tv_EZongJie_biaozhun.setText("总任务数：" + t.advert.getOrder_ceiling() + "             参与人数：" + t.advert.getNum_driver() + "人");
        tv_EZongJie_rijun.setText("车贴售价：¥" + t.advert.getPrice_advert());

        BitmapUtil.getInstance(context).download(iv_YZongJie_image, KaKuApplication.qian_zhui + t.advert.getImage_advert());
    }

    public void DaiLi() {
        Intent intent = new Intent();
        if ("N".equals(flag_jump)) {
            intent.setClass(this, CheTieDaiLiActivity.class);
        } else {
            intent.setClass(this, StickerOrderActivity.class);
        }
        startActivity(intent);
    }

}
