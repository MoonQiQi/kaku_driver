package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CarData2Obj;
import com.yichang.kaku.request.XuanFaDongJiReq;
import com.yichang.kaku.response.XuanFaDongJiResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class FaDongJiInfoActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private GridView gv_fadongji1, gv_fadongji2, gv_fadongji3, gv_fadongji4;
    private List<CarData2Obj> list_fadongji1 = new ArrayList<>();
    private List<CarData2Obj> list_fadongji2 = new ArrayList<>();
    private List<CarData2Obj> list_fadongji3 = new ArrayList<>();
    private List<CarData2Obj> list_fadongji4 = new ArrayList<>();
    private String id_data1 = "", id_data2 = "", id_data3 = "", id_data4 = "";
    private FaDongJiAdapter adapter1;
    private FaDongJiAdapter adapter2;
    private FaDongJiAdapter adapter3;
    private FaDongJiAdapter adapter4;
    private TextView tv_fadongji1, tv_fadongji2, tv_fadongji3, tv_fadongji4;
    private RelativeLayout rela_fadongji1, rela_fadongji2, rela_fadongji3, rela_fadongji4;
    private Button btn_fadongji_next;
    private ScrollView scroll_fadongji;
    android.os.Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadongjiinfo);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("发动机信息");
        scroll_fadongji = (ScrollView) findViewById(R.id.scroll_fadongji);
        tv_fadongji1 = (TextView) findViewById(R.id.tv_fadongji1);
        tv_fadongji2 = (TextView) findViewById(R.id.tv_fadongji2);
        tv_fadongji3 = (TextView) findViewById(R.id.tv_fadongji3);
        tv_fadongji4 = (TextView) findViewById(R.id.tv_fadongji4);
        rela_fadongji1 = (RelativeLayout) findViewById(R.id.rela_fadongji1);
        rela_fadongji2 = (RelativeLayout) findViewById(R.id.rela_fadongji2);
        rela_fadongji3 = (RelativeLayout) findViewById(R.id.rela_fadongji3);
        rela_fadongji4 = (RelativeLayout) findViewById(R.id.rela_fadongji4);
        gv_fadongji1 = (GridView) findViewById(R.id.gv_fadongji1);
        gv_fadongji1.setOnItemClickListener(this);
        gv_fadongji2 = (GridView) findViewById(R.id.gv_fadongji2);
        gv_fadongji2.setOnItemClickListener(this);
        gv_fadongji3 = (GridView) findViewById(R.id.gv_fadongji3);
        gv_fadongji3.setOnItemClickListener(this);
        gv_fadongji4 = (GridView) findViewById(R.id.gv_fadongji4);
        gv_fadongji4.setOnItemClickListener(this);
        btn_fadongji_next = (Button) findViewById(R.id.btn_fadongji_next);
        btn_fadongji_next.setOnClickListener(this);
        GetFaDongJi("1");
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
        } else if (R.id.btn_fadongji_next == id) {
            Intent intent = new Intent(this, AddCarActivity.class);
            intent.putExtra("id_data", id_data4);
            startActivity(intent);
        }
    }

    public void GetFaDongJi(final String step) {
        showProgressDialog();
        XuanFaDongJiReq req = new XuanFaDongJiReq();
        req.code = "20017";
        req.id_brand = KaKuApplication.id_brand;
        req.step = step;
        req.id_data1 = id_data1;
        req.id_data2 = id_data2;
        req.id_data3 = id_data3;
        req.car_code = KaKuApplication.car_code;
        KaKuApiProvider.XuanFaDongJi(req, new KakuResponseListener<XuanFaDongJiResp>(context, XuanFaDongJiResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("getfadongji res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if ("1".equals(step)) {
                            list_fadongji1 = t.data;
                            adapter1 = new FaDongJiAdapter(context, list_fadongji1);
                            gv_fadongji1.setAdapter(adapter1);
                            tv_fadongji1.setVisibility(View.VISIBLE);
                        } else if ("2".equals(step)) {
                            list_fadongji2 = t.data;
                            adapter2 = new FaDongJiAdapter(context, list_fadongji2);
                            gv_fadongji2.setAdapter(adapter2);
                            tv_fadongji2.setVisibility(View.VISIBLE);
                            rela_fadongji2.setVisibility(View.VISIBLE);
                            tv_fadongji3.setVisibility(View.GONE);
                            rela_fadongji3.setVisibility(View.GONE);
                            tv_fadongji4.setVisibility(View.GONE);
                            rela_fadongji4.setVisibility(View.GONE);
                        } else if ("3".equals(step)) {
                            list_fadongji3 = t.data;
                            adapter3 = new FaDongJiAdapter(context, list_fadongji3);
                            gv_fadongji3.setAdapter(adapter3);
                            tv_fadongji3.setVisibility(View.VISIBLE);
                            rela_fadongji3.setVisibility(View.VISIBLE);
                            tv_fadongji4.setVisibility(View.GONE);
                            rela_fadongji4.setVisibility(View.GONE);
                        } else if ("4".equals(step)) {
                            list_fadongji4 = t.data;
                            adapter4 = new FaDongJiAdapter(context, list_fadongji4);
                            gv_fadongji4.setAdapter(adapter4);
                            tv_fadongji4.setVisibility(View.VISIBLE);
                            rela_fadongji4.setVisibility(View.VISIBLE);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                scroll_fadongji.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (R.id.gv_fadongji1 == parent.getId()) {
            for (int i = 0; i < list_fadongji1.size(); i++) {
                list_fadongji1.get(i).setColor_type("B");
            }
            id_data1 = list_fadongji1.get(position).getId_data();
            list_fadongji1.get(position).setColor_type("R");
            adapter1.notifyDataSetChanged();
            btn_fadongji_next.setVisibility(View.INVISIBLE);
            GetFaDongJi("2");
        } else if (R.id.gv_fadongji2 == parent.getId()) {
            for (int i = 0; i < list_fadongji2.size(); i++) {
                list_fadongji2.get(i).setColor_type("B");
            }
            id_data2 = list_fadongji2.get(position).getId_data();
            list_fadongji2.get(position).setColor_type("R");
            adapter2.notifyDataSetChanged();
            btn_fadongji_next.setVisibility(View.INVISIBLE);
            GetFaDongJi("3");
        } else if (R.id.gv_fadongji3 == parent.getId()) {
            for (int i = 0; i < list_fadongji3.size(); i++) {
                list_fadongji3.get(i).setColor_type("B");
            }
            id_data3 = list_fadongji3.get(position).getId_data();
            list_fadongji3.get(position).setColor_type("R");
            adapter3.notifyDataSetChanged();
            btn_fadongji_next.setVisibility(View.INVISIBLE);
            GetFaDongJi("4");
        } else if (R.id.gv_fadongji4 == parent.getId()) {
            for (int i = 0; i < list_fadongji4.size(); i++) {
                list_fadongji4.get(i).setColor_type("B");
            }
            id_data4 = list_fadongji4.get(position).getId_data();
            list_fadongji4.get(position).setColor_type("R");
            adapter4.notifyDataSetChanged();
            btn_fadongji_next.setVisibility(View.VISIBLE);
        }
    }
}
