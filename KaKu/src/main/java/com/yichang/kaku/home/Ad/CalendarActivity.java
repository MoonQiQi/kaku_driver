package com.yichang.kaku.home.ad;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.obj.DayObj;
import com.yichang.kaku.request.BaseReq;
import com.yichang.kaku.response.BigCalendarResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.MyGridView;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private MyGridView gv_calendar1, gv_calendar2, gv_calendar3, gv_calendar4;
    private TextView tv_calendar_yue1, tv_calendar_yue2, tv_calendar_yue3, tv_calendar_yue4;
    private String date, date1;
    private String yue1, yue2, yue3, yue4;
    private DayObj dayObj;
    private List<DayObj> list1 = new ArrayList<>();
    private List<DayObj> list2 = new ArrayList<>();
    private List<DayObj> list3 = new ArrayList<>();
    private List<DayObj> list4 = new ArrayList<>();
    private RelativeLayout rela_calendar1, rela_calendar2, rela_calendar3, rela_calendar4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("任务日历");
        tv_calendar_yue1 = (TextView) findViewById(R.id.tv_calendar_yue1);
        tv_calendar_yue2 = (TextView) findViewById(R.id.tv_calendar_yue2);
        tv_calendar_yue3 = (TextView) findViewById(R.id.tv_calendar_yue3);
        tv_calendar_yue4 = (TextView) findViewById(R.id.tv_calendar_yue4);
        gv_calendar1 = (MyGridView) findViewById(R.id.gv_calendar1);
        gv_calendar2 = (MyGridView) findViewById(R.id.gv_calendar2);
        gv_calendar3 = (MyGridView) findViewById(R.id.gv_calendar3);
        gv_calendar4 = (MyGridView) findViewById(R.id.gv_calendar4);
        gv_calendar1.setFocusable(false);
        gv_calendar2.setFocusable(false);
        gv_calendar3.setFocusable(false);
        gv_calendar4.setFocusable(false);
        rela_calendar1 = (RelativeLayout) findViewById(R.id.rela_calendar1);
        rela_calendar2 = (RelativeLayout) findViewById(R.id.rela_calendar2);
        rela_calendar3 = (RelativeLayout) findViewById(R.id.rela_calendar3);
        rela_calendar4 = (RelativeLayout) findViewById(R.id.rela_calendar4);
        BigCalendar();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            GetAdd();
        }
    }

    private void BigCalendar() {
        showProgressDialog();
        BaseReq req = new BaseReq();
        req.code = "600111";
        KaKuApiProvider.BigCalendar(req, new KakuResponseListener<BigCalendarResp>(this, BigCalendarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("bigcalendar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t);
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

        });
    }

    public void SetText(BigCalendarResp t) {
        date1 = t.calendars.get(0).week.get(0).getDate();
        rela_calendar1.setVisibility(View.VISIBLE);
        yue1 = date1.substring(5, 7);
        tv_calendar_yue1.setText(yue1 + "月");

        int i;
        for (i = 0; i < t.calendars.size(); i++) {
            for (int j = 0; j < 7; j++) {
                date = t.calendars.get(i).week.get(j).getDate();
                dayObj = t.calendars.get(i).week.get(j);
                if (yue1.equals(date.substring(5, 7))) {
                    list1.add(dayObj);
                    if (i != t.calendars.size() - 1) {
                        yue2 = t.calendars.get(i + 1).week.get(0).getDate().substring(5, 7);
                    }
                } else if ((!yue1.equals(yue2)) && yue2.equals(date.substring(5, 7))) {
                    list2.add(dayObj);
                    rela_calendar2.setVisibility(View.VISIBLE);
                    tv_calendar_yue2.setText(yue2 + "月");
                    if (i != t.calendars.size() - 1) {
                        yue3 = t.calendars.get(i + 1).week.get(0).getDate().substring(5, 7);
                    }
                } else if ((!yue1.equals(yue3)) && (!yue2.equals(yue3)) && yue3.equals(date.substring(5, 7))) {
                    list3.add(dayObj);
                    rela_calendar3.setVisibility(View.VISIBLE);
                    tv_calendar_yue3.setText(yue3 + "月");
                    if (i != t.calendars.size() - 1) {
                        yue4 = t.calendars.get(i + 1).week.get(0).getDate().substring(5, 7);
                    }
                } else if ((!yue1.equals(yue4)) && (!yue2.equals(yue4)) && (!yue3.equals(yue4)) && yue4.equals(date.substring(5, 7))) {
                    list4.add(dayObj);
                    rela_calendar4.setVisibility(View.VISIBLE);
                    tv_calendar_yue4.setText(yue4 + "月");
                }
            }
        }
        CalendarAdapter calendarAdapter1 = new CalendarAdapter(CalendarActivity.this, list1, Integer.parseInt(t.index_y));
        gv_calendar1.setAdapter(calendarAdapter1);
        CalendarAdapter calendarAdapter2 = new CalendarAdapter(CalendarActivity.this, list2, Integer.parseInt(t.index_y));
        gv_calendar2.setAdapter(calendarAdapter2);
        CalendarAdapter calendarAdapter3 = new CalendarAdapter(CalendarActivity.this, list3, Integer.parseInt(t.index_y));
        gv_calendar3.setAdapter(calendarAdapter3);
        CalendarAdapter calendarAdapter4 = new CalendarAdapter(CalendarActivity.this, list4, Integer.parseInt(t.index_y));
        gv_calendar4.setAdapter(calendarAdapter4);
    }


    public void GetAdd() {
        Utils.GetAdType(baseActivity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GetAdd();
        }
        return false;
    }

}
