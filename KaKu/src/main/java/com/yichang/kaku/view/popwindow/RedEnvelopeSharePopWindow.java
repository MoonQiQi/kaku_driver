package com.yichang.kaku.view.popwindow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.response.ShortURLResp;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.RequestCallback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by xiaosu on 2015/12/8.
 */
public class RedEnvelopeSharePopWindow extends PopupWindow implements View.OnClickListener {

    private final BaseActivity context;
    private final ShareContentObj shareContent;

    private String mTitle;
    //是否使用短网址
    private Boolean isShortUrl = true;

    public Boolean getIsShortUrl() {
        return isShortUrl;
    }

    public void setIsShortUrl(Boolean isShortUrl) {
        this.isShortUrl = isShortUrl;
    }

    public RedEnvelopeSharePopWindow(BaseActivity context, ShareContentObj shareContent) {
        super(context);
        this.context = context;
        this.shareContent = shareContent;
        if(TextUtils.isEmpty(shareContent.title)){
            mTitle="卡库分享";

        }else {
            mTitle=shareContent.title;
        }

        ShareSDK.initSDK(context);

        View rootView = View.inflate(context, R.layout.layout_pop_share, null);

        rootView.findViewById(R.id.share_wx).setOnClickListener(this);
        rootView.findViewById(R.id.share_pyq).setOnClickListener(this);
        rootView.findViewById(R.id.share_qq).setOnClickListener(this);

        setContentView(rootView);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        context.showProgressDialog();
        switch (v.getId()) {
            case R.id.share_wx:
                String wxUrl = shareContent.url + "&type=Wechat";

                Log.d("xiaosu", "====" + wxUrl);
                if (isShortUrl) {
                    try {
                        wxUrl = URLEncoder.encode(wxUrl, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getShortURL(wxUrl, new onSuccessListener() {
                        @Override
                        public void onSuccess(String shortUrl) {
                            Log.d("xiaosu", "----" + shortUrl);
                            Wechat.ShareParams params = new Wechat.ShareParams();
                            params.setTitle(mTitle);
                            params.setShareType(Wechat.SHARE_WEBPAGE);
                            params.setText(shareContent.content + shortUrl);
                            //params.setImageUrl("http://kaku.wekaku.com/icon_share.png");
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qianghongbao);
                            params.setImageData(bitmap);
                            params.setUrl(shortUrl);
                            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                            wechat.setPlatformActionListener(new InnerPlatformActionListener("微信"));
                            wechat.share(params);
                        }
                    });
                } else {
                    Wechat.ShareParams params = new Wechat.ShareParams();
                    params.setTitle(mTitle);
                    params.setShareType(Wechat.SHARE_WEBPAGE);
                    params.setText(shareContent.content + shareContent.url);
                    //params.setImageUrl("http://kaku.wekaku.com/icon_share.png");
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qianghongbao);
                    params.setImageData(bitmap);
                    params.setUrl(shareContent.url);
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    wechat.setPlatformActionListener(new InnerPlatformActionListener("微信"));
                    wechat.share(params);
                }
                break;
            case R.id.share_pyq:
                String wmUrl = shareContent.url + "&type=WechatMoments";


                if (isShortUrl) {
                    try {
                        wmUrl = URLEncoder.encode(wmUrl, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getShortURL(wmUrl, new onSuccessListener() {
                        @Override
                        public void onSuccess(String shortUrl) {
                            WechatMoments.ShareParams params = new WechatMoments.ShareParams();
                            params.setShareType(WechatMoments.SHARE_WEBPAGE);
                            params.setTitle(shareContent.content + shortUrl);
                            //params.setImageUrl("http://kaku.wekaku.com/icon_share.png");
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qianghongbao);
                            params.setImageData(bitmap);
                            params.setUrl(shortUrl);
                            Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                            wechatMoments.setPlatformActionListener(new InnerPlatformActionListener("微信朋友圈"));
                            wechatMoments.share(params);
                        }
                    });
                } else {
                    WechatMoments.ShareParams params = new WechatMoments.ShareParams();
                    params.setShareType(WechatMoments.SHARE_WEBPAGE);
                    params.setTitle(shareContent.content + shareContent.url);
                    //params.setImageUrl("http://kaku.wekaku.com/icon_share.png");
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qianghongbao);
                    params.setImageData(bitmap);
                    params.setUrl(shareContent.url);
                    Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatMoments.setPlatformActionListener(new InnerPlatformActionListener("微信朋友圈"));
                    wechatMoments.share(params);
                }
                break;
            case R.id.share_qq:
                String qqUrl = shareContent.url + "&type=QQ";

                if (isShortUrl) {
                    try {
                        qqUrl = URLEncoder.encode(qqUrl, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    getShortURL(qqUrl, new onSuccessListener() {
                        @Override
                        public void onSuccess(String shortUrl) {
                            QQ.ShareParams params = new QQ.ShareParams();
                            params.setTitle(mTitle);
                            //params.setImageUrl("http://kaku.wekaku.com/icon_share.png");
                            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qianghongbao);
                            params.setImageData(bitmap);
                            params.setText(shareContent.content + shortUrl);
                            params.setTitleUrl(shortUrl);
                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(new InnerPlatformActionListener("QQ")); // 设置分享事件回调
                            qq.share(params);
                        }
                    });
                } else {
                    QQ.ShareParams params = new QQ.ShareParams();
                    params.setTitle(mTitle);
                    //params.setImageUrl("http://kaku.wekaku.com/icon_share.png");
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.qianghongbao);
                    params.setImageData(bitmap);
                    params.setText(shareContent.content + shareContent.url);
                    params.setTitleUrl(shareContent.url);
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(new InnerPlatformActionListener("QQ")); // 设置分享事件回调
                    qq.share(params);
                }
                break;
        }
        dismiss();
    }

    public class InnerPlatformActionListener implements PlatformActionListener {

        public InnerPlatformActionListener(String platform) {
            this.platform = platform;
        }

        String platform;

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            context.stopProgressDialog();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            context.stopProgressDialog();
            context.showShortToast("抱歉，分享到" + platform + "失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            context.stopProgressDialog();
        }
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public interface onSuccessListener {
        void onSuccess(String shortUrl);
    }

    private void getShortURL(String OrignalUrl, final onSuccessListener onSuccessListener) {

        OkHttpUtil.getAsync("http://985.so/api.php?format=json&url=" + OrignalUrl, new RequestCallback<ShortURLResp>(context, ShortURLResp.class) {

            @Override
            public void onSuccess(ShortURLResp obj, String result) {

            }

            @Override
            public void onSuccess1(ShortURLResp obj, String result) {
                context.stopProgressDialog();
                onSuccessListener.onSuccess(obj.url);
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                context.stopProgressDialog();
                context.showShortToast("网络异常，请稍后再试");
            }
        });
    }

}
