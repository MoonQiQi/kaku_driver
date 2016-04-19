package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.EditCarInfoReq;
import com.yichang.kaku.response.EditCarInfoResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.Response;

public class EditCarInfosActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private RelativeLayout rela_car_series, rela_oil_type, rela_car_type, rela_driver_type, rela_max_hp, rela_emission_standards, rela_car_engine;
    private TextView tv_car_series, tv_oil_type, tv_car_type, tv_driver_type, tv_max_hp, tv_emission_standards, tv_car_engine;
    private ImageView iv_car_brand;
    private TextView tv_car_brand;

    private String id_brand = "1";
    private String img_url;
    private String name_brand;

    private String str_car_series, str_oil_type, str_car_type, str_driver_type, str_max_hp, str_emission_standards, str_car_engine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_carinfo_edit);
        init();
    }

    @Override
    protected void onStart() {
        if (!TextUtils.isEmpty(KaKuApplication.editCarInfoType)) {
            if (!TextUtils.isEmpty(KaKuApplication.editCarInfo)) {
                switch (KaKuApplication.editCarInfoType) {
                    case "series":
                        tv_car_series.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                    case "oil":
                        tv_oil_type.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                    case "cartype":
                        tv_car_type.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                    case "driver":
                        tv_driver_type.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                    case "hp":
                        tv_max_hp.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                    case "es":
                        tv_emission_standards.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                    case "engine":
                        tv_car_engine.setText(KaKuApplication.editCarInfo);
                        KaKuApplication.editCarInfo = "";
                        break;
                }
            }
        }
        super.onStart();
    }

    private void init() {
        // TODO Auto-generated method stub
        initTitleBar();
        getCarInfos();
        initRelaLayout();
        initTextView();

        initBrand();


    }

    private void initBrand() {
        iv_car_brand = (ImageView) findViewById(R.id.iv_car_brand);
        /*iv_car_brand.setImageResource(R.drawable.logo);*/
        BitmapUtil.getInstance(this).download(iv_car_brand, KaKuApplication.qian_zhui + img_url);
        tv_car_brand = (TextView) findViewById(R.id.tv_car_brand);
        tv_car_brand.setText(name_brand);

    }

    private void getCarInfos() {
/*todo 增加CarInfo的图片信息*/
        /*id_brand = getIntent().getStringExtra("id_brand");
        name_brand = getIntent().getStringExtra("name_brand");
        img_url=getIntent().getStringExtra("img_url");*/
        Bundle bundle = this.getIntent().getExtras();
        id_brand = bundle.get("id_brand").toString();
        name_brand = bundle.get("name_brand").toString();
        img_url = bundle.get("img_url").toString();



        /*id_brand=getIntent().getBundleExtra("id_brand").toString();
        name_brand=getIntent().getBundleExtra("name_brand").toString();
        img_url=getIntent().getBundleExtra("img_url").toString();*/

        str_car_series = bundle.get("name_series").toString();
        str_oil_type = bundle.get("name_fuel").toString();
        str_car_type = bundle.get("name_model").toString();
        str_driver_type = bundle.get("name_actuate").toString();
        str_max_hp = bundle.get("name_power").toString();
        str_emission_standards = bundle.get("name_emissions").toString();
        str_car_engine = bundle.get("name_engine").toString();

    }

    private void initTextView() {
        tv_car_series = (TextView) findViewById(R.id.tv_car_series);
        tv_car_series.setText(str_car_series);
        tv_oil_type = (TextView) findViewById(R.id.tv_oil_type);
        tv_oil_type.setText(str_oil_type);
        tv_car_type = (TextView) findViewById(R.id.tv_car_type);
        tv_car_type.setText(str_car_type);
        tv_driver_type = (TextView) findViewById(R.id.tv_driver_type);
        tv_driver_type.setText(str_driver_type);
        tv_max_hp = (TextView) findViewById(R.id.tv_max_hp);
        tv_max_hp.setText(str_max_hp);
        tv_emission_standards = (TextView) findViewById(R.id.tv_emission_standards);
        tv_emission_standards.setText(str_emission_standards);
        tv_car_engine = (TextView) findViewById(R.id.tv_car_engine);
        tv_car_engine.setText(str_car_engine);
    }

    private void initRelaLayout() {
        rela_car_series = (RelativeLayout) findViewById(R.id.rela_car_series);
        rela_car_series.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改车系";
                KaKuApplication.editCarInfoType = "series";
                gotoEditPage(tv_car_series.getText().toString().trim());
            }
        });
        rela_oil_type = (RelativeLayout) findViewById(R.id.rela_oil_type);
        rela_oil_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改燃油种类";
                KaKuApplication.editCarInfoType = "oil";
                gotoEditPage(tv_oil_type.getText().toString().trim());
            }
        });
        rela_car_type = (RelativeLayout) findViewById(R.id.rela_car_type);
        rela_car_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改车型";
                KaKuApplication.editCarInfoType = "cartype";
                gotoEditPage(tv_car_type.getText().toString().trim());
            }
        });
        rela_driver_type = (RelativeLayout) findViewById(R.id.rela_driver_type);
        rela_driver_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改驱动形式";
                KaKuApplication.editCarInfoType = "driver";
                gotoEditPage(tv_driver_type.getText().toString().trim());
            }
        });
        rela_max_hp = (RelativeLayout) findViewById(R.id.rela_max_hp);
        rela_max_hp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改最大马力";
                KaKuApplication.editCarInfoType = "hp";
                gotoEditPage(tv_max_hp.getText().toString().trim());
            }
        });
        rela_emission_standards = (RelativeLayout) findViewById(R.id.rela_emission_standards);
        rela_emission_standards.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改排放标准";
                KaKuApplication.editCarInfoType = "es";
                gotoEditPage(tv_emission_standards.getText().toString().trim());
            }
        });
        rela_car_engine = (RelativeLayout) findViewById(R.id.rela_car_engine);
        rela_car_engine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KaKuApplication.editCarInfoPageTitle = "修改发动机";
                KaKuApplication.editCarInfoType = "engine";
                gotoEditPage(tv_car_engine.getText().toString().trim());
            }
        });
    }

    //跳转到编辑页面
    private void gotoEditPage(String param) {
        Intent intent = new Intent(this, EditCarInfoPageActivity.class);
        intent.putExtra("param", param);
        startActivity(intent);
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("填写车辆资料");

        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setOnClickListener(this);
        right.setText("保存");
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
            saveCarInfos();
        }
    }

    private void saveCarInfos() {
        /*todo*/
        Utils.NoNet(context);

        EditCarInfoReq req = new EditCarInfoReq();
        req.code = "20013";
        req.id_brand = id_brand;
        if (!TextUtils.isEmpty(tv_car_series.getText().toString().trim())) {
            req.data_series = tv_car_series.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "车系信息不能为空");
            return;
        }
        if (!TextUtils.isEmpty(tv_oil_type.getText().toString().trim())) {
            req.data_fuel = tv_oil_type.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "燃油种类不能为空");
            return;
        }
        if (!TextUtils.isEmpty(tv_car_type.getText().toString().trim())) {
            req.data_model = tv_car_type.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "车型信息不能为空");
            return;
        }
        if (!TextUtils.isEmpty(tv_driver_type.getText().toString().trim())) {
            req.data_actuate = tv_driver_type.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "驱动形式信息不能为空");
            return;
        }

        if (!TextUtils.isEmpty(tv_max_hp.getText().toString().trim())) {
            req.data_power = tv_max_hp.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "最大马力信息不能为空");
            return;
        }

        if (!TextUtils.isEmpty(tv_emission_standards.getText().toString().trim())) {
            req.data_emissions = tv_emission_standards.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "排放标准不能为空");
            return;
        }

        if (!TextUtils.isEmpty(tv_car_engine.getText().toString().trim())) {
            req.data_engine = tv_car_engine.getText().toString().trim();
        } else {
            LogUtil.showShortToast(this, "发动机型号信息不能为空");
            return;
        }
        KaKuApiProvider.submitCarInfos(req, new KakuResponseListener<EditCarInfoResp>(this, EditCarInfoResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {

                    LogUtil.E("submitCarInfos res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        //提交数据
                        finish();
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
            }

        });
    }
}
