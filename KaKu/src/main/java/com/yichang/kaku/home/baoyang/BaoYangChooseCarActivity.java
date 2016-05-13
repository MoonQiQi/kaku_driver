package com.yichang.kaku.home.baoyang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.mycar.PinPaiXuanZeActivity;
import com.yichang.kaku.obj.MyCarObj;
import com.yichang.kaku.request.BYChooseCarReq;
import com.yichang.kaku.request.MyCarReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.response.MyCarResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BaoYangChooseCarActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private ListView lv_baoyangchoosecar;
    private List<MyCarObj> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baoyangchoosecar);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择爱车");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("添加爱车");
        right.setOnClickListener(this);
        lv_baoyangchoosecar = (ListView) findViewById(R.id.lv_baoyangchoosecar);
        lv_baoyangchoosecar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseCar(list.get(position).getId_driver_car());
            }
        });
        GetMyCar();
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
            KaKuApplication.flag_car = "T";
            startActivity(new Intent(this, PinPaiXuanZeActivity.class));
        }
    }

    public void GetMyCar() {
        showProgressDialog();
        MyCarReq req = new MyCarReq();
        req.code = "20020";
        KaKuApiProvider.GetMyCar(req, new KakuResponseListener<MyCarResp>(this, MyCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                if (t != null) {
                    LogUtil.E("mycar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list = t.driver_cars;
                        BaoYangChooseCarAdapter adapter = new BaoYangChooseCarAdapter(context, list);
                        lv_baoyangchoosecar.setAdapter(adapter);
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

    public void ChooseCar(String id_driver_car) {
        showProgressDialog();
        BYChooseCarReq req = new BYChooseCarReq();
        req.code = "20023";
        req.id_driver_car = id_driver_car;
        KaKuApiProvider.BYChangeCar(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("login res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        finish();
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }

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
