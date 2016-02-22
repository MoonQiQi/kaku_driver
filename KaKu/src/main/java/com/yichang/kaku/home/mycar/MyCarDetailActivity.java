package com.yichang.kaku.home.mycar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.CarDetailSubmitReq;
import com.yichang.kaku.request.MyCarDetailReq;
import com.yichang.kaku.response.CarDetailSubmitResp;
import com.yichang.kaku.response.MyCarDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.Calendar;

public class MyCarDetailActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView showDate = null;
    private RelativeLayout pickDate = null;
    private EditText et_miles;

    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;

    private int mYear;
    private int mMonth;
    private int mDay;

    private String id_driver_car = "";
    private String id_car = "";

    private ImageView iv_brand;
    private TextView tv_nick_name;

    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyCarDetailActivity.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }

    };
    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar_detail);

        init();

        id_driver_car = getIntent().getStringExtra("id_driver_car");
        if(id_driver_car==null){
            id_driver_car="";
        }
        id_car = getIntent().getStringExtra("id_car");
        if(id_car==null){
            id_car="";
        }
        final Calendar c = Calendar.getInstance();
        currentTime = c.getTimeInMillis();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();
        getMyCarDetailInfo();

    }

    private void getMyCarDetailInfo() {
        Utils.NoNet(this);
        showProgressDialog();

        MyCarDetailReq req = new MyCarDetailReq();
        req.code = "20016";
        req.id_driver_car = id_driver_car;
        req.id_car = id_car;

        KaKuApiProvider.getMyCarDetail(req, new BaseCallback<MyCarDetailResp>(MyCarDetailResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, MyCarDetailResp t) {
                if (t != null) {
                    //LogUtil.E("getMyCarDetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        setData(t);
                    } else {
                        if (Constants.RES_TEN.equals(t.res)){
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

    private void setData(MyCarDetailResp t) {
        BitmapUtil.getInstance(this).download(iv_brand, KaKuApplication.qian_zhui + t.driver_car.getImage_brand());
        tv_nick_name.setText(t.driver_car.getNick_name());
        showDate.setText(t.driver_car.getTime_production());
        et_miles.setText(t.driver_car.getTravel_mileage());

    }

    private void init() {
        initTitleBar();
        showDate = (TextView) findViewById(R.id.showdate);
        pickDate = (RelativeLayout) findViewById(R.id.rela_buy_time);
        pickDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = MyCarDetailActivity.SHOW_DATAPICK;
                MyCarDetailActivity.this.dateandtimeHandler.sendMessage(msg);
            }
        });

        et_miles = (EditText) findViewById(R.id.et_miles);

        iv_brand = (ImageView) findViewById(R.id.iv_brand);
        tv_nick_name = (TextView) findViewById(R.id.tv_nick_name);
    }

    /**
     * 设置日期
     */
    private void setDateTime() {
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDateDisplay();
    }

    /**
     * 更新日期显示
     */
    private void updateDateDisplay() {
        showDate.setText(new StringBuilder().append(mYear).append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            updateDateDisplay();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog dp = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                dp.getDatePicker().setMaxDate(currentTime);
                return dp;

        }

        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;

        }
    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的爱车");
        right = (TextView) findViewById(R.id.tv_right);
        right.setVisibility(View.VISIBLE);
        right.setText("保存");
        right.setOnClickListener(this);
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
            save();
        }

    }

    private void save() {
        //LogUtil.showShortToast(this, "保存信息");

        Utils.NoNet(this);
        showProgressDialog();
        CarDetailSubmitReq req = new CarDetailSubmitReq();
        req.code = "20015";
//        todo  页面传递过来
        req.id_car = id_car;
        req.id_driver_car = id_driver_car;

        req.id_driver = Utils.getIdDriver();

        req.time_production = showDate.getText().toString().trim();
        req.travel_mileage = et_miles.getText().toString().trim();

        KaKuApiProvider.submitMyCarDetail(req, new BaseCallback<CarDetailSubmitResp>(CarDetailSubmitResp.class) {
                    @Override
                    public void onSuccessful(int statusCode, Header[] headers, CarDetailSubmitResp t) {
                        if (t != null) {
                            LogUtil.E("savecar res: " + t.res);
                            if (Constants.RES.equals(t.res)) {
                                finish();
                            } else {
                                if (Constants.RES_TEN.equals(t.res)){
                                    Utils.Exit(context);
                                    finish();
                                }
                                LogUtil.showShortToast(context, t.msg);
                            }
                        }
                        stopProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String msg, Throwable
                            error) {
                        stopProgressDialog();
                    }
                }

        );


    }


}
