package com.yichang.kaku.home.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wly.android.widget.AdGalleryHelper;
import com.wly.android.widget.Advertising;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.dingwei.DaoHangActivity;
import com.yichang.kaku.member.serviceorder.PingJiaOrderActivity;
import com.yichang.kaku.obj.RollsObj;
import com.yichang.kaku.request.CancleCollectReq;
import com.yichang.kaku.request.CollectShopReq;
import com.yichang.kaku.request.ShopDetailReq;
import com.yichang.kaku.response.CancleCollectResp;
import com.yichang.kaku.response.CollectShopResp;
import com.yichang.kaku.response.ShopDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailActivity extends BaseActivity implements OnClickListener{

    private TextView left;
    private RelativeLayout rela_shopdetail_addr,rela_shopdetail_phone,rela_shopdetail_pingjia;
    private ImageView iv_shopdetail_image,iv_shopdetail_guanzhu,iv_shopdetail_image2;
    private TextView tv_shopdetail_shopname,tv_shopdetail_shoptime,tv_shopdetail_addr,tv_shopdetail_phone,
                      tv_shopdetail_pingjiatime,tv_shopdetail_content,tv_shopdetail_more;
    private RatingBar star_shopdetail;
    private String lat,lon,flag_guanzhu;
    private Button btn_shopdetail_dianping;
    private String name_shop,phone_shop,addr_shop,image_shop;
    private RelativeLayout rela_shopdetail_gallery;
    private AdGalleryHelper mGalleryHelper;
    private List<RollsObj> rolls_list = new ArrayList<RollsObj>();
    private String url_ad;
    private String name_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        left=(TextView) findViewById(R.id.tv_left);
        left.setOnClickListener(this);
        iv_shopdetail_image= (ImageView) findViewById(R.id.iv_shopdetail_image);
        iv_shopdetail_image2= (ImageView) findViewById(R.id.iv_shopdetail_image2);
        rela_shopdetail_addr= (RelativeLayout) findViewById(R.id.rela_shopdetail_addr);
        rela_shopdetail_addr.setOnClickListener(this);
        rela_shopdetail_phone= (RelativeLayout) findViewById(R.id.rela_shopdetail_phone);
        rela_shopdetail_phone.setOnClickListener(this);
        rela_shopdetail_pingjia= (RelativeLayout) findViewById(R.id.rela_shopdetail_pingjia);
        tv_shopdetail_shopname= (TextView) findViewById(R.id.tv_shopdetail_shopname);
        tv_shopdetail_shoptime= (TextView) findViewById(R.id.tv_shopdetail_shoptime);
        tv_shopdetail_addr= (TextView) findViewById(R.id.tv_shopdetail_addr);
        tv_shopdetail_phone= (TextView) findViewById(R.id.tv_shopdetail_phone);
        iv_shopdetail_guanzhu= (ImageView) findViewById(R.id.iv_shopdetail_guanzhu);
        iv_shopdetail_guanzhu.setOnClickListener(this);
        tv_shopdetail_pingjiatime= (TextView) findViewById(R.id.tv_shopdetail_pingjiatime);
        tv_shopdetail_content= (TextView) findViewById(R.id.tv_shopdetail_content);
        tv_shopdetail_more= (TextView) findViewById(R.id.tv_shopdetail_more);
        tv_shopdetail_more.setOnClickListener(this);
        star_shopdetail= (RatingBar) findViewById(R.id.star_shopdetail);
        btn_shopdetail_dianping= (Button) findViewById(R.id.btn_shopdetail_dianping);
        btn_shopdetail_dianping.setOnClickListener(this);
        rela_shopdetail_gallery= (RelativeLayout) findViewById(R.id.shopdetail_gallery);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShopDetail();
    }

    @Override
    public void onClick(View v) {
        Utils.NoNet(context);
        if (Utils.Many()){
            return;
        }
        int id = v.getId();
        if (R.id.tv_left == id) {
            finish();
        } else if (R.id.rela_shopdetail_addr == id){
            Intent intent = new Intent(this,DaoHangActivity.class);
            intent.putExtra("e_lat",lat);
            intent.putExtra("e_lon",lon);
            startActivity(intent);
        } else if (R.id.rela_shopdetail_phone == id){
            Utils.Call(ShopDetailActivity.this,phone_shop);
        } else if (R.id.iv_shopdetail_guanzhu == id){
            if ("N".equals(flag_guanzhu)) {
                CollectShop();
            } else {
                CancleCollect();
            }
        } else if (R.id.tv_shopdetail_more == id){
            startActivity(new Intent(this,ShopPingJiaActivity.class));
        } else if (R.id.btn_shopdetail_dianping == id){
            Intent intent = new Intent(this, PingJiaOrderActivity.class);
            Bundle bundle = new Bundle();
            KaKuApplication.flag_IsDetailToPingJia = true;
            bundle.putString("image_shop",image_shop);
            bundle.putString("name_shop",name_shop);
            bundle.putString("addr_shop", addr_shop);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void ShopDetail(){
        showProgressDialog();
        ShopDetailReq req = new ShopDetailReq();
        req.code = "8005";
        req.id_shop = KaKuApplication.id_shop;
        req.id_driver = Utils.getIdDriver();
        KaKuApiProvider.ShopDetail(req, new BaseCallback<ShopDetailResp>(ShopDetailResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, ShopDetailResp t) {
                if (t != null) {
                    LogUtil.E("shopdetail res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        //轮播图
                        rolls_list = t.rolls;
                        autoAdvance(rolls_list);
                        url_ad = rolls_list.get(0).getUrl_roll();
                        name_ad = rolls_list.get(0).getName_roll();
                        tv_shopdetail_shopname.setText(t.shop.getName_shop());
                        tv_shopdetail_shoptime.setText("营业时间："+t.shop.getHour_shop_begin()+"-"+t.shop.getHour_shop_end());
                        tv_shopdetail_addr.setText(t.shop.getAddr_shop());
                        tv_shopdetail_phone.setText(t.shop.getMobile_shop());
                        tv_shopdetail_content.setText(t.eval.getContent_eval());
                        tv_shopdetail_pingjiatime.setText(t.eval.getName_driver()+"  "+t.eval.getTime_eval());
                        lat=t.shop.getVar_lat();
                        lon=t.shop.getVar_lon();
                        phone_shop = t.shop.getMobile_shop();
                        name_shop = t.shop.getName_shop();
                        addr_shop = t.shop.getAddr_shop();
                        image_shop = t.shop.getImage_shop();
                        if ("Y".equals(t.shop.getIs_collection())){
                            iv_shopdetail_guanzhu.setImageResource(R.drawable.yiguanzhu);
                        } else {
                            iv_shopdetail_guanzhu.setImageResource(R.drawable.weiguanzhu);
                        }
                        if ("Y".equals(t.shop.getIs_collection())) {
                            iv_shopdetail_guanzhu.setImageResource(R.drawable.yiguanzhu);
                            flag_guanzhu = "Y";
                        } else {
                            iv_shopdetail_guanzhu.setImageResource(R.drawable.weiguanzhu);
                            flag_guanzhu = "N";
                        }
                        if (TextUtils.isEmpty(t.eval.getContent_eval())){
                            rela_shopdetail_pingjia.setVisibility(View.GONE);
                            tv_shopdetail_more.setTextColor(getResources().getColor(R.color.color_word));
                            tv_shopdetail_more.setText("新店入驻，我来评价");
                        } else {
                            rela_shopdetail_pingjia.setVisibility(View.VISIBLE);
                            tv_shopdetail_more.setText("查看更多评价");
                        }

                        String star2 = t.eval.getStar_eval();
                        String star1 = t.shop.getNum_star();
                        if (!TextUtils.isEmpty(star1)){
                            float starFloat1 = Float.valueOf(star1);
                            star_shopdetail.setRating(starFloat1);
                        }
                        if (!TextUtils.isEmpty(star2)){
                            float starFloat2 = Float.valueOf(star2);
                            star_shopdetail.setRating(starFloat2);
                        }
                        BitmapUtil.getInstance(context).download(iv_shopdetail_image, KaKuApplication.qian_zhui + t.shop.getImage_shop());
                        BitmapUtil.getInstance(context).download(iv_shopdetail_image2, KaKuApplication.qian_zhui + t.shop.getImage_shop_up());
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

    public void CollectShop() {
        Utils.NoNet(context);
        showProgressDialog();
        CollectShopReq req = new CollectShopReq();
        req.code = "4004";
        req.id_driver = Utils.getIdDriver();
        req.id_shop = KaKuApplication.id_shop;
        KaKuApiProvider.CollectShop(req, new BaseCallback<CollectShopResp>(CollectShopResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CollectShopResp t) {
                if (t != null) {
                    LogUtil.E("collectshop res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        iv_shopdetail_guanzhu.setImageResource(R.drawable.yiguanzhu);
                        flag_guanzhu = "Y";
                    } else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    public void CancleCollect() {
        Utils.NoNet(context);
        showProgressDialog();
        CancleCollectReq req = new CancleCollectReq();
        req.code = "4005";
        req.id_driver = Utils.getIdDriver();
        req.id_shop = KaKuApplication.id_shop;
        KaKuApiProvider.CancleCollect(req, new BaseCallback<CancleCollectResp>(CancleCollectResp.class) {
            @Override
            public void onSuccessful(int statusCode, Header[] headers, CancleCollectResp t) {
                if (t != null) {
                    LogUtil.E("canclecollect res: " + t.res);
                    if (Constants.RES.equals(t.res)) {
                        iv_shopdetail_guanzhu.setImageResource(R.drawable.weiguanzhu);
                        flag_guanzhu = "N";
                    } else {
                        if (Constants.RES_TEN.equals(t.res)){
                            Utils.Exit(context);
                            finish();
                        }
                    }
                    LogUtil.showShortToast(context, t.msg);
                }
                stopProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {
                stopProgressDialog();
            }
        });
    }

    private void autoAdvance(List<RollsObj> imgList) {
        // TODO Auto-generated method stub
        if (imgList == null) {
            return;
        }
        if (imgList.size() <= 0) {
            return;
        }
        List<Advertising> list = new ArrayList<Advertising>();

        for (RollsObj obj : imgList) {
            Advertising advertising = new Advertising(obj.getImage_roll(), obj.getUrl_roll(), null);
            advertising.setPicURL(KaKuApplication.qian_zhui + obj.getImage_roll());
            list.add(advertising);
        }

        if (list.size() > 0) {
            mGalleryHelper = new AdGalleryHelper(context, list, Constants.AUTO_SCROLL_DURATION,false,false,true);
            rela_shopdetail_gallery.addView(mGalleryHelper.getLayout());

        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mGalleryHelper != null) {
            mGalleryHelper.stopAutoSwitch();
        }
    }

}
