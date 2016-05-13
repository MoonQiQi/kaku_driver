package com.yichang.kaku.money;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.ShareContentCustomizeDemo;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.tools.Utils;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MoneyRankActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView lv_moneyrank;
    private String content, url,title_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneyrank);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("排行榜");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("分享");
        right.setOnClickListener(this);
        lv_moneyrank = (ListView) findViewById(R.id.lv_moneyrank);
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        title_content = getIntent().getStringExtra("title_content");
        List list = getIntent().getStringArrayListExtra("list");
        MoneyRankAdapter adapter = new MoneyRankAdapter(context, list);
        lv_moneyrank.setAdapter(adapter);
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
        } else if (R.id.tv_right == id) {
            Share();
        }
    }

    public void Share() {

        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title_content);
        oks.setTitleUrl(url);
        oks.setText(content);
        oks.setImageUrl(Constants.ICON_KAKU);
        oks.setUrl(url);
        oks.setComment("评论。。。");
        oks.setSite("卡库养车");
        oks.setSiteUrl(url);
        oks.show(context);

    }

}
