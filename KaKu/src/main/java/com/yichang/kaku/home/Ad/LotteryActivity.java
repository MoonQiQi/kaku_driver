package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.choujiang.MyPrizeActivity;
import com.yichang.kaku.tools.Utils;

public class LotteryActivity extends BaseActivity implements OnClickListener {

    private TextView left, title, right;

    private WebView wv_lottery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        init();
    }

    private void init() {
        // TODO Auto-generated method stub

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("幸运大抽奖");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("我的奖品");
        right.setOnClickListener(this);



        wv_lottery= (WebView) findViewById(R.id.wv_lottery);

        wv_lottery.getSettings().setDefaultTextEncodingName("utf-8");
        wv_lottery.getSettings().setSupportZoom(true);
        wv_lottery.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wv_lottery.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_lottery.setWebChromeClient(new WebChromeClient());
        wv_lottery.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /*wv_lottery.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Activity和Webview根据加载程度决定进度条的进度大小
                //当加载到100%的时候 进度条自动消失
                LotteryActivity.this.setProgress(progress * 100);
            }
        });*/
        wv_lottery.addJavascriptInterface(this, "androidObj");
        /*getLotteryUrl();*/
        wv_lottery.loadUrl(getIntent().getStringExtra("url"));
    }

    @android.webkit.JavascriptInterface
    public void toMyPrizeActivity(String msg) {
        startActivity(new Intent(this, MyPrizeActivity.class));
        finish();
    }

/*
    private void getLotteryUrl() {

        Utils.NoNet(context);
        showProgressDialog();

        LotteryUrlReq req = new LotteryUrlReq();
        req.code = "70039";
        req.id_driver = Utils.getIdDriver();


        KaKuApiProvider.getLotteryUrl(req, new BaseCallback<LotteryUrlResp>(LotteryUrlResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, LotteryUrlResp t) {
                if (t != null) {

                    if (Constants.RES.equals(t.res)) {
                        LogUtil.E("getLotteryUrl :"+t.url);
                        wv_lottery.loadUrl(t.url);

                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }
*/


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            //goToHome();
            finish();
        } else if (R.id.tv_right == id) {
            //进入我的奖品列表
            startActivity(new Intent(this, MyPrizeActivity.class));
        }
    }


    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //goToHome();
            finish();
        }
        return false;
    }

}
