package com.yichang.kaku.payhelper.arrivalpay;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.Ad.CheTieOrderListActivity;
import com.yichang.kaku.member.serviceorder.ServiceOrderListActivity;
import com.yichang.kaku.member.truckorder.TruckOrderListActivity;

public class ArrivalpayCallBackActivity extends BaseActivity {


    TextView textView, tv_price, tv_title;
    private Button btn_result_list, btn_result_home;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result_arrival);
        /*textView = (TextView) this.findViewById(R.id.tv_payresult_info);
        textView.setText("您已成功提交订单");
        tv_price = (TextView) this.findViewById(R.id.tv_price);
tv_price.setVisibility(View.INVISIBLE);
       // tv_price.setText("￥"+KaKuApplication.realPayment);

        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("订单提交成功，请保持电话畅通，维修人员会尽快和您联系");*/

        btn_result_list = (Button) findViewById(R.id.btn_result_list);
        btn_result_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (KaKuApplication.payType.equals("SERVICE")) {
                    gotoShopListActivity();
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("TRUCK")) {
                    gotoTruckOrderListActivity();
                    KaKuApplication.payType = "";
                } else if (KaKuApplication.payType.equals("STICKER")) {
                    gotoCheTieOrderListActivity();
                    KaKuApplication.payType = "";
                }
                finish();
            }
        });
        btn_result_home = (Button) findViewById(R.id.btn_result_home);
        btn_result_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
                startActivity(intent);
                finish();
            }
        });
    }


    private void gotoCheTieOrderListActivity() {
        Intent intent = new Intent(context, CheTieOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void gotoTruckOrderListActivity() {
        Intent intent = new Intent(context, TruckOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.truck_order_state = "";
        //intent.putExtra("state", "");
        startActivity(intent);
    }

    private void gotoShopListActivity() {
        Intent intent = new Intent(context, ServiceOrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        KaKuApplication.color_order = "";
        KaKuApplication.state_order = "";
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}