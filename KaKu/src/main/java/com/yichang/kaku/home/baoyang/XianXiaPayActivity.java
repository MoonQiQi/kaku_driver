package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.Worker2Obj;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.Utils;

public class XianXiaPayActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ImageView iv_xianxiapay_head, iv_xianxiapay_dahuangyou, iv_xianxiapay_luntai, iv_xianxiapay_baolun, iv_xianxiapay_call;
    private TextView tv_xianxiapay_name, tv_xianxiapay_dan;
    private RatingBar star_xianxiapay_star;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianxiapay);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("线下支付");
        iv_xianxiapay_head = (ImageView) findViewById(R.id.iv_xianxiapay_head);
        iv_xianxiapay_dahuangyou = (ImageView) findViewById(R.id.iv_xianxiapay_dahuangyou);
        iv_xianxiapay_luntai = (ImageView) findViewById(R.id.iv_xianxiapay_luntai);
        iv_xianxiapay_baolun = (ImageView) findViewById(R.id.iv_xianxiapay_baolun);
        iv_xianxiapay_call = (ImageView) findViewById(R.id.iv_xianxiapay_call);
        iv_xianxiapay_call.setOnClickListener(this);
        tv_xianxiapay_name = (TextView) findViewById(R.id.tv_xianxiapay_name);
        tv_xianxiapay_dan = (TextView) findViewById(R.id.tv_xianxiapay_dan);
        star_xianxiapay_star = (RatingBar) findViewById(R.id.star_xianxiapay_star);
        Worker2Obj worker = KaKuApplication.worker;
        tv_xianxiapay_name.setText(worker.getName_worker());
        tv_xianxiapay_dan.setText(worker.getNum_bill() + "单");
        float star = Float.parseFloat(worker.getNum_star());
        star_xianxiapay_star.setRating(star);
        phone = worker.getTel_worker();
        if ("Y".equals(worker.getFlag_butter())) {
            iv_xianxiapay_dahuangyou.setVisibility(View.VISIBLE);
        } else {
            iv_xianxiapay_dahuangyou.setVisibility(View.GONE);
        }
        if ("Y".equals(worker.getFlag_wheel())) {
            iv_xianxiapay_baolun.setVisibility(View.VISIBLE);
        } else {
            iv_xianxiapay_baolun.setVisibility(View.GONE);
        }
        if ("Y".equals(worker.getFlag_tire())) {
            iv_xianxiapay_luntai.setVisibility(View.VISIBLE);
        } else {
            iv_xianxiapay_luntai.setVisibility(View.GONE);
        }
        BitmapUtil.getInstance(context).download(iv_xianxiapay_head, worker.getImage_worker());
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
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        } else if (R.id.iv_xianxiapay_call == id) {
            Utils.Call(context, phone);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
