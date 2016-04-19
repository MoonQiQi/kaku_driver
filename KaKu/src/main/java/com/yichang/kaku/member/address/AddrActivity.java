package com.yichang.kaku.member.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import com.yichang.kaku.response.AddrMorenResp;
import com.yichang.kaku.response.AddrResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class AddrActivity extends BaseActivity implements OnClickListener, AdapterView.OnItemClickListener {

    private TextView left, right, title;
    private ListView lv_addr;
    private Button btn_addr_new;
    private List<AddrObj> list_addr = new ArrayList<AddrObj>();
    private AddrAdapter adapter;

    private Boolean isComfirmOrder = false;

    		/*无数据和无网络界面*/

    private RelativeLayout layout_data_none, layout_net_none;
    private TextView tv_desc, tv_advice;
    private Button btn_refresh;
    private LinearLayout ll_container;

    //private int itemPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (KaKuApplication.isEditAddr) {
            KaKuApplication.isEditAddr = false;
            int itemPosition = KaKuApplication.itemPosition;
            AddrObj obj = list_addr.get(itemPosition);

            obj.setAddr(KaKuApplication.dizhi_addr);
            obj.setName_addr(KaKuApplication.name_addr);
            obj.setPhone_addr(KaKuApplication.phone_addr);

            list_addr.set(itemPosition, obj);
            adapter.notifyDataSetChanged();
        }
        GetAddr();
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

        isComfirmOrder = getIntent().getBooleanExtra("flag", false);
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
            Intent intent = new Intent();
            intent.putExtra("name", "");
            intent.putExtra("phone", "");
            intent.putExtra("addr", "");
            setResult(125, intent);
            finish();
        } else if (R.id.btn_addr_new == id) {
            NewBuild();
        } else if (R.id.btn_refresh == id) {
            GetAddr();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.E("条目位置：" + position);
        //增加多次点击判断
        if (Utils.Many()) {
            return;
        }
        if (isComfirmOrder) {
            Intent intent = new Intent();
            intent.putExtra("name", list_addr.get(position).getName_addr());
            intent.putExtra("phone", list_addr.get(position).getPhone_addr());
            intent.putExtra("addr", list_addr.get(position).getAddr());
            setResult(121, intent);
            finish();
        } else if ("Prize".equals(KaKuApplication.flag_addr)){
            Intent intent = new Intent();
            intent.putExtra("name", list_addr.get(position).getName_addr());
            intent.putExtra("phone", list_addr.get(position).getPhone_addr());
            intent.putExtra("addr", list_addr.get(position).getAddr());
            setResult(0, intent);
            KaKuApplication.flag_addr = "";
            finish();
        } else {
            if (!Utils.checkNetworkConnection(context)) {
                setNoDataLayoutState(layout_net_none);

                return;
            } else {
                setNoDataLayoutState(ll_container);

            }
            final int positionf = position;
            AddrMorenReq req = new AddrMorenReq();
            req.code = "10017";
            req.id_addr = list_addr.get(position).getId_addr();
            req.id_driver = Utils.getIdDriver();
            KaKuApiProvider.MorenAddr(req, new KakuResponseListener<AddrMorenResp>(this, AddrMorenResp.class) {
                @Override
                public void onSucceed(int what, Response response) {
                    super.onSucceed(what, response);
                    if (t != null) {
                        LogUtil.E("morenaddr res: " + t.res);
                        if (Constants.RES.equals(t.res)) {

                            for (int i = 0; i < list_addr.size(); i++) {
                                list_addr.get(i).setFlag_default("N");
                            }
                            list_addr.get(positionf).setFlag_default("Y");
                            adapter.notifyDataSetChanged();
                        } else {
                            LogUtil.showShortToast(context, t.msg);
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void NewBuild() {
        KaKuApplication.id_dizhi = "";
        KaKuApplication.county_addr = "";
        KaKuApplication.dizhi_addr = "";
        KaKuApplication.name_addr = "";
        KaKuApplication.phone_addr = "";
        KaKuApplication.flag_addr = "Y";
        KaKuApplication.province_addrname = "";
        KaKuApplication.city_addrname = "";
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

                        LogUtil.E("addr size" + list_addr.size());
                        if (list_addr.size() == 0) {
                            LogUtil.E("addr size" + list_addr.size());
                            setNoDataLayoutState(layout_data_none);
                            stopProgressDialog();
                            return;
                        } else {
                            setNoDataLayoutState(ll_container);
                        }

                        adapter = new AddrAdapter(AddrActivity.this, list_addr);
                        lv_addr.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(lv_addr);
                        Utils.setListViewHeightBasedOnChildren(lv_addr);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }

    private void setNoDataLayoutState(View view) {
        layout_data_none.setVisibility(View.GONE);
        ll_container.setVisibility(View.GONE);
        layout_net_none.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;

        listView.setLayoutParams(params);
    }
}
