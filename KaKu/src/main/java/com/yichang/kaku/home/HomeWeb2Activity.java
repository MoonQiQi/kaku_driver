package com.yichang.kaku.home;

import android.annotation.SuppressLint;
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
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.member.login.LoginActivity;
import com.yichang.kaku.tools.Utils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class HomeWeb2Activity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeweb);
        init();
    }

    @SuppressLint("JavascriptInterface")
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
        if (KaKuApplication.isShare) {
            right = (TextView) findViewById(R.id.tv_right);
            right.setText("分享");
            right.setVisibility(View.VISIBLE);
            right.setOnClickListener(this);
        }
        wv = (WebView) findViewById(R.id.wv_homeweb);
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
        String url = getIntent().getStringExtra("url");
        wv.loadUrl(url);
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
            Share();
        }
    }

    public void Share() {

        String url = "http://www.bjggyy.com/app/index.php?i=47&c=entry&do=index&m=zq2016";
        String content = "我报名参加了中国重汽高效节油挑战赛，快来为我加油助威吧！";
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        oks.setTitle("中国重汽高效节油挑战赛，快来为我加油助威吧！");
        oks.setTitleUrl(url);
        oks.setText(content);
        oks.setImageUrl(Constants.ICON_KAKU);
        oks.setUrl(url);
        oks.setComment("评论。。。");
        oks.setSite("卡库养车");
        oks.setSiteUrl(url);
        oks.show(context);

    }

}
