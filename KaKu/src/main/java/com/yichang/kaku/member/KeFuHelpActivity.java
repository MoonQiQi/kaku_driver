package com.yichang.kaku.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.settings.MemberSettingsCommentActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.ChouJiangResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class KeFuHelpActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wb_kefuhelp;
    private ImageView iv_kefuhelp1, iv_kefuhelp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefuhelp);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("客服中心");
        iv_kefuhelp1 = (ImageView) findViewById(R.id.iv_kefuhelp1);
        iv_kefuhelp1.setOnClickListener(this);
        iv_kefuhelp2 = (ImageView) findViewById(R.id.iv_kefuhelp2);
        iv_kefuhelp2.setOnClickListener(this);
        wb_kefuhelp = (WebView) findViewById(R.id.wb_kefuhelp);
        wb_kefuhelp.getSettings().setDefaultTextEncodingName("utf-8");
        wb_kefuhelp.getSettings().setSupportZoom(true);
        wb_kefuhelp.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wb_kefuhelp.getSettings();
        settings.setJavaScriptEnabled(true);
        wb_kefuhelp.setWebChromeClient(new WebChromeClient());
        wb_kefuhelp.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wb_kefuhelp.addJavascriptInterface(this, "androidObj");

        GetUrl();
    }

    public void GetUrl() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "10034";
        KaKuApiProvider.KeFuHelp(req, new KakuResponseListener<ChouJiangResp>(this, ChouJiangResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("kefuhelp res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wb_kefuhelp.loadUrl(t.url);
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

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.iv_kefuhelp1 == id) {
            Utils.Call(this, "400-6867585");
        } else if (R.id.iv_kefuhelp2 == id) {
            startActivity(new Intent(this, MemberSettingsCommentActivity.class));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb_kefuhelp.canGoBack()) {
            wb_kefuhelp.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
