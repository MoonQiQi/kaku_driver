package com.yichang.kaku.home.mycar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.home.ClearEditText;
import com.yichang.kaku.home.MyGridView;
import com.yichang.kaku.home.ReMenAdapter;
import com.yichang.kaku.obj.CarData2Obj;
import com.yichang.kaku.obj.CarData3Obj;
import com.yichang.kaku.obj.CarData4Obj;
import com.yichang.kaku.obj.PinPaiXuanZeObj;
import com.yichang.kaku.request.CarDetailSubmitReq;
import com.yichang.kaku.request.PinPaiXuanZeReq;
import com.yichang.kaku.request.XuanCheReq;
import com.yichang.kaku.response.CarDetailSubmitResp;
import com.yichang.kaku.response.PinPaiXuanZeResp;
import com.yichang.kaku.response.XuanCheResp;
import com.yichang.kaku.tools.BitmapUtil;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PinPaiXuanZeActivity extends BaseActivity implements OnClickListener,SectionIndexer {
	
	private TextView left,right,title1;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortGroupMemberAdapter adapter;
	private ClearEditText mClearEditText;
	private TextView title;
	private TextView tvNofriends;
	private LinearLayout titleLayout;
	private ImageView image;
	List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();
	/**
	 * 上次第一个可见元素，用于滚动时记录标识。
	 */
	private int lastFirstVisibleItem = -1;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<GroupMemberBean> SourceDateList = new ArrayList<GroupMemberBean>();
	private List<PinPaiXuanZeObj> customList = new ArrayList<PinPaiXuanZeObj>();
	private String[] str;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	private MyGridView gv_remen;
	private List<PinPaiXuanZeObj> list_remen = new ArrayList<PinPaiXuanZeObj>();
	private List<CarData2Obj> list_data2 = new ArrayList<CarData2Obj>();
	private List<CarData3Obj> list_data3 = new ArrayList<CarData3Obj>();
	private List<CarData4Obj> list_data4 = new ArrayList<CarData4Obj>();
	private ReMenAdapter adapter_remen;
	private Step2Adapter adapter_step2;
	private Step3Adapter adapter_step3;
	private Step4Adapter adapter_step4;
	private LinearLayout line_pinpai_step1;
	private RelativeLayout line_pinpai_step2;
	private ImageView iv2_pinpai_pinpai;
	private String id_brand = "";
	private String id_series = "";
	private String id_fuel = "";
	private String id_model = "";
	private String id_actuate = "";
	private TextView tv2_pinpai_line1;
	private TextView tv2_pinpai_line2;
	private TextView tv2_pinpai_line3;
	private TextView tv2_pinpai_line4;
	private TextView tv2_pinpai_line5;
	private ListView lv_pinpai_step2;
	private ListView lv_pinpai_step3;
	private ListView lv_pinpai_step4;
	private RelativeLayout rela_xuanche_text;
	private TextView tv_pinpai_text1,tv_pinpai_text2;
	private String id_car;
	private String name_brand;
	private String img_url;
	private String flag = "1";
	private boolean flag_dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pinpaixuanze);
		init();
	}

	@Override
	protected void onStart() {
		super.onStart();
		flag_dialog = true;
		PinPaiXuanZe();
	}

	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title1=(TextView) findViewById(R.id.tv_mid);
		title1.setText("品牌选择");
		//titleLayout = (LinearLayout) findViewById(R.id.title_layout);
		gv_remen= (MyGridView) findViewById(R.id.gv_remen);
		gv_remen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				id_brand = list_remen.get(position).getId_brand();
				name_brand = list_remen.get(position).getName_brand();
				img_url  =list_remen.get(position).getImage_brand();
				MobclickAgent.onEvent(context, "ChooseBrand");
				line_pinpai_step2.setVisibility(View.VISIBLE);
				lv_pinpai_step2.setVisibility(View.VISIBLE);
				lv_pinpai_step3.setVisibility(View.GONE);
				lv_pinpai_step4.setVisibility(View.GONE);
				title1.setText("选车系");
				StepOne();
			}
		});
		line_pinpai_step1 = (LinearLayout) findViewById(R.id.line_pinpai_step1);
		line_pinpai_step2 = (RelativeLayout) findViewById(R.id.line_pinpai_step2);
		line_pinpai_step2.setOnClickListener(this);
		image = (ImageView) this.findViewById(R.id.image);
		iv2_pinpai_pinpai= (ImageView) findViewById(R.id.iv2_pinpai_pinpai);
		tv2_pinpai_line1= (TextView) findViewById(R.id.tv2_pinpai_line1);
		tv2_pinpai_line2= (TextView) findViewById(R.id.tv2_pinpai_line2);
		tv2_pinpai_line3= (TextView) findViewById(R.id.tv2_pinpai_line3);
		tv2_pinpai_line4= (TextView) findViewById(R.id.tv2_pinpai_line4);
		tv2_pinpai_line5= (TextView) findViewById(R.id.tv2_pinpai_line5);
		lv_pinpai_step2= (ListView) findViewById(R.id.lv_pinpai_step2);
		rela_xuanche_text = (RelativeLayout) findViewById(R.id.rela_xuanche_text);
		tv_pinpai_text1= (TextView) findViewById(R.id.tv_pinpai_text1);
		tv_pinpai_text2= (TextView) findViewById(R.id.tv_pinpai_text2);
		tv_pinpai_text2.setOnClickListener(this);

		lv_pinpai_step2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				lv_pinpai_step2.setVisibility(View.GONE);
				lv_pinpai_step3.setVisibility(View.VISIBLE);
				lv_pinpai_step4.setVisibility(View.GONE);
				rela_xuanche_text.setVisibility(View.GONE);
				title1.setText("选车型");
				id_series = list_data2.get(position).getId_series();
				id_fuel = list_data2.get(position).getId_fuel();
				StepTwo();
			}
		});

		lv_pinpai_step3= (ListView) findViewById(R.id.lv_pinpai_step3);
		lv_pinpai_step3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				lv_pinpai_step2.setVisibility(View.GONE);
				lv_pinpai_step3.setVisibility(View.GONE);
				lv_pinpai_step4.setVisibility(View.VISIBLE);
				rela_xuanche_text.setVisibility(View.VISIBLE);
				title1.setText("选马力");
				id_model = list_data3.get(position).getId_model();
				id_actuate = list_data3.get(position).getId_actuate();
				StepThree();
			}
		});

		lv_pinpai_step4= (ListView) findViewById(R.id.lv_pinpai_step4);
		lv_pinpai_step4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				save(list_data4.get(position).getId_car());
			}
		});
		lv_pinpai_step2.setFocusable(false);
		lv_pinpai_step3.setFocusable(false);
		lv_pinpai_step4.setFocusable(false);
		tvNofriends = (TextView) this
				.findViewById(R.id.title_layout_no_friends);
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		//sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		//sideBar.setTextView(dialog);

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setFocusable(false);

		// 设置右侧触摸监听
		/*sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}
			}
		});*/


		sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Utils.NoNet(PinPaiXuanZeActivity.this);
				if (TextUtils.isEmpty(mClearEditText.getText().toString())){
					id_brand = SourceDateList.get(position).getId();
					name_brand=SourceDateList.get(position).getName();
					img_url=SourceDateList.get(position).getImage();
				} else {
					id_brand = filterDateList.get(position).getId();
					name_brand=SourceDateList.get(position).getName();
					img_url=SourceDateList.get(position).getImage();
				}
				MobclickAgent.onEvent(context, "ChooseBrand");
				line_pinpai_step2.setVisibility(View.VISIBLE);
				lv_pinpai_step2.setVisibility(View.VISIBLE);
				lv_pinpai_step3.setVisibility(View.GONE);
				lv_pinpai_step4.setVisibility(View.GONE);
				title1.setText("选车系");
				StepOne();

				/*Intent intent = new Intent(PinPaiXuanZeActivity.this,ChooseCarActivity.class);
				intent.putExtra("id_brand", id_brand);
				startActivity(intent);*/
			}
		});

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// 这个时候不需要挤压效果 就把他隐藏掉
				//titleLayout.setVisibility(View.GONE);
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	/**
	 * 为ListView填充数据
	 *
	 * @param
	 * @return
	 */
	private List<GroupMemberBean> filledData(List<PinPaiXuanZeObj> list) {
		String[] date = new String[list.size()];
		for (int i = 0; i < date.length; i++) {
			date[i] = list.get(i).getName_brand();
		}
		List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
		for (int i = 0; i < date.length; i++) {
			GroupMemberBean sortModel = new GroupMemberBean();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			sortModel.setId(list.get(i).getId_brand());
			sortModel.setImage(list.get(i).getImage_brand());
			mSortList.add(sortModel);
		}
		return mSortList;

	}


	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 *
	 * @param filterStr
	 */
	private void filterData(String filterStr) {

		if (TextUtils.isEmpty(filterStr)) {
			//filterDateList = SourceDateList;
			flag_dialog = false;
			tvNofriends.setVisibility(View.GONE);
			PinPaiXuanZe();
		} else {
			filterDateList.clear();
			for (GroupMemberBean sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
						filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
			tvNofriends.setVisibility(View.GONE);
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
		if (filterDateList.size() == 0) {
			tvNofriends.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		LogUtil.E("flag:"+flag);
		if (Utils.Many()){
			return;
		}
		int id = v.getId();
		if (R.id.tv_left == id) {
			if ("4".equals(flag)){
				flag = "3";
				id_model = "";
				id_actuate = "";
				tv2_pinpai_line2.setVisibility(View.VISIBLE);
				tv2_pinpai_line3.setVisibility(View.VISIBLE);
				tv2_pinpai_line4.setVisibility(View.GONE);
				tv2_pinpai_line5.setVisibility(View.GONE);
				lv_pinpai_step4.setVisibility(View.GONE);
				lv_pinpai_step3.setVisibility(View.VISIBLE);
				lv_pinpai_step2.setVisibility(View.GONE);
				rela_xuanche_text.setVisibility(View.GONE);
				title1.setText("选车型");
			} else if ("3".equals(flag)){
				flag = "2";
				id_series = "";
				id_fuel = "";
				tv2_pinpai_line2.setVisibility(View.GONE);
				tv2_pinpai_line3.setVisibility(View.GONE);
				tv2_pinpai_line4.setVisibility(View.GONE);
				tv2_pinpai_line5.setVisibility(View.GONE);
				lv_pinpai_step4.setVisibility(View.GONE);
				lv_pinpai_step3.setVisibility(View.GONE);
				lv_pinpai_step2.setVisibility(View.VISIBLE);
				rela_xuanche_text.setVisibility(View.GONE);
				title1.setText("选车系");
			} else {
				flag = "1";
				finish();
			}
		} else if (R.id.tv_pinpai_text2 == id){
			Intent intent = new Intent(this,EditCarInfosActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("id_brand",id_brand);
			bundle.putString("name_brand",tv2_pinpai_line1.getText().toString().trim());
			bundle.putString("name_series",tv2_pinpai_line2.getText().toString().trim());
			bundle.putString("name_fuel",tv2_pinpai_line3.getText().toString().trim());
			bundle.putString("name_model",tv2_pinpai_line4.getText().toString().trim());
			bundle.putString("name_actuate",tv2_pinpai_line5.getText().toString().trim());
			bundle.putString("name_power",list_data4.get(0).getStep4_name1());
			bundle.putString("name_emissions",list_data4.get(0).getStep4_name2());
			bundle.putString("name_engine",list_data4.get(0).getStep4_name3());
			bundle.putString("img_url",img_url);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		} else if (R.id.line_pinpai_step2 == id){

		}
	}

	public void PinPaiXuanZe(){
		if (flag_dialog){
			showProgressDialog();
		}
		PinPaiXuanZeReq req = new PinPaiXuanZeReq();
		req.code = "2008";
		KaKuApiProvider.PinPaiXuanZe(req, new BaseCallback<PinPaiXuanZeResp>(PinPaiXuanZeResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, PinPaiXuanZeResp t) {
				if (t != null) {
					LogUtil.E("pinpaixuanze res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if (t.brands.size()==0) {
							return;
						}
						customList = t.brands;
						list_remen = t.brands_hot;
						adapter_remen = new ReMenAdapter(context,list_remen);
						gv_remen.setAdapter(adapter_remen);
						SourceDateList = filledData(customList);
						Collections.sort(SourceDateList, pinyinComparator);
						adapter = new SortGroupMemberAdapter(PinPaiXuanZeActivity.this, SourceDateList);
						sortListView.setAdapter(adapter);
						Utils.setListViewHeightBasedOnChildren(sortListView);
						sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {
							}

							@Override
							public void onScroll(AbsListView view, int firstVisibleItem,
												 int visibleItemCount, int totalItemCount) {

									int section = getSectionForPosition(firstVisibleItem);
									int nextSection = getSectionForPosition(firstVisibleItem);
									int nextSecPosition = getPositionForSection(+nextSection);
								/*if (firstVisibleItem != lastFirstVisibleItem) {
									ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
											.getLayoutParams();
									params.topMargin = 0;
									titleLayout.setLayoutParams(params);
									*//*title.setText(SourceDateList.get(
											getPositionForSection(section)).getSortLetters());*//*
								}*/
									if (nextSecPosition == firstVisibleItem + 1) {
										View childView = view.getChildAt(0);
									/*if (childView != null) {
										int titleHeight = titleLayout.getHeight();
										int bottom = childView.getBottom();
										ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
												.getLayoutParams();
										if (bottom < titleHeight) {
											float pushedDistance = bottom - titleHeight;
											params.topMargin = (int) pushedDistance;
											titleLayout.setLayoutParams(params);
										} else {
											if (params.topMargin != 0) {
												params.topMargin = 0;
												titleLayout.setLayoutParams(params);
											}
										}
									}*/
									}
									lastFirstVisibleItem = firstVisibleItem;
								}
						});
					}
				}
				stopProgressDialog();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg,
								  Throwable error) {
				stopProgressDialog();
			}
		});
	}

	/**
	 * 根据分类的首字母的Charascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < SourceDateList.size(); i++) {
			String sortStr = SourceDateList.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Charascii值ֵ
	 */
	public int getSectionForPosition(int position) {
		return SourceDateList.get(position).getSortLetters().charAt(0);
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	public void StepOne(){
		Utils.NoNet(PinPaiXuanZeActivity.this);
		showProgressDialog();
		XuanCheReq req = new XuanCheReq();
		req.code = "20014";
		req.step = "2";
		req.id_brand = id_brand;
		req.id_series = id_series;
		req.id_model = id_model;
		req.id_actuate = id_actuate;
		req.id_fuel = id_fuel;
		KaKuApiProvider.XuanChe(req, new BaseCallback<XuanCheResp>(XuanCheResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, XuanCheResp t) {
				if (t != null) {
					LogUtil.E("xuanche2 res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if ("Y".equals(t.flag_jump)){
							SaveCar(t.id_car);
							stopProgressDialog();
							finish();
							return;
						}
						flag = "2";
						tv2_pinpai_line2.setVisibility(View.VISIBLE);
						tv2_pinpai_line3.setVisibility(View.VISIBLE);
						tv2_pinpai_line4.setVisibility(View.VISIBLE);
						tv2_pinpai_line5.setVisibility(View.VISIBLE);
						tv2_pinpai_line1.setText(t.brand.getName_brand());
						tv2_pinpai_line2.setText(t.brand.getName_series());
						tv2_pinpai_line3.setText(t.brand.getName_fuel());
						tv2_pinpai_line4.setText(t.brand.getName_model());
						tv2_pinpai_line5.setText(t.brand.getName_actuate());
						BitmapUtil.getInstance(context).download(iv2_pinpai_pinpai, KaKuApplication.qian_zhui + t.brand.getImage_brand());
						img_url = t.brand.getImage_brand();
						list_data2 = t.datas2;
						adapter_step2 = new Step2Adapter(context,list_data2);
						lv_pinpai_step2.setAdapter(adapter_step2);
						Utils.setListViewHeightBasedOnChildren(lv_pinpai_step2);
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

	public void StepTwo(){
		Utils.NoNet(PinPaiXuanZeActivity.this);
		showProgressDialog();
		XuanCheReq req = new XuanCheReq();
		req.code = "20014";
		req.step = "3";
		req.id_brand = id_brand;
		req.id_series = id_series;
		req.id_model = id_model;
		req.id_actuate = id_actuate;
		req.id_fuel = id_fuel;
		KaKuApiProvider.XuanChe(req, new BaseCallback<XuanCheResp>(XuanCheResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, XuanCheResp t) {
				if (t != null) {
					LogUtil.E("xuanche3 res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if ("Y".equals(t.flag_jump)){
							SaveCar(t.id_car);
							stopProgressDialog();
							finish();
							return;
						}
						flag = "3";
						tv2_pinpai_line2.setVisibility(View.VISIBLE);
						tv2_pinpai_line3.setVisibility(View.VISIBLE);
						tv2_pinpai_line4.setVisibility(View.VISIBLE);
						tv2_pinpai_line5.setVisibility(View.VISIBLE);
						tv2_pinpai_line1.setText(t.brand.getName_brand());
						tv2_pinpai_line2.setText(t.brand.getName_series());
						tv2_pinpai_line3.setText(t.brand.getName_fuel());
						tv2_pinpai_line4.setText(t.brand.getName_model());
						tv2_pinpai_line5.setText(t.brand.getName_actuate());
						BitmapUtil.getInstance(context).download(iv2_pinpai_pinpai, KaKuApplication.qian_zhui + t.brand.getImage_brand());
						list_data3 = t.datas3;
						adapter_step3 = new Step3Adapter(context,list_data3);
						lv_pinpai_step3.setAdapter(adapter_step3);
						Utils.setListViewHeightBasedOnChildren(lv_pinpai_step3);
					}else if (Constants.RES_ONE.equals(t.res)){
						finish();
						LogUtil.showShortToast(context, t.msg);
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

	public void StepThree(){
		Utils.NoNet(PinPaiXuanZeActivity.this);
		showProgressDialog();
		XuanCheReq req = new XuanCheReq();
		req.code = "20014";
		req.step = "4";
		req.id_brand = id_brand;
		req.id_series = id_series;
		req.id_model = id_model;
		req.id_actuate = id_actuate;
		req.id_fuel = id_fuel;
		KaKuApiProvider.XuanChe(req, new BaseCallback<XuanCheResp>(XuanCheResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, XuanCheResp t) {
				if (t != null) {
					LogUtil.E("xuanche4 res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if ("Y".equals(t.flag_jump)){
							SaveCar(t.id_car);
							stopProgressDialog();
							finish();
							return;
						}
						flag = "4";
						tv2_pinpai_line2.setVisibility(View.VISIBLE);
						tv2_pinpai_line3.setVisibility(View.VISIBLE);
						tv2_pinpai_line4.setVisibility(View.VISIBLE);
						tv2_pinpai_line5.setVisibility(View.VISIBLE);
						tv2_pinpai_line1.setText(t.brand.getName_brand());
						tv2_pinpai_line2.setText(t.brand.getName_series());
						tv2_pinpai_line3.setText(t.brand.getName_fuel());
						tv2_pinpai_line4.setText(t.brand.getName_model());
						tv2_pinpai_line5.setText(t.brand.getName_actuate());
						BitmapUtil.getInstance(context).download(iv2_pinpai_pinpai, KaKuApplication.qian_zhui + t.brand.getImage_brand());
						list_data4 = t.datas4;
						adapter_step4 = new Step4Adapter(context,list_data4);
						lv_pinpai_step4.setAdapter(adapter_step4);
						Utils.setListViewHeightBasedOnChildren(lv_pinpai_step4);
					} else if (Constants.RES_ONE.equals(t.res)){
						finish();
						LogUtil.showShortToast(context, t.msg);
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

	private void save(String id_car) {
		Utils.NoNet(this);
		showProgressDialog();
		CarDetailSubmitReq req = new CarDetailSubmitReq();
		req.code = "20015";
		req.id_car = id_car;
		req.id_driver_car = "";
		req.id_driver = Utils.getIdDriver();
		req.time_production = "";
		req.travel_mileage = "";

		KaKuApiProvider.submitMyCarDetail(req, new BaseCallback<CarDetailSubmitResp>(CarDetailSubmitResp.class) {
					@Override
					public void onSuccessful(int statusCode, Header[] headers, CarDetailSubmitResp t) {
						if (t != null) {
							LogUtil.E("savecar res: " + t.res);
							if (Constants.RES.equals(t.res)) {
								Intent intent = new Intent(context,MyCarActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);
								finish();
								LogUtil.showShortToast(context,t.msg);
							} else if (Constants.RES_ONE.equals(t.res)){
								finish();
								LogUtil.showShortToast(context, t.msg);
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
					public void onFailure(int statusCode, Header[] headers, String msg, Throwable
							error) {
						stopProgressDialog();
					}
				}
		);
	}

	private void SaveCar(String id_car){
		SharedPreferences.Editor editor = KaKuApplication.editor;
		editor.putString(Constants.IDCAR, id_car);
		editor.commit();
	}
}
