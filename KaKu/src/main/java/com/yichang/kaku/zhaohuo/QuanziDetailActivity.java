package com.yichang.kaku.zhaohuo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.request.QuanziDetailReq;
import com.yichang.kaku.request.QuanziHuifuReq;
import com.yichang.kaku.response.ChouJiangResp;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class QuanziDetailActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wb_quanzidetail;
    private EditText et_quanzi_huifu;
    private TextView tv_quanzi_huifu;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanzidetail);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("圈子详情");
        KaKuApplication.quanzitype = "right";
        et_quanzi_huifu = (EditText) findViewById(R.id.et_quanzi_huifu);
        tv_quanzi_huifu = (TextView) findViewById(R.id.tv_quanzi_huifu);
        tv_quanzi_huifu.setOnClickListener(this);
        wb_quanzidetail = (WebView) findViewById(R.id.wb_quanzidetail);
        wb_quanzidetail.getSettings().setDefaultTextEncodingName("utf-8");
        wb_quanzidetail.getSettings().setSupportZoom(true);
        wb_quanzidetail.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wb_quanzidetail.getSettings();
        settings.setJavaScriptEnabled(true);
        wb_quanzidetail.post(new Runnable() {
            @Override
            public void run() {
                wb_quanzidetail.loadUrl("javascript:display_alert()");
            }
        });
        wb_quanzidetail.setWebChromeClient(new WebChromeClient());
        wb_quanzidetail.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wb_quanzidetail.addJavascriptInterface(this, "androidObj");

        getDetail();
    }


    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        } else if (R.id.tv_quanzi_huifu == id) {
            HuiFu();
        }
    }

    public void getDetail() {
        showProgressDialog();
        QuanziDetailReq req = new QuanziDetailReq();
        req.id_circle = KaKuApplication.id_circle;
        req.code = "70012";
        KaKuApiProvider.getQuanziDetail(req, new KakuResponseListener<ChouJiangResp>(context, ChouJiangResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("quanzidetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wb_quanzidetail.loadUrl(t.url);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
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

    public void HuiFu() {
        showProgressDialog();
        QuanziHuifuReq req = new QuanziHuifuReq();
        req.code = "70015";
        req.id_circle = KaKuApplication.id_circle;
        req.content_comment = et_quanzi_huifu.getText().toString().trim();
        KaKuApiProvider.QuanziHuifu(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("QuanziHuifu res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
