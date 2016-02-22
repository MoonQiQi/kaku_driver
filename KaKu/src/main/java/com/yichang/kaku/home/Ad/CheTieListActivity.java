package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.obj.CheTieObj;
import com.yichang.kaku.request.CheTieListReq;
import com.yichang.kaku.request.GetFlagShowReq;
import com.yichang.kaku.response.CheTieListResp;
import com.yichang.kaku.response.GetFlagShowResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.view.ValidatePopWindow;
import com.yichang.kaku.view.widget.YiYuanPopWindow;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class CheTieListActivity extends BaseActivity implements OnClickListener , AdapterView.OnItemClickListener{
	
	private TextView left,right,title;
	private TextView tv_chetielist_getchetie;
	private ListView lv_chetielist;
	private List<CheTieObj> list_chetie = new ArrayList<CheTieObj>();
	private CheTieAdapter adapter_chetie;
	private Boolean isPwdPopWindowShow = false;


	private View mCoin;
	private static Boolean isClose = false;
	private static Boolean isImgShow = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chetielist);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("抢车贴");
		right= (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setText("我的车贴");
		right.setOnClickListener(this);
		tv_chetielist_getchetie= (TextView) findViewById(R.id.tv_chetielist_getchetie);
		tv_chetielist_getchetie.setOnClickListener(this);
		lv_chetielist= (ListView) findViewById(R.id.lv_chetielist);
		lv_chetielist.setOnItemClickListener(this);
		mCoin = findViewById(R.id.iv_coin);

		GetCheTieList();
	}

	@Override
	protected void onStart() {
		super.onStart();
		getFlagShow();
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
		} else if (R.id.tv_right == id){
			Intent intent = new Intent(this,CheTieOrderListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (R.id.tv_chetielist_getchetie == id){
			showPwdInputWindow();
		}
	}

	public void GetCheTieList(){
		showProgressDialog();
		CheTieListReq req = new CheTieListReq();
		req.code = "60031";
		KaKuApiProvider.getCheTieList(req , new BaseCallback<CheTieListResp>(CheTieListResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, CheTieListResp t) {
				if (t != null) {
					LogUtil.E("chetielist res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						list_chetie = t.adverts;
						adapter_chetie = new CheTieAdapter(CheTieListActivity.this,list_chetie);
						lv_chetielist.setAdapter(adapter_chetie);
						Utils.setListViewHeightBasedOnChildren(lv_chetielist);
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (TextUtils.equals(list_chetie.get(position).getFlag_type(), "O")){
			LogUtil.showShortToast(this,"即将开始");
		} else if (TextUtils.equals(list_chetie.get(position).getFlag_type(),"Y")){
			KaKuApplication.id_advert = list_chetie.get(position).getId_advert();
			Intent intent = new Intent(this,CheTieDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else if (TextUtils.equals(list_chetie.get(position).getFlag_type(),"E")){
			LogUtil.showShortToast(this, "已结束");
		} else if (TextUtils.equals(list_chetie.get(position).getFlag_type(),"N")){
			LogUtil.showShortToast(this, "已抢完");
		}
	}


	private void createCoin() {
		mCoin.setVisibility(View.VISIBLE);

		mCoin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtil.E("isClose :" + isClose);
				if (isClose) {
					resetAnimation();

				} else {

					showValidatePopWindow();
					//mCoin.clearAnimation();
					//isImgShow = false;
					//mCoin.setVisibility(View.GONE);
				}

			}
		});
		startAnimation(5000);
	}

	private void getFlagShow() {
		Utils.NoNet(context);
		showProgressDialog();

		GetFlagShowReq req = new GetFlagShowReq();
		req.code = "60017";

		req.id_driver = Utils.getIdDriver();

		KaKuApiProvider.getCoinFlagShow(req, new BaseCallback<GetFlagShowResp>(GetFlagShowResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, GetFlagShowResp t) {
				if (t != null) {
					LogUtil.E("getCalendarList res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						//isImgShow = Boolean.parseBoolean(t.flag_show);
						LogUtil.E("chaih t.flag_show:"+t.flag_show);
						if ("Y".equals(t.flag_show)) {
							isImgShow = true;
							createCoin();


						} else {
							removeCoin();
						}
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
	private void removeCoin() {
		if (mCoin != null) {
			mCoin.clearAnimation();
			mCoin.setVisibility(View.GONE);
		}
	}


	private void startAnimation(Integer delayTime) {
		if (isImgShow) {
			RotateAnimation animation;
			animation = new RotateAnimation(0, -135, RotateAnimation.RELATIVE_TO_SELF, 0.8f, RotateAnimation.RELATIVE_TO_SELF, 0.2f);
			animation.setDuration(500);
			animation.setFillAfter(true);// 动画结束之后保持当时状态
			animation.setStartOffset(delayTime);// 设置动画延时开启的时间
			animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					isClose = true;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
			mCoin.startAnimation(animation);
		}
	}

	private void resetAnimation() {

		if (isImgShow) {
			RotateAnimation animation;
			animation = new RotateAnimation(-135, 0, RotateAnimation.RELATIVE_TO_SELF, 0.8f, RotateAnimation.RELATIVE_TO_SELF, 0.2f);
			animation.setDuration(500);
			animation.setFillAfter(true);// 动画结束之后保持当时状态
			animation.setStartOffset(0);// 设置动画延时开启的时间
			animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}
				@Override
				public void onAnimationEnd(Animation animation) {
					isClose=false;
					startAnimation(5000);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
			mCoin.startAnimation(animation);

		}

	}


	private void showValidatePopWindow() {
		getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				ValidatePopWindow input =
						new ValidatePopWindow(CheTieListActivity.this);
				input.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				input.setConfirmListener(new ValidatePopWindow.ConfirmListener() {
					@Override
					public void confirmValidateCode(Boolean isConfirmed) {

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
		}, 0);
	}

	private void showPwdInputWindow() {
		getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				isPwdPopWindowShow = true;

				YiYuanPopWindow input =
						new YiYuanPopWindow(CheTieListActivity.this , "4006867585");
				input.show();

			}
		}, 200);
	}

}
