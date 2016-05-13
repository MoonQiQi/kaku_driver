package com.yichang.kaku.home.miaosha;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.home.giftmall.ProductCommentListActivity;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.home.giftmall.TruckProductCommentAdapter;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailLinearList;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailScrollViewPage;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailSlidingMenu;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailWebView;
import com.yichang.kaku.obj.SeckillDetailObj;
import com.yichang.kaku.obj.ShopMallProductCommentObj;
import com.yichang.kaku.request.SeckillDetailReq;
import com.yichang.kaku.request.ServerDateTimeReq;
import com.yichang.kaku.response.SeckillDetailResp;
import com.yichang.kaku.response.ServerDateTimeResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeSeckillActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;

    private RelativeLayout rela_seckill_1, rela_seckill_2, rela_seckill_3;
    private TextView tv_seckill_1, tv_seckill_2, tv_seckill_3;
    private TextView tv_seckill_state_1, tv_seckill_state_2, tv_seckill_state_3;
    private TextView tv_hint, tv_countdown_state, tv_countdown_hour, tv_countdown_min, tv_countdown_second;

    private ImageView iv_seckill_goods, iv_action;
    private TextView tv_name_goods, tv_price_seckill, tv_price_goods, tv_num_limit, tv_progressbar;
    private ProgressBar progress_seckill;

    private int clickTabState = 0;

    private String mId_goods, mFlag_type;

    private String mTime_now = "";

    private final List<SeckillDetailObj> list = new ArrayList<>();
    private final List<List<ShopMallProductCommentObj>> evals = new ArrayList<>();
    //滑动控件
    private ProductDetailSlidingMenu productDetailSlidingMenu;
    private ProductDetailScrollViewPage productDetailScrollViewPage;
    private ProductDetailWebView productDetailWebView;
    private ProductDetailLinearList lv_comment_list;

    private TextView tv_comment_more;
    private TextView tv_num_stars,tv_num_evals,tv_num_exchange;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_seckill);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        initTitleBar();

        initTabs();

        initHintInfo();

        initOthers();

        clickTabState = getIntent().getIntExtra("clickTabState", 0);

        productDetailSlidingMenu = (ProductDetailSlidingMenu) findViewById(R.id.expanded_menu);
        productDetailSlidingMenu.setScreenHeight(119);
        productDetailScrollViewPage = (ProductDetailScrollViewPage) findViewById(R.id.productDetailScrollViewPage);
        productDetailScrollViewPage.setScreenHeight(119);
        productDetailWebView = (ProductDetailWebView) findViewById(R.id.wv_product_detail);

        lv_comment_list = (ProductDetailLinearList) findViewById(R.id.lv_comment_list);

        tv_comment_more= (TextView) findViewById(R.id.tv_comment_more);
        tv_comment_more.setOnClickListener(this);

        tv_num_stars= (TextView) findViewById(R.id.tv_num_stars);
        tv_num_evals= (TextView) findViewById(R.id.tv_num_evals);
        tv_num_exchange= (TextView) findViewById(R.id.tv_num_exchange);


        getSecKillDetailObjsInfo();

    }

    private void getSecKillDetailObjsInfo() {
        Utils.NoNet(context);
        showProgressDialog();

        SeckillDetailReq req = new SeckillDetailReq();
        req.code = "8009";
        req.id_driver = Utils.getIdDriver();

        KaKuApiProvider.getSeckillDetailList(req, new KakuResponseListener<SeckillDetailResp>(this, SeckillDetailResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {
                    LogUtil.E("home res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        if (t.seckills != null) {
                            list.addAll(t.seckills);
                            for (int i = 0; i < t.seckills.size(); i++
                                    ) {
                                switch (i) {
                                    case 0:
                                        evals.add(t.evals0);
                                        break;
                                    case 1:
                                        evals.add(t.evals1);
                                        break;
                                    case 2:
                                        evals.add(t.evals2);
                                        break;
                                    default:
                                        break;
                                }

                            }
                            setData();
                        }
                    } else {
                        if (Constants.RES_TEN.equals(t.res)) {
                            Utils.Exit(context);
                            finish();
                        }
                        LogUtil.showShortToast(context, t.msg);
                    }
                }
                stopProgressDialog();
            }

            @Override
            public void onFailed(int i, Response response) {

            }

        });
    }

    private void setData() {
        tv_seckill_1.setText(list.get(0).getTime_begin().substring(0, 5));
        tv_seckill_2.setText(list.get(1).getTime_begin().substring(0, 5));
        tv_seckill_3.setText(list.get(2).getTime_begin().substring(0, 5));

        setSeckillState(list.get(0), tv_seckill_state_1);
        setSeckillState(list.get(1), tv_seckill_state_2);
        setSeckillState(list.get(2), tv_seckill_state_3);

        switch (clickTabState) {
            case 0:
                //setFirstTab();
                getServerDateTime(1);
                break;
            case 1:
                getServerDateTime(2);
                break;
            case 2:
                getServerDateTime(3);
                break;
        }

    }

    private void setSeckillState(SeckillDetailObj obj, TextView view) {
        switch (obj.getFlag_type()) {
            case "N":

                view.setText("即将开始");
                break;
            case "Y":

                view.setText("抢购中");
                break;
            case "G":

                view.setText("已抢完");
                break;
        }

    }

    private void setThirdTab() {
        productDetailWebView.stopLoading();
        productDetailWebView.loadUrl("");
        SeckillDetailObj obj = list.get(2);
        //mProductId=obj.getId_goods();
        initCountDownTimer();
        mId_goods = obj.getId_goods();
        mFlag_type = obj.getFlag_type();

        rela_seckill_1.setBackgroundResource(R.drawable.bg_seckill_rela_dark);
        rela_seckill_2.setBackgroundResource(R.drawable.bg_seckill_rela_dark);
        rela_seckill_3.setBackgroundResource(R.drawable.bg_seckill_rela_red);

        changeViewStateBaseOnFlag(obj);

        BitmapUtil.getInstance(this).download(iv_seckill_goods, KaKuApplication.qian_zhui + obj.getImage_goods());

        tv_name_goods.setText(obj.getName_goods());
        tv_price_seckill.setText(obj.getPrice_seckill());
        tv_price_goods.setText("￥" + obj.getPrice_goods());
        tv_price_goods.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        tv_num_limit.setText("限购" + obj.getNum_seckill() + "件");

        tv_progressbar.setText("已售" + obj.getPercent() + "%");

        /*todo 设置进度条*/

        progress_seckill.setProgress(Integer.parseInt(obj.getPercent()));
        if (obj.getPercent().equals("100")) {

            iv_action.setImageResource(R.drawable.btn_seckill_more);
            mFlag_type = "G";
        }
        tv_num_stars.setText(obj.getStar_goods());
        tv_num_evals.setText(obj.getNum_eval());
        tv_num_exchange.setText(obj.getNum_exchange());
        productDetailSlidingMenu.setWebUrl(obj.getUrl_goods());
        lv_comment_list.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lv_comment_list.setAdapter(new TruckProductCommentAdapter(context, evals.get(2)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productDetailSlidingMenu.scrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailScrollViewPage.scrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailScrollViewPage.smoothScrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
            }
        }, 200);
    }

    private void setSecondTab() {
        productDetailWebView.stopLoading();
        productDetailWebView.loadUrl("");
        SeckillDetailObj obj = list.get(1);
        initCountDownTimer();
        mId_goods = obj.getId_goods();
        mFlag_type = obj.getFlag_type();

        rela_seckill_1.setBackgroundResource(R.drawable.bg_seckill_rela_dark);
        rela_seckill_2.setBackgroundResource(R.drawable.bg_seckill_rela_red);
        rela_seckill_3.setBackgroundResource(R.drawable.bg_seckill_rela_dark);

        changeViewStateBaseOnFlag(obj);

        BitmapUtil.getInstance(this).download(iv_seckill_goods, KaKuApplication.qian_zhui + obj.getImage_goods());

        tv_name_goods.setText(obj.getName_goods());
        tv_price_seckill.setText(obj.getPrice_seckill());
        tv_price_goods.setText("￥" + obj.getPrice_goods());
        tv_price_goods.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        tv_num_limit.setText("限购" + obj.getNum_seckill() + "件");

        tv_progressbar.setText("已售" + obj.getPercent() + "%");

        /*todo 设置进度条*/

        progress_seckill.setProgress(Integer.parseInt(obj.getPercent()));
        if (obj.getPercent().equals("100")) {
            iv_action.setImageResource(R.drawable.btn_seckill_more);
            mFlag_type = "G";
        }
        tv_num_stars.setText(obj.getStar_goods());
        tv_num_evals.setText(obj.getNum_eval());
        tv_num_exchange.setText(obj.getNum_exchange());
        productDetailSlidingMenu.setWebUrl(obj.getUrl_goods());
        lv_comment_list.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lv_comment_list.setAdapter(new TruckProductCommentAdapter(context, evals.get(1)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productDetailSlidingMenu.scrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailScrollViewPage.scrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailScrollViewPage.smoothScrollTo(0, -DensityUtils.dp2px(context, 119) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
            }
        }, 200);
    }

    private void setFirstTab() {
        productDetailWebView.stopLoading();
        productDetailWebView.loadUrl("");

        SeckillDetailObj obj = list.get(0);
        initCountDownTimer();
        mId_goods = obj.getId_goods();
        mFlag_type = obj.getFlag_type();

        rela_seckill_1.setBackgroundResource(R.drawable.bg_seckill_rela_red);
        rela_seckill_2.setBackgroundResource(R.drawable.bg_seckill_rela_dark);
        rela_seckill_3.setBackgroundResource(R.drawable.bg_seckill_rela_dark);

        changeViewStateBaseOnFlag(obj);

        BitmapUtil.getInstance(this).download(iv_seckill_goods, KaKuApplication.qian_zhui + obj.getImage_goods());

        tv_name_goods.setText(obj.getName_goods());
        tv_price_seckill.setText(obj.getPrice_seckill());
        tv_price_goods.setText("￥" + obj.getPrice_goods());
        tv_price_goods.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        tv_num_limit.setText("限购" + obj.getNum_seckill() + "件");

        tv_progressbar.setText("已售" + obj.getPercent() + "%");

        /*todo 设置进度条*/

        progress_seckill.setProgress(Integer.parseInt(obj.getPercent()));
        if (obj.getPercent().equals("100")) {
            iv_action.setImageResource(R.drawable.btn_seckill_more);
            mFlag_type = "G";
        }

        tv_num_stars.setText(obj.getStar_goods());
        tv_num_evals.setText(obj.getNum_eval());
        tv_num_exchange.setText(obj.getNum_exchange());



        productDetailSlidingMenu.setWebUrl(obj.getUrl_goods());
        lv_comment_list.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lv_comment_list.setAdapter(new TruckProductCommentAdapter(context, evals.get(0)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productDetailSlidingMenu.scrollTo(0, -DensityUtils.dp2px(context, 50 + 44) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 50 + 44) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailScrollViewPage.scrollTo(0, -DensityUtils.dp2px(context, 50 + 44) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
                productDetailScrollViewPage.smoothScrollTo(0, -DensityUtils.dp2px(context, 50 + 44) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
            }
        }, 200);
    }

    private void getServerDateTime(final int a) {
        Utils.NoNet(context);
        showProgressDialog();

        ServerDateTimeReq req = new ServerDateTimeReq();
        req.code = "80012";

        KaKuApiProvider.getServerDateTime(req, new KakuResponseListener<ServerDateTimeResp>(this, ServerDateTimeResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);
                if (t != null) {

                    if (Constants.RES.equals(t.res)) {
                        if (t.time_now != null) {
                            mTime_now = t.time_now;
                            switch (a) {
                                case 1:
                                    setFirstTab();
                                    break;
                                case 2:
                                    setSecondTab();
                                    break;
                                case 3:
                                    setThirdTab();
                                    break;
                            }


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

    private void changeViewStateBaseOnFlag(SeckillDetailObj obj) {
        if (obj.getFlag_type().equals("Y")) {
            tv_hint.setText("抢购中" + obj.getTime_end() + "结束");
            tv_countdown_state.setText("距结束");
            iv_action.setImageResource(R.drawable.btn_seckill_start);
            setCountDownTime(obj.getTime_end());
        } else if (obj.getFlag_type().equals("N")) {
            tv_hint.setText("即将开始" + obj.getTime_begin() + "开抢");
            tv_countdown_state.setText("距开始");
            iv_action.setImageResource(R.drawable.btn_seckill_ready);
            setCountDownTime(obj.getTime_begin());
        } else if (obj.getFlag_type().equals("G")) {
            tv_hint.setText("明日" + obj.getTime_begin() + "开抢");
            tv_countdown_state.setText("距开始");
            iv_action.setImageResource(R.drawable.btn_seckill_more);
            setCountDownTime(obj.getTime_begin());
        }
    }

    private void initCountDownTimer() {
        tv_countdown_hour.setText("00");
        tv_countdown_min.setText("00");
        tv_countdown_second.setText("00");
    }


    private void setCountDownTime(String time_end) {
        //startTime=
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        String startDate = dateFormat.format(date) + " " + time_end;


        //先把字符串转成Date类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = new Date();

        Date currentDate = new Date();
//此处会抛异常
        try {
            newDate = sdf.parse(startDate);
            currentDate = sdf.parse(mTime_now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//获取毫秒数
        long startTime = newDate.getTime();

        //long currentTime = System.currentTimeMillis();

        long currentTime = currentDate.getTime();


        long timeRemains = startTime - currentTime;
        if (timeRemains + 7200000 < 0) {
            //如果timeRemains小于0，则取第二天的同一时刻
            timeRemains = timeRemains + 86400000;
        }
        if (timeRemains < 0) {

        }
        formatDuring(timeRemains);
        handler.postDelayed(runnable, 1000);
    }


    private void initOthers() {
        iv_seckill_goods = (ImageView) findViewById(R.id.iv_seckill_goods);
        iv_action = (ImageView) findViewById(R.id.iv_action);
        iv_action.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlag_type.equals("Y")) {
                    Intent intent = new Intent(context, SeckillOrderActivity.class);
                    intent.putExtra("id_goods", mId_goods);
                    //KaKuApplication.id_goods_shopcars = mId_goods_shopcars;
                    startActivity(intent);
                } else if (mFlag_type.equals("N")) {
                    LogUtil.showShortToast(context, "秒杀尚未开始，敬请期待");
                } else if (mFlag_type.equals("G")) {
                    //LogUtil.showShortToast(context, "秒杀已经结束，请明日再试");
                    startActivity(new Intent(context, ShopMallActivity.class));
                }
            }
        });

        tv_price_seckill = (TextView) findViewById(R.id.tv_price_seckill);
        tv_price_goods = (TextView) findViewById(R.id.tv_price_goods);
        tv_name_goods = (TextView) findViewById(R.id.tv_name_goods);
        tv_num_limit = (TextView) findViewById(R.id.tv_num_limit);
        tv_progressbar = (TextView) findViewById(R.id.tv_progressbar);

        progress_seckill = (ProgressBar) findViewById(R.id.progress_seckill);
    }

    private void initHintInfo() {
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_countdown_state = (TextView) findViewById(R.id.tv_countdown_state);
        tv_countdown_hour = (TextView) findViewById(R.id.tv_countdown_hour);
        tv_countdown_min = (TextView) findViewById(R.id.tv_countdown_min);
        tv_countdown_second = (TextView) findViewById(R.id.tv_countdown_second);

    }

    private void initTabs() {
        rela_seckill_1 = (RelativeLayout) findViewById(R.id.rela_seckill_1);
        rela_seckill_1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                handler.removeMessages(0);

                getServerDateTime(1);
            }
        });
        rela_seckill_2 = (RelativeLayout) findViewById(R.id.rela_seckill_2);
        rela_seckill_2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                handler.removeMessages(0);
                getServerDateTime(2);
            }
        });
        rela_seckill_3 = (RelativeLayout) findViewById(R.id.rela_seckill_3);
        rela_seckill_3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.Many()) {
                    return;
                }
                handler.removeMessages(0);
                getServerDateTime(3);
            }
        });
        tv_seckill_1 = (TextView) findViewById(R.id.tv_seckill_1);
        tv_seckill_2 = (TextView) findViewById(R.id.tv_seckill_2);
        tv_seckill_3 = (TextView) findViewById(R.id.tv_seckill_3);
        tv_seckill_state_1 = (TextView) findViewById(R.id.tv_seckill_state_1);
        tv_seckill_state_2 = (TextView) findViewById(R.id.tv_seckill_state_2);
        tv_seckill_state_3 = (TextView) findViewById(R.id.tv_seckill_state_3);


    }

    private void initTitleBar() {
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("掌上秒杀");
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
        }else if (R.id.tv_comment_more==id){
            Intent intent = new Intent(context, ProductCommentListActivity.class);
            intent.putExtra("id_goods", mId_goods);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //清空秒杀倒计时handler
        handler.removeMessages(0);
        handler = null;
    }

//todo 秒杀区域


    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            ComputeTime();

            if (msecond <= 9) {
                tv_countdown_second.setText("0" + String.valueOf(msecond));
            } else {
                tv_countdown_second.setText(String.valueOf(msecond));
            }
            if (mmin <= 9) {
                tv_countdown_min.setText("0" + String.valueOf(mmin));
            } else {

                tv_countdown_min.setText(String.valueOf(mmin));
            }
            if (mhour <= 9) {
                tv_countdown_hour.setText("0" + String.valueOf(mhour));
            } else {
                tv_countdown_hour.setText(String.valueOf(mhour));
            }


            handler.postDelayed(this, 1000);
        }
    };

    private long mday, mhour, mmin, msecond;

    /**
     * 倒计时计算
     */
    private void ComputeTime() {
        msecond--;

        if (msecond < 0) {
            mmin--;
            msecond = 59;
            if (mmin < 0) {
                mmin = 59;
                mhour--;
                if (mhour < 0) {
                    // 倒计时结束，一天有24个小时
                    mhour = 23;
                    mday--;

                }
            }

        }

    }

    /**
     * @param
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author chaih
     */
    public void formatDuring(long mss) {
        LogUtil.E("logn:" + mss);
        mday = mss / (1000 * 60 * 60 * 24);
        mhour = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        mmin = (mss % (1000 * 60 * 60)) / (1000 * 60);
        msecond = (mss % (1000 * 60)) / 1000;

    }


}
