package com.yichang.kaku.member.settings;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

public class AboutKakuFunctionActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_about_function);
        init();
    }

    private void init() {

        initTitleBar();

    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("功能特点");

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
        }
    }


}
