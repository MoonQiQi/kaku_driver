package com.yichang.kaku.home.ad;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

public class TaskExplainActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private WebView wv_taskexplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskexplain);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("任务说明");
        String url = getIntent().getExtras().getString("url");
        wv_taskexplain = (WebView) findViewById(R.id.wv_taskexplain);
        wv_taskexplain.getSettings().setDefaultTextEncodingName("utf-8");
        wv_taskexplain.getSettings().setSupportZoom(true);
        wv_taskexplain.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = wv_taskexplain.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_taskexplain.loadUrl(url);
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
