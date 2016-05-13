package com.yichang.kaku.request;

import java.io.Serializable;

public class GenerateOrderReq extends BaseReq implements Serializable {
	public String name_addr;
	public String phone_addr;
	public String addr;
	public String price_goods;
	public String price_point;
	public String price_bill;
	public String point_used;
	public String price_transport;
	public String price_balance;
	public String price_coupon;
	public String id_driver_coupon;
	public String flag_buy;
	public String sign;

}
