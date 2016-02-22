package com.yichang.kaku.response;

import com.yichang.kaku.obj.CouponObj;
import com.yichang.kaku.obj.ShopBaoYangObj;
import com.yichang.kaku.obj.ShopCarObj;
import com.yichang.kaku.obj.ShopPingJiaObj;

import java.io.Serializable;
import java.util.List;

public class BaoYangResp extends BaseResp implements Serializable {
	public ShopBaoYangObj shop;
    public String cost;
    public List<CouponObj> coupons;
    public List<ShopPingJiaObj> evaluations;
    public List<ShopCarObj> items;
}
