package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.Worker2Obj;
import com.yichang.kaku.request.BaoYangPingJiaReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.CircularImage;
import com.yichang.kaku.view.popwindow.OilPingJiaWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class OilPingJiaActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private CircularImage iv_yellowoilpingjia_head;
    private TextView tv_yellowoilpingjia_name, tv_yellowoilpingjia_dan;
    private RatingBar star_yellowoilpingjia_star, star_baoyangpingjia_pingjia;
    private ImageView iv_yellowoilpingjia_call, iv_yellowoilpingjia_dahuangyou, iv_yellowoilpingjia_luntai, iv_yellowoilpingjia_baolun;
    private EditText et_baoyangpingjia;
    private Button btn_baoyangpingjia;
    private String phone;
    private Boolean isPwdPopWindowShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellowoilpingjia);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("待评价");
        iv_yellowoilpingjia_head = (CircularImage) findViewById(R.id.iv_yellowoilpingjia_head);
        tv_yellowoilpingjia_name = (TextView) findViewById(R.id.tv_yellowoilpingjia_name);
        tv_yellowoilpingjia_dan = (TextView) findViewById(R.id.tv_yellowoilpingjia_dan);
        star_yellowoilpingjia_star = (RatingBar) findViewById(R.id.star_yellowoilpingjia_star);
        star_baoyangpingjia_pingjia = (RatingBar) findViewById(R.id.star_baoyangpingjia_pingjia);
        iv_yellowoilpingjia_call = (ImageView) findViewById(R.id.iv_yellowoilpingjia_call);
        iv_yellowoilpingjia_call.setOnClickListener(this);
        et_baoyangpingjia = (EditText) findViewById(R.id.et_baoyangpingjia);
        btn_baoyangpingjia = (Button) findViewById(R.id.btn_baoyangpingjia);
        btn_baoyangpingjia.setOnClickListener(this);
        iv_yellowoilpingjia_dahuangyou = (ImageView) findViewById(R.id.iv_yellowoilpingjia_dahuangyou);
        iv_yellowoilpingjia_luntai = (ImageView) findViewById(R.id.iv_yellowoilpingjia_luntai);
        iv_yellowoilpingjia_baolun = (ImageView) findViewById(R.id.iv_yellowoilpingjia_baolun);

        Worker2Obj worker = KaKuApplication.worker;
        tv_yellowoilpingjia_name.setText(worker.getName_worker());
        tv_yellowoilpingjia_dan.setText(worker.getNum_bill() + "单");
        float star = Float.parseFloat(worker.getNum_star());
        star_yellowoilpingjia_star.setRating(star);
        phone = worker.getTel_worker();
        if ("Y".equals(worker.getFlag_butter())) {
            iv_yellowoilpingjia_dahuangyou.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoilpingjia_dahuangyou.setVisibility(View.GONE);
        }
        if ("Y".equals(worker.getFlag_wheel())) {
            iv_yellowoilpingjia_baolun.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoilpingjia_baolun.setVisibility(View.GONE);
        }
        if ("Y".equals(worker.getFlag_tire())) {
            iv_yellowoilpingjia_luntai.setVisibility(View.VISIBLE);
        } else {
            iv_yellowoilpingjia_luntai.setVisibility(View.GONE);
        }
        BitmapUtil.getInstance(context).download(iv_yellowoilpingjia_head, worker.getImage_worker());
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        } else if (R.id.iv_yellowoilpingjia_call == id) {
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
                        new OilPingJiaWindow(OilPingJiaActivity.this);
                popWindow.show();

            }
        }, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
