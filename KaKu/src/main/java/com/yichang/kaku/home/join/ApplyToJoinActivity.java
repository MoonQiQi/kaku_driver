package com.yichang.kaku.home.join;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.squareup.okhttp.Request;
import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ChooseTimeActivity;
import com.yichang.kaku.home.FindShopLocationActivity;
import com.yichang.kaku.response.BrandListResp;
import com.yichang.kaku.response.ImageUploadResp;
import com.yichang.kaku.response.LocationInfo;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.RegexpUtils;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.tools.okhttp.OkHttpUtil;
import com.yichang.kaku.tools.okhttp.Params;
import com.yichang.kaku.tools.okhttp.RequestCallback;
import com.yichang.kaku.view.widget.PicturePickPopWindow;
import com.yichang.kaku.webService.UrlCtnt;

import java.io.IOException;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by xiaosu on 2015/11/11.
 * 申请加盟
 */
public class ApplyToJoinActivity extends BaseActivity implements PicturePickPopWindow.Callback {

    private static final int TIME_REQUEST = 1001;
    private static final int BRAND_REQUEST = 1002;
    private static final int INTRO_REQUEST = 1003;
    private PicturePickPopWindow popWindow;
    private ImageView iv_shop;
    private KaKuApplication application;
    private TextView tvLoaction;
    private TextView tv_mid;
    private BDLocation mLocation;

    private LocationInfo mLocationInfo;

    private String hour_shop_begin = "09:00";
    private String hour_shop_end = "18:00";
    private TextView tvTime;
    private BrandAdapter brandAdapter;
    private GridView noScrollGridView;
    private ArrayList<BrandListResp.ItemEntity> mList;
    private TextView et_shop_name;
    private EditText et_phone;
    private EditText et_tel;
    private StringBuffer id_brands,name_brands;
    private String intro;
    private String bitmapStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_to_join);

        initView();

        EventBus.getDefault().register(this);
        application = (KaKuApplication) getApplication();//开始定位
        application.startLocate();
    }

    private void initView() {
        iv_shop = (ImageView) findViewById(R.id.iv_shop);
        tvLoaction = (TextView) findViewById(R.id.location);
        tv_mid = findView(R.id.tv_mid);
        tvTime = findView(R.id.tv_time);
        et_phone = findView(R.id.et_phone);
        et_tel = findView(R.id.et_tel);
        et_shop_name = findView(R.id.et_shop_name);
        noScrollGridView = findView(R.id.noScrollGridView);

        tv_mid.setText("商户加盟");
        tvTime.setText(hour_shop_begin + "-" + hour_shop_end);

        /**结束页面*/
        findView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 选择图片
     *
     * @param view
     */
    public void pickImg(View view) {
        if (popWindow == null) {
            popWindow = new PicturePickPopWindow(this, getResources().getDimensionPixelOffset(R.dimen.x130));
            popWindow.setmCallback(this);
        }
        popWindow.show();
    }

    /**
     * 选择时间
     *
     * @param view
     */
    public void chooseTime(View view) {
        startActivityForResult(new Intent(this, ChooseTimeActivity.class)
                .putExtra("hour_shop_begin", hour_shop_begin)
                .putExtra("hour_shop_end", hour_shop_end), TIME_REQUEST);
    }

    /**
     * 定位店铺地址
     *
     * @param view
     */
    public void queryCity(View view) {
        Intent intent = new Intent(this, FindShopLocationActivity.class);
        if (mLocation != null) {
            if (mLocationInfo == null) {
                intent.putExtra("lat", mLocation.getLatitude());
                intent.putExtra("lng", mLocation.getLongitude());
                intent.putExtra("city", mLocation.getCity());
                intent.putExtra("name", mLocation.getAddrStr());
            } else {//说明用户选择了位置
                intent.putExtra("lat", mLocationInfo.latLng.latitude);
                intent.putExtra("lng", mLocationInfo.latLng.longitude);
                intent.putExtra("city", mLocationInfo.city);
                intent.putExtra("name", mLocationInfo.name);
            }
        }
        startActivity(intent);
    }

    /**
     * 选择品牌
     *
     * @param view
     */
    public void chooseBrand(View view) {
        Intent intent = new Intent(this, BrandListActivity.class);
        if (mList != null) {
            intent.putParcelableArrayListExtra("info", mList);
        }
        startActivityForResult(intent, BRAND_REQUEST);
    }

    /**
     * 店铺介绍
     *
     * @param view
     */
    public void toIntro(View view) {
        Intent intent = new Intent(this, ShopIntroductionActivity.class);
        if (!TextUtils.isEmpty(intro)) {
            intent.putExtra("intro", intro);
        }
        startActivityForResult(intent, INTRO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode > PicturePickPopWindow.REQUEST_DOMAIN) {
            popWindow.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TIME_REQUEST:
                    hour_shop_begin = data.getStringExtra("hour_shop_begin");
                    hour_shop_end = data.getStringExtra("hour_shop_end");
                    tvTime.setText(hour_shop_begin + "-" + hour_shop_end);
                    break;
                case BRAND_REQUEST:
                    mList = data.getParcelableArrayListExtra("brands");
                    createId_brands();
                    if (brandAdapter == null) {
                        brandAdapter = new BrandAdapter(mList);
                        noScrollGridView.setAdapter(brandAdapter);
                        noScrollGridView.setVisibility(View.VISIBLE);
                    } else {
                        brandAdapter.notifyDataSetChanged(mList);
                    }
                    break;
                case INTRO_REQUEST:
                    intro = data.getStringExtra("intro");
                    break;
            }
        }

    }

    private void createId_brands() {
        id_brands = new StringBuffer();
        name_brands = new StringBuffer();
        for (BrandListResp.ItemEntity entity : mList) {
            id_brands.append(entity.id_brand).append(",");
            name_brands.append(entity.name_brand).append(",");
        }
        id_brands.delete(id_brands.length() - 1, id_brands.length());
        name_brands.delete(name_brands.length() - 1, name_brands.length());
    }

    @Override
    public void photoCutCallback(Bitmap bitmap) {
        iv_shop.setImageBitmap(bitmap);

        showProgressDialog();

        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("code", "90020")
                .p("type", "business")
                .p("img", BitmapUtil.bitmap2Str(bitmap));

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<ImageUploadResp>(this, ImageUploadResp.class) {
            @Override
            public void onSuccess(ImageUploadResp obj, String result) {
                bitmapStr = obj.path;
                stopProgressDialog();
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });

    }

    public void onEvent(BDLocation location) {
        this.mLocation = location;
        tvLoaction.setText(location.getAddrStr());
        application.stopLocate();
    }

    public void onEvent(LocationInfo mLocationInfo) {
        this.mLocationInfo = mLocationInfo;
        tvLoaction.setText(mLocationInfo.name);
    }

    /**
     * 提交数据
     *
     * @param v
     */
    public void sendData(View v) {

        String shop_name = getText(et_shop_name);
        if (TextUtils.isEmpty(shop_name)) {
            showShortToast("请输入店铺名称");
            return;
        }

        String tel = getText(et_tel);
        if (TextUtils.isEmpty(tel)) {
            showShortToast("请输入店铺座机号码");
            return;
        }

        if (!RegexpUtils.isMatch(tel, RegexpUtils.PHONE_REGEXP)) {
            showShortToast("请输入正确的座机号码");
            return;
        }

        if (!TextUtils.isEmpty(getText(et_phone)) && !RegexpUtils.isMatch(getText(et_phone), RegexpUtils.MOBILE_PHONE_REGEXP)) {
            showShortToast("请输入正确的手机号码");
            return;
        }

        if (id_brands == null) {
            showShortToast("请选择品牌");
            return;
        }

        if (TextUtils.isEmpty(bitmapStr)) {
            showShortToast("请选择图片");
            return;
        }

        showProgressDialog();

        Params.builder builder = new Params.builder();
        builder.p("sid", Utils.getSid())
                .p("code", "9004")
                .p("id_driver", Utils.getIdDriver())
                .p("hour_end", hour_shop_end)
                .p("name_shop", et_shop_name.getText().toString().trim())
                .p("addr", mLocationInfo == null ? mLocation.getAddrStr() : mLocationInfo.name)
                .p("lng", mLocationInfo == null ? String.valueOf(mLocation.getLongitude()) : String.valueOf(mLocationInfo.latLng.longitude))
                .p("lat", mLocationInfo == null ? String.valueOf(mLocation.getLatitude()) : String.valueOf(mLocationInfo.latLng.latitude))
                .p("phone1", getText(et_phone))
                .p("tel", getText(et_tel))
                .p("id_brands", id_brands.toString())
                .p("name_brands", name_brands.toString())
                .p("description1", intro)
                .p("img", bitmapStr)
                .p("hour_begin", hour_shop_begin);

        OkHttpUtil.postAsync(UrlCtnt.BASEIP, builder.build(), new RequestCallback<BrandListResp>(this, BrandListResp.class) {
            @Override
            public void onSuccess(BrandListResp obj, String result) {
                stopProgressDialog();
                showShortToast("信息保存成功");
            }

            @Override
            public void onInnerFailure(Request request, IOException e) {
                stopProgressDialog();
                showShortToast("网络连接失败，请稍后再试");
            }
        });

    }
}
