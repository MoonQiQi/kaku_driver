package com.yichang.kaku.home.moneybook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.obj.MokeyBook1Obj;
import com.yichang.kaku.request.MoneyBookReq;
import com.yichang.kaku.response.MoneyBookResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MoneyBookActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_mb_year, tv_mb_month, tv_mb_zhichu, tv_mb_shouru;
    private int mYear, mMonth, mDay;
    private String mm;
    private ListView lv_moneybook;
    private Button btn_mb_jiyibi;
    private ImageView iv_moneybook_nodata;
    private RelativeLayout rela_mb_top;
    private List<MokeyBook1Obj> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moneybook);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("记账本");
        iv_moneybook_nodata = (ImageView) findViewById(R.id.iv_moneybook_nodata);
        rela_mb_top = (RelativeLayout) findViewById(R.id.rela_mb_top);
        rela_mb_top.setOnClickListener(this);
        tv_mb_year = (TextView) findViewById(R.id.tv_mb_year);
        tv_mb_month = (TextView) findViewById(R.id.tv_mb_month);
        tv_mb_month.setOnClickListener(this);
        tv_mb_zhichu = (TextView) findViewById(R.id.tv_mb_zhichu);
        tv_mb_shouru = (TextView) findViewById(R.id.tv_mb_shouru);
        lv_moneybook = (ListView) findViewById(R.id.lv_moneybook);
        btn_mb_jiyibi = (Button) findViewById(R.id.btn_mb_jiyibi);
        btn_mb_jiyibi.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (KaKuApplication.mb_year != null) {
            mm = KaKuApplication.mb_month;
            if (!"".equals(KaKuApplication.mb_year)) {
                mYear = Integer.parseInt(KaKuApplication.mb_year);
            }

            tv_mb_year.setText(mYear + "年");
            tv_mb_month.setText(mm);
        }
        GetInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KaKuApplication.mb_month = "";
        KaKuApplication.mb_year = "";
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        } else if (R.id.rela_mb_top == id) {
            if (!isFinishing()) {
                showDialog(0);
            }
        } else if (R.id.btn_mb_jiyibi == id) {
            startActivity(new Intent(this, WhiteMoneyActivity.class));
            finish();
        }
    }

    public void GetInfo() {
        showProgressDialog();
        MoneyBookReq req = new MoneyBookReq();
        req.code = "60051";
        req.year = String.valueOf(mYear);
        req.month = mm;
        KaKuApiProvider.MoneyBook(req, new KakuResponseListener<MoneyBookResp>(context, MoneyBookResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("moneybook res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
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

    public void SetText(MoneyBookResp t) {
        tv_mb_year.setText(t.year + "年");
        tv_mb_month.setText(t.month);
        tv_mb_zhichu.setText(t.pay_total);
        tv_mb_shouru.setText(t.get_total);
        list.clear();
        list = t.account;
        if (list.size() == 0) {
            iv_moneybook_nodata.setVisibility(View.VISIBLE);
        } else {
            iv_moneybook_nodata.setVisibility(View.GONE);
            MoneyBookAdapter adapter = new MoneyBookAdapter(context, list);
            lv_moneybook.setAdapter(adapter);
        }
    }

    protected Dialog onCreateDialog(int id) {
        Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
        mYear = dateAndTime.get(Calendar.YEAR);
        mMonth = dateAndTime.get(Calendar.MONTH);
        mDay = dateAndTime.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case 0:
                return new MonPickerDialog(context, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            if (monthOfYear <= 9) {
                mMonth = monthOfYear + 1;
                mm = "0" + mMonth;
            } else {
                mMonth = monthOfYear + 1;
                mm = String.valueOf(mMonth);
            }
            mDay = dayOfMonth;
            tv_mb_year.setText(String.valueOf(mYear) + "年");
            tv_mb_month.setText(mm);
            GetInfo();
        }
    };

    public class MonPickerDialog extends DatePickerDialog {
        public MonPickerDialog(Context context, OnDateSetListener callBack,
                               int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
            this.setTitle(year + "年" + (monthOfYear + 1) + "月");


          /*  ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
                    .getChildAt(0)).getChildAt(2).setVisibility(View.GONE);*/
            View childAt = ((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
                    .getChildAt(0)).getChildAt(2);
            if (childAt != null) {
                childAt.setVisibility(View.GONE);
            }

        }


        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            super.onDateChanged(view, year, month, day);
            this.setTitle(year + "年" + (month + 1) + "月");
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME4);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
