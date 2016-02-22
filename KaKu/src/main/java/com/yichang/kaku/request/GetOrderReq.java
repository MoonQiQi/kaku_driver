package com.yichang.kaku.request;

import java.io.Serializable;

public class GetOrderReq extends BaseReq implements Serializable {
	public String id_driver;
	public String id_shop;
	public String total_price;
}
