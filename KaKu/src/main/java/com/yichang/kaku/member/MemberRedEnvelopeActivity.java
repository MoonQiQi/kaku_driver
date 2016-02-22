package com.yichang.kaku.member;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.home.MyPrizeActivity;
import com.yichang.kaku.member.recommend.MemberSendMsgActivity;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.request.MemberRecommendReq;
import com.yichang.kaku.request.RedEnvelopeShareReq;
import com.yichang.kaku.response.MemberRecommendResp;
import com.yichang.kaku.response.RedEnvelopeShareResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.OneKeySharePopWindow;
import com.yichang.kaku.view.RedEnvelopeSharePopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class MemberRedEnvelopeActivity extends BaseActivity implements OnClickListener, View.OnFocusChangeListener {
    //    titleBar
    private TextView title, left, right;


    private String smsContent = "";
    private String smsUrl = "";
    private String smsTitle = "";

    private RedEnvelopeSharePopWindow oneKeySharePopWindow;
    private WebView wv_recommend;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_recommend);

        init();
    }

    private void init() {
        initTitleBar();
        getRedEnvelopeContent();
        wv_recommend = (WebView) findViewById(R.id.wv_recommend);
        wv_recommend.getSettings().setDefaultTextEncodingName("utf-8");
        wv_recommend.getSettings().setSupportZoom(true);
        wv_recommend.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wv_recommend.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_recommend.setWebChromeClient(new WebChromeClient());
        wv_recommend.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        wv_recommend.addJavascriptInterface(new Object() {

            @JavascriptInterface
            public void showSharePopWindow(String msg) {
                showShare();


            }

           /* @JavascriptInterface
            public void toMyPrizeActivity(String msg) {
                startActivity(new Intent(context, MyPrizeActivity.class));
                finish();
            }*/

        }, "androidObj");

        wv_recommend.loadUrl(getIntent().getStringExtra("url"));


    }

    private void getRedEnvelopeContent() {
        Utils.NoNet(context);
        showProgressDialog();

        RedEnvelopeShareReq req = new RedEnvelopeShareReq();
        req.code = "10036";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getRedEnvelopeShareContent(req, new BaseCallback<RedEnvelopeShareResp>(RedEnvelopeShareResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, RedEnvelopeShareResp t) {
                if (t != null) {
                    smsContent = t.content;
                    smsUrl = t.url;
                    smsTitle=t.title;
                    LogUtil.E("getMemberRecommendInfo smsTitle: " + smsTitle+"smsContent"+smsContent+"smsUrl"+smsUrl);

                    /*LogUtil.E("getMemberRecommendInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }*/
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("红包分享");

       /* right = (TextView) findViewById(R.id.tv_right);
        right.setText("分享");
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(this);*/
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        EditText _v = (EditText) v;
        if (!hasFocus) {// 失去焦点
            _v.setHint(_v.getTag().toString());


        } else {
            String hint = _v.getHint().toString();
            _v.requestFocus();
            /*_v.setSelection(0);*/
            _v.setTag(hint);
            _v.setCursorVisible(true);
            _v.setHint("");
        }

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
            /*todo 分享页面*/
            showShare();

        }
    }

    private void showShare() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (oneKeySharePopWindow == null) {
                    ShareContentObj obj = new ShareContentObj();
                    obj.url = smsUrl;
                    obj.content = smsContent;
                    obj.title = smsTitle;
                    oneKeySharePopWindow = new RedEnvelopeSharePopWindow(MemberRedEnvelopeActivity.this, obj);
                }
                oneKeySharePopWindow.show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
