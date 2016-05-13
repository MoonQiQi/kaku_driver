package com.yichang.kaku.home.choujiang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.settings.MemberSettingsCommentActivity;
import com.yichang.kaku.request.ChouJiangReq;
import com.yichang.kaku.response.ChouJiangResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class ChouJiangActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choujiang);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("幸运大转盘");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("我的奖品");
        right.setOnClickListener(this);
        wv = (WebView) findViewById(R.id.wv_choujiang);
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv.addJavascriptInterface(this, "androidObj");

        ChouJiang();
    }

    @android.webkit.JavascriptInterface
    public void toCommentActivity(String msg) {
        startActivity(new Intent(this, MemberSettingsCommentActivity.class));
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
            MyPrizeActivity.gotoMyPrize();
        }
    }

    public void ChouJiang() {
        Utils.NoNet(context);
        showProgressDialog();
        ChouJiangReq req = new ChouJiangReq();
        req.code = "700241";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.ChouJiang(req, new KakuResponseListener<ChouJiangResp>(this, ChouJiangResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("choujiang res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wv.loadUrl(t.url);
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

}
