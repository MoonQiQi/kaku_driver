package com.yichang.kaku.home.call;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.ExitReq;
import com.yichang.kaku.response.CallMoreResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

public class CallMoreActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_callmore_shengyu;
    private ListView lv_callmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callmore);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("更多");
        int time = Utils.getCallSheng();
        tv_callmore_shengyu = (TextView) findViewById(R.id.tv_callmore_shengyu);
        tv_callmore_shengyu.setText("剩余时长 " + time / 60 + " 分钟");
        lv_callmore = (ListView) findViewById(R.id.lv_callmore);
        getInfo();
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

    public void getInfo() {
        showProgressDialog();
        ExitReq req = new ExitReq();
        req.code = "60055";
        KaKuApiProvider.CallMore(req, new KakuResponseListener<CallMoreResp>(context, CallMoreResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("callmore res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        CallMoreAdapter adapter = new CallMoreAdapter(context, t.duration);
                        lv_callmore.setAdapter(adapter);
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

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

}
