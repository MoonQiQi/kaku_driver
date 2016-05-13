package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.KakuResponseListener;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MainActivity;
import com.yichang.kaku.home.baoyang.BaoYangActivity;
import com.yichang.kaku.obj.MyCarObj;
import com.yichang.kaku.request.MyCarReq;
import com.yichang.kaku.response.MyCarResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class MyCarActivity extends BaseActivity implements OnClickListener {

    private TextView left, right, title;
    private Boolean isToMember;
    private List<MyCarObj> car_list = new ArrayList<>();
    private RelativeLayout rela_mycar_zuobaoyang, rela_mycar_tiechetie;
    private ViewPager viewpager;
    private ImageView iv_mycar_line1, iv_mycar_line2, iv_mycar_line3, iv_mycar_line4, iv_mycar_line5, iv_mycar_meiyouaiche;
    ArrayList<View> viewContainter = new ArrayList<>();
    ScaleAnimation animation;
    private int size;
    private LinearLayout line_mycar;
    private Button btn_mycar_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left = (TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_mid);
        title.setText("我的爱车");
        right = (TextView) findViewById(R.id.tv_right);
        right.setOnClickListener(this);
        right.setVisibility(View.VISIBLE);
        right.setText("添加爱车");
        animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        iv_mycar_line1 = (ImageView) findViewById(R.id.iv_mycar_line1);
        iv_mycar_line2 = (ImageView) findViewById(R.id.iv_mycar_line2);
        iv_mycar_line3 = (ImageView) findViewById(R.id.iv_mycar_line3);
        iv_mycar_line4 = (ImageView) findViewById(R.id.iv_mycar_line4);
        iv_mycar_line5 = (ImageView) findViewById(R.id.iv_mycar_line5);
        line_mycar = (LinearLayout) findViewById(R.id.line_mycar);
        btn_mycar_add = (Button) findViewById(R.id.btn_mycar_add);
        btn_mycar_add.setOnClickListener(this);
        iv_mycar_meiyouaiche = (ImageView) findViewById(R.id.iv_mycar_meiyouaiche);
        rela_mycar_zuobaoyang = (RelativeLayout) findViewById(R.id.rela_mycar_zuobaoyang);
        rela_mycar_zuobaoyang.setOnClickListener(this);
        rela_mycar_tiechetie = (RelativeLayout) findViewById(R.id.rela_mycar_tiechetie);
        rela_mycar_tiechetie.setOnClickListener(this);
        isToMember = getIntent().getBooleanExtra("isToMember", false);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    StopAnimation();
                    iv_mycar_line1.startAnimation(animation);
                }
                if (position == 1) {
                    StopAnimation();
                    iv_mycar_line2.startAnimation(animation);
                }
                if (position == 2) {
                    StopAnimation();
                    iv_mycar_line3.startAnimation(animation);
                }
                if (position == 3) {
                    StopAnimation();
                    iv_mycar_line4.startAnimation(animation);
                }
                if (position == 4) {
                    StopAnimation();
                    iv_mycar_line5.startAnimation(animation);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void StopAnimation() {
        iv_mycar_line1.clearAnimation();
        iv_mycar_line2.clearAnimation();
        iv_mycar_line3.clearAnimation();
        iv_mycar_line4.clearAnimation();
        iv_mycar_line5.clearAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetMyCar();

    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()) {
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            if (isToMember) {
                goToMember();
            } else {
                goToHome();
            }
        } else if (R.id.tv_right == id) {
            KaKuApplication.flag_car = "T";
            GoToPinPaiXuanZe();
        } else if (R.id.rela_mycar_zuobaoyang == id) {
            startActivity(new Intent(this, BaoYangActivity.class));
        } else if (R.id.rela_mycar_tiechetie == id) {
            Utils.GetAdType();
        } else if (R.id.btn_mycar_add == id) {
            KaKuApplication.flag_car = "T";
            GoToPinPaiXuanZe();
        }
    }

    public void GetMyCar() {
        showProgressDialog();
        MyCarReq req = new MyCarReq();
        req.code = "20020";
        KaKuApiProvider.GetMyCar(req, new KakuResponseListener<MyCarResp>(this, MyCarResp.class) {
            @Override
            public void onSucceed(int what, Response response) {
                super.onSucceed(what, response);

                if (t != null) {
                    LogUtil.E("mycar res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        SetText(t.driver_cars);
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

    public void SetText(final List<MyCarObj> list_car) {
        size = list_car.size();
        if (size == 0) {
            iv_mycar_meiyouaiche.setVisibility(View.VISIBLE);
            btn_mycar_add.setVisibility(View.VISIBLE);
            return;
        } else if (size == 1) {
            line_mycar.setVisibility(View.VISIBLE);
            iv_mycar_line1.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(context).download(iv_mycar_line1, KaKuApplication.qian_zhui + list_car.get(0).getImage_brand());
            iv_mycar_line1.startAnimation(animation);
        } else if (size == 2) {
            line_mycar.setVisibility(View.VISIBLE);
            iv_mycar_line1.setVisibility(View.VISIBLE);
            iv_mycar_line2.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(context).download(iv_mycar_line1, KaKuApplication.qian_zhui + list_car.get(0).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line2, KaKuApplication.qian_zhui + list_car.get(1).getImage_brand());
            iv_mycar_line1.startAnimation(animation);
        } else if (size == 3) {
            line_mycar.setVisibility(View.VISIBLE);
            iv_mycar_line1.setVisibility(View.VISIBLE);
            iv_mycar_line2.setVisibility(View.VISIBLE);
            iv_mycar_line3.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(context).download(iv_mycar_line1, KaKuApplication.qian_zhui + list_car.get(0).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line2, KaKuApplication.qian_zhui + list_car.get(1).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line3, KaKuApplication.qian_zhui + list_car.get(2).getImage_brand());
            iv_mycar_line1.startAnimation(animation);
        } else if (size == 4) {
            line_mycar.setVisibility(View.VISIBLE);
            iv_mycar_line1.setVisibility(View.VISIBLE);
            iv_mycar_line2.setVisibility(View.VISIBLE);
            iv_mycar_line3.setVisibility(View.VISIBLE);
            iv_mycar_line4.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(context).download(iv_mycar_line1, KaKuApplication.qian_zhui + list_car.get(0).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line2, KaKuApplication.qian_zhui + list_car.get(1).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line3, KaKuApplication.qian_zhui + list_car.get(2).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line4, KaKuApplication.qian_zhui + list_car.get(3).getImage_brand());
            iv_mycar_line1.startAnimation(animation);
        } else if (size == 5) {
            line_mycar.setVisibility(View.VISIBLE);
            iv_mycar_line1.setVisibility(View.VISIBLE);
            iv_mycar_line2.setVisibility(View.VISIBLE);
            iv_mycar_line3.setVisibility(View.VISIBLE);
            iv_mycar_line4.setVisibility(View.VISIBLE);
            iv_mycar_line5.setVisibility(View.VISIBLE);
            BitmapUtil.getInstance(context).download(iv_mycar_line1, KaKuApplication.qian_zhui + list_car.get(0).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line2, KaKuApplication.qian_zhui + list_car.get(1).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line3, KaKuApplication.qian_zhui + list_car.get(2).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line4, KaKuApplication.qian_zhui + list_car.get(3).getImage_brand());
            BitmapUtil.getInstance(context).download(iv_mycar_line5, KaKuApplication.qian_zhui + list_car.get(4).getImage_brand());
            iv_mycar_line1.startAnimation(animation);
        }
        for (int i = 0; i < list_car.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_viewpager, null);
            TextView tv_mycar_carnum = (TextView) view.findViewById(R.id.tv_mycar_carnum);
            TextView tv_mycar_mali = (TextView) view.findViewById(R.id.tv_mycar_mali);
            TextView tv_mycar_chexi = (TextView) view.findViewById(R.id.tv_mycar_chexi);
            TextView tv_mycar_carbrand = (TextView) view.findViewById(R.id.tv_mycar_carbrand);
            TextView tv_mycar_bianji = (TextView) view.findViewById(R.id.tv_mycar_bianji);
            ImageView iv_mycar_moren = (ImageView) view.findViewById(R.id.iv_mycar_moren);
            if ("Y".equals(list_car.get(i).getFlag_default())) {
                iv_mycar_moren.setVisibility(View.VISIBLE);
            } else {
                iv_mycar_moren.setVisibility(View.GONE);
            }
            tv_mycar_carnum.setText(list_car.get(i).getCar_no());
            tv_mycar_mali.setText(list_car.get(i).getPower() + "马力");
            tv_mycar_chexi.setText(list_car.get(i).getName_engine() + list_car.get(i).getSeries_engine());
            tv_mycar_carbrand.setText(list_car.get(i).getName_brand());
            final int finalI = i;
            tv_mycar_bianji.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    KaKuApplication.id_driver_car = list_car.get(finalI).getId_driver_car();
                    KaKuApplication.flag_car = "B";
                    startActivity(new Intent(MyCarActivity.this, AddCarActivity.class));
                }
            });
            viewContainter.add(view);
        }

        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewContainter.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewContainter.get(position));
                return viewContainter.get(position);
            }
        });


    }

    public void GoToPinPaiXuanZe() {
        startActivity(new Intent(this, PinPaiXuanZeActivity.class));
    }

    private void goToHome() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME1);
        startActivity(intent);
        finish();
    }

    private void goToMember() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.GO_TO_TAB, Constants.TAB_POSITION_HOME5);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isToMember) {
                goToMember();
            } else {
                goToHome();
            }
        }
        return false;
    }

}
