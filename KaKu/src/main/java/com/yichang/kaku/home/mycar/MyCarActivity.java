package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.shop.PinPaiFuWuZhanActivity;
import com.yichang.kaku.obj.MyCarObj;
import com.yichang.kaku.request.DeleteMyCarReq;
import com.yichang.kaku.request.MoRenCarReq;
import com.yichang.kaku.request.MyCarReq;
import com.yichang.kaku.response.DeleteMyCarResp;
import com.yichang.kaku.response.MoRenCarResp;
import com.yichang.kaku.response.MyCarResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.popwindow.MenDianPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.List;

public class MyCarActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private RelativeLayout  titlebar_mycar;

    private TextView left, right, title;
    private ListView lv;
    private List<MyCarObj> car_list;
    private MyCarAdapter adapter;
    /*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private ScrollView sv_top;
    private Boolean isToMember;

    private ImageView iv_nodata;

    private LinearLayout ll_warning;
    private Button btn_addcar;
    private Boolean isPwdPopWindowShow = false;
    private MenDianPopWindow input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        isToMember = getIntent().getBooleanExtra("isToMember", false);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        GetMyCar();

    }

    private void init() {
        // TODO Auto-generated method stub

        initTitleBar();
        initNoDataLayout();
        btn_addcar = (Button) findViewById(R.id.btn_addcar);
        btn_addcar.setOnClickListener(this);

        lv = (ListView) findViewById(R.id.lv_mycar);
        lv.setOnItemClickListener(this);
        lv.setOverScrollMode(View.OVER_SCROLL_NEVER);

        sv_top = (ScrollView) findViewById(R.id.sv_top);
        // sv_top.setOverScrollMode(View.OVER_SCROLL_NEVER);


        ll_warning = (LinearLayout) findViewById(R.id.ll_warning);
//		setListViewHeightBasedOnChildren(lv);
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        iv_nodata = (ImageView) findViewById(R.id.iv_nodata);
        iv_nodata.setImageResource(R.drawable.bg_truck);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("添加爱车，可为您匹配保养项目哦");
        tv_advice = (TextView) findViewById(R.id.tv_advice);
        tv_advice.setVisibility(View.GONE);

        layout_net_none = (RelativeLayout) findViewById(R.id.layout_net_none);

        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);
    }

    private void initTitleBar() {
        titlebar_mycar= (RelativeLayout) findViewById(R.id.titlebar_mycar);

        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的爱车");

    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            if (isToMember) {
                goToMember();
            } else {
                goToHome();
            }
        } else if (R.id.btn_refresh == id) {
            GetMyCar();
        } else if (R.id.btn_addcar == id) {
            int carSize = getCarSize();

            if (carSize >= 5) {
                LogUtil.showShortToast(context, "最多只能添加五辆爱车");
                return;
            }
            MobclickAgent.onEvent(context, "AddCar");
            GoToPinPaiXuanZe();
        }

    }

    public void GetMyCar() {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);
            return;
        } else {
            setNoDataLayoutState(sv_top);
        }
        showProgressDialog();
        MyCarReq req = new MyCarReq();
        req.code = "2002";
        req.id_driver = Utils.getIdDriver();
        req.id_car = Utils.getIdCar();
        KaKuApiProvider.GetMyCar(req, new KakuResponseListener<MyCarResp>(this, MyCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                if (t != null) {
                    LogUtil.E("mycar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        stopProgressDialog();
                        if (TextUtils.equals(t.flag_enter, "N")) {
                            if (!"".equals(Utils.getIdCar())) {
                                if (!isFinishing()){
                                    showPwdInputWindow(t.mobile_brand);
                                }
                            }
                        }
                        car_list = t.driver_cars;

                        if (car_list.size() == 0) {

                            setNoDataLayoutState(layout_data_none);
                            return;
                        } else {
                            setNoDataLayoutState(sv_top);
                        }
                        int carSize = getCarSize();
                        if (carSize >= 5) {
                            ll_warning.setVisibility(View.VISIBLE);

                        } else {
                            ll_warning.setVisibility(View.GONE);
                        }
                        adapter = new MyCarAdapter(MyCarActivity.this, car_list);
                        adapter.setButtonState(new MyCarAdapter.IChangeButtonState() {

                            @Override
                            public void changeDefaultCar(String id_driver_car, int position) {
                                MoRen(id_driver_car, position);
                            }

                            @Override
                            public void deleteCar(String id_driver_car, int position) {
                                Delete(id_driver_car, position);
                            }
                        });
                        lv.setAdapter(adapter);
                        Utils.setListViewHeightBasedOnChildren(lv);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }

            }

        });
    }

    public void MoRen(String id_driver_car, final int position) {
        Utils.NoNet(context);
        MoRenCarReq req = new MoRenCarReq();
        req.code = "2006";
        req.id_driver = Utils.getIdDriver();
        req.id_driver_car = id_driver_car;
        KaKuApiProvider.MoRenMyCar(req, new KakuResponseListener<MoRenCarResp>(this, MoRenCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("morencar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (TextUtils.equals(t.flag_enter, "N")) {
                            if (!"".equals(Utils.getIdCar())) {
                                if (!isDestroyed())
                                showPwdInputWindow(t.mobile_brand);
                            }
                        }
                        for (int i = 0; i < car_list.size(); i++) {
                            car_list.get(i).setFlag_default("N");
                        }
                        car_list.get(position).setFlag_default("Y");
                        adapter.notifyDataSetChanged();
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    public void Delete(String id_driver_car, final int position) {
        Utils.NoNet(context);
        DeleteMyCarReq req = new DeleteMyCarReq();
        req.code = "2007";
        req.id_driver_car = id_driver_car;
        KaKuApiProvider.DeleteMyCar(req, new KakuResponseListener<DeleteMyCarResp>(this, DeleteMyCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("deletecar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        car_list.remove(position);
                        adapter.notifyDataSetChanged();
                        int carSize = getCarSize();
                        if (carSize >= 5) {
                            btn_addcar.setEnabled(false);
                            ll_warning.setVisibility(View.VISIBLE);

                        } else {
                            btn_addcar.setEnabled(true);
                            ll_warning.setVisibility(View.GONE);
                        }
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }

    private int getCarSize() {
        int size = 0;
        for (MyCarObj obj : car_list) {
            if (obj.getFlag_check().equals("Y")) {
                size++;
            }
        }
        return size;
    }

    public void GoToPinPaiXuanZe() {
        startActivity(new Intent(this, PinPaiXuanZeActivity.class));
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    private void goToMember() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_MEMBER);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isToMember) {
                goToMember();
            } else {
                goToHome();
            }
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (car_list.get(position).getFlag_check().equals("Y")) {

            String id_driver_car = car_list.get(position).getId_driver_car();
            MoRen(id_driver_car);
            SharedPreferences.Editor editor = KaKuApplication.editor;
            editor.putString(Constants.IDCAR, id_driver_car);
            editor.commit();
        } else if (car_list.get(position).getFlag_check().equals("N")) {
            LogUtil.showShortToast(this, "车辆信息审核中，请耐心等待");
        }
    }

    public void MoRen(String id_driver_car) {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);

            return;
        } else {
            setNoDataLayoutState(sv_top);

        }
        showProgressDialog();
        MoRenCarReq req = new MoRenCarReq();
        req.code = "2006";
        req.id_driver = Utils.getIdDriver();
        req.id_driver_car = id_driver_car;
        KaKuApiProvider.MoRenMyCar(req, new KakuResponseListener<MoRenCarResp>(this, MoRenCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("morencar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        Intent intent = new Intent(context, PinPaiFuWuZhanActivity.class);
                        startActivity(intent);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }
        });
    }


    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        sv_top.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    private void showPwdInputWindow(final String phone) {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPwdPopWindowShow = true;
                input = new MenDianPopWindow(MyCarActivity.this ,phone);

                input.show();

            }
        }, 0);
    }

}
