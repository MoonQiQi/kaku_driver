package com.yichang.kaku.view.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.LineGridView;
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class AreaPopWindow extends PopupWindow {

    private Activity context;
    private RelativeLayout rela_zhaohuo_grid;
    private String id_area = "";
    private TextView tv_pup_left,tv_pup_mid,tv_pup_right;
    private String id_type = "province";
    private LineGridView gv_city;
    private CityAdapter adapter;
    private List<AreaObj> list_province = new ArrayList<AreaObj>();
    private String tv_name;
    private String id_province,id_city,id_county;

    public AreaPopWindow(final Activity context) {
        super(context);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#77000000")));
        setOutsideTouchable(false);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.area_pupwindow, null);
        setContentView(view);
        init(view);
        GetProvince();

    }

    private void init(View view){
        rela_zhaohuo_grid= (RelativeLayout) view.findViewById(R.id.rela_shoplist_grid);
        rela_zhaohuo_grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_pup_mid= (TextView) view.findViewById(R.id.tv_pupp_mid);
        tv_pup_left= (TextView) view.findViewById(R.id.tv_pupp_left);
        tv_pup_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rela_zhaohuo_grid.setVisibility(View.GONE);
                id_type = "province";
                tv_pup_mid.setText("品牌");
                tv_pup_right.setVisibility(View.GONE);
            }
        });
        tv_pup_right= (TextView) view.findViewById(R.id.tv_pupp_right);
        tv_pup_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rela_zhaohuo_grid.setVisibility(View.GONE);
                tv_pup_mid.setText("中国");
                tv_pup_right.setVisibility(View.GONE);
                mCallBack.close(tv_name,id_area);
            }
        });
        gv_city = (LineGridView)view.findViewById(R.id.gv_shoplist_city);

        gv_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int parentId = parent.getId();
                if (R.id.gv_shoplist_city == parentId) {
                    if ("province".equals(id_type)) {
                        id_province = list_province.get(position).getId_area();
                        tv_pup_mid.setText(list_province.get(position).getName_area());
                        GetCity(id_province);
                        id_type = "city";
                        id_area = list_province.get(position).getId_area();
                        tv_name = list_province.get(position).getName_area();
                    } else if ("city".equals(id_type)) {
                        id_city = list_province.get(position).getId_area();
                        tv_pup_mid.setText(list_province.get(position).getName_area());
                        GetCounty(id_city);
                        id_type = "county";
                        id_area = list_province.get(position).getId_area();
                        tv_name = list_province.get(position).getName_area();
                    } else if ("county".equals(id_type)) {
                        id_county = list_province.get(position).getId_area();
                        tv_pup_mid.setText(list_province.get(position).getName_area());
                        id_type = "province";
                        rela_zhaohuo_grid.setVisibility(View.GONE);
                        id_area = list_province.get(position).getId_area();
                        tv_name = list_province.get(position).getName_area();
                        mCallBack.close(tv_name,id_area);
                    }
                }
            }
        });
        tv_pup_right.setVisibility(View.GONE);

    }

    public void GetProvince() {
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = "0";
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context,AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.areas.size() != 0) {
                            list_province = t.areas;
                            id_type = "province";
                            adapter = new CityAdapter(context, list_province);
                            gv_city.setAdapter(adapter);
                            rela_zhaohuo_grid.setVisibility(View.VISIBLE);
                        }
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    public void GetCity(String id_province) {
        Utils.NoNet(context);
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_province;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context,AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.areas.size() != 0) {
                            list_province = t.areas;
                            adapter = new CityAdapter(context, list_province);
                            gv_city.setAdapter(adapter);
                            tv_pup_right.setVisibility(View.VISIBLE);
                        }
                    }else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void GetCounty(String id_city) {
        Utils.NoNet(context);
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_city;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(context,AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.areas.size() != 0) {
                            list_province = t.areas;
                            adapter = new CityAdapter(context, list_province);
                            gv_city.setAdapter(adapter);
                        }
                    }else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

            @Override
            public void onFailed(int i, Response response) {

            }


        });
    }

    public void show() {
        showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    ;public interface ShowPopWindowCallBack{
        void close(String sName,String mId);
    }

    private ShowPopWindowCallBack mCallBack;

    public void setShowPopWindowCallBack(ShowPopWindowCallBack callBack){
        this.mCallBack=callBack;

    }
}
