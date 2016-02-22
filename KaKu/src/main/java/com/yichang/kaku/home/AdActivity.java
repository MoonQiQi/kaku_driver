package com.yichang.kaku.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;

public class AdActivity extends BaseActivity implements OnClickListener {

    private WebView wv;
    private TextView left, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("广告详情");
        String url_ad = getIntent().getExtras().getString("url_ad");
        String name_ad = getIntent().getExtras().getString("name_ad");
        if (!TextUtils.isEmpty(name_ad)){
            title.setText(name_ad);
        }
        wv = (WebView) findViewById(R.id.wv_ad);
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

        wv.loadUrl(url_ad);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (com.wly.android.widget.R.id.tv_left == id) {
            finish();
        }
    }
}
