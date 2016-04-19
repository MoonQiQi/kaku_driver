package com.yichang.kaku.member.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

public class CommentTypeActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;

    private TextView tv_function, tv_page, tv_operate, tv_others;
    private String TYPE_FUNCTION="1";
    private String TYPE_PAGE="2";
    private String TYPE_OPERATE="3";
    private String TYPE_OTHERS="4";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_settings_commenttype);

        init();
    }

    private void init() {

        initTitleBar();

        tv_function = (TextView) findViewById(R.id.tv_function);
        tv_function.setOnClickListener(this);
        tv_page = (TextView) findViewById(R.id.tv_page);
        tv_page.setOnClickListener(this);
        tv_operate = (TextView) findViewById(R.id.tv_operate);
        tv_operate.setOnClickListener(this);
        tv_others = (TextView) findViewById(R.id.tv_others);
        tv_others.setOnClickListener(this);
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("产品吐槽");

    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(getApplicationContext());
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.tv_function == id) {
            /**/
            Intent intent=new Intent();
            intent.putExtra("type",TYPE_FUNCTION);
            setResult(141, intent);
            finish();

        }else if (R.id.tv_page == id) {
            /*选择反馈类型*/
            Intent intent=new Intent();
            intent.putExtra("type",TYPE_PAGE);
            setResult(142,intent);
            finish();
        }else if (R.id.tv_operate == id) {
            /*选择反馈类型*/
            Intent intent=new Intent();
            intent.putExtra("type",TYPE_OPERATE);
            setResult(143,intent);
            finish();
        }else if (R.id.tv_others == id) {
            /*选择反馈类型*/
            Intent intent=new Intent();
            setResult(144,intent);
            intent.putExtra("type",TYPE_OTHERS);
            finish();
        }
    }


}
