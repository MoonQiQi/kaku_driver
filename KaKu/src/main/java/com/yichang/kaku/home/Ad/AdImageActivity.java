package com.yichang.kaku.home.Ad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.QiNiuYunTokenReq;
import com.yichang.kaku.request.UploadImageReq;
import com.yichang.kaku.response.QiNiuYunTokenResp;
import com.yichang.kaku.response.UploadImageResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AdImageActivity extends BaseActivity implements OnClickListener{

	private TextView left,right,title;
	private View view_adimage_shenheshibai;
	private ImageView iv_adimage_1,iv_adimage_2,iv_adimage_3;
	private Button btn_adimage_tijiao;
	// 创建一个以当前时间为名称的文件
	private boolean isTAKEPHOTO = false;
	private final int CAMERA_WITH_DATA = 2;//拍照
	private final int CROP_RESULT_CODE = 3;//结果
	private Bitmap photo1,photo2,photo3;
	private Map<Integer ,Bitmap > bmpMap=new HashMap<>();
	private String imagePath;
	private int mAdapterIndex;
	private TextView tv_takephoto, tv_myphoto, tv_exitphoto;
	private int anInt;
	public static final String TMP_PATH1 = "clip_temp1.jpg";
	public static final String TMP_PATH2 = "clip_temp2.jpg";
	public static final String TMP_PATH3 = "clip_temp3.jpg";
	public static final String TMP_PATH4 = "clip_temp4.jpg";
	public static final String TMP_PATH5 = "clip_temp5.jpg";
	private KaKuApplication application;
	public String token1,token2,token3;
	public String key1,key2,key3;
	public String path1,path2,path3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adimage);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		left=(TextView) findViewById(R.id.tv_left);
		left.setOnClickListener(this);
		title=(TextView) findViewById(R.id.tv_mid);
		title.setText("上传图片");
		right= (TextView) findViewById(R.id.tv_right);
		right.setVisibility(View.VISIBLE);
		right.setText("联系客服");
		right.setOnClickListener(this);
		view_adimage_shenheshibai=findViewById(R.id.view_adimage_shenheshibai);
		view_adimage_shenheshibai.setVisibility(View.GONE);
		iv_adimage_1= (ImageView) findViewById(R.id.iv_adimage_1);
		iv_adimage_1.setOnClickListener(this);
		iv_adimage_2= (ImageView) findViewById(R.id.iv_adimage_2);
		iv_adimage_2.setOnClickListener(this);
		iv_adimage_3= (ImageView) findViewById(R.id.iv_adimage_3);
		iv_adimage_3.setOnClickListener(this);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		anInt = (outMetrics.widthPixels - 40) / 3;
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(anInt,anInt);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(anInt,anInt);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(anInt,anInt);
		params1.setMargins(10,10,5,0);
		params2.setMargins(5,10,5,0);
		params3.setMargins(5,10,10,0);
		iv_adimage_1.setLayoutParams(params1);
		iv_adimage_2.setLayoutParams(params2);
		iv_adimage_3.setLayoutParams(params3);
		btn_adimage_tijiao= (Button) findViewById(R.id.btn_adimage_tijiao);
		btn_adimage_tijiao.setOnClickListener(this);

		application = (KaKuApplication) getApplication();
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
			Utils.Call(context, "400-6867585");
		} else if (R.id.iv_adimage_1 == id || R.id.iv_adimage_2 == id || R.id.iv_adimage_3 == id){
			if (R.id.iv_adimage_1 == id){
				KaKuApplication.flag_image = "zhong";
			} else if (R.id.iv_adimage_2 == id){
				KaKuApplication.flag_image = "zuo";
			} else if (R.id.iv_adimage_3 == id){
				KaKuApplication.flag_image = "you";
			}
			startCapture();
		} else if (R.id.btn_adimage_tijiao == id){
			if ("".equals(KaKuApplication.ImageZhong)||KaKuApplication.ImageZhong == null||
					"".equals(KaKuApplication.ImageZuo)||KaKuApplication.ImageZuo == null||
					"".equals(KaKuApplication.ImageYou)||KaKuApplication.ImageYou == null) {
				LogUtil.showShortToast(context, "必须上传3张图片才可提交");
				return;
			}
			application.startTrace(context,Utils.getPhone());
			QiNiuYunToken("1");
			QiNiuYunToken("2");
			QiNiuYunToken("3");
		}
	}

	/**
	 * 通过照相获得图片
	 *
	 * @param
	 */
	private void startCapture() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if ("zhong".equals(KaKuApplication.flag_image)){
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), TMP_PATH1)));
		} else if ("zuo".equals(KaKuApplication.flag_image)){
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), TMP_PATH2)));
		} else if ("you".equals(KaKuApplication.flag_image)){
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), TMP_PATH3)));
		}

		startActivityForResult(intent, CAMERA_WITH_DATA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
			case CROP_RESULT_CODE:
				if ("zhong".equals(KaKuApplication.flag_image)){
					path1 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
					photo1 = BitmapFactory.decodeFile(path1);
					KaKuApplication.ImageZhong = photo1;
					iv_adimage_1.setImageBitmap(photo1);
				} else if ("zuo".equals(KaKuApplication.flag_image)){
					path2 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
					photo2 = BitmapFactory.decodeFile(path2);
					KaKuApplication.ImageZuo = photo2;
					iv_adimage_2.setImageBitmap(photo2);
				} else if ("you".equals(KaKuApplication.flag_image)){
					path3 = data.getStringExtra(ClipImageActivity.RESULT_PATH);
					photo3 = BitmapFactory.decodeFile(path3);
					KaKuApplication.ImageYou = photo3;
					iv_adimage_3.setImageBitmap(photo3);
				}
				break;

			case CAMERA_WITH_DATA:
					if ("zhong".equals(KaKuApplication.flag_image)){
						startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH1);
					} else if ("zuo".equals(KaKuApplication.flag_image)){
						startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH2);
					} else if ("you".equals(KaKuApplication.flag_image)){
						startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH3);
					}
				break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 裁剪图片的Activity
	private void startCropImageActivity(String path) {
		ClipImageActivity.startActivity(this, path, CROP_RESULT_CODE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		KaKuApplication.ImageZhong = null;
		KaKuApplication.ImageZuo = null;
		KaKuApplication.ImageYou = null;
	}

		public void QiNiuYunToken(final String sort) {
		showProgressDialog();
		QiNiuYunTokenReq req = new QiNiuYunTokenReq();
		req.code = "qn01";
		req.sort = sort;
		req.id_driver = Utils.getIdDriver();
		KaKuApiProvider.QiNiuYunToken(req , new BaseCallback<QiNiuYunTokenResp>(QiNiuYunTokenResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, QiNiuYunTokenResp t) {
				if (t != null) {
					LogUtil.E("qiniuyuntoken res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						if ("1".equals(sort)){
							token1 = t.token;
							key1 = t.key;
							uploadImg(token1,key1,sort);
						} else if ("2".equals(sort)){
							token2 = t.token;
							key2 = t.key;
							uploadImg(token2,key2,sort);
						} else if ("3".equals(sort)){
							token3 = t.token;
							key3 = t.key;
							uploadImg(token3,key3,sort);
						}

					} else {
						if (Constants.RES_TEN.equals(t.res)){
							Utils.Exit(context);
							finish();
						}
						LogUtil.showShortToast(context, t.msg);
					}
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String msg, Throwable error) {

			}
		});
	}

	private void uploadImg(final String token , final String key , final String sort){

		new Thread(new Runnable(){
			@Override
			public void run() {
				String file = "";
				if ("1".equals(sort)){
					file = path1;
				} else if ("2".equals(sort)){
					file = path2;
				} else if ("3".equals(sort)){
					file = path3;
				}

				UploadManager uploadManager = new UploadManager();
				uploadManager.put(file, key, token,
						new UpCompletionHandler() {
							@Override
							public void complete(String arg0, ResponseInfo info, JSONObject response) {
								// TODO Auto-generated method stub
								if (info.isOK()){
									IsThree();
								}
							}
						}, null);
			}
		}).start();
	}

	private int num = 0;
	public void IsThree(){
		num++;
		if (num == 3){
			Upload();
		}
	}

	public void Upload(){
		UploadImageReq req = new UploadImageReq();
		req.code = "60018";
		req.id_advert = "1";
		req.id_driver = Utils.getIdDriver();
		req.image0_advert = key1;
		req.image1_advert = key2;
		req.image2_advert = key3;
		req.var_lat = Utils.getLat();
		req.var_lon = Utils.getLon();
		KaKuApiProvider.uploadImage(req, new BaseCallback<UploadImageResp>(UploadImageResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, UploadImageResp t) {
				if (t != null) {
					LogUtil.E("uploadimage res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						startActivity(new Intent(context, ImageHistoryActivity.class));
						finish();
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

}
