package com.yichang.kaku.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.PingJiaHuoDanReq;
import com.yichang.kaku.response.PingJiaHuoDanResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

public class PingJiaHuoDanActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private RatingBar star_pingjiahuodan;
    private RelativeLayout rela_pingjiahuodan_yilianxihuozhu, rela_pingjiahuodan_xinxishushi;
    private ImageView iv_pingjiahuodan_yilianxihuozhu, iv_pingjiahuodan_xinxishushi;
    private String flag_1 = "N", flag_2 = "N";
    private EditText et_pingjiahuodan;
    private Button btn_pingjiahuodan_commit;
    private String id_supply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjiahuodan);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("评价货单");
        star_pingjiahuodan = (RatingBar) findViewById(R.id.star_pingjiahuodan);
        rela_pingjiahuodan_yilianxihuozhu = (RelativeLayout) findViewById(R.id.rela_pingjiahuodan_yilianxihuozhu);
        rela_pingjiahuodan_yilianxihuozhu.setOnClickListener(this);
        rela_pingjiahuodan_xinxishushi = (RelativeLayout) findViewById(R.id.rela_pingjiahuodan_xinxishushi);
        rela_pingjiahuodan_xinxishushi.setOnClickListener(this);
        iv_pingjiahuodan_yilianxihuozhu = (ImageView) findViewById(R.id.iv_pingjiahuodan_yilianxihuozhu);
        iv_pingjiahuodan_xinxishushi = (ImageView) findViewById(R.id.iv_pingjiahuodan_xinxishushi);
        et_pingjiahuodan = (EditText) findViewById(R.id.et_pingjiahuodan);
        btn_pingjiahuodan_commit = (Button) findViewById(R.id.btn_pingjiahuodan_commit);
        btn_pingjiahuodan_commit.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id_supply = extras.getString("id_supply");
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
        } else if (R.id.rela_pingjiahuodan_xinxishushi == id) {
            if ("Y".equals(flag_1)) {
                iv_pingjiahuodan_xinxishushi.setImageResource(R.drawable.uncheck_yuan);
                flag_1 = "N";
            } else {
                iv_pingjiahuodan_xinxishushi.setImageResource(R.drawable.check_yuan);
                flag_1 = "Y";
            }

        } else if (R.id.rela_pingjiahuodan_yilianxihuozhu == id) {
            if ("Y".equals(flag_2)) {
                iv_pingjiahuodan_yilianxihuozhu.setImageResource(R.drawable.uncheck_yuan);
                flag_2 = "N";
            } else {
                iv_pingjiahuodan_yilianxihuozhu.setImageResource(R.drawable.check_yuan);
                flag_2 = "Y";
            }
        } else if (R.id.btn_pingjiahuodan_commit == id) {
            Commit();
        }
    }

    public void Commit() {
        Utils.NoNet(context);
        PingJiaHuoDanReq req = new PingJiaHuoDanReq();
        req.code = "6008";
        req.id_driver = Utils.getIdDriver();
        req.id_supply = id_supply;
        req.flag_true = flag_1;
        req.flag_contact = flag_2;
        req.star_eval = String.valueOf(star_pingjiahuodan.getRating());
        req.content_eval = et_pingjiahuodan.getText().toString().trim();
        KaKuApiProvider.PingJiaHuoDan(req, new KakuResponseListener<PingJiaHuoDanResp>(this, PingJiaHuoDanResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("pingjiahuodan res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        finish();
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }
}
