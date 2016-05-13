package com.yichang.kaku.home;

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
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class YouHuiQuanLingActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wv;
    private String url_share;
    public String url_coupon;

    public YouHuiQuanLingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youhuiquanling);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("每日领券");
        if ("".equals(Utils.getIdDriver())) {
            url_coupon = "http://www.99kaku.com/public/index.php/appweb/coupon_rotary?id_driver=0";
        } else {
            url_coupon = "http://www.99kaku.com/public/index.php/appweb/coupon_rotary?id_driver=" + Utils.getIdDriver();
        }
        url_share = "http://www.99kaku.com/public/index.php/appweb/register?id_driver=" + Utils.getIdDriver();
        wv = (WebView) findViewById(R.id.wb_youhuiquanling);
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url_coupon);
                return true;
            }
        });

        wv.addJavascriptInterface(this, "androidObj");
        wv.loadUrl(url_coupon);
    }

    @android.webkit.JavascriptInterface
    public void gotoMall(String msg) {
        startActivity(new Intent(context, ShopMallActivity.class));
    }

    @android.webkit.JavascriptInterface
    public void gotoShare(String msg) {
        Share();
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

    public void Share() {

        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        oks.setTitle("卡库养车");
        oks.setTitleUrl(url_share);
        oks.setText("卡库养车免费送保养，¥300保养优惠等你拿！");
        oks.setImageUrl("http://manage.360kaku.com/index.php?m=Img&a=imgAction&img=icon_share.png");
        oks.setUrl(url_share);
        oks.setComment("评论。。。");
        oks.setSite("卡库养车");
        oks.setSiteUrl(url_share);
        oks.show(context);
        ShareAfter();
    }

    public void ShareAfter() {
        ExitReq req = new ExitReq();
        req.code = "70026";
        KaKuApiProvider.ShareAfter(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

            }
        });
    }

}
