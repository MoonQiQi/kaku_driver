package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.Utils;

public class BaoYangTransActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private RelativeLayout rela_baoyangtrans1, rela_baoyangtrans2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangtrans);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择配送方式");
        rela_baoyangtrans1 = (RelativeLayout) findViewById(R.id.rela_baoyangtrans1);
        rela_baoyangtrans1.setOnClickListener(this);
        rela_baoyangtrans2 = (RelativeLayout) findViewById(R.id.rela_baoyangtrans2);
        rela_baoyangtrans2.setOnClickListener(this);
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
        } else if (R.id.rela_baoyangtrans1 == id) {
            startActivity(new Intent(this, ChooseShopActivity.class));
        } else if (R.id.rela_baoyangtrans2 == id) {
            KaKuApplication.id_baoyangshop = "0";
            startActivity(new Intent(this, BaoYangOrderActivity.class));
        }
    }

}
