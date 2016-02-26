package com.yichang.kaku.home.weizhang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.request.CityInfoReq;
import com.yichang.kaku.request.IllegalDriverInfoReq;
import com.yichang.kaku.request.IllegalQueryReq;
import com.yichang.kaku.response.IllegalDirverInfoResp;
import com.yichang.kaku.response.IllegalQueryResp;
import com.yichang.kaku.response.IllegalResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.ChooseCityListPopWindow;
import com.yichang.kaku.view.IllegalPopWindow;
import com.yichang.kaku.view.LicenseChoosePop;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

/**
 * Created by xiaosu on 2015/11/9.
 * 违章查询
 */
public class IllegalQueryActivity extends BaseActivity implements AdapterView.OnItemClickListener, ChooseCityListPopWindow.OnProvinceChooseListener {

    private static final int CITY_REQUEST = 1000;
    private IllegalResp resp;

    private TextView license_plate_number;
    private LicenseChoosePop licenseChoosePop;
    private View rl_carEngine;
    private TextView name_city;
    private View rl_vehicle_frame;
    private EditText carNum;
    private EditText vehicle_frame;
    private EditText engineNum;
    private View line_vehicle_frame;
    private LinearLayout ll_look_for;

    private int groupPosition = 0;
    private int childPosition = 0;
    private View lineCar;
    private ChooseCityListPopWindow chooseCityListPopWindow;
    private View rl_license;

    private Boolean isChooseCity=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illegal_query);
        initView();

        getCityInfo();
    }

    private void getIllegalDriverInfo() {
        showProgressDialog();

        IllegalDriverInfoReq req = new IllegalDriverInfoReq();
        req.code = "9000";
        req.id_driver = Utils.getIdDriver();


        KaKuApiProvider.getIllegalDriverInfo(req, new BaseCallback<IllegalDirverInfoResp>(IllegalDirverInfoResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, IllegalDirverInfoResp t) {
                if (Constants.RES.equals(t.res)) {
                    stopProgressDialog();
                    LogUtil.E("chaih " + t.driver.toString());

                    if (TextUtils.isEmpty(t.driver.getCarnumber()) && TextUtils.isEmpty(t.driver.getCarcode()) && TextUtils.isEmpty(t.driver.getCarcode())) {
//省市


                    } else {
                        name_city.setText(t.driver.getCarci());
                        //车牌短内容
                        license_plate_number.setText(t.driver.getCarno());
                        //车牌号
                        carNum.setText(t.driver.getCarnumber());
                        //车架号
                        if (TextUtils.isEmpty(t.driver.getCarcode())) {
                            rl_vehicle_frame.setVisibility(View.GONE);
                            vehicle_frame.setText("");

                        } else {
                            rl_vehicle_frame.setVisibility(View.VISIBLE);
                            vehicle_frame.setText(t.driver.getCarcode());
                        }
                        //发动机号
                        if (TextUtils.isEmpty(t.driver.getCardrivenumber())) {
                            rl_carEngine.setVisibility(View.GONE);
                            engineNum.setText("");
                        } else {
                            rl_carEngine.setVisibility(View.VISIBLE);
                            engineNum.setText(t.driver.getCardrivenumber());
                        }

                        mCarNo = t.driver.getCarno();
                        mCarPro = "";
                        mCarCi = t.driver.getCarci();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();

            }
        });
    }

    private void getCityInfo() {
        showProgressDialog();
        CityInfoReq cityInfoReq = new CityInfoReq();
        cityInfoReq.code = "9001";
        KaKuApiProvider.CityQuery(cityInfoReq, new BaseCallback<IllegalResp>(IllegalResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, IllegalResp illegalResp) {
                if (illegalResp.res == 0) {
                    stopProgressDialog();
                    resp = illegalResp;
                    Log.d("xiaosu", illegalResp.Data.size() + "个省");
                    refreshView();
                    getIllegalDriverInfo();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
                new AlertDialog.
                        Builder(IllegalQueryActivity.this).
                        setTitle("城市信息获取失败").
                        setMessage("重新获取数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//点击确定重新获取数据
                        getCityInfo();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//点击取消退出页面
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IllegalQueryActivity.this.finish();
                    }
                }).setCancelable(false).
                        show();
            }
        });
    }

    private void initView() {
        ((TextView) findView(R.id.tv_mid)).setText("违章查询");
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lineCar = findView(R.id.line_car);

        license_plate_number = (TextView) findViewById(R.id._license_plate_number);
        name_city = (TextView) findViewById(R.id.name_city);
        rl_carEngine = findViewById(R.id.rl_carEngine);
        rl_vehicle_frame = findViewById(R.id.rl_vehicle_frame);
        carNum = (EditText) findViewById(R.id.carNum);
        vehicle_frame = (EditText) findViewById(R.id._vehicle_frame);
        engineNum = (EditText) findViewById(R.id.engineNum);
        line_vehicle_frame = findViewById(R.id.line_vehicle_frame);
        rl_license = findViewById(R.id.rl_license);

        ll_look_for = (LinearLayout) findViewById(R.id.ll_look_for);
        ll_look_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new IllegalPopWindow(IllegalQueryActivity.this).show();

                    }
                }, 200);
            }
        });
    }

    /**
     * 选择城市
     *
     * @param view
     */
    public void queryCity(View view) {
        if (this.resp == null)
            return;

        if (this.chooseCityListPopWindow == null) {
            chooseCityListPopWindow = new ChooseCityListPopWindow(this, this.resp.Data, this);
            chooseCityListPopWindow.setListener(this);
        }

        chooseCityListPopWindow.show();
    }

    /**
     * 选择牌照
     *
     * @param view
     */
    public void chooseLicense(View view) {
        if (resp == null) {
            return;
        }
        if (licenseChoosePop == null) {
            licenseChoosePop = new LicenseChoosePop(this, this.resp.Data.get(this.groupPosition).Cities, new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                    if (childPosition != paramInt) {
                        childPosition = paramInt;
                        clearInput();
                        refreshView();
                    }
                    IllegalQueryActivity.this.licenseChoosePop.dismiss();
                }
            });
            licenseChoosePop.setWidth(rl_license.getMeasuredWidth());
        } else {
            licenseChoosePop.updateData(resp.Data.get(this.groupPosition).Cities);
        }
        licenseChoosePop.updateHeight();
        licenseChoosePop.showAsDropDown(rl_license, 0, 1);
    }

    /**
     * 查询违规信息
     *
     * @param view
     */
    public void query(View view) {

        String _carNum = carNum.getText().toString();
        String _vehicle_frame = vehicle_frame.getText().toString();
        String _engineNum = engineNum.getText().toString();
        if (isChooseCity){
            //选择省市后校验输入字符，默认值不校验
            if (invalid(_carNum, _vehicle_frame, _engineNum)) return;
        }


        showProgressDialog();

        final IllegalQueryReq illegalQueryReq = new IllegalQueryReq();
        illegalQueryReq.code = "9002";
        illegalQueryReq.id_driver = Utils.getIdDriver();
        illegalQueryReq.carcode = _vehicle_frame;
        illegalQueryReq.cardrivenumber = _engineNum;
        illegalQueryReq.carnumber = license_plate_number.getText().toString() + _carNum;

        LogUtil.E(mCarCi + ":::" + mCarNo + ":::" + mCarPro + ":::");
        illegalQueryReq.carno = mCarNo;
        illegalQueryReq.carpro = mCarPro;
        illegalQueryReq.carci = mCarCi;

        Log.d("xiaosu", illegalQueryReq.carnumber);

        KaKuApiProvider.IllegalQuery(illegalQueryReq, new BaseCallback<IllegalQueryResp>(IllegalQueryResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, IllegalQueryResp illegalQueryResp) {
                stopProgressDialog();
                if (illegalQueryResp.res.equals("0")) {
                    startActivity(new Intent(IllegalQueryActivity.this, IllegalQueryResultActivity.class).
                            putExtra("carNum", illegalQueryReq.carnumber).
                            putExtra("info", illegalQueryResp));
                } else {
                    if (Constants.RES_TEN.equals(illegalQueryResp.res)) {
                        Utils.Exit(context);
                        finish();
                    }
                    LogUtil.showShortToast(context, illegalQueryResp.msg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
                showShortToast("网络状态不好，请稍后再试");
            }
        });
    }

    private boolean invalid(String carNum, String _vehicle_frame, String _engineNum) {
        IllegalResp.Cities city = resp.Data.get(groupPosition).Cities.get(childPosition);

        if (TextUtils.isEmpty(carNum)) {
            showShortToast("请输入车牌号");
            return true;
        }

        if (new String(carNum + getText(license_plate_number)).length() != 7) {
            showShortToast("车牌号的长度为7位");
            return true;
        }

      /*  if (city.CarCodeLen != 0 && TextUtils.isEmpty(_vehicle_frame)) {
            showShortToast("请输入车架号");
            return true;
        }*/

        if (city.CarCodeLen != 0) {
            if (TextUtils.isEmpty(_vehicle_frame)) {

                showShortToast("请输入车架号");
                return true;
            } else {
                if (city.CarCodeLen != 99) {

                    if (_vehicle_frame.length() != city.CarCodeLen) {
                        showShortToast("请输入车架号后" + city.CarCodeLen + "位");
                        return true;
                    }
                }
            }
        }
        if (city.CarEngineLen != 0) {
            if (TextUtils.isEmpty(_engineNum)) {

                showShortToast("请输入发动机号");
                return true;
            } else {
                if (city.CarEngineLen != 99) {
                    if (_engineNum.length() != city.CarEngineLen) {
                        showShortToast("请输入发动机号后" + city.CarEngineLen + "位");
                        return true;
                    }
                }
            }
        }
        /*if (city.CarEngineLen != 0 && TextUtils.isEmpty(_engineNum)) {
            showShortToast("请输入发动机号");
            return true;
        }*/
        return false;
    }

    private String mCarNo;
    private String mCarPro;
    private String mCarCi;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isChooseCity=true;

        Object obj = this.chooseCityListPopWindow.performClick(position);

        if (obj instanceof IllegalResp.Province && (this.groupPosition != position)) {
            this.groupPosition = position;
            clearInput();
            IllegalResp.Province province = (IllegalResp.Province) obj;
            name_city.setText(province.ProvinceName);
        } else if (obj instanceof IllegalResp.Cities && chooseCityListPopWindow.isProvincePresent() && this.groupPosition != position) {
            this.groupPosition = position;
            this.childPosition = 0;
            clearInput();
            IllegalResp.Cities city = (IllegalResp.Cities) obj;

            //mCarPro=city.
            for (IllegalResp.Province pro : resp.Data) {
                if (pro.Cities.contains(city)) {
                    name_city.setText(pro.ProvinceName);
                    break;
                }
            }
            refreshView();
        } else if (obj instanceof IllegalResp.Cities) {
            this.childPosition = position;
            refreshView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            /*选择了不同的城市就会清空上一次输入的数据*/
            if (groupPosition != data.getIntExtra("groupPosition", 0) || childPosition != data.getIntExtra("childPosition", 0)) {
                clearInput();
                groupPosition = data.getIntExtra("groupPosition", 0);
                childPosition = data.getIntExtra("childPosition", 0);
                refreshView();
            }
        }
    }

    /**
     * 清空输入的数据
     */
    private void clearInput() {
        engineNum.setText("");
        vehicle_frame.setText("");
        carNum.setText("");
    }

    private void refreshView() {

        if (resp == null) {
            return;
        }

        IllegalResp.Cities city = resp.Data.get(groupPosition).Cities.get(childPosition);
        license_plate_number.setText(city.CarNumberPrefix);

        mCarNo = city.CarNumberPrefix;
        mCarPro = "";
        mCarCi = city.CityName;

        /*两个有可能都为空*/
        if (city.CarEngineLen == 0 && city.CarCodeLen == 0) {
            updateMargin(new View[]{lineCar}, 0);
            rl_carEngine.setVisibility(View.GONE);

            rl_vehicle_frame.setVisibility(View.GONE);
            vehicle_frame.setText("");
            engineNum.setText("");
            return;
        }

        if (city.CarEngineLen == 0) {//长度未0表示不需要输入
            rl_carEngine.setVisibility(View.GONE);
            engineNum.setText("");
            updateMargin(new View[]{line_vehicle_frame}, 0);
        } else if (city.CarEngineLen == 99) {//长度为99表示不需要限制长度
            rl_carEngine.setVisibility(View.VISIBLE);
            engineNum.setFilters(new InputFilter[]{});//清除长度限制

            engineNum.setHint("请输入发动机号");

            updateMargin(new View[]{line_vehicle_frame}, getResources().getDimensionPixelOffset(R.dimen.x30));
        } else {//其他情况需要限制对应长度

            engineNum.setHint("请输入发动机号后" + city.CarEngineLen + "位");

            rl_carEngine.setVisibility(View.VISIBLE);
            /*设置长度*/
            engineNum.setFilters(new InputFilter[]{new InputFilter.LengthFilter(city.CarEngineLen)});

            updateMargin(new View[]{line_vehicle_frame}, getResources().getDimensionPixelOffset(R.dimen.x30));
        }

        if (city.CarCodeLen == 0) {
            rl_vehicle_frame.setVisibility(View.GONE);
            vehicle_frame.setText("");

        } else if (city.CarCodeLen == 99) {
            vehicle_frame.setHint("请输入车架号");
            rl_vehicle_frame.setVisibility(View.VISIBLE);
            vehicle_frame.setFilters(new InputFilter[]{});
        } else {
            vehicle_frame.setHint("请输入车架号后" + city.CarCodeLen + "位");
            rl_vehicle_frame.setVisibility(View.VISIBLE);
            vehicle_frame.setFilters(new InputFilter[]{new InputFilter.LengthFilter(city.CarCodeLen)});
        }
    }

    public void updateMargin(View[] targets, int margin) {
        for (View target : targets) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) target.getLayoutParams();
            if (layoutParams.leftMargin == margin) {
                continue;
            }
            layoutParams.leftMargin = margin;
            target.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void provinceChoose(int position) {
        clearInput();
        groupPosition = position;
        name_city.setText(resp.Data.get(this.groupPosition).ProvinceName);

        license_plate_number.setText(resp.Data.get(this.groupPosition).Cities.get(0).CarNumberPrefix);
        refreshView();
    }
}
