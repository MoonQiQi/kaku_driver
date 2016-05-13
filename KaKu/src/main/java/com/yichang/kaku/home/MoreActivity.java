package com.yichang.kaku.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.ShopFenLeiObj;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.MoreServiceResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private LineGridView gv_more;
    private List<ShopFenLeiObj> fuwu_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("更多服务");
        gv_more = (LineGridView) findViewById(R.id.gv_more);
        gv_more.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KaKuApplication.id_service = fuwu_list.get(position).getId_service();
                KaKuApplication.type_service = "7";
                KaKuApplication.name_service = fuwu_list.get(position).getName_service();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME2);
                startActivity(intent);
                finish();
            }
        });
        getMore();
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

    public void getMore() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "8009";
        KaKuApiProvider.MoreService(req, new KakuResponseListener<MoreServiceResp>(context, MoreServiceResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("MoreService res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        fuwu_list = t.services;
                        MoreServiceAdapter adapter = new MoreServiceAdapter(context, fuwu_list);
                        gv_more.setAdapter(adapter);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }
        });

    }

}
