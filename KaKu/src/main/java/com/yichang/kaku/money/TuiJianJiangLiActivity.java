package com.yichang.kaku.money;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.TuiJianObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.TuiJianResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class TuiJianJiangLiActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_tuijian_yaoqingma, tv_tuijian_chenggong, tv_tuijian_xianjin, tv_tuijian_wenhao, tv_tuijian_total;
    private ImageView iv_tuijian_weixin, iv_tuijian_duanxin, iv_tuijian_qrcode;
    private ListView lv_tuijian;
    private List<TuiJianObj> list = new ArrayList<>();
    private String url0, url, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijianjiangli);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("推荐奖励");
        tv_tuijian_yaoqingma = (TextView) findViewById(R.id.tv_tuijian_yaoqingma);
        tv_tuijian_chenggong = (TextView) findViewById(R.id.tv_tuijian_chenggong);
        tv_tuijian_xianjin = (TextView) findViewById(R.id.tv_tuijian_xianjin);
        tv_tuijian_wenhao = (TextView) findViewById(R.id.tv_tuijian_wenhao);
        tv_tuijian_total = (TextView) findViewById(R.id.tv_tuijian_total);
        tv_tuijian_wenhao.setOnClickListener(this);
        iv_tuijian_weixin = (ImageView) findViewById(R.id.iv_tuijian_weixin);
        iv_tuijian_weixin.setOnClickListener(this);
        iv_tuijian_qrcode = (ImageView) findViewById(R.id.iv_tuijian_qrcode);
        iv_tuijian_duanxin = (ImageView) findViewById(R.id.iv_tuijian_duanxin);
        iv_tuijian_duanxin.setOnClickListener(this);
        lv_tuijian = (ListView) findViewById(R.id.lv_tuijian);
        lv_tuijian.setFocusable(false);
        GetInfo();
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
        } else if (R.id.tv_tuijian_wenhao == id) {
            Intent intent = new Intent(this, JiangLiGuiZeActivity.class);
            intent.putExtra("url", url0);
            startActivity(intent);
        } else if (R.id.iv_tuijian_weixin == id) {
            Share();
        } else if (R.id.iv_tuijian_duanxin == id) {
            SendMsg();
        }
    }

    public void GetInfo() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "10032";
        KaKuApiProvider.getMemberRecommendInfo(req, new KakuResponseListener<TuiJianResp>(context, TuiJianResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("tuijian res: " + t.res);
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

    public void SetText(TuiJianResp t) {
        url0 = t.url0;
        url = t.url;
        content = t.content;
        Bitmap creatMyCode = Utils.createQRCode(url);
        iv_tuijian_qrcode.setImageBitmap(creatMyCode);
        tv_tuijian_yaoqingma.setText("您的邀请码：" + t.code_recommended);
        list = t.recommendeds;
        tv_tuijian_total.setText("¥ " + t.total_money);
        TuiJianAdapter adapter = new TuiJianAdapter(context, list);
        lv_tuijian.setAdapter(adapter);
        lv_tuijian.setFocusable(false);
        Utils.setListViewHeightBasedOnChildren(lv_tuijian);
    }

    public void Share() {

        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        oks.setTitle("推荐好友");
        oks.setTitleUrl(url);
        oks.setText(content);
        oks.setImageUrl(Constants.ICON_KAKU);
        oks.setUrl(url);
        oks.setComment("评论。。。");
        oks.setSite("卡库养车");
        oks.setSiteUrl(url);
        oks.show(context);

    }

    private void SendMsg() {

        Uri smsToUri = Uri.parse("smsto:" + "");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", content + url);
        startActivity(intent);
    }

}
