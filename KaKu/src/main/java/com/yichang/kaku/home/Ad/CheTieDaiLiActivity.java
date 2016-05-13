package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.tools.Utils;

public class CheTieDaiLiActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private Button btn_buychetie;
    private TextView tv_daili_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chetiedaili);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("车贴代理");
        tv_daili_code = (TextView) findViewById(R.id.tv_daili_code);
        tv_daili_code.setText("我的邀请码：" + KaKuApplication.code_my);
        btn_buychetie = (Button) findViewById(R.id.btn_buychetie);
        btn_buychetie.setOnClickListener(this);
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
        } else if (R.id.btn_buychetie == id) {
            XiaDan();
        }
    }

    private void XiaDan() {
        KaKuApplication.flag_dory = "D";
        Intent intent = new Intent(this, StickerOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
