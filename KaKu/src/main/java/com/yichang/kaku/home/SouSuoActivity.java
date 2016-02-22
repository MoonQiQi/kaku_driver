package com.yichang.kaku.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.giftmall.ShopMallActivity;
import com.yichang.kaku.obj.Shops_wxzObj;
import com.yichang.kaku.request.SouSuoReq;
import com.yichang.kaku.response.SouSuoResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SouSuoActivity extends BaseActivity implements OnClickListener ,AdapterView.OnItemClickListener,OnEditorActionListener {

	private ImageView iv_sousuo_fanhui;
	private EditText et_sousuo_sousuo;
	private ListView lv_home_item1, lv_lishi;
	private List<Shops_wxzObj> list_wxz = new ArrayList<Shops_wxzObj>();
	private ShopItemAdapter adapter;
	private SouSuoLiShiAdapter adapter_lishi;
	private TextView tv_home_dayuhao1;
	private LinearLayout line_sousuo_content, ll_sousuo_lishi;
	private String save_history;
	private List<String> list_string;
	private StringBuilder sb;
	private TextView tv_lishi_qingkong,tv_sousuo_quxiao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sousuo);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		iv_sousuo_fanhui = (ImageView) findViewById(R.id.iv_sousuo_fanhui);
		iv_sousuo_fanhui.setOnClickListener(this);
		et_sousuo_sousuo = (EditText) findViewById(R.id.et_sousuo_sousuo);
		et_sousuo_sousuo.setOnEditorActionListener(this);
		line_sousuo_content = (LinearLayout) findViewById(R.id.line_sousuo_content);
		ll_sousuo_lishi = (LinearLayout) findViewById(R.id.ll_sousuo_lishi);
		lv_home_item1 = (ListView) findViewById(R.id.lv_sousuo_item1);
		lv_home_item1.setOnItemClickListener(this);
		lv_lishi = (ListView) findViewById(R.id.lv_lishi);
		lv_lishi.setOnItemClickListener(this);
		tv_home_dayuhao1 = (TextView) findViewById(R.id.tv_sousuo_dayuhao1);
		tv_home_dayuhao1.setOnClickListener(this);
		tv_lishi_qingkong = (TextView) findViewById(R.id.tv_lishi_qingkong);
		tv_lishi_qingkong.setOnClickListener(this);
		tv_sousuo_quxiao = (TextView) findViewById(R.id.tv_sousuo_quxiao);
		tv_sousuo_quxiao.setOnClickListener(this);
		adapter = new ShopItemAdapter(this, list_wxz);
		lv_home_item1.setAdapter(adapter);
		lv_home_item1.setFocusable(false);
		setListViewHeightBasedOnChildren(lv_home_item1);
		Show();
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		if (Utils.Many()) {
			return;
		}
		int id = v.getId();
		if (R.id.iv_sousuo_fanhui == id) {
			finish();
		} else if (R.id.tv_lishi_qingkong == id) {
			QingKong();
		}  else if (R.id.tv_sousuo_dayuhao1 == id) {
			GoToPinPaiFuWuZhan();
		} else if (R.id.tv_sousuo_quxiao == id){
			et_sousuo_sousuo.setText("");
		}

	}

	public void SouSuo(String sousuo) {
		Utils.NoNet(this);
		showProgressDialog();
		SouSuoReq req = new SouSuoReq();
		req.code = "8004";
		req.id_driver = Utils.getIdDriver();
		req.lat = Utils.getLat();
		req.lon = Utils.getLon();
		req.name_shop = sousuo;
		req.start = "0";
		req.len = "5";
		KaKuApiProvider.SouSuo(req, new BaseCallback<SouSuoResp>(SouSuoResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, SouSuoResp t) {
				if (t != null) {
					LogUtil.E("sousuo res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						line_sousuo_content.setVisibility(View.VISIBLE);
						list_wxz = t.shops;
						adapter = new ShopItemAdapter(SouSuoActivity.this, list_wxz);
						lv_home_item1.setAdapter(adapter);
						setListViewHeightBasedOnChildren(lv_home_item1);
						ll_sousuo_lishi.setVisibility(View.GONE);
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

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height += 5;

		listView.setLayoutParams(params);
	}

	public void Save() {

		String text = et_sousuo_sousuo.getText().toString();
		SharedPreferences sp = getSharedPreferences("history_strs", 0);
		String save_Str = sp.getString("history", "");
		list_string = Arrays.asList(save_Str.split(","));
		List arrayList = new ArrayList(list_string);
		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).equals(text)) {
				return;
			}
		}
		sb = new StringBuilder(save_Str);
		sb.append(text + ",");

		sp.edit().putString("history", sb.toString()).commit();
	}

	public void Show() {
		SharedPreferences sp = getSharedPreferences("history_strs", 0);
		save_history = sp.getString("history", "");
		if ("".equals(save_history)) {
			return;
		}
		list_string = Arrays.asList(save_history.split(","));
		List arrayList = new ArrayList(list_string);
		adapter_lishi = new SouSuoLiShiAdapter(this, arrayList);
		lv_lishi.setAdapter(adapter_lishi);

	}

	public void QingKong() {
		SharedPreferences sp = getSharedPreferences("history_strs", 0);
		sp.edit().putString("history", "").commit();
		lv_lishi.setVisibility(View.GONE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		int parentId = parent.getId();
		if (R.id.lv_lishi == parentId) {
			et_sousuo_sousuo.setText(list_string.get(position));
			SouSuo(list_string.get(position));
		} else if (R.id.lv_sousuo_item1 == parentId) {
			MobclickAgent.onEvent(context, "BaoYang");
			KaKuApplication.id_shop = list_wxz.get(position).getId_shop();
			GoToShopDetail();
		}
	}

	public void GoToShopDetail() {
		Intent intent = new Intent(context, ShopDetailActivity.class);
		startActivity(intent);
	}

	public void GoToPinPaiFuWuZhan() {
		startActivity(new Intent(context, PinPaiFuWuZhanActivity.class));
		finish();
	}

	private void GoTOShopingMall() {
		Utils.NoNet(context);
		startActivity(new Intent(context, ShopMallActivity.class));
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {

			String tempWord = et_sousuo_sousuo.getText().toString().trim();

			if (TextUtils.isEmpty(tempWord)) {
				LogUtil.showShortToast(context, "请输入搜索内容");
				return false;
			}
			Save();
			SouSuo(tempWord);
			return true;
		} else {
			return false;
		}
	}
}