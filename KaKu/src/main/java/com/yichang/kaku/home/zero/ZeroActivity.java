package com.yichang.kaku.home.zero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.home.ad.QiangImageActivity;
import com.yichang.kaku.response.ZeroResp;
import com.yichang.kaku.tools.Utils;

public class ZeroActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_zero_people, tv_zero_yuanjia;
    private RelativeLayout rela_zero_shenqing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("0元购");
        tv_zero_people = (TextView) findViewById(R.id.tv_zero_people);
        tv_zero_yuanjia = (TextView) findViewById(R.id.tv_zero_yuanjia);
        rela_zero_shenqing = (RelativeLayout) findViewById(R.id.rela_zero_shenqing);
        rela_zero_shenqing.setOnClickListener(this);
        ZeroResp t = (ZeroResp) getIntent().getSerializableExtra("t");
        SetText(t);
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
        } else if (R.id.rela_zero_shenqing == id) {
            startActivity(new Intent(this, QiangImageActivity.class));
            finish();
        }
    }

    public void SetText(ZeroResp t) {
        tv_zero_people.setText(t.people_buy + "人已申请");
        tv_zero_yuanjia.setText("¥ " + t.price_buy);
    }

}
