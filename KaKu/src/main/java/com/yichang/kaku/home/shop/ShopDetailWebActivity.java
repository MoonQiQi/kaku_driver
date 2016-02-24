package com.yichang.kaku.home.shop;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yichang.kaku.R;
import com.yichang.kaku.callback.BaseCallback;
import com.yichang.kaku.global.BaseActivity;
import com.yichang.kaku.global.Constants;
import com.yichang.kaku.global.KaKuApplication;
import com.yichang.kaku.request.ShopDetailWebReq;
import com.yichang.kaku.response.ShopDetailWebResp;
import com.yichang.kaku.tools.LogUtil;
import com.yichang.kaku.tools.Utils;
import com.yichang.kaku.webService.KaKuApiProvider;

import org.apache.http.Header;

public class ShopDetailWebActivity extends BaseActivity implements OnClickListener{

	private TextView tv_back;
	private WebView wv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopdetailweb);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
		wv = (WebView) findViewById(R.id.wv_shopdetailweb);
		wv.getSettings().setDefaultTextEncodingName("utf-8");
		wv.getSettings().setSupportZoom(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setPluginState(WebSettings.PluginState.ON);
		wv.setWebChromeClient(new WebChromeClient());
		wv.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void onClick(View v) {
		Utils.NoNet(context);
		if (Utils.Many()){
			return;
		}
		int id = v.getId();
		if (R.id.tv_back == id){
			finish();
			overridePendingTransition(R.anim.top_in,R.anim.top_out);
		}
	}

	public void ShopDetailWeb(){
		ShopDetailWebReq req = new ShopDetailWebReq();
		req.code = "4003";
		req.id_shop = KaKuApplication.id_shop;
		KaKuApiProvider.ShopDetailWeb(req, new BaseCallback<ShopDetailWebResp>(ShopDetailWebResp.class) {
			@Override
			public void onSuccessful(int statusCode, Header[] headers, ShopDetailWebResp t) {
				if (t != null) {
					LogUtil.E("ShopDetailWeb res: " + t.res);
					if (Constants.RES.equals(t.res)) {
						wv.loadUrl(t.url);
					}else{
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

}
