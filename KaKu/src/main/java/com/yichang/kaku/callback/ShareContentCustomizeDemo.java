package com.yichang.kaku.callback;

import com.yichang.kaku.tools.Utils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class ShareContentCustomizeDemo implements ShareContentCustomizeCallback{

	public String id_driver;
	@Override
	public void onShare(Platform platform, ShareParams paramsToShare) {
		
			id_driver=Utils.getIdDriver();
	}
}
