package com.yichang.kaku.request;

import java.io.Serializable;

public class ConfirmOrderReq extends BaseReq implements Serializable {
	public String id_goods_shopcars;
	public String id_goods;
	public String point_choose;
	public String balance_choose;
	public String id_driver_coupon;
	public String num_shopcar;
	public String flag_buy;
}
