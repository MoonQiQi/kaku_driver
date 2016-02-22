package com.yichang.kaku.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yichang.kaku.R;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.MyPrizeActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.view.widget.HongBaoSharePopWindow;

/**
 * Created by xiaosu on 2015/12/3.
 */
public class PrizePopWindow extends PopupWindow {

    private MainActivity context;
    private String flag;

    public PrizePopWindow(final MainActivity context, String flag_show) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        View lay_pop_prize = context.inflate(R.layout.lay_pop_prize);
        setContentView(lay_pop_prize);

        ImageView iv_prize = context.findView(lay_pop_prize, R.id.prize);
        switch (flag_show) {
            case "1":
                iv_prize.setImageResource(R.drawable.jianpin1);
                flag = "1";
                break;
            case "2":
                iv_prize.setImageResource(R.drawable.jiangpin2);
                flag = "2";
                break;
            case "3":
                iv_prize.setImageResource(R.drawable.jiangpin3);
                flag = "3";
                break;
            case "4":
                iv_prize.setImageResource(R.drawable.jiangpin4);
                flag = "4";
                break;
            case "5":
                iv_prize.setImageResource(R.drawable.qiangyouhongbao);
                flag = "5";
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
                if ("1".equals(flag)||"2".equals(flag)){
                    context.startActivity(new Intent(context, MyPrizeActivity.class));
                } else if ("3".equals(flag)){
                    //context.startActivity(new Intent(context, MyPrizeActivity.class));
                    //掉接口
                } else if ("4".equals(flag)){
                    ShareRedBag();
                } else if ("5".equals(flag)){
                    context.startActivity(new Intent(context, YueActivity.class));
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

}
