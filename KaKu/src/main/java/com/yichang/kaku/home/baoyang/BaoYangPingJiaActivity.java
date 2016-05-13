package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.BaoYangPingJiaReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.OilPingJiaWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class BaoYangPingJiaActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_baoyangpingjia_name, tv_baoyangpingjia_addr;
    private RatingBar star_baoyangpingjia_pingjia;
    private EditText et_baoyangpingjia;
    private Button btn_baoyangpingjia;
    private String phone;
    private Boolean isPwdPopWindowShow = false;
    private ImageView iv_baoyangpingjia_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangpingjia);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("待评价");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tv_baoyangpingjia_name = (TextView) findViewById(R.id.tv_baoyangpingjia_addrname);
        tv_baoyangpingjia_addr = (TextView) findViewById(R.id.tv_baoyangpingjia_addr);
        iv_baoyangpingjia_call = (ImageView) findViewById(R.id.iv_baoyangpingjia_call);
        iv_baoyangpingjia_call.setOnClickListener(this);
        et_baoyangpingjia = (EditText) findViewById(R.id.et_baoyangpingjia);
        star_baoyangpingjia_pingjia = (RatingBar) findViewById(R.id.star_baoyangpingjia_pingjia);
        btn_baoyangpingjia = (Button) findViewById(R.id.btn_baoyangpingjia);
        btn_baoyangpingjia.setOnClickListener(this);
        tv_baoyangpingjia_name.setText(bundle.getString("name_addr"));
        tv_baoyangpingjia_addr.setText(bundle.getString("addr"));
        phone = bundle.getString("phone_addr");
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
        } else if (R.id.iv_baoyangpingjia_call == id) {
            Utils.Call(this, phone);
        } else if (R.id.btn_baoyangpingjia == id) {
            PingJia();
        }
    }

    public void PingJia() {
        showProgressDialog();
        BaoYangPingJiaReq req = new BaoYangPingJiaReq();
        req.code = "400123";
        req.id_upkeep_bill = KaKuApplication.id_upkeep_bill;
        req.content_eval = et_baoyangpingjia.getText().toString().trim();
        req.star_eval = String.valueOf(star_baoyangpingjia_pingjia.getRating());
        KaKuApiProvider.BaoYangPingJiaSubmit(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("pingjiayellowoil res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        showOilPingJiaPupWindow();
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

    private void showOilPingJiaPupWindow() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;

                OilPingJiaWindow popWindow =
                        new OilPingJiaWindow(BaoYangPingJiaActivity.this);
                popWindow.show();

            }
        }, 0);
    }
}
