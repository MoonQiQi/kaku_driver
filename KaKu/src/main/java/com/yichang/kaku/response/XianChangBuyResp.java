package com.yichang.kaku.response;

import com.yichang.kaku.obj.GoodsXianChangObj;

import java.io.Serializable;

public class XianChangBuyResp extends BaseResp implements Serializable {
    public GoodsXianChangObj goods;
	public String point_limit;
	public String price_goods;
	public String price_point;
	public String price_bill;
	public String money_balance;
	public String flag_pay;

}
