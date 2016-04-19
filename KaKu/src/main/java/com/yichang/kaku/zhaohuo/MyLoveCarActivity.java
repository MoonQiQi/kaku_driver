package com.yichang.kaku.zhaohuo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.MyLoveCarObj;
import com.yichang.kaku.request.MyLoveCarReq;
import com.yichang.kaku.response.MyLoveCarResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class MyLoveCarActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_mylovecar;
    private MyLoveCarAdapter adapter;
    private List<MyLoveCarObj> list_mylovecar = new ArrayList<MyLoveCarObj>();

    /*无数据和无网络界面*/
    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylovecar);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的爱车");

        initNoDataLayout();
        lv_mylovecar = (ListView) findViewById(R.id.lv_mylovecar);
        lv_mylovecar.setOnItemClickListener(this);
        MyLoveCar();
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_advice = (TextView) findViewById(R.id.tv_advice);

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);

        ll_container = (LinearLayout) findViewById(R.id.ll_container);


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
        } else if (R.id.btn_refresh == id) {

            MyLoveCar();

        }
    }

    public void MyLoveCar() {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);

            return;
        } else {
            setNoDataLayoutState(ll_container);

        }
        MyLoveCarReq req = new MyLoveCarReq();
        req.code = "10028";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.MyLoveCar(req, new KakuResponseListener<MyLoveCarResp>(this, MyLoveCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("mylovecar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_mylovecar = t.driver_cars;
                        if (list_mylovecar.size() == 0) {
                            setNoDataLayoutState(layout_data_none);
                            return;
                        } else {
                            setNoDataLayoutState(ll_container);
                        }
                        adapter = new MyLoveCarAdapter(context, list_mylovecar);
                        lv_mylovecar.setAdapter(adapter);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()){
            return;
        }
        String id_car = list_mylovecar.get(position).getId_driver_car();
        String name_car = list_mylovecar.get(position).getName_brand() + list_mylovecar.get(position).getName_series();
        Intent intent = new Intent();
        intent.putExtra("id_car", id_car);
        intent.putExtra("name_car", name_car);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }
}
