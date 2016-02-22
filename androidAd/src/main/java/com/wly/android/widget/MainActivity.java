package com.wly.android.widget;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	LinearLayout galleryContainer;
	LayoutInflater inflater;
	AdGalleryHelper mGalleryHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		//============
		String cacheDir;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			File f = this.getApplicationContext().getExternalCacheDir();
			if (null == f) {

				cacheDir = Environment.getExternalStorageDirectory().getPath()
						+ File.separator + this.getApplicationContext().getPackageName()
						+ File.separator + "cache";
			} else {
				cacheDir = f.getPath();
			}
		} else {
			File f = this.getApplicationContext().getCacheDir();
			cacheDir = f.getPath();
		}
		FinalBitmap.create(this/*, cacheDir + File.separator
				+ "images" + File.separator,
				0.3f, 1024 * 1024 * 10,
				 10*/);
		//==================
		
		Advertising ad1 = new Advertising("http://img.my.csdn.net/uploads/201312/14/1386989803_3335.PNG"
				, "http://blog.csdn.net/u011638883/article/details/17302293"
				, "111");
		Advertising ad2 = new Advertising("http://img.my.csdn.net/uploads/201312/14/1386989613_6900.jpg"
				, "http://blog.csdn.net/u011638883/article/details/17245371"
				, "3222");
		Advertising ad3 = new Advertising("http://img.my.csdn.net/uploads/201312/14/1386989802_7236.PNG"
				, "http://blog.csdn.net/u011638883/article/details/17248135"
				, "Artificial Intelligence");
		//Advertising[] ads = {ad1,ad2,ad3};
		List<Advertising> ads = new ArrayList<Advertising>();
		ads.add(ad1);
		ads.add(ad2);
		ads.add(ad3);
		galleryContainer = (LinearLayout) this
				.findViewById(R.id.home_gallery);
		mGalleryHelper = new AdGalleryHelper(this, ads, 5000,true,false,true);
		galleryContainer.addView(mGalleryHelper.getLayout());
		mGalleryHelper.startAutoSwitch();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mGalleryHelper.stopAutoSwitch();
	}
}
