package com.yichang.kaku.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.member.driver.PointRulesActivity;
import com.yichang.kaku.obj.RecommendedsObj;
import com.yichang.kaku.request.JiangLiMingXiReq;
import com.yichang.kaku.response.JiangLiMingXiResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class JiangLiMingXiActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView xListView;
    private List<RecommendedsObj> jiangli_list = new ArrayList<RecommendedsObj>();
    private JiangLiMingXiAdapter adapter;
    //private TextView tv_jianglimingxi_zongji;

    private ImageView iv_jlgz;
    //准现金
    private TextView tv_prize_quasi_cash;
    //奖励积分
    private TextView tv_prize_point;
    //奖励现金
    private TextView tv_prize_cash;


   /* private final static int STEP = 5;
    private int start = 0, pageindex = 0, pagesize = STEP;
    private final static int INDEX = 5;// 一屏显示的个数
    private boolean isShowProgress = false;*/
    /*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianglimingxi);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("奖励明细");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(this);
        right.setText("我的余额");
        xListView = (ListView) findViewById(R.id.lv_jianglimingxi);
        //tv_jianglimingxi_zongji= (TextView) findViewById(R.id.tv_jianglimingxi_zongji);

        iv_jlgz = (ImageView) findViewById(R.id.iv_jlgz);
        iv_jlgz.setOnClickListener(this);
        initNoDataLayout();

        tv_prize_quasi_cash= (TextView) findViewById(R.id.tv_prize_quasi_cash);
        tv_prize_point= (TextView) findViewById(R.id.tv_prize_point);
        tv_prize_cash= (TextView) findViewById(R.id.tv_prize_cash);

        GetInfo();
        //setPullState(false);
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
        } else if (R.id.tv_right == id) {
            startActivity(new Intent(this, YueActivity.class));
        } else if (R.id.btn_refresh == id) {
            /*setPullState(false);*/
            GetInfo();
        } else if (R.id.iv_jlgz == id) {
            startActivity(new Intent(this, PointRulesActivity.class));
        }
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("暂无积分奖励");
        tv_advice = (TextView) findViewById(R.id.tv_advice);

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);


    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    public void GetInfo() {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);
            return;
        } else {
            setNoDataLayoutState(ll_container);
        }
        JiangLiMingXiReq req = new JiangLiMingXiReq();
        req.code = "10033";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.JiangLiMingXi(req, new KakuResponseListener<JiangLiMingXiResp>(this, JiangLiMingXiResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("jainglimingxi res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t.recommendeds);

                        //准现金
                        tv_prize_quasi_cash.setText("￥" + t.money_earnings);
                        //奖励积分
                        tv_prize_point.setText(t.points);
                        //奖励现金
                        tv_prize_cash.setText("￥" + t.money_balance);
//                        String string = "合计   " + t.points;
//                        SpannableStringBuilder style = new SpannableStringBuilder(string);
//                        style.setSpan(new ForegroundColorSpan(Color.rgb(0, 188, 135)), 4, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        //tv_jianglimingxi_zongji.setText(style);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    private void setData(List<RecommendedsObj> list) {
        // TODO Auto-generated method stub
        if (list != null) {
            jiangli_list.addAll(list);
        }
        if (jiangli_list.size() == 0) {

            setNoDataLayoutState(layout_data_none);
            return;
        } else {

            setNoDataLayoutState(ll_container);
        }

        JiangLiMingXiAdapter adapter = new JiangLiMingXiAdapter(JiangLiMingXiActivity.this, jiangli_list);
        xListView.setAdapter(adapter);
    }
}
