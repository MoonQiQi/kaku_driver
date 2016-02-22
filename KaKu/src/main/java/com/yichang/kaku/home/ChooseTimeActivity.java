package com.yichang.kaku.home;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;

import java.util.Calendar;
import java.util.Locale;


/**
 * Created by 小苏 on 2015/8/13.
 */
public class ChooseTimeActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener {
    TextView stime;
    TextView etime;
    private TimePickerDialog timePickerDialog;
    private int eTime = -1;
    private int sTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosetime);
        initUI();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        timePickerDialog = new TimePickerDialog(this, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    private void initUI() {
        ((TextView) findView(R.id.tv_mid)).setText("选择营业时间");
        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        View tv_right = findView(R.id.tv_right);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOK();
            }
        });
        tv_right.setVisibility(View.VISIBLE);
        stime = findView(R.id.stime);
        etime = findView(R.id.etime);

        String begin = getIntent().getStringExtra("hour_shop_begin");
        String end = getIntent().getStringExtra("hour_shop_end");
        stime.setText(begin);
        etime.setText(end);

        if (!TextUtils.isEmpty(begin) && !TextUtils.isEmpty(end)) {
            int[] sTimes = getTimes(begin);//初始化开始时间的毫秒值
            sTime = convertTime(sTimes[0], sTimes[1]);

            int[] eTimes = getTimes(end);//初始化结束时间的毫秒值
            eTime = convertTime(eTimes[0], eTimes[1]);
        }
    }

    private boolean flag;

    /**
     * 选择开水时间
     *
     * @param view
     */
    public void chooseStartTime(View view) {
        flag = false;
        String s = stime.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            int[] sTimes = getTimes(s);
            timePickerDialog.updateTime(sTimes[0], sTimes[1]);
        }
        timePickerDialog.show();
    }

    /**
     * 选择结束时间
     *
     * @param view
     */
    public void chooseEndTime(View view) {
        flag = true;
        String s = etime.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            int[] eTimes = getTimes(s);
            timePickerDialog.updateTime(eTimes[0], eTimes[1]);
        }
        timePickerDialog.show();
    }

    private void setOK() {
        Intent intent = new Intent();
        intent.putExtra("hour_shop_begin", stime.getText().toString());
        intent.putExtra("hour_shop_end", etime.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (flag) {//结束时间
            eTime = convertTime(hourOfDay, minute);

            if (sTime != -1 && sTime > eTime) {//选择了开始时间
                showShortToast("结束时间不能小于开始时间");
                return;
            }

            etime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
        } else {//起始时间
            //判断不能比结束时间小
            sTime = convertTime(hourOfDay, minute);

            if (eTime != -1 && sTime > eTime) {
                showShortToast("开始时间不能大于结束时间");
                return;
            }

            stime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
        }
    }

    public int convertTime(int hourOfDay, int minute) {
        return hourOfDay * 60 + minute;
    }

    public int[] getTimes(String time) {
        String[] split = time.split(":");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

}
