package com.yichang.kaku.money;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseFragment;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.home.choujiang.ChouJiangActivity;
import com.yichang.kaku.home.qiandao.DailySignActivity;
import com.yichang.kaku.obj.MoneyRankObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.MoneyResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Home3Fragment extends BaseFragment implements OnClickListener {

    private Activity mActivity;
    private TextView tv_money_money, tv_money_point, tv_money_paihang;
    private ImageView iv_money1, iv_money2, iv_money3, iv_money4;
    private RelativeLayout rela_money_rank;
    private List<MoneyRankObj> list = new ArrayList<>();
    private String content, url, title_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_money, container, false);
        init(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMoneyInfo();
    }

    private void init(View view) {
        tv_money_money = (TextView) view.findViewById(R.id.tv_money_money);
        tv_money_point = (TextView) view.findViewById(R.id.tv_money_point);
        tv_money_paihang = (TextView) view.findViewById(R.id.tv_money_paihang);
        iv_money1 = (ImageView) view.findViewById(R.id.iv_money1);
        iv_money1.setOnClickListener(this);
        iv_money2 = (ImageView) view.findViewById(R.id.iv_money2);
        iv_money2.setOnClickListener(this);
        iv_money3 = (ImageView) view.findViewById(R.id.iv_money3);
        iv_money3.setOnClickListener(this);
        iv_money4 = (ImageView) view.findViewById(R.id.iv_money4);
        iv_money4.setOnClickListener(this);
        rela_money_rank = (RelativeLayout) view.findViewById(R.id.rela_money_rank);
        rela_money_rank.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(mActivity);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.iv_money1 == id) {
            MobclickAgent.onEvent(mActivity, "chetie");
            Utils.GetAdType();
        } else if (R.id.iv_money2 == id) {
            MobclickAgent.onEvent(mActivity, "yaoqinghaoyou");
            startActivity(new Intent(mActivity, TuiJianJiangLiActivity.class));
        } else if (R.id.iv_money3 == id) {
            MobclickAgent.onEvent(mActivity, "meirichoujiang");
            startActivity(new Intent(mActivity, ChouJiangActivity.class));
        } else if (R.id.iv_money4 == id) {
            MobclickAgent.onEvent(mActivity, "meiriqiandao");
            startActivity(new Intent(mActivity, DailySignActivity.class));
        } else if (R.id.rela_money_rank == id) {
            MobclickAgent.onEvent(mActivity, "paihang");
            Intent intent = new Intent(mActivity, MoneyRankActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("content", content);
            intent.putExtra("title_content", title_content);
            intent.putExtra("list", (Serializable) list);
            startActivity(intent);
        }
    }

    public void getMoneyInfo() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "10037";
        KaKuApiProvider.money(req, new KakuResponseListener<MoneyResp>(mActivity, MoneyResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("money res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(mActivity);
                        }
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void SetText(MoneyResp t) {
        tv_money_money.setText(t.driver.getMoney_income_total());
        tv_money_point.setText(t.driver.getPoint_his());
        content = t.content;
        url = t.url;
        title_content = t.title;
        list = t.drivers;
        String rank_string = "排行 " + t.driver.getRanking() + " 名";
        SpannableStringBuilder style_rank = new SpannableStringBuilder(rank_string);
        style_rank.setSpan(new ForegroundColorSpan(Color.rgb(255, 168, 01)), 2, rank_string.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_money_paihang.setText(style_rank);
    }

}
