package com.yichang.kaku.view.popwindow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.ad.AdImageActivity;
import com.yichang.kaku.home.ad.XingShiZhengImageActivity;
import com.yichang.kaku.home.choujiang.MyPrizeActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.request.GetAddReq;
import com.yichang.kaku.response.GetAddResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class MainPopWindow extends PopupWindow {

    private MainActivity context;

    public MainPopWindow(final MainActivity context) {
        super(context);
        this.context = context;
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B2000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        View lay_pop_prize = context.inflate(R.layout.lay_pop_prize);
        setContentView(lay_pop_prize);

        ImageView iv_prize = context.findView(lay_pop_prize, R.id.prize);
        switch (KaKuApplication.flag_show) {
            case "1":
                iv_prize.setImageResource(R.drawable.jianpin1);
                break;
            case "2":
                iv_prize.setImageResource(R.drawable.jiangpin2);
                break;
            case "3":
                iv_prize.setImageResource(R.drawable.jiangpin3);
                break;
            case "4":
                iv_prize.setImageResource(R.drawable.jiangpin4);
                break;
            case "5":
                iv_prize.setImageResource(R.drawable.qiangyouhongbao);
                break;
            case "12":
                iv_prize.setImageResource(R.drawable.jiangpin12);
                break;
            case "13":
                //iv_prize.setImageResource(R.drawable.home_kulong);
                break;
            case "14":
                iv_prize.setImageResource(R.drawable.jiangpin14);
                break;
            case "15":
                iv_prize.setImageResource(R.drawable.jiangpin15);
                break;
            default:
                break;
        }

        context.findView(lay_pop_prize, R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        iv_prize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if ("1".equals(KaKuApplication.flag_show) || "2".equals(KaKuApplication.flag_show)) {
                    context.startActivity(new Intent(context, MyPrizeActivity.class));
                } else if ("3".equals(KaKuApplication.flag_show)) {
                    GetAdd();
                } else if ("4".equals(KaKuApplication.flag_show)) {
                    ShareRedBag();
                } else if ("5".equals(KaKuApplication.flag_show)) {
                    context.startActivity(new Intent(context, YueActivity.class));
                } else if ("11".equals(KaKuApplication.flag_show)) {
                    KaKuApplication.flag_mengban = "Y";
                    GetAdd();
                } else if ("12".equals(KaKuApplication.flag_show)) {
                    KaKuApplication.flag_recommended = "";
                    context.startActivity(new Intent(context, XingShiZhengImageActivity.class));
                } else if ("14".equals(KaKuApplication.flag_show)) {
                    GetAdImage();
                } else if ("15".equals(KaKuApplication.flag_show)) {
                    GetAdImage();
                }
            }
        });
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    private HongBaoSharePopWindow oneKeySharePopWindow;
    private ShareContentObj shareContent = new ShareContentObj();

    private void ShareRedBag() {
        if (oneKeySharePopWindow == null) {
            shareContent.url = KaKuApplication.hongbao_url;
            shareContent.content = KaKuApplication.hongbao_content;
            shareContent.title = KaKuApplication.hongbao_title;
            oneKeySharePopWindow = new HongBaoSharePopWindow(context, shareContent);
            oneKeySharePopWindow.setIsShortUrl(false);
        }
        oneKeySharePopWindow.show();

    }

    public void GetAdd() {
        Utils.GetAdType(context);
    }

    public void GetAdImage() {
        context.showProgressDialog();
        GetAddReq req = new GetAddReq();
        req.code = "60011";
        req.id_driver = Utils.getIdDriver();
        req.id_advert = KaKuApplication.id_advert;
        KaKuApiProvider.GetAdd(req, new KakuResponseListener<GetAddResp>(context, GetAddResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getadd res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        KaKuApplication.id_advert = t.advert.getId_advert();
                        KaKuApplication.flag_recommended = t.advert.getFlag_recommended();
                        KaKuApplication.flag_jiashinum = t.advert.getNum_privilege();
                        KaKuApplication.flag_position = t.advert.getFlag_position();
                        KaKuApplication.flag_heart = t.advert.getFlag_show();
                        KaKuApplication.code_my = t.advert.getCode_recommended();
                        context.startActivity(new Intent(context, AdImageActivity.class));
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                context.stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }
}
