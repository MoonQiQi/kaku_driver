package com.yichang.kaku.request;

import java.io.Serializable;

public class SOSReq extends BaseReq implements Serializable {
	public String id_driver;
	public String lon;
	public String lat;
	public String start;
	public String len;
}
