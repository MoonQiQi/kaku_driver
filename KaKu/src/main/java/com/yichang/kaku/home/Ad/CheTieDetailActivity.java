package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.global.MyActivityManager;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailScrollViewPage;
import com.yichang.kaku.home.giftmall.sliding.ProductDetailSlidingMenu;
import com.yichang.kaku.obj.Addr2Obj;
import com.yichang.kaku.request.GetCheTieDetailReq;
import com.yichang.kaku.response.GetCheTieDetailResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.DensityUtils;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.widget.InputYaoCodeWindow;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class CheTieDetailActivity extends BaseActivity implements OnClickListener{
	
	private TextView left,right,title;
	private ImageView iv_chetiedetail_image;
	private TextView tv_chetiedetail_name,tv_chetiedetail_funame,tv_chetiedetail_shoutiejia,tv_chetiedetail_price,
					  tv_chetiedetail_cuxiao1info,tv_chetiedetail_cuxiao2info,tv_chetiedetail_tishi1info,tv_chetiedetail_tishi2info,
					  tv_chetiedetail_tishi3info,tv_chetiedetail_people,tv_chetiedetail_pay;
	private String string_money;
	private ProductDetailSlidingMenu productDetailSlidingMenu;
	private String phone;
	private LinearLayout ll_product_content;
	private LinearLayout line_chetiedetail_call, ll_shopcar;
	private ProductDetailScrollViewPage productDetailScrollViewPage;
	private String flag_reco,flag_one,flag_advert;
	private Boolean isPwdPopWindowShow = false;

	private String mFlag_pay;
	private String mFlag_one;
	private String mPrice_advert;
	private String mBreaks_money;
	private String mMoney_balance;
	private Addr2Obj mAddr;
	private String mImage_advert;
	private String mName_advert;
	private String mId_advert;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chetiedetail);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("车贴详情");
		iv_chetiedetail_image= (ImageView) findViewById(R.id.iv_chetiedetail_image);
		tv_chetiedetail_name= (TextView) findViewById(R.id.tv_chetiedetail_name);
		tv_chetiedetail_funame= (TextView) findViewById(R.id.tv_chetiedetail_funame);
		tv_chetiedetail_shoutiejia= (TextView) findViewById(R.id.tv_chetiedetail_shoutiejia);
		tv_chetiedetail_price= (TextView) findViewById(R.id.tv_chetiedetail_price);
		tv_chetiedetail_people= (TextView) findViewById(R.id.tv_chetiedetail_people);
		tv_chetiedetail_cuxiao1info= (TextView) findViewById(R.id.tv_chetiedetail_cuxiao1info);
		tv_chetiedetail_cuxiao2info= (TextView) findViewById(R.id.tv_chetiedetail_cuxiao2info);
		tv_chetiedetail_tishi1info= (TextView) findViewById(R.id.tv_chetiedetail_tishi1info);
		tv_chetiedetail_tishi2info= (TextView) findViewById(R.id.tv_chetiedetail_tishi2info);
		tv_chetiedetail_tishi3info= (TextView) findViewById(R.id.tv_chetiedetail_tishi3info);
		tv_chetiedetail_pay= (TextView) findViewById(R.id.tv_chetiedetail_pay);
		tv_chetiedetail_pay.setOnClickListener(this);

		ll_product_content = (LinearLayout) findViewById(R.id.ll_product_content);
		line_chetiedetail_call = (LinearLayout) findViewById(R.id.line_chetiedetail_call);
		line_chetiedetail_call.setOnClickListener(this);

		productDetailScrollViewPage = findView(R.id.ysnowScrollViewPageOne);
		productDetailScrollViewPage.setScreenHeight(100);

		productDetailSlidingMenu = (ProductDetailSlidingMenu) findViewById(R.id.expanded_menu);
		productDetailSlidingMenu.setScreenHeight(100);
	}

	@Override
	protected void onStart() {
		super.onStart();
		GetCheTieDetail();
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
		} else if (R.id.line_chetiedetail_call == id){
			Utils.Call(this,phone);
		} else if (R.id.tv_chetiedetail_pay == id){
			if (TextUtils.equals(flag_reco,"Y")){
				//弹邀请码框
				showPwdInputWindow();
				return;
			} else if ((TextUtils.equals(flag_reco,"N"))){
				if (TextUtils.equals(flag_advert,"Y")){
					//去拍照页面
					Intent intent = new Intent(this,QiangImageActivity.class);
					/*Bundle bundle = new Bundle();
					bundle.putString("flag_one",mFlag_one);
					bundle.putString("money_balance",mMoney_balance);
					bundle.putString("flag_pay",mFlag_pay);

					bundle.putString("price_advert",mPrice_advert);
					bundle.putString("breaks_money",mBreaks_money);
					bundle.putString("image_advert",mImage_advert);
					bundle.putString("name_advert",mName_advert);
					bundle.putString("id_advert", mId_advert);
					bundle.putSerializable("addr", (Serializable) mAddr);
					intent.putExtras(bundle);*/

					startActivity(intent);



					return;
				} else if ((TextUtils.equals(flag_advert,"N"))){
					//去订单结算页面
					Intent intent = new Intent(this,StickerOrderActivity.class);
					/*Bundle bundle = new Bundle();
					bundle.putString("flag_one",mFlag_one);
					bundle.putString("money_balance",mMoney_balance);
					bundle.putString("flag_pay",mFlag_pay);

					bundle.putString("price_advert",mPrice_advert);
					bundle.putString("breaks_money",mBreaks_money);
					bundle.putString("image_advert",mImage_advert);
					bundle.putString("name_advert",mName_advert);
					bundle.putString("id_advert", mId_advert);
					bundle.putSerializable("addr", (Serializable) mAddr);
					intent.putExtras(bundle);*/

					startActivity(intent);

				}
			}
		}

	}

	public void GetCheTieDetail(){
		showProgressDialog();
		GetCheTieDetailReq req = new GetCheTieDetailReq();
		req.code = "60032";
		req.id_driver = Utils.getIdDriver();
		req.id_advert = KaKuApplication.id_advert;
		KaKuApiProvider.getCheTieDetail(req, new BaseCallback<GetCheTieDetailResp>(GetCheTieDetailResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, GetCheTieDetailResp t) {
				if (t != null) {
					LogUtil.E("getchetiedetail res: " + t.res);
					LogUtil.E("flag_reco res: " + t.flag_reco);
					LogUtil.E("flag_advert res: " + t.flag_advert);
					if (Constants.RES.equals(t.res)) {
						phone = t.customer_tel;
						flag_reco = t.flag_reco;
						flag_advert = t.flag_advert;
						flag_one = t.flag_one;
						tv_chetiedetail_name.setText(t.advert.getName_advert());
						tv_chetiedetail_funame.setText(t.advert.getPromotion_advert());
						double yuanjia = Double.parseDouble(t.advert.getPrice_advert());
						double youhui = Double.parseDouble(t.advert.getBreaks_money());

						if (TextUtils.equals(t.advert.getBreaks_money(),"0")){
							string_money = "购买价 ¥"+(yuanjia - youhui);
						} else {
							string_money = "首贴价 ¥"+(yuanjia - youhui);
						}
						SpannableStringBuilder style = new SpannableStringBuilder(string_money);
						style.setSpan(new AbsoluteSizeSpan(18, true), 3, string_money.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						tv_chetiedetail_shoutiejia.setText(style);

						tv_chetiedetail_price.setText("价格："+t.advert.getPrice_advert());
						tv_chetiedetail_people.setText("已售："+t.advert.getSales_num());
						tv_chetiedetail_cuxiao1info.setText(t.advert.getRemark_content1());
						tv_chetiedetail_cuxiao2info.setText(t.advert.getRemark_content2());
						tv_chetiedetail_tishi1info.setText("收 益 周 期：" + t.advert.getTime_begin() + "至" + t.advert.getTime_end());
						tv_chetiedetail_tishi2info.setText("预计总收益：" + t.advert.getTotal_earnings());
						tv_chetiedetail_tishi3info.setText("日 收 益：" + t.advert.getDay_earnings());
						BitmapUtil.getInstance(context).download(iv_chetiedetail_image, KaKuApplication.qian_zhui + t.advert.getImage_advert());
						ll_product_content.measure(0, 0);

						mFlag_pay=t.flag_pay;
						mFlag_one=t.flag_one;
						mMoney_balance=t.money_balance;
						mAddr=t.addr;
						mPrice_advert=t.advert.price_advert;
						mBreaks_money=t.advert.breaks_money;
						mImage_advert=t.advert.image_advert;
						mName_advert = t.advert.name_advert;
						mId_advert=t.advert.id_advert;

						KaKuApplication.breaks_money_qiang = mBreaks_money;
						KaKuApplication.flag_one_qiang = mFlag_one;
						KaKuApplication.id_advert_qiang = mId_advert;
						KaKuApplication.flag_pay_qiang = mFlag_pay;
						KaKuApplication.image_advert_qiang = mImage_advert;
						KaKuApplication.money_balance_qiang = mMoney_balance;
						KaKuApplication.price_advert_qiang = mPrice_advert;
						KaKuApplication.name_advert_qiang = mName_advert;
						KaKuApplication.addr_qiang = mAddr;

						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								productDetailSlidingMenu.smoothScrollTo(0, -DensityUtils.dp2px(context, 50) - DensityUtils.getStatusHeight(MyActivityManager.getInstance().getActivty()));
							}
						}, 200);

						productDetailSlidingMenu.setWebUrl(t.advert.getUrl_advert());

					}  else {
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

	public void toggleMenu(View v) {
		productDetailSlidingMenu.toggleMenu();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!isPwdPopWindowShow) {
			finish();
		}
	}

	private void showPwdInputWindow() {
		getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				isPwdPopWindowShow = true;

				InputYaoCodeWindow input =
						new InputYaoCodeWindow(CheTieDetailActivity.this);
				input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				input.setConfirmListener(new InputYaoCodeWindow.ConfirmListener() {
					@Override
					public void confirmPwd(Boolean isConfirmed) {
						if (isConfirmed) {
							isPwdPopWindowShow = false;
						}
					}

					@Override
					public void showDialog() {
						showProgressDialog();
					}

					@Override
					public void stopDialog() {
						stopProgressDialog();
					}

				});

				input.show();

			}
		}, 200);
	}

}
