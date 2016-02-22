package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.CarListObj;
import com.yichang.kaku.request.CarListReq;
import com.yichang.kaku.response.CarListResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.List;

public class CarListActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv;
    private List<CarListObj> car_list;
    private CarListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carlist);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("匹配车辆");
        lv = (ListView) findViewById(R.id.lv_carlist);
        lv.setOnItemClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id_brand = bundle.getString("id_brand");
        String id_series = bundle.getString("id_series");
        String id_model = bundle.getString("id_model");
        String id_actuate = bundle.getString("id_actuate");
        String id_fuel = bundle.getString("id_fuel");
        String id_emissions = bundle.getString("id_emissions");
        String id_gonglv = bundle.getString("id_gonglv");
        CarList(id_brand, id_series, id_model, id_actuate,
                id_fuel, id_emissions, id_gonglv);
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

    public void CarList(String id_brand, String id_series, String id_model, String id_actuate,
                        String id_fuel, String id_emissions, String id_power) {
        Utils.NoNet(this);
        showProgressDialog();
        CarListReq req = new CarListReq();
        req.code = "2004";
        req.id_brand = id_brand;
        req.id_series = id_series;
        req.id_model = id_model;
        req.id_actuate = id_actuate;
        req.id_fuel = id_fuel;
        req.id_emissions = id_emissions;
        req.id_power = id_power;
        KaKuApiProvider.CarList(req, new BaseCallback<CarListResp>(CarListResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CarListResp t) {
                if (t != null) {
                    LogUtil.E("carlist res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        car_list = t.cars;
                        adapter = new CarListAdapter(CarListActivity.this, car_list);
                        lv.setAdapter(adapter);
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
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        MobclickAgent.onEvent(context, "MatchCar");
        Intent intent = new Intent(this, OkCarActivity.class);
        String id_car = car_list.get(position).getId_car();
        intent.putExtra("id_car", id_car);
        startActivity(intent);
    }
}
