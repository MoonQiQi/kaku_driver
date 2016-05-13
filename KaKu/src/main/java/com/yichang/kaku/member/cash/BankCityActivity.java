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
import com.yichang.kaku.zhaohuo.province.CityAdapter;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class BankCityActivity extends BaseActivity implements OnClickListener {
    //    titleBar
    private TextView title, left, right;
    private ListView lv_bank_list;

    private List<AreaObj> mCityList=new ArrayList<>();
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
        mProvince = getIntent().getStringExtra("provinceId");
        if (!TextUtils.isEmpty(mProvince)) {
            getCityList(mProvince);
        }else {
            finish();
        }
    }

    public void getCityList(String id_province) {
        Utils.NoNet(context);
        AreaReq req = new AreaReq();
        req.code = "10018";
        req.id_area = id_province;
        KaKuApiProvider.Area(req, new KakuResponseListener<AreaResp>(this, AreaResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("area res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.areas.size() != 0) {
                            /*list_province = t.areas;
                            adapter = new CityAdapter(mActivity, list_province);
                            gv_city.setAdapter(adapter);
                            tv_pup_right.setVisibility(View.VISIBLE);*/
                            mCityList.clear();
                            mCityList.addAll(t.areas);
                            lv_bank_list.setAdapter(new CityAdapter(context, mCityList));
                            lv_bank_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent();
                                    //把返回数据存入Intent
                                    intent.putExtra("bankCity", mCityList.get(position).getName_area());
                                    //设置返回数据
                                    BankCityActivity.this.setResult(AddBankCardActivity.BANK_CITY_RESPONSE_SUCCESS, intent);
                                    //关闭Activity
                                    BankCityActivity.this.finish();
                                }
                            });
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


    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);

        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("选择城市");
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(getApplicationContext());
        if (Utils.Many()) {
            return;
        }

        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("bankCity", "");
            //设置返回数据
            this.setResult(AddBankCardActivity.BANK_CITY_RESPONSE_SUCCESS, intent);
            finish();
        }
    }

}
