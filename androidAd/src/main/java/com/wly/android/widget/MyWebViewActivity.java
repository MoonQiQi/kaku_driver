package com.wly.android.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MyWebViewActivity extends Activity implements OnClickListener {

    private WebView wv;
    private TextView left, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbodetail);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("活动详情");
        wv = (WebView) findViewById(R.id.wv_lunbo);
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
        String url = getIntent().getStringExtra("url");

        wv.addJavascriptInterface(this, "androidObj");

        wv.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        }
    }
}
