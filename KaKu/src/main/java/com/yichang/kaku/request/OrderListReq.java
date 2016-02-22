package com.yichang.kaku.request;

import java.io.Serializable;

public class OrderListReq extends BaseReq implements Serializable {
	public String id_driver;
	public String state_order;
	public String start;
	public String len;
}
