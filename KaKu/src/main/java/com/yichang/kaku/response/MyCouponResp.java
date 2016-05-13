package com.yichang.kaku.response;

import com.yichang.kaku.obj.YouHuiQuanObj;

import java.io.Serializable;
import java.util.List;

public class MyCouponResp extends BaseResp implements Serializable {
	public List<YouHuiQuanObj> coupons_wsy;
	public List<YouHuiQuanObj> coupons_ysy;
	public List<YouHuiQuanObj> coupons_ygq;
}
