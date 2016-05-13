package com.yichang.kaku.home.faxian;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.request.DiscoveryAddFavorReq;
import com.yichang.kaku.request.DiscoveryCancelFavorReq;
import com.yichang.kaku.request.DiscoveryCommentSendReq;
import com.yichang.kaku.request.DiscoveryDetailReq;
import com.yichang.kaku.request.DiscoveryShareReq;
import com.yichang.kaku.response.DiscoveryAddFavorResp;
import com.yichang.kaku.response.DiscoveryCancelFavorResp;
import com.yichang.kaku.response.DiscoveryCommentSendResp;
import com.yichang.kaku.response.DiscoveryDetailResp;
import com.yichang.kaku.response.DiscoveryShareResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DiscoveryDetailActivity extends BaseActivity implements OnClickListener, TextView.OnEditorActionListener {

    private TextView left, right, title;
    private TextView tv_discovery_dianzan, tv_discovery_pinglun;
    private String codes;
    private WebView wv_discovery_detail;
    private ImageView iv_discovery_dianzan, iv_discovery_pinglun;
    private String share_title, share_content, share_url, share_image;
    private EditText et_discovery_pinglun;
    private RelativeLayout rela_discovery_dianzan, rela_discovery_pinglun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_detail);
        init();
    }


    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("资讯详情");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("分享");
        right.setOnClickListener(this);
        KaKuApplication.quanzitype = "left";
        tv_discovery_dianzan = (TextView) findViewById(R.id.tv_discovery_dianzan);
        tv_discovery_pinglun = (TextView) findViewById(R.id.tv_discovery_pinglun);
        iv_discovery_dianzan = (ImageView) findViewById(R.id.iv_discovery_dianzan);
        iv_discovery_pinglun = (ImageView) findViewById(R.id.iv_discovery_pinglun);
        et_discovery_pinglun = (EditText) findViewById(R.id.et_discovery_pinglun);
        et_discovery_pinglun.setOnEditorActionListener(this);
        rela_discovery_dianzan = (RelativeLayout) findViewById(R.id.rela_discovery_dianzan);
        rela_discovery_dianzan.setOnClickListener(this);
        rela_discovery_pinglun = (RelativeLayout) findViewById(R.id.rela_discovery_pinglun);
        rela_discovery_pinglun.setOnClickListener(this);
        wv_discovery_detail = (WebView) findViewById(R.id.wv_discovery_detail);
        wv_discovery_detail.getSettings().setDefaultTextEncodingName("utf-8");
        wv_discovery_detail.getSettings().setSupportZoom(true);
        wv_discovery_detail.getSettings().setBuiltInZoomControls(true);
        wv_discovery_detail.getSettings().setUseWideViewPort(true);
        wv_discovery_detail.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = wv_discovery_detail.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);

        wv_discovery_detail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                stopProgressDialog();
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DiscoveryDetail();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        } else if (R.id.rela_discovery_dianzan == id) {
            if ("hong".equals(iv_discovery_dianzan.getTag())) {
                codes = "7005";
                removeFavorItem(codes, KaKuApplication.id_news);
            } else {
                codes = "7004";
                addFavor(codes, KaKuApplication.id_news);
            }
        } else if (R.id.rela_discovery_pinglun == id) {
            startActivity(new Intent(this, DiscoveryCommentActivity.class));
        } else if (R.id.tv_right == id) {
            getShare();
        }
    }

    public void DiscoveryDetail() {
        Utils.NoNet(DiscoveryDetailActivity.this);
        showProgressDialog();
        DiscoveryDetailReq req = new DiscoveryDetailReq();
        req.code = "7002";
        req.id_news = KaKuApplication.id_news;
        KaKuApiProvider.getDiscoveryDetailUrl(req, new KakuResponseListener<DiscoveryDetailResp>(this, DiscoveryDetailResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                // TODO Auto-generated method stub
                if (t != null) {
                    LogUtil.E("discoverydetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    public void SetText(DiscoveryDetailResp t) {
        wv_discovery_detail.loadUrl(t.url);
        tv_discovery_dianzan.setText(t.news.getNum_collection());
        tv_discovery_pinglun.setText(t.news.getNum_comments());
        if ("Y".equals(t.news.getIs_collection())) {
            iv_discovery_dianzan.setImageResource(R.drawable.faxian_btn_dianzan_hong);
            iv_discovery_dianzan.setTag("hong");
        } else {
            iv_discovery_dianzan.setImageResource(R.drawable.faxian_btn_dianzan_hei);
            iv_discovery_dianzan.setTag("hei");
        }
    }

    public void addFavor(String code, String id_news) {
        showProgressDialog();

        DiscoveryAddFavorReq req = new DiscoveryAddFavorReq();
        req.code = code;
        req.id_driver = Utils.getIdDriver();
        req.id_news = id_news;
        KaKuApiProvider.addDiscoveryFavor(req, new KakuResponseListener<DiscoveryAddFavorResp>(this, DiscoveryAddFavorResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                if (t != null) {
                    if (Constants.RES.equals(t.res)) {
                        DiscoveryDetail();

                    } else {
                        LogUtil.E(t.res);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    private void removeFavorItem(String code, String id_news) {
        showProgressDialog();

        DiscoveryCancelFavorReq req = new DiscoveryCancelFavorReq();
        req.code = code;
        req.id_driver = Utils.getIdDriver();
        req.id_news = id_news;

        KaKuApiProvider.cancelDiscoveryFavor(req, new KakuResponseListener<DiscoveryCancelFavorResp>(this, DiscoveryCancelFavorResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("cancelDiscoveryFavor res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        DiscoveryDetail();

                    } else {
                        LogUtil.E(t.res);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    public void PingLun() {
        showProgressDialog();
        DiscoveryCommentSendReq req = new DiscoveryCommentSendReq();
        req.code = "7007";
        req.id_news = KaKuApplication.id_news;
        req.content_comment = et_discovery_pinglun.getText().toString().trim();
        KaKuApiProvider.sendDiscoveryComment(req, new KakuResponseListener<DiscoveryCommentSendResp>(context, DiscoveryCommentSendResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("login res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        et_discovery_pinglun.setText("");
                        et_discovery_pinglun.setHint("发表评论");
                        DiscoveryDetail();
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    public void getShare() {
        showProgressDialog();
        DiscoveryShareReq req = new DiscoveryShareReq();
        req.code = "7008";
        req.id_news = KaKuApplication.id_news;
        KaKuApiProvider.getShareInfos(req, new KakuResponseListener<DiscoveryShareResp>(this, DiscoveryShareResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                stopProgressDialog();
                if (t != null) {
                    LogUtil.E("getshare res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        share_content = t.intro_news;
                        share_url = t.url;
                        share_image = t.image;
                        share_title = t.name_news;
                        showShare();
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }
        });
    }

    public void showShare() {

        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        oks.setTitle(share_title);
        oks.setTitleUrl(share_url);
        oks.setText(share_content);
        oks.setImageUrl(share_image);
        oks.setUrl(share_url);
        oks.setComment("评论。。。");
        oks.setSite("卡库养车");
        oks.setSiteUrl(share_url);
        oks.show(context);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_NULL:
                break;
            case EditorInfo.IME_ACTION_SEND:
                PingLun();
                break;
            case EditorInfo.IME_ACTION_DONE:
                break;
        }

        return true;
    }
}
