package com.yichang.kaku.request;

import com.yichang.kaku.tools.Utils;

import java.io.Serializable;

public class XuanCheReq extends BaseReq implements Serializable {
	public String step;
	public String id_brand;
	public String id_series;
	public String id_fuel;
	public String id_model;
	public String id_actuate;
	public String id_driver = Utils.getIdDriver();
}
