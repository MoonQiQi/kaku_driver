package com.yichang.kaku.home.faxian;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.DiscoveryCommentsObj;
import com.yichang.kaku.request.DiscoveryAddFavorReq;
import com.yichang.kaku.request.DiscoveryCancelFavorReq;
import com.yichang.kaku.request.DiscoveryDetailReq;
import com.yichang.kaku.request.DiscoveryShareReq;
import com.yichang.kaku.response.DiscoveryAddFavorResp;
import com.yichang.kaku.response.DiscoveryCancelFavorResp;
import com.yichang.kaku.response.DiscoveryDetailResp;
import com.yichang.kaku.response.DiscoveryShareResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class DiscoveryDetailActivity extends BaseActivity implements OnClickListener {

    private TextView left, right;
    private TextView title;
    private TextView tv_footer;
    //发现内容详情
    private WebView wv_discovery_detail;
    private ListView lv_discovery_detail_comment;
    List<DiscoveryCommentsObj> list_comments = new ArrayList<>();
    private View view_comment;
    //private List<DiscoveryCommentsObj> comments=new ArrayList<>();

    //底部工具栏
    private ImageView iv_discovery_detail_shoucang;
    private RelativeLayout rela_discovery_detail_shoucang, rela_discovery_detail_pinglun, rela_discovery_detail_fenxiang;
    private TextView tv_discovery_detail_shoucang, tv_discovery_detail_pinglun;
    private String codes;
    private int num_shoucang;
    private String id_news;
    //分享
    private String share_title, share_content, share_url, share_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_detail);
        init();
    }


    private void init() {
        // TODO Auto-generated method stub
        initTitleBar();

        lv_discovery_detail_comment = (ListView) findViewById(R.id.lv_discovery_detail_comment);
        view_comment = findViewById(R.id.view_comment);
        tv_footer = (TextView) findViewById(R.id.tv_footer);
        tv_footer.setOnClickListener(this);
        initBottomBar();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id_news = bundle.getString("id_news");
        String is_collection = bundle.getString("is_collection");
        String num_collection = bundle.getString("num_collection");
        String num_comments = bundle.getString("num_comments");
        num_shoucang = Integer.valueOf(num_collection);

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
                //setData(comments);
                stopProgressDialog();
                super.onPageFinished(view, url);
            }
        });

        tv_discovery_detail_shoucang.setText(num_collection);
        tv_discovery_detail_pinglun.setText(num_comments);
        if ("Y".equals(is_collection)) {
            iv_discovery_detail_shoucang.setImageResource(R.drawable.btn_discovery_favor_red);
            iv_discovery_detail_shoucang.setTag("hong");
        } else {
            iv_discovery_detail_shoucang.setImageResource(R.drawable.btn_discovery_favor_normal);
            iv_discovery_detail_shoucang.setTag("bai");
        }
        discovery_Detail(id_news);
    }

    private void initBottomBar() {
        iv_discovery_detail_shoucang = (ImageView) findViewById(R.id.iv_discovery_detail_shoucang);
        rela_discovery_detail_shoucang = (RelativeLayout) findViewById(R.id.rela_discovery_detail_shoucang);
        rela_discovery_detail_pinglun = (RelativeLayout) findViewById(R.id.rela_discovery_detail_pinglun);
        rela_discovery_detail_fenxiang = (RelativeLayout) findViewById(R.id.rela_discovery_detail_fenxiang);
        rela_discovery_detail_shoucang.setOnClickListener(this);
        rela_discovery_detail_pinglun.setOnClickListener(this);
        rela_discovery_detail_fenxiang.setOnClickListener(this);
        tv_discovery_detail_shoucang = (TextView) findViewById(R.id.tv_discovery_detail_shoucang);
        tv_discovery_detail_pinglun = (TextView) findViewById(R.id.tv_discovery_detail_pinglun);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("资讯详情");
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent();
            intent.setClass(DiscoveryDetailActivity.this, DiscoveryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//注意本行的FLAG设置
            startActivity(intent);
            finish();
        } else if (R.id.tv_footer == id) {
            KaKuApplication.discoveryId = id_news;
            startActivity(new Intent(this, DiscoveryCommentActivity.class));
        } else if (R.id.rela_discovery_detail_shoucang == id) {
            if (!Utils.checkNetworkConnection(context)) {
                Toast.makeText(context, "当前无网络，请检查重试", Toast.LENGTH_SHORT).show();
                return;
            }
            if ("hong".equals(iv_discovery_detail_shoucang.getTag())) {
                iv_discovery_detail_shoucang.setImageResource(R.drawable.btn_discovery_favor_normal);
                num_shoucang -= 1;
                tv_discovery_detail_shoucang.setText(num_shoucang + "");
                iv_discovery_detail_shoucang.setTag("bai");
                codes = "7005";
                removeFavorItem(codes, id_news);

            } else {
                iv_discovery_detail_shoucang.setImageResource(R.drawable.btn_discovery_favor_red);
                num_shoucang += 1;
                tv_discovery_detail_shoucang.setText(num_shoucang + "");
                iv_discovery_detail_shoucang.setTag("hong");
                codes = "7004";
                addFavor(codes, id_news);
            }
            //addFavor(codes,id_news);
        } else if (R.id.rela_discovery_detail_pinglun == id) {
            KaKuApplication.discoveryId = id_news;
            startActivity(new Intent(this, DiscoveryCommentActivity.class));
        } else if (R.id.rela_discovery_detail_fenxiang == id) {
            getShare();
        }
    }

    public void discovery_Detail(String id_news) {
        Utils.NoNet(DiscoveryDetailActivity.this);
        showProgressDialog();
        DiscoveryDetailReq req = new DiscoveryDetailReq();
        req.code = "7002";
        req.id_news = id_news;
        KaKuApiProvider.getDiscoveryDetailUrl(req, new KakuResponseListener<DiscoveryDetailResp>(this, DiscoveryDetailResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                // TODO Auto-generated method stub
                if (t != null) {
                    LogUtil.E("discovery_detail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        wv_discovery_detail.loadUrl(t.url);
                        //comments.addAll(t.comments);
                        setData(t.comments);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }


        });
    }

    private void setData(List<DiscoveryCommentsObj> list) {

        if (list != null) {
            list_comments.addAll(list);
        }
        if (list_comments.size() == 0) {
            tv_footer.setText("暂无评论");
            view_comment.setVisibility(View.VISIBLE);
        } else {
            tv_footer.setText("查看更多评价");
            view_comment.setVisibility(View.GONE);
        }

        DiscoveryDetailAdapter adapter = new DiscoveryDetailAdapter(this, list_comments);
        lv_discovery_detail_comment.setAdapter(adapter);

        setListViewHeightBasedOnChildren(lv_discovery_detail_comment);
    }

    /*
   * 显示listView条目，在使用ScrollView嵌套时必须注意要在setAdapter之后调用以下方法
   * 否则listView条目显示不全，只能显示其中一条
   * */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            LogUtil.E("listAdapter.getCount():" + listAdapter.getCount());
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();
            LogUtil.E("totalHeight" + i + ":" + totalHeight);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //params.height += 5;
        LogUtil.E("params.height:" + params.height);
        listView.setLayoutParams(params);
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


                    } else {
                        LogUtil.E(t.res);
                    }
                }
                stopProgressDialog();
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


                    } else {
                        LogUtil.E(t.res);
                    }
                }
                stopProgressDialog();
            }

        });

    }

    public void getShare() {
        showProgressDialog();
        DiscoveryShareReq req = new DiscoveryShareReq();
        req.code = "7008";
        req.id_news = id_news;
        KaKuApiProvider.getShareInfos(req, new KakuResponseListener<DiscoveryShareResp>(this, DiscoveryShareResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                // TODO Auto-generated method stub
                stopProgressDialog();
                if (t != null) {
                    LogUtil.E("getshare res: " + t.res);
                    LogUtil.E("t info: " + "t.intro_news" + t.intro_news + "t.t.url" + t.url + "t.t.image" + t.image + "t.t.name_news" + t.name_news);
                    if (Constants.RES.equals(t.res)) {
                        share_content = t.intro_news;
                        share_url = t.url;
                        share_image = t.image;
                        share_title = t.name_news;
                        showShare();
                    }
                }
            }


        });
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
        oks.setTitle(share_title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(share_url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(share_content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImageUrl(share_image);
        oks.setUrl(share_url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论。。。");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("卡库");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(share_url);

        // 启动分享GUI
        oks.show(context);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(DiscoveryDetailActivity.this, DiscoveryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//注意本行的FLAG设置
            startActivity(intent);
            finish();
        }
        return false;
    }
}
