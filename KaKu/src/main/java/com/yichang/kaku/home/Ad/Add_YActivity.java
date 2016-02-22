package com.yichang.kaku.home.Ad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.wly.android.widget.AdGalleryHelper;
import com.wly.android.widget.Advertising;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.member.recommend.MemberRecommendActivity;
import com.yichang.kaku.member.cash.YueActivity;
import com.yichang.kaku.obj.RollsAddObj;
import com.yichang.kaku.obj.ShareContentObj;
import com.yichang.kaku.request.AdCalendarReq;
import com.yichang.kaku.request.StickerShareReq;
import com.yichang.kaku.response.AdCalendarResp;
import com.yichang.kaku.response.StickerShareResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.CalendarView;
import com.yichang.kaku.view.OneKeySharePopWindow;
import com.yichang.kaku.view.RiseNumberTextView;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_YActivity extends BaseActivity implements OnClickListener {

    private TextView left, title;
    private TextView right;
    private Bundle bundle;
    private String name_advert, day_earnings, time_begin, time_end, num_driver, free_remind, image_advert,
            image_size, day_continue, day_remaining, total_earning, flag_type, now_earnings;
    private TextView tv_shouyi_name, tv_shouyi_time, tv_shouyi_zongshouyi, tv_shouyi_canyu,
            tv_shouyi_meiriqian, tv_shouyi_shengyutian, tv_shuoming_1, tv_shuoming_4, tv_chetiexinxi_1, tv_shuoming_5, tv_shuoming_8, tv_shuoming_10;
    private TextView tv_shuoming_lottery;
    private RiseNumberTextView tv_shouyi_dangqianshouyiqian;
    private ImageView iv_tietieyangshi, iv_shouqi_y;
    private RelativeLayout rela_chetiexinxi, rela_addn_button;
    private AdGalleryHelper mGalleryHelper;
    private List<RollsAddObj> rollsadd_list = new ArrayList<RollsAddObj>();
    private Button btn_add;
    private ScrollView scroll_add_y;
    private View renwushuoming_y, chetiexinxi_y;
    private boolean flag_zhankai = true;

    private CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private ImageView iv_history;
    private String mPhotoDate;


    private LinearLayout ll_yaoqing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_y);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        bundle = intent.getExtras();
        name_advert = bundle.getString("name_advert");//广告名称
        day_earnings = bundle.getString("day_earnings");//每日收益
        time_begin = bundle.getString("time_begin");//开始日期
        time_end = bundle.getString("time_end");//结束日期
        num_driver = bundle.getString("num_driver");//参与人数
        free_remind = bundle.getString("free_remind");//每日免费提现次数
        image_advert = bundle.getString("image_advert");//车贴图片
        image_size = bundle.getString("image_size");//车贴规格
        day_continue = bundle.getString("day_continue");//持续天数
        day_remaining = bundle.getString("day_remaining");//剩余天数
        total_earning = bundle.getString("total_earning");//预计总收益
        flag_type = bundle.getString("flag_type");
        now_earnings = bundle.getString("now_earnings");//当前收益
        rollsadd_list = (List<RollsAddObj>) bundle.getSerializable("rollsadd_list");
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("任务详情");
        right = (TextView) findViewById(R.id.tv_right);
        /*right.setVisibility(View.VISIBLE);
        right.setText("邀好友");*/
        right.setOnClickListener(this);

        SetText();
        autoAdvance(rollsadd_list);

        iv_history = (ImageView) findViewById(R.id.iv_history);
        iv_history.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        initCalendarView();

        getCalendarData(String.valueOf(cal.get(Calendar.MONTH) + 1), String.valueOf(cal.get(Calendar.YEAR)));


        ll_yaoqing = (LinearLayout) findViewById(R.id.ll_yaoqing);
        ll_yaoqing.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void getCalendarData(String month, String year) {

        Utils.NoNet(context);
        showProgressDialog();

        AdCalendarReq req = new AdCalendarReq();
        req.code = "600140";
        //todo req.id_driver = Utils.getIdDriver();
        req.id_driver = Utils.getIdDriver();
        req.id_advert = "1";
        req.month = month;
        req.year = year;

        KaKuApiProvider.getCalendarList(req, new BaseCallback<AdCalendarResp>(AdCalendarResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, AdCalendarResp t) {
                if (t != null) {
                    LogUtil.E("getCalendarList res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        calendar.setDateStatusMap(t.calendars);
                        mPhotoDate = t.start_photo;
                        if (mPhotoDate.length() == 2) {
                            if (mPhotoDate.substring(0, 1).equals("0")) {
                                mPhotoDate = mPhotoDate.substring(1, 2);
                            }
                        }
                        String[] ya = calendar.getYearAndmonth().split("-");
                        calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
                    } else {
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
                tv_shouyi_dangqianshouyiqian.withNumber(Float.parseFloat(now_earnings)).start();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });

    }


    private void initCalendarView() {

        format = new SimpleDateFormat("yyyy-MM-dd");
        //获取日历控件对象
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选


        calendarLeft = (ImageButton) findViewById(R.id.calendarLeft);
        calendarCenter = (TextView) findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton) findViewById(R.id.calendarRight);
        /*try {
            //设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        String[] ya = calendar.getYearAndmonth().split("-");
        calendarCenter.setText(ya[0] + "年" + ya[1] + "月");

        calendarLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendar.clickLeftMonth();
                String[] ya = leftYearAndmonth.split("-");
                getCalendarData(ya[1], ya[0]);
                //calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
            }
        });

        calendarRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendar.clickRightMonth();
                String[] ya = rightYearAndmonth.split("-");
                getCalendarData(ya[1], ya[0]);
                //calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
            }
        });

        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(String s, String downDate) {
                /*if (calendar.isSelectMore()) {
                    Toast.makeText(getApplicationContext(), format.format(selectedStartDate) + "到" + format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
                }*/

                if (downDate.equals(mPhotoDate)) {
                    //最佳拍照日，弹出拍照对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_YActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("拍照激活下一轮？");
                    builder.setNegativeButton("是", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(Add_YActivity.this, AdImageActivity.class);
                            startActivity(intent);
                        }
                    });

                    builder.setPositiveButton("否", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    if (!isFinishing()) {

                        builder.create().show();
                    }

                } else {

                    if (!"NODATA".equals(s)) {
                        //无数据的数据块不响应点击事件
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            goToHome();
        } else if (R.id.tv_right == id) {
            //// TODO: 2016/1/6  分享
            //startActivity(new Intent(this,MemberRecommendActivity.class));
            //MobclickAgent.onEvent(context, "JinYuanBao");
            getStickerShareInfo();
        } else if (R.id.btn_add_y == id) {
            MobclickAgent.onEvent(context, "ShenQingXiaYiLun");
            Intent intent = new Intent(context, AdImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (R.id.rela_addn_button_y == id) {
            MobclickAgent.onEvent(context, "ShenQingXiaYiLun");
            Intent intent = new Intent(context, AdImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (R.id.iv_history == id) {
            MobclickAgent.onEvent(context, "TimeList");
            //todo 跳转到时间轴列表
            Intent intent = new Intent(context, ImageHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (R.id.iv_shouqi_y == id) {
            if (flag_zhankai) {
                iv_shouqi_y.setImageResource(R.drawable.btn_gengduoxiangqing);
                renwushuoming_y.setVisibility(View.GONE);
                chetiexinxi_y.setVisibility(View.GONE);
                flag_zhankai = false;
            } else if (!flag_zhankai) {
                iv_shouqi_y.setImageResource(R.drawable.btn_shouqixiangqing);
                renwushuoming_y.setVisibility(View.VISIBLE);
                chetiexinxi_y.setVisibility(View.VISIBLE);
                flag_zhankai = true;
            }
        } else if (R.id.tv_shuoming_5 == id) {
            startActivity(new Intent(this, YueActivity.class));
        } else if (R.id.tv_shuoming_8 == id) {
            startActivity(new Intent(this, MemberRecommendActivity.class));
        } else if (R.id.tv_shuoming_10 == id) {
            Utils.Call(this, "400-6867585");
        } else if (R.id.tv_shuoming_lottery == id) {
            startActivity(new Intent(this, LotteryActivity.class));
        } else if (R.id.ll_yaoqing == id) {
            startActivity(new Intent(context, MemberRecommendActivity.class));
        }
    }

    public void SetText() {
        title.setText(name_advert);
        renwushuoming_y = findViewById(R.id.renwushuoming_y);
        chetiexinxi_y = findViewById(R.id.chetiexinxi_y);
        scroll_add_y = (ScrollView) findViewById(R.id.scroll_add_y);
        tv_shouyi_time = (TextView) findViewById(R.id.tv_shouyi_time);
        tv_shouyi_zongshouyi = (TextView) findViewById(R.id.tv_shouyi_zongshouyi);
        tv_shouyi_dangqianshouyiqian = (RiseNumberTextView) findViewById(R.id.tv_shouyi_dangqianshouyiqian);
        tv_shouyi_canyu = (TextView) findViewById(R.id.tv_shouyi_canyu);
        tv_shouyi_meiriqian = (TextView) findViewById(R.id.tv_shouyi_meiriqian);
        tv_shouyi_shengyutian = (TextView) findViewById(R.id.tv_shouyi_shengyutian);
        tv_shuoming_1 = (TextView) findViewById(R.id.tv_shuoming_1);
        tv_shuoming_4 = (TextView) findViewById(R.id.tv_shuoming_4);
        tv_shuoming_5 = (TextView) findViewById(R.id.tv_shuoming_5);
        tv_shuoming_8 = (TextView) findViewById(R.id.tv_shuoming_8);
        tv_shuoming_10 = (TextView) findViewById(R.id.tv_shuoming_10);

        tv_shuoming_lottery = (TextView) findViewById(R.id.tv_shuoming_lottery);
        tv_shuoming_lottery.setOnClickListener(this);

        String strings = "收益可前往【我】-【我的余额】查看。";
        SpannableStringBuilder styles = new SpannableStringBuilder(strings);
        styles.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styles.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 5, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shuoming_5.setText(styles);
        tv_shuoming_5.setOnClickListener(this);
        String stringss = "【邀请好友】，获取更多收益。";
        SpannableStringBuilder styless = new SpannableStringBuilder(stringss);
        styless.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shuoming_8.setText(styless);
        tv_shuoming_8.setOnClickListener(this);
        String stringsss = "如有疑问，请拨打【400-6867585】。";
        SpannableStringBuilder stylesss = new SpannableStringBuilder(stringsss);
        stylesss.setSpan(new ForegroundColorSpan(Color.rgb(17, 155, 234)), 8, stringsss.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shuoming_10.setText(stylesss);
        tv_shuoming_10.setOnClickListener(this);

        iv_tietieyangshi = (ImageView) findViewById(R.id.iv_tietieyangshi);
        iv_shouqi_y = (ImageView) findViewById(R.id.iv_shouqi_y);
        iv_shouqi_y.setOnClickListener(this);
        rela_chetiexinxi = (RelativeLayout) findViewById(R.id.rela_chetiexinxi);
        rela_addn_button = (RelativeLayout) findViewById(R.id.rela_addn_button_y);
        rela_addn_button.setOnClickListener(this);
        btn_add = (Button) findViewById(R.id.btn_add_y);
        btn_add.setOnClickListener(this);
        rela_chetiexinxi.setClickable(false);

        scroll_add_y.smoothScrollTo(0, 0);
        tv_shouyi_time.setText(time_begin + "至" + time_end);
        String string = "预期总收益：¥ " + total_earning;
        SpannableStringBuilder style = new SpannableStringBuilder(string);
        style.setSpan(new AbsoluteSizeSpan(18, true), 6, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_shouyi_zongshouyi.setText(style);
        tv_shouyi_canyu.setText(num_driver + "人参与");
        tv_shouyi_meiriqian.setText("¥ " + day_earnings);
        tv_shouyi_shengyutian.setText(day_remaining + "天");
        tv_shuoming_1.setText("每天收益" + day_earnings + "元，共计" + day_continue + "天。");
        iv_shouqi_y.setImageResource(R.drawable.btn_gengduoxiangqing);
        renwushuoming_y.setVisibility(View.VISIBLE);
        chetiexinxi_y.setVisibility(View.VISIBLE);
        BitmapUtil.getInstance(context).download(iv_tietieyangshi, KaKuApplication.qian_zhui + image_advert);
    }

    private void autoAdvance(List<RollsAddObj> imgList) {
        // TODO Auto-generated method stub
        if (imgList == null) {
            return;
        }
        if (imgList.size() <= 0) {
            return;
        }
        List<Advertising> list = new ArrayList<Advertising>();

        for (RollsAddObj obj : imgList) {
            Advertising advertising = new Advertising(null, obj.getImage_roll(), null);
            advertising.setPicURL(KaKuApplication.qian_zhui + obj.getImage_roll());
            list.add(advertising);
        }

        if (list.size() > 0) {
            mGalleryHelper = new AdGalleryHelper(context, list, Constants.AUTO_SCROLL_DURATION, true, false, false);
            rela_chetiexinxi.addView(mGalleryHelper.getLayout());
            mGalleryHelper.startAutoSwitch();
        }
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToHome();
        }
        return false;
    }

    private void getStickerShareInfo() {

        Utils.NoNet(context);
        showProgressDialog();

        StickerShareReq req = new StickerShareReq();
        req.code = "60019";

        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getStickerShareInfo(req, new BaseCallback<StickerShareResp>(StickerShareResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, StickerShareResp t) {
                if (t != null) {
                    LogUtil.E("getCalendarList res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        showShare(t);

                    } else {
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

    private void showShare(StickerShareResp t) {
        if (oneKeySharePopWindow == null) {
            shareContent.url = t.url;
            shareContent.content = t.content;
            oneKeySharePopWindow = new OneKeySharePopWindow(this, shareContent);
            oneKeySharePopWindow.setIsShortUrl(false);
        }
        oneKeySharePopWindow.show();

    }

    private ShareContentObj shareContent = new ShareContentObj();
    private OneKeySharePopWindow oneKeySharePopWindow;
}
