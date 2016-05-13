package com.yichang.kaku.home.moneybook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.WhiteMoneyReq;
import com.yichang.kaku.response.ExitResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.Calendar;

public class WhiteMoneyActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private TextView tv_wm_shouru, tv_wm_zhichu, tv_wm_time;
    private ImageView iv_wm1, iv_wm2, iv_wm3, iv_wm4, iv_wm5, iv_wm6, iv_wm7, iv_wm8, iv_wm9, iv_wm10;
    private RelativeLayout rela_wm_time;
    private LinearLayout line_wm_shouru, line_wm_zhichu;
    private String flag_way = "0", flag_type = "G";
    private Button btn_wm;
    private EditText et_wm_beizhu, et_wm_money;
    private static final int SHOW_DATAPICK = 0;
    private static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    /**
     * 处理日期和时间控件的Handler
     */
    Handler dateandtimeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WhiteMoneyActivity.SHOW_DATAPICK:
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
        setContentView(R.layout.activity_whitemoney);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("记账本");
        tv_wm_time = (TextView) findViewById(R.id.tv_wm_time);
        tv_wm_shouru = (TextView) findViewById(R.id.tv_wm_shouru);
        tv_wm_shouru.setOnClickListener(this);
        et_wm_beizhu = (EditText) findViewById(R.id.et_wm_beizhu);
        et_wm_money = (EditText) findViewById(R.id.et_wm_money);
        tv_wm_zhichu = (TextView) findViewById(R.id.tv_wm_zhichu);
        tv_wm_zhichu.setOnClickListener(this);
        btn_wm = (Button) findViewById(R.id.btn_wm);
        btn_wm.setOnClickListener(this);
        iv_wm1 = (ImageView) findViewById(R.id.iv_wm1);
        iv_wm1.setOnClickListener(this);
        iv_wm2 = (ImageView) findViewById(R.id.iv_wm2);
        iv_wm2.setOnClickListener(this);
        iv_wm3 = (ImageView) findViewById(R.id.iv_wm3);
        iv_wm3.setOnClickListener(this);
        iv_wm4 = (ImageView) findViewById(R.id.iv_wm4);
        iv_wm4.setOnClickListener(this);
        iv_wm5 = (ImageView) findViewById(R.id.iv_wm5);
        iv_wm5.setOnClickListener(this);
        iv_wm6 = (ImageView) findViewById(R.id.iv_wm6);
        iv_wm6.setOnClickListener(this);
        iv_wm7 = (ImageView) findViewById(R.id.iv_wm7);
        iv_wm7.setOnClickListener(this);
        iv_wm8 = (ImageView) findViewById(R.id.iv_wm8);
        iv_wm8.setOnClickListener(this);
        iv_wm9 = (ImageView) findViewById(R.id.iv_wm9);
        iv_wm9.setOnClickListener(this);
        iv_wm10 = (ImageView) findViewById(R.id.iv_wm10);
        iv_wm10.setOnClickListener(this);
        rela_wm_time = (RelativeLayout) findViewById(R.id.rela_wm_time);
        rela_wm_time.setOnClickListener(this);
        line_wm_shouru = (LinearLayout) findViewById(R.id.line_wm_shouru);
        line_wm_zhichu = (LinearLayout) findViewById(R.id.line_wm_zhichu);
        final Calendar c = Calendar.getInstance();
        currentTime = c.getTimeInMillis();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            startActivity(new Intent(this, MoneyBookActivity.class));
            finish();
        } else if (R.id.tv_wm_shouru == id) {
            flag_type = "G";
            tv_wm_shouru.setTextColor(getResources().getColor(R.color.color_red));
            tv_wm_zhichu.setTextColor(getResources().getColor(R.color.black));
            line_wm_shouru.setVisibility(View.VISIBLE);
            line_wm_zhichu.setVisibility(View.GONE);
        } else if (R.id.tv_wm_zhichu == id) {
            flag_type = "P";
            tv_wm_shouru.setTextColor(getResources().getColor(R.color.black));
            tv_wm_zhichu.setTextColor(getResources().getColor(R.color.color_red));
            line_wm_shouru.setVisibility(View.GONE);
            line_wm_zhichu.setVisibility(View.VISIBLE);
        } else if (R.id.iv_wm1 == id) {
            if ("G1".equals(flag_way)) {
                return;
            } else {
                flag_way = "G1";
                SetWhite();
                iv_wm1.setImageResource(R.drawable.jzb_yunfei);
            }
        } else if (R.id.iv_wm2 == id) {
            if ("G2".equals(flag_way)) {
                return;
            } else {
                flag_way = "G2";
                SetWhite();
                iv_wm2.setImageResource(R.drawable.jzb_gongzi);
            }
        } else if (R.id.iv_wm3 == id) {
            if ("G3".equals(flag_way)) {
                return;
            } else {
                flag_way = "G3";
                SetWhite();
                iv_wm3.setImageResource(R.drawable.jzb_waikuai);
            }
        } else if (R.id.iv_wm4 == id) {
            if ("G4".equals(flag_way)) {
                return;
            } else {
                flag_way = "G4";
                SetWhite();
                iv_wm4.setImageResource(R.drawable.jzb_qitalv);
            }
        } else if (R.id.iv_wm5 == id) {
            if ("P1".equals(flag_way)) {
                return;
            } else {
                flag_way = "P1";
                SetWhite();
                iv_wm5.setImageResource(R.drawable.jzb_jiayou);
            }
        } else if (R.id.iv_wm6 == id) {
            if ("P2".equals(flag_way)) {
                return;
            } else {
                flag_way = "P2";
                SetWhite();
                iv_wm6.setImageResource(R.drawable.jzb_guolu);
            }
        } else if (R.id.iv_wm7 == id) {
            if ("P3".equals(flag_way)) {
                return;
            } else {
                flag_way = "P3";
                SetWhite();
                iv_wm7.setImageResource(R.drawable.jzb_canfei);
            }
        } else if (R.id.iv_wm8 == id) {
            if ("P4".equals(flag_way)) {
                return;
            } else {
                flag_way = "P4";
                SetWhite();
                iv_wm8.setImageResource(R.drawable.jzb_weixiu);
            }
        } else if (R.id.iv_wm9 == id) {
            if ("P5".equals(flag_way)) {
                return;
            } else {
                flag_way = "P5";
                SetWhite();
                iv_wm9.setImageResource(R.drawable.jzb_tongxun);
            }
        } else if (R.id.iv_wm10 == id) {
            if ("P6".equals(flag_way)) {
                return;
            } else {
                flag_way = "P6";
                SetWhite();
                iv_wm10.setImageResource(R.drawable.jzb_qitahong);
            }
        } else if (R.id.rela_wm_time == id) {
            Message msg = new Message();
            msg.what = WhiteMoneyActivity.SHOW_DATAPICK;
            WhiteMoneyActivity.this.dateandtimeHandler.sendMessage(msg);
        } else if (R.id.btn_wm == id) {
            if ("请选择".equals(tv_wm_time.getText().toString().trim())) {
                LogUtil.showShortToast(this, "请选择时间");
                return;
            }
            if ("".equals(et_wm_beizhu.getText().toString().trim())) {
                LogUtil.showShortToast(this, "请填写备注");
                return;
            }
            if ("".equals(et_wm_money.getText().toString().trim())) {
                LogUtil.showShortToast(this, "请输入金额");
                return;
            }
            if ("0".equals(flag_way)) {
                LogUtil.showShortToast(this, "请选择标签");
                return;
            }
            Save();
        }
    }

    public void Save() {
        showProgressDialog();
        WhiteMoneyReq req = new WhiteMoneyReq();
        req.code = "60052";
        req.flag_type = flag_type;
        req.flag_way = flag_way;
        req.date_record = tv_wm_time.getText().toString().trim();
        req.remark_account = et_wm_beizhu.getText().toString().trim();
        req.money_account = et_wm_money.getText().toString().trim();
        KaKuApiProvider.WhiteMoney(req, new KakuResponseListener<ExitResp>(context, ExitResp.class) {

            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("WhiteMoney res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        String[] split = tv_wm_time.getText().toString().trim().split("-");
                        KaKuApplication.mb_year = split[0];
                        KaKuApplication.mb_month = split[1];
                        startActivity(new Intent(WhiteMoneyActivity.this, MoneyBookActivity.class));
                        finish();
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

    public void SetWhite() {
        iv_wm1.setImageResource(R.drawable.jzb_yunfei_no);
        iv_wm2.setImageResource(R.drawable.jzb_gongzino);
        iv_wm3.setImageResource(R.drawable.jzb_waikuai_no);
        iv_wm4.setImageResource(R.drawable.jzb_qita_no);
        iv_wm5.setImageResource(R.drawable.jzb_jiayou_no);
        iv_wm6.setImageResource(R.drawable.jzb_guolufei_no);
        iv_wm7.setImageResource(R.drawable.jzb_canfeino);
        iv_wm8.setImageResource(R.drawable.jzb_weixiu_no);
        iv_wm9.setImageResource(R.drawable.jzb_tongxun_no);
        iv_wm10.setImageResource(R.drawable.jzb_qita_no);
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
        tv_wm_time.setText(new StringBuilder().append(mYear).append("-")
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MoneyBookActivity.class));
            finish();
        }
        return false;
    }

}
