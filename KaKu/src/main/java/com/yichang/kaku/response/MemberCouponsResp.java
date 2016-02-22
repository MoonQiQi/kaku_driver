package com.yichang.kaku.response;

import com.yichang.kaku.obj.MemberCouponObj;
import com.yichang.kaku.obj.TruckOrderObj;

import java.io.Serializable;
import java.util.List;

public class MemberCouponsResp extends BaseResp implements Serializable {
    public List<MemberCouponObj> coupons;
	
}
