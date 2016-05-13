package com.yichang.kaku.member.address;

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
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.AddrObj;
import com.yichang.kaku.request.AddrMorenReq;
import com.yichang.kaku.request.AddrReq;
import com.yichang.kaku.request.DeleteAddrReq;
import com.yichang.kaku.response.AddrMorenResp;
import com.yichang.kaku.response.AddrResp;
import com.yichang.kaku.response.DeleteAddrResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class AddrActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_addr;
    private Button btn_addr_new;
    private List<AddrObj> list_addr = new ArrayList<AddrObj>();
    private AddrAdapter adapter;
    private Boolean isComfirmOrder = false;
    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        initNoDataLayout();
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("收货地址");
        lv_addr = (ListView) findViewById(R.id.lv_addr);
        lv_addr.setOnItemClickListener(this);
        btn_addr_new = (Button) findViewById(R.id.btn_addr_new);
        btn_addr_new.setOnClickListener(this);
        GetAddr();
    }

    /*初始化空白页页面布局*/
    private void initNoDataLayout() {
        layout_data_none = (RelativeLayout) findViewById(R.id.layout_data_none);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_desc.setText("您还没有添加收货地址");
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
        } else if (R.id.btn_addr_new == id) {
            KaKuApplication.new_addr = "new";
            NewBuild();
        } else if (R.id.btn_refresh == id) {
            GetAddr();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Utils.Many()) {
            return;
        }
        if (KaKuApplication.IsOrderToAddr) {
            KaKuApplication.AddrObj = list_addr.get(position);
            MoRenAddr2(list_addr.get(position).getId_addr());
        }
    }

    public void NewBuild() {
        KaKuApplication.id_dizhi = "";
        KaKuApplication.county_addr = "";
        KaKuApplication.flag_addr = "Y";
        startActivity(new Intent(this, NewAddrActivity.class));
    }

    public void GetAddr() {
        if (!Utils.checkNetworkConnection(context)) {
            setNoDataLayoutState(layout_net_none);
            btn_addr_new.setVisibility(View.INVISIBLE);
            return;
        } else {
            setNoDataLayoutState(ll_container);
            btn_addr_new.setVisibility(View.VISIBLE);

        }
        showProgressDialog();
        AddrReq req = new AddrReq();
        req.code = "10014";
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.getAddr(req, new KakuResponseListener<AddrResp>(this, AddrResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("addr res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        list_addr.clear();
                        list_addr.addAll(t.addrs);

                        if (list_addr.size() == 0) {
                            setNoDataLayoutState(layout_data_none);
                            stopProgressDialog();
                            return;
                        } else {
                            setNoDataLayoutState(ll_container);
                        }

                        adapter = new AddrAdapter(AddrActivity.this, list_addr);
                        adapter.setShowProgress(new AddrAdapter.AddrInterface() {

                            @Override
                            public void MoRen(String id_addr) {
                                MoRenAddr(id_addr);
                            }

                            @Override
                            public void Delete(String id_addr) {
                                DeleteAddr(id_addr);
                            }
                        });
                        lv_addr.setAdapter(adapter);
                        Utils.setListViewHeightBasedOnChildren(lv_addr);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                    stopProgressDialog();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    public void DeleteAddr(String id_addr) {
        Utils.NoNet(context);
        showProgressDialog();
        DeleteAddrReq req = new DeleteAddrReq();
        req.code = "10016";
        req.id_addr = id_addr;
        KaKuApiProvider.DeleteAddr(req, new KakuResponseListener<DeleteAddrResp>(context, DeleteAddrResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("deleteaddr res: " + t.res);
                    LogUtil.showShortToast(context, t.msg);
                    stopProgressDialog();
                    GetAddr();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void MoRenAddr(String id_addr) {
        Utils.NoNet(context);
        showProgressDialog();
        AddrMorenReq req = new AddrMorenReq();
        req.code = "10017";
        req.id_addr = id_addr;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.MorenAddr(req, new KakuResponseListener<AddrMorenResp>(context, AddrMorenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("morenaddr res: " + t.res);
                    LogUtil.showShortToast(context, t.msg);
                    stopProgressDialog();
                    GetAddr();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void MoRenAddr2(String id_addr) {
        Utils.NoNet(context);
        showProgressDialog();
        AddrMorenReq req = new AddrMorenReq();
        req.code = "10017";
        req.id_addr = id_addr;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.MorenAddr(req, new KakuResponseListener<AddrMorenResp>(context, AddrMorenResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("morenaddr res: " + t.res);
                    LogUtil.showShortToast(context, t.msg);
                    stopProgressDialog();
                    finish();
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

}
