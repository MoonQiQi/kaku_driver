package com.yichang.kaku.member.recommend;

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
import com.yichang.kaku.member.JiangLiMingXiActivity;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.request.MemberRecommendReq;
import com.yichang.kaku.response.MemberRecommendResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.OneKeySharePopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class MemberRecommendActivity extends BaseActivity implements OnClickListener, View.OnFocusChangeListener {
    //    titleBar
    private TextView title, left, right;


    private String smsContent = "";
    private String smsUrl = "";
    private String downLoadUrl;
    private MemberRecommendResp shareContent;
    private OneKeySharePopWindow oneKeySharePopWindow;
    private WebView wv_recommend;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_recommend);

        init();
    }

    private void init() {
        initTitleBar();
        wv_recommend = (WebView) findViewById(R.id.wv_recommend);
        getRecommendInfo();
    }

    private void getRecommendInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        MemberRecommendReq req = new MemberRecommendReq();
        req.code = "10032";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getMemberRecommendInfo(req, new BaseCallback<MemberRecommendResp>(MemberRecommendResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, MemberRecommendResp t) {
                if (t != null) {
                    LogUtil.E("getMemberRecommendInfo res: " + t.res);
                    if (Constants.RES.equals(t.res)) {

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
                        /*wv_recommend.setWebChromeClient(new WebChromeClient() {
                            public void onProgressChanged(WebView view, int progress) {
                                //Activity和Webview根据加载程度决定进度条的进度大小
                                //当加载到100%的时候 进度条自动消失
                                LotteryActivity.this.setProgress(progress * 100);
                            }
                        });*/
                        //wv_recommend.addJavascriptInterface(this, "androidObj");
        /*getLotteryUrl();*/

                        wv_recommend.addJavascriptInterface(new Object() {

                            @JavascriptInterface
                            public void toSendMsgActivity(String msg) {
                                Intent intent = new Intent(context, MemberSendMsgActivity.class);
                                intent.putExtra("smsContent", smsContent);
                                intent.putExtra("smsUrl", smsUrl);
                                startActivity(intent);
                                finish();
                            }

                            @JavascriptInterface
                            public void toMyPrizeActivity(String msg) {
                                startActivity(new Intent(context, JiangLiMingXiActivity.class));
                                finish();
                            }

                        }, "androidObj");

                        wv_recommend.loadUrl(t.url_1);

                        MemberRecommendActivity.this.shareContent = t;
                        smsContent = t.content;
                        smsUrl = t.url;
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
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("邀请好友");

        right = (TextView) findViewById(R.id.tv_right);
        right.setText("分享");
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(this);


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
        if (oneKeySharePopWindow == null) {
            ShareContentObj obj = new ShareContentObj();
            obj.url = shareContent.url;
            obj.content = shareContent.content;
            oneKeySharePopWindow = new OneKeySharePopWindow(this, obj);
        }
        oneKeySharePopWindow.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
