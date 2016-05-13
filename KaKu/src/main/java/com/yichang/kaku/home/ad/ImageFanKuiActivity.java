package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.ImageFanKuiResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.ImageFanKuiWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class ImageFanKuiActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_fankui_zuo, iv_fankui_you, iv_imagefankui;
    private TextView tv_imagefankui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagefankui);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("提交成功");
        tv_imagefankui = (TextView) findViewById(R.id.tv_imagefankui);
        iv_fankui_zuo = (ImageView) findViewById(R.id.iv_fankui_zuo);
        iv_fankui_zuo.setOnClickListener(this);
        iv_fankui_you = (ImageView) findViewById(R.id.iv_fankui_you);
        iv_fankui_you.setOnClickListener(this);
        iv_imagefankui = (ImageView) findViewById(R.id.iv_imagefankui);
        iv_imagefankui.setOnClickListener(this);

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
        } else if (R.id.iv_fankui_zuo == id) {
            Utils.GetAdType();
        } else if (R.id.iv_fankui_you == id) {
            startActivity(new Intent(this, ShopMallActivity.class));
        } else if (R.id.iv_imagefankui == id) {
            GetHongBao();
        }
    }

    public void GetHongBao() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "60024";
        KaKuApiProvider.GetFanKui(req, new KakuResponseListener<ImageFanKuiResp>(this, ImageFanKuiResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getfankui res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        ShowPop(t.flag_get, t.money_coupon);
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

    private void ShowPop(final String flag_get, final String money_coupon) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageFanKuiWindow popWindow = new ImageFanKuiWindow(ImageFanKuiActivity.this, flag_get, money_coupon);
                popWindow.show();
                iv_imagefankui.setVisibility(View.GONE);
                tv_imagefankui.setVisibility(View.GONE);
            }
        }, 0);
    }


    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME3);
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

}
