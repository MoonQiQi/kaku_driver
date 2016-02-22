package com.yichang.kaku.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.tools.Utils;

import java.util.ArrayList;
import java.util.List;

public class SOSCallActivity extends BaseActivity implements OnClickListener,AdapterView.OnItemClickListener{

	private TextView tv_mid,tv_left;
	private TextView tv_soscall_name,tv_soscall_phone;
	private RelativeLayout rela_soscall1,rela_soscall2,rela_soscall3,rela_soscall;
	private String phone;
	private ListView lv_soscall;
	private TextView tv_soscall_baoxian,tv_soscall_num;
	private ImageView iv_soscall3,iv_soscall_back;
	private SOSCallAdapter adapter;
	private List<String> list_soscall = new ArrayList<String>();
	private boolean flag_call = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soscall);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		tv_mid= (TextView) findViewById(R.id.tv_mid);
		tv_mid.setText("一键救援");
		tv_left= (TextView) findViewById(R.id.tv_left);
		tv_left.setVisibility(View.GONE);
		tv_soscall_name= (TextView) findViewById(R.id.tv_soscall_name);
		tv_soscall_phone= (TextView) findViewById(R.id.tv_soscall_phone);
		tv_soscall_baoxian= (TextView) findViewById(R.id.tv_soscall_baoxian);
		tv_soscall_num= (TextView) findViewById(R.id.tv_soscall_num);
		rela_soscall1= (RelativeLayout) findViewById(R.id.rela_soscall1);
		rela_soscall1.setOnClickListener(this);
		rela_soscall2= (RelativeLayout) findViewById(R.id.rela_soscall2);
		rela_soscall2.setOnClickListener(this);
		rela_soscall3= (RelativeLayout) findViewById(R.id.rela_soscall3);
		rela_soscall3.setOnClickListener(this);
		rela_soscall= (RelativeLayout) findViewById(R.id.rela_soscall);
		rela_soscall.setOnClickListener(this);
		iv_soscall3= (ImageView) findViewById(R.id.iv_soscall3);
		iv_soscall3.setOnClickListener(this);
		lv_soscall= (ListView) findViewById(R.id.lv_soscall);
		list_soscall.add("中国人保：95518");
		list_soscall.add("中国人寿财险：95519");
		list_soscall.add("太平洋保险：95500");
		list_soscall.add("平安保险：95511");
		list_soscall.add("中华联合保险：95585");
		list_soscall.add("大地保险：95590");
		list_soscall.add("天安保险：95505");
		list_soscall.add("大众保险：96012345");
		list_soscall.add("华泰保险：95509");
		list_soscall.add("永安保险：95502");
		list_soscall.add("华安保险：95556");
		list_soscall.add("安邦保险：95569");
		list_soscall.add("阳光保险：95510");
		list_soscall.add("中银保险：400-699-5566");
		list_soscall.add("都邦保险：400-889-5586");
		list_soscall.add("渤海保险：400-611-6666");
		list_soscall.add("众诚保险：4008-600-600");
		list_soscall.add("永城保险：95552");
		list_soscall.add("富邦财险：400-881-7518");
		list_soscall.add("太平保险：95589");
		list_soscall.add("长安责任保险：95592");
		list_soscall.add("英大财险：4000-188-688");
		adapter = new SOSCallAdapter(this,list_soscall);
		lv_soscall.setAdapter(adapter);
		lv_soscall.setOnItemClickListener(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String name_brand = bundle.getString("name_brand");
		String phone_brand = bundle.getString("phone_brand");
		tv_soscall_name.setText(name_brand);
		tv_soscall_phone.setText(phone_brand);
		DongHua();
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		if (Utils.Many()){
			return;
		}
		int id = v.getId();
		if (R.id.rela_soscall1 == id){
			Utils.Call(this, "112");
		} else if (R.id.rela_soscall2 == id){
			Utils.Call(this,tv_soscall_phone.getText().toString().trim());
		} else if (R.id.rela_soscall3 == id){
			if (flag_call){
				DongHua();
				lv_soscall.setVisibility(View.GONE);
				flag_call = false;
			} else {
				DongHua();
				lv_soscall.setVisibility(View.VISIBLE);
				lv_soscall.setSelection(0);
				flag_call = true;
			}
		} else if (R.id.iv_soscall3 == id){
			Utils.Call(this,tv_soscall_num.getText().toString().trim());
		} else if (R.id.rela_soscall == id){
			finish();
			overridePendingTransition(R.anim.top_in, R.anim.top_out);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String phone = list_soscall.get(position);
		String[] split = phone.split("：");
		tv_soscall_baoxian.setText(split[0]);
		tv_soscall_num.setText(split[1]);
		lv_soscall.setVisibility(View.GONE);
		flag_call = false;
	}

	public void DongHua(){
		AnimationSet set = new AnimationSet(false);
		Animation animation = new AlphaAnimation(0,1);   //AlphaAnimation 控制渐变透明的动画效果
		animation.setDuration(200);     //动画时间毫秒数
		set.addAnimation(animation);    //加入动画集合
		LayoutAnimationController controller = new LayoutAnimationController(set, 1);
		ListView listview= (ListView)this.findViewById(R.id.lv_soscall);
		listview.setLayoutAnimation(controller);   //ListView 设置动画效果
	}
}
