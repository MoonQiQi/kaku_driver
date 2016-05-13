package com.yichang.kaku.home.baoyang;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.BaoYangQingDanResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class BaoYangQingDanActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView lv_baoyangqingdan;
    private TextView tv_qingdan_topnum, tv_qingdan_topprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangqingdan);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("服务配件清单");
        lv_baoyangqingdan = (ListView) findViewById(R.id.lv_baoyangqingdan);
        tv_qingdan_topnum = (TextView) findViewById(R.id.tv_qingdan_topnum);
        tv_qingdan_topprice = (TextView) findViewById(R.id.tv_qingdan_topprice);
        GetQingDan();
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
        }
    }

    public void GetQingDan() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "400125";
        KaKuApiProvider.BaoYangQingDan(req, new KakuResponseListener<BaoYangQingDanResp>(context, BaoYangQingDanResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getqingdan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        int sum = 0;
                        int num = 0;
                        for (int i = 0; i < t.shopcar.size(); i++) {
                            sum += Float.parseFloat(t.shopcar.get(i).getNum_item()) * Float.parseFloat(t.shopcar.get(i).getPrice_item());
                            num += Integer.parseInt(t.shopcar.get(i).getNum_item());
                        }
                        tv_qingdan_topprice.setText("¥ " + sum);
                        tv_qingdan_topnum.setText("共" + (num - 1) + "件商品，1个服务");
                        BaoYangQingDanAdapter adapter = new BaoYangQingDanAdapter(context, t.shopcar);
                        lv_baoyangqingdan.setAdapter(adapter);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
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


}
