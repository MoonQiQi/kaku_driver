package com.yichang.kaku.home.ad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieTaskObj;
import com.yichang.kaku.request.CheTieListReq;
import com.yichang.kaku.response.CheTieTaskListResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class CheTieListActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_chetielist;
    private List<CheTieTaskObj> list_chetie = new ArrayList<CheTieTaskObj>();
    private CheTieAdapter adapter_chetie;
    private Boolean isPwdPopWindowShow = false;
    private RelativeLayout rela_chetie_state1, rela_chetie_state2, rela_chetie_state3;
    private TextView tv_chetie_state1, tv_chetie_state2, tv_chetie_state3;
    private View view_chetieview;
    private LinearLayout line_chetie_state;
    private String url;
    private String show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chetielist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("车贴任务");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("任务说明");
        right.setOnClickListener(this);
        lv_chetielist = (ListView) findViewById(R.id.lv_chetielist);
        lv_chetielist.setOnItemClickListener(this);
        rela_chetie_state1 = (RelativeLayout) findViewById(R.id.rela_chetie_state1);
        rela_chetie_state2 = (RelativeLayout) findViewById(R.id.rela_chetie_state2);
        rela_chetie_state3 = (RelativeLayout) findViewById(R.id.rela_chetie_state3);
        rela_chetie_state1.setOnClickListener(this);
        rela_chetie_state2.setOnClickListener(this);
        rela_chetie_state3.setOnClickListener(this);
        tv_chetie_state1 = (TextView) findViewById(R.id.tv_chetie_state1);
        tv_chetie_state2 = (TextView) findViewById(R.id.tv_chetie_state2);
        tv_chetie_state3 = (TextView) findViewById(R.id.tv_chetie_state3);
        line_chetie_state = (LinearLayout) findViewById(R.id.line_chetie_state);
        view_chetieview = findViewById(R.id.view_chetieview);
        SetColor();
        tv_chetie_state1.setTextColor(getResources().getColor(R.color.color_red));
        GetCheTieList("Y");
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        } else if (R.id.rela_chetie_state1 == id) {
            SetColor();
            tv_chetie_state1.setTextColor(getResources().getColor(R.color.color_red));
            GetCheTieList("Y");
        } else if (R.id.rela_chetie_state2 == id) {
            SetColor();
            tv_chetie_state2.setTextColor(getResources().getColor(R.color.color_red));
            GetCheTieList("O");
        } else if (R.id.rela_chetie_state3 == id) {
            SetColor();
            tv_chetie_state3.setTextColor(getResources().getColor(R.color.color_red));
            GetCheTieList("E");
        } else if (R.id.tv_right == id) {
            MobclickAgent.onEvent(context, "CheTieShuoMing");
            Intent intent = new Intent(this, TaskExplainActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

    public void GetCheTieList(String flag_type) {
        showProgressDialog();
        CheTieListReq req = new CheTieListReq();
        req.code = "600100";
        req.flag_type = flag_type;
        KaKuApiProvider.getCheTieList(req, new KakuResponseListener<CheTieTaskListResp>(this, CheTieTaskListResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("chetielist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        url = t.url;
                        if ("Y".equals(t.flag_show)) {
                            view_chetieview.setVisibility(View.VISIBLE);
                            line_chetie_state.setVisibility(View.VISIBLE);
                            show = "Y";
                        } else {
                            view_chetieview.setVisibility(View.GONE);
                            line_chetie_state.setVisibility(View.GONE);
                            show = "N";
                        }
                        list_chetie = t.adverts;
                        adapter_chetie = new CheTieAdapter(CheTieListActivity.this, list_chetie);
                        lv_chetielist.setAdapter(adapter_chetie);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MobclickAgent.onEvent(context, "CheTieItem");
        if ("N".equals(show)) {
            if (position == 0) {
                KaKuApplication.id_advert_daili = list_chetie.get(position).getId_advert();
                KaKuApplication.flag_advert_sign = list_chetie.get(position).getFlag_sign();
                GoZongJie(list_chetie.get(position).getFlag_type());
            }
        } else {
            KaKuApplication.id_advert_daili = list_chetie.get(position).getId_advert();
            KaKuApplication.flag_advert_sign = list_chetie.get(position).getFlag_sign();
            GoZongJie(list_chetie.get(position).getFlag_type());
        }

    }


    public void GoZongJie(String type) {
        if (TextUtils.equals(type, "O")) {
            startActivity(new Intent(this, ZongJieYuYueZhongActivity.class));
        } else if (TextUtils.equals(type, "Y")) {
            startActivity(new Intent(this, ZongJieJinXingZhongActivity.class));
        } else if (TextUtils.equals(type, "E")) {
            startActivity(new Intent(this, ZongJieYiJieShuActivity.class));
        }
    }

    private void SetColor() {
        tv_chetie_state1.setTextColor(getResources().getColor(R.color.color_word));
        tv_chetie_state2.setTextColor(getResources().getColor(R.color.color_word));
        tv_chetie_state3.setTextColor(getResources().getColor(R.color.color_word));
    }

}
