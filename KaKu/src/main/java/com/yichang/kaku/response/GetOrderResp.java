package com.yichang.kaku.response;

import java.io.Serializable;

public class GetOrderResp extends BaseResp implements Serializable {
	public String point_limit;
	public String activity_sj;
	public String activity_mj;
	public String price_shouj;
	public String price_manj;
	public String flag_ticket;
	public String num_coupons;
	public String name_driver;
	public String phone_driver;
}
