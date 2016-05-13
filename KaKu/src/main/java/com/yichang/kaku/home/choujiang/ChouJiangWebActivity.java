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
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.member.settings.MemberSettingsCommentActivity;
import com.yichang.kaku.tools.Utils;

public class ChouJiangWebActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choujiangweb);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("活动详情");
        if (!Utils.isLogin()) {
            startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        wv = (WebView) findViewById(R.id.wv_choujiangweb);
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
        String url = "http://www.99kaku.com/public/index.php/appweb/wheels_app?id_driver=" + Utils.getIdDriver() + "&id_wheel=3";
        wv.loadUrl(url);
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
        }
    }

}
