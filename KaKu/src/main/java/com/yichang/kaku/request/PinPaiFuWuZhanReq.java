package com.yichang.kaku.request;

import java.io.Serializable;

public class PinPaiFuWuZhanReq extends BaseReq implements Serializable {
	public String id_driver;
	public String id_car;
	public String lon;
	public String lat;
	public String flag_type;
	public String start;
	public String len;
	public String id_area;
	public String id_brand;
}
