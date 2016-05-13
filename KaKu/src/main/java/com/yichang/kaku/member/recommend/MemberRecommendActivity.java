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
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.member.JiangLiMingXiActivity;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.MemberRecommendResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.OneKeySharePopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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

        ExitReq req = new ExitReq();
        req.code = "10032";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getMemberRecommendInfo(req, new KakuResponseListener<MemberRecommendResp>(this, MemberRecommendResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
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
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

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

    public void showShare() {

        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字
        //oks.setNotification(R.drawable.ic_launcher, "金牌维修通");
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("卡库养车");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(shareContent.url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareContent.content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImageUrl("http://manage.360kaku.com/index.php?m=Img&a=imgAction&img=icon_share.png");
        oks.setUrl(shareContent.url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论。。。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("卡库");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareContent.url);

        // 启动分享GUI
        oks.show(context);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
