package com.yichang.kaku.member.cash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.AreaObj;
import com.yichang.kaku.request.AreaReq;
import com.yichang.kaku.response.AreaResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yichang.kaku.zhaohuo.province.ProvinceAdapter;
import com.yolanda.nohttp.Response;

import java.util.ArrayList;
import java.util.List;

public class BankProvinceActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;
    private ListView lv_bank_list;

    private List<AreaObj> mProvinceList=new ArrayList<>();
    private String mProvince;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_bank_list);

        init();
    }

    private void init() {
        initTitleBar();
        lv_bank_list = (ListView) findViewById(R.id.lv_bank_list);


        getProvinceList();
        /*lv_bank_list.setAdapter(new ArrayAdapter<String>(this, R.layout.item_bank_list, mBankName));
        lv_bank_list.setOnItemClickListener(this);*/

    }

    public void getProvinceList() {
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = "0";
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(this, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.areas.size() != 0) {
                           /* list_province = t.areas;
                            id_type = "province";
                            adapter = new CityAdapter(mActivity, list_province);
                            gv_city.setAdapter(adapter);
                            rela_zhaohuo_grid.setVisibility(View.VISIBLE);*/
                            mProvinceList.clear();
                            mProvinceList.addAll(t.areas);

                            lv_bank_list.setAdapter(new ProvinceAdapter(context, mProvinceList));
                            lv_bank_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    LogUtil.E("Province:" + mProvinceList.get(position));
                                    startActivityForResult(new Intent(context, BankCityActivity.class).putExtra("provinceId", mProvinceList.get(position).getId_area()), AddBankCardActivity.BANK_CITY_REQUEST);
                                }
                            });
                        }
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }
        });
    }


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择省份");
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(getApplicationContext());
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*super.onActivityResult(requestCode, resultCode, data);*/
        //数据是使用Intent返回
       /* Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("bankCity", data.getStringExtra("bankCity"));
        //设置返回数据*/
        if(!TextUtils.isEmpty(data.getStringExtra("bankCity"))){

            this.setResult(AddBankCardActivity.BANK_CITY_RESPONSE_SUCCESS, data);
            //关闭Activity
            this.finish();
        }
    }
}
