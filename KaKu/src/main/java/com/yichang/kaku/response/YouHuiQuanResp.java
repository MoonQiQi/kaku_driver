package com.yichang.kaku.response;

import com.yichang.kaku.obj.YouHuiQuanObj;

import java.io.Serializable;
import java.util.List;

public class YouHuiQuanResp extends BaseResp implements Serializable {
	public List<YouHuiQuanObj> coupons;
}
