package com.yichang.kaku.member.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

public class AboutKakuActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;

    private RelativeLayout rela_about_introduction,rela_about_functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_about);
        init();
    }

    private void init() {

        initTitleBar();
        initRela();

    }

    private void initRela() {
        rela_about_introduction= (RelativeLayout) findViewById(R.id.rela_about_introduction);
        rela_about_introduction.setOnClickListener(this);
        rela_about_functions= (RelativeLayout) findViewById(R.id.rela_about_functions);
        rela_about_functions.setOnClickListener(this);
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("关于卡库养车");


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
        }else if(R.id.rela_about_introduction==id){
            startActivity(new Intent(this,AboutKakuIntroduceActivity.class));

        }else if(R.id.rela_about_functions==id){
            startActivity(new Intent(this,AboutKakuFunctionActivity.class));
        }
    }


}
