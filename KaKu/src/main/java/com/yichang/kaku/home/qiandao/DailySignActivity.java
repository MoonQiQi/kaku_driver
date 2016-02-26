package com.yichang.kaku.home.qiandao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.request.DailySignReq;
import com.yichang.kaku.response.DailySignResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailySignActivity extends BaseActivity implements OnClickListener {

    private TextView left, title, right;

    private TextView tv_today_rewards, tv_points, tv_today_date, tv_day_sign;

    private TextView tv_extra_points, tv_days;
    private TextView btn_exchange;
    //private CheckBox cbx_rewards_toggle;
    //是否开启提醒功能
    private boolean isRemindChecked = false;

    private ImageView iv_day_9, iv_day_8, iv_day_7, iv_day_6, iv_day_4, iv_day_3, iv_day_2, iv_day_1;
    private ImageView iv_day_29, iv_day_28, iv_day_27, iv_day_26, iv_day_25, iv_day_24, iv_day_23, iv_day_22, iv_day_21;
    private ImageView iv_day_19, iv_day_18, iv_day_17, iv_day_16, iv_day_15, iv_day_14, iv_day_13, iv_day_12, iv_day_11;

    private List<ImageView> imageViewList = new ArrayList<>();

    private TextView tv_reward_five, tv_reward_ten, tv_reward_twenty, tv_reward_thirty;
    private TextView tv_reward_five_point, tv_reward_ten_point, tv_reward_twenty_point, tv_reward_thirty_point;
    private LinearLayout ll_reward_five, ll_reward_ten, ll_reward_twenty, ll_reward_thirty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_sign);
        init();
    }


    private void init() {
        // TODO Auto-generated method stub
        KaKuApplication.isShowRemoveImg_discovery = false;
        initTitleBar();

        initImageViews();

        tv_reward_five = (TextView) findViewById(R.id.tv_reward_five);
        tv_reward_ten = (TextView) findViewById(R.id.tv_reward_ten);
        tv_reward_twenty = (TextView) findViewById(R.id.tv_reward_twenty);
        tv_reward_thirty = (TextView) findViewById(R.id.tv_reward_thirty);

        tv_reward_five_point = (TextView) findViewById(R.id.tv_reward_five_point);
        tv_reward_ten_point = (TextView) findViewById(R.id.tv_reward_ten_point);
        tv_reward_twenty_point = (TextView) findViewById(R.id.tv_reward_twenty_point);
        tv_reward_thirty_point = (TextView) findViewById(R.id.tv_reward_thirty_point);

        ll_reward_five = (LinearLayout) findViewById(R.id.ll_reward_five);
        ll_reward_ten = (LinearLayout) findViewById(R.id.ll_reward_ten);
        ll_reward_twenty = (LinearLayout) findViewById(R.id.ll_reward_twenty);
        ll_reward_thirty = (LinearLayout) findViewById(R.id.ll_reward_thirty);

        tv_today_rewards = (TextView) findViewById(R.id.tv_today_rewards);
        tv_points = (TextView) findViewById(R.id.tv_points);
        //设置当前日期
        tv_today_date = (TextView) findViewById(R.id.tv_today_date);
        //设置签到天数
        tv_day_sign = (TextView) findViewById(R.id.tv_day_sign);
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH)+1;
        int d = cal.get(Calendar.DATE);
        tv_today_date.setText(y + "-" + m + "-" + d);

        btn_exchange = (TextView) findViewById(R.id.btn_exchange);
        btn_exchange.setOnClickListener(this);

        isRemindChecked = KaKuApplication.sp.getBoolean("isRemindChecked", false);

        /*cbx_rewards_toggle = (CheckBox) findViewById(R.id.cbx_rewards_toggle);

        cbx_rewards_toggle.setChecked(isRemindChecked);
        cbx_rewards_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRemindChecked = !isRemindChecked;
                if (isRemindChecked) {
                    //每日提醒签到
                    PollingUtils.startPollingService(DailySignActivity.this, 5, DailyRemindService.class, DailyRemindService.ACTION);


                } else {
                    PollingUtils.stopPollingService(DailySignActivity.this,  DailyRemindService.class, DailyRemindService.ACTION);

                }
                SharedPreferences.Editor editor = KaKuApplication.sp.edit();
                editor.putBoolean("isRemindChecked", isRemindChecked);
                editor.commit();
            }
        });*/

        tv_extra_points = (TextView) findViewById(R.id.tv_extra_points);
        tv_days = (TextView) findViewById(R.id.tv_days);

        getDailySignInfo();


    }

    private void initImageViews() {
        iv_day_1 = (ImageView) findViewById(R.id.iv_day_1);
        iv_day_2 = (ImageView) findViewById(R.id.iv_day_2);
        iv_day_3 = (ImageView) findViewById(R.id.iv_day_3);
        iv_day_4 = (ImageView) findViewById(R.id.iv_day_4);
        iv_day_6 = (ImageView) findViewById(R.id.iv_day_6);
        iv_day_7 = (ImageView) findViewById(R.id.iv_day_7);
        iv_day_8 = (ImageView) findViewById(R.id.iv_day_8);
        iv_day_9 = (ImageView) findViewById(R.id.iv_day_9);

        iv_day_11 = (ImageView) findViewById(R.id.iv_day_11);
        iv_day_12 = (ImageView) findViewById(R.id.iv_day_12);
        iv_day_13 = (ImageView) findViewById(R.id.iv_day_13);
        iv_day_14 = (ImageView) findViewById(R.id.iv_day_14);
        iv_day_15 = (ImageView) findViewById(R.id.iv_day_15);
        iv_day_16 = (ImageView) findViewById(R.id.iv_day_16);
        iv_day_17 = (ImageView) findViewById(R.id.iv_day_17);
        iv_day_18 = (ImageView) findViewById(R.id.iv_day_18);
        iv_day_19 = (ImageView) findViewById(R.id.iv_day_19);
        iv_day_21 = (ImageView) findViewById(R.id.iv_day_21);
        iv_day_22 = (ImageView) findViewById(R.id.iv_day_22);
        iv_day_23 = (ImageView) findViewById(R.id.iv_day_23);
        iv_day_24 = (ImageView) findViewById(R.id.iv_day_24);
        iv_day_25 = (ImageView) findViewById(R.id.iv_day_25);
        iv_day_26 = (ImageView) findViewById(R.id.iv_day_26);
        iv_day_27 = (ImageView) findViewById(R.id.iv_day_27);
        iv_day_28 = (ImageView) findViewById(R.id.iv_day_28);
        iv_day_29 = (ImageView) findViewById(R.id.iv_day_29);


        imageViewList.add(iv_day_1);
        imageViewList.add(iv_day_2);
        imageViewList.add(iv_day_3);
        imageViewList.add(iv_day_4);

        imageViewList.add(iv_day_6);
        imageViewList.add(iv_day_7);
        imageViewList.add(iv_day_8);
        imageViewList.add(iv_day_9);

        imageViewList.add(iv_day_11);
        imageViewList.add(iv_day_12);
        imageViewList.add(iv_day_13);
        imageViewList.add(iv_day_14);
        imageViewList.add(iv_day_15);
        imageViewList.add(iv_day_16);
        imageViewList.add(iv_day_17);
        imageViewList.add(iv_day_18);
        imageViewList.add(iv_day_19);

        imageViewList.add(iv_day_21);
        imageViewList.add(iv_day_22);
        imageViewList.add(iv_day_23);
        imageViewList.add(iv_day_24);
        imageViewList.add(iv_day_25);
        imageViewList.add(iv_day_26);
        imageViewList.add(iv_day_27);
        imageViewList.add(iv_day_28);
        imageViewList.add(iv_day_29);

    }

    private void getDailySignInfo() {
        showProgressDialog();
        DailySignReq req = new DailySignReq();
        req.code = "8002";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getDailySignInfo(req, new BaseCallback<DailySignResp>(DailySignResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, DailySignResp t) {

                if (t != null) {
                    LogUtil.E("getDailySignInfo res: " + t.res);
                    //showPopWindow(t.content, 10 + "");

                    if (Constants.RES_ONE.equals(t.res)) {
                        //showPopWindow();
                        showPopWindow(t.content, t.point);
                    } else {
                        //showPopWindow(t.content,t.point);
                        Toast.makeText(DailySignActivity.this, t.msg, Toast.LENGTH_SHORT).show();
                    }
                    setData(t);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });


    }

    private void showPopWindow(String content, String point) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow = inflater.inflate(R.layout.popwindow_daily_sign, null, false);
        final PopupWindow popWindow = new PopupWindow(vPopWindow, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);

        ImageView iv_close = (ImageView) vPopWindow.findViewById(R.id.btn_popwindow_close);
        TextView tv_point = (TextView) vPopWindow.findViewById(R.id.tv_popwindw_point);
        tv_point.setText("+" + point + "积分");
        /*TextView tv_content = (TextView) vPopWindow.findViewById(R.id.tv_popwindw_content);
        tv_content.setText(content);*/
        iv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });


        popWindow.showAtLocation(this.findViewById(R.id.tv_today_date), Gravity.CENTER, 0, 0);
    }

    private void setData(DailySignResp t) {

        tv_today_rewards.setText(t.point);
        tv_points.setText(t.point_now);
        tv_day_sign.setText(t.day_sign);

        tv_reward_five.setText(t.point_sign_five);
        tv_reward_ten.setText(t.point_sign_ten);
        tv_reward_twenty.setText(t.point_sign_twenty);
        tv_reward_thirty.setText(t.point_sign_thirty);

        tv_days.setText(t.days);
        tv_extra_points.setText(t.points);

        setImageDailySign(t.day_sign);

        //switch ()


    }

    int mDay = 0;

    private void setImageDailySign(String daysign) {

        Integer intDay = 0;
        try {
            intDay = Integer.parseInt(daysign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (intDay == 0) {
            /*tv_reward_five.setBackgroundResource(R.drawable.daily_sign_reward);
            tv_reward_five.setTextColor(Color.WHITE);*/
        } else if (intDay <= 5) {
            if (intDay == 5) {
                mDay = intDay - 2;
                tv_reward_five.setTextColor(Color.WHITE);
                tv_reward_five_point.setTextColor(Color.WHITE);
                ll_reward_five.setBackgroundResource(R.drawable.daily_sign_reward);
            } else {
                mDay = intDay - 1;
            }

        } else if (intDay <= 10) {

            if (intDay == 10) {
                mDay = intDay - 3;
                tv_reward_ten.setTextColor(Color.WHITE);
                tv_reward_ten_point.setTextColor(Color.WHITE);
                ll_reward_ten.setBackgroundResource(R.drawable.daily_sign_reward);
            } else {
                mDay = intDay - 2;
            }
            tv_reward_five.setTextColor(Color.WHITE);
            tv_reward_five_point.setTextColor(Color.WHITE);
            ll_reward_five.setBackgroundResource(R.drawable.daily_sign_reward);


        } else if (intDay <= 20) {
            if (intDay == 20) {
                mDay = intDay - 4;
                tv_reward_twenty.setTextColor(Color.WHITE);
                tv_reward_twenty_point.setTextColor(Color.WHITE);
                ll_reward_twenty.setBackgroundResource(R.drawable.daily_sign_reward);
            } else {
                mDay = intDay - 3;
            }
            tv_reward_ten.setTextColor(Color.WHITE);
            tv_reward_ten_point.setTextColor(Color.WHITE);
            ll_reward_ten.setBackgroundResource(R.drawable.daily_sign_reward);

            tv_reward_five.setTextColor(Color.WHITE);
            tv_reward_five_point.setTextColor(Color.WHITE);
            ll_reward_five.setBackgroundResource(R.drawable.daily_sign_reward);


        } else if (intDay <= 30) {
            if (intDay == 30) {
                mDay = intDay - 5;
                tv_reward_thirty.setTextColor(Color.WHITE);
                tv_reward_thirty_point.setTextColor(Color.WHITE);
                ll_reward_thirty.setBackgroundResource(R.drawable.daily_sign_reward);

            } else {
                mDay = intDay - 4;
            }
            tv_reward_twenty.setTextColor(Color.WHITE);
            tv_reward_twenty_point.setTextColor(Color.WHITE);
            ll_reward_twenty.setBackgroundResource(R.drawable.daily_sign_reward);

            tv_reward_ten.setTextColor(Color.WHITE);
            tv_reward_ten_point.setTextColor(Color.WHITE);
            ll_reward_ten.setBackgroundResource(R.drawable.daily_sign_reward);

            tv_reward_five.setTextColor(Color.WHITE);
            tv_reward_five_point.setTextColor(Color.WHITE);
            ll_reward_five.setBackgroundResource(R.drawable.daily_sign_reward);


        } else {

        }

        changeImageViewBg(mDay);


    }

    private void changeImageViewBg(int mDay) {
        for (int i = 0; i <= mDay; i++) {
            imageViewList.get(i).setImageResource(R.drawable.daily_signed);
        }
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("每日签到");

    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()){
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.btn_exchange == id) {
            /*跳转到车品商城*/
            startActivity(new Intent(this, ShopMallActivity.class));
            finish();

        }
    }
}
