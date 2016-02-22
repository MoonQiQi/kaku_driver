package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopBaoYangObj implements Serializable {
	
	private String id_shop;
	private String name_shop;
	private String addr_shop;
	private String num_star;
	private String num_eval;
	private String num_order;
	private String upkeep_type;
	private String var_lat;
	private String var_lon;
	private String distance;
	private String mobile_shop;
	private String hour_shop_begin;
	private String hour_shop_end;

	public String getId_shop() {
		return id_shop;
	}

	public void setId_shop(String id_shop) {
		this.id_shop = id_shop;
	}

	public String getName_shop() {
		return name_shop;
	}

	public void setName_shop(String name_shop) {
		this.name_shop = name_shop;
	}

	public String getAddr_shop() {
		return addr_shop;
	}

	public void setAddr_shop(String addr_shop) {
		this.addr_shop = addr_shop;
	}

	public String getNum_star() {
		return num_star;
	}

	public void setNum_star(String num_star) {
		this.num_star = num_star;
	}

	public String getNum_eval() {
		return num_eval;
	}

	public void setNum_eval(String num_eval) {
		this.num_eval = num_eval;
	}

	public String getNum_order() {
		return num_order;
	}

	public void setNum_order(String num_order) {
		this.num_order = num_order;
	}

	public String getUpkeep_type() {
		return upkeep_type;
	}

	public void setUpkeep_type(String upkeep_type) {
		this.upkeep_type = upkeep_type;
	}

	public String getVar_lat() {
		return var_lat;
	}

	public void setVar_lat(String var_lat) {
		this.var_lat = var_lat;
	}

	public String getVar_lon() {
		return var_lon;
	}

	public void setVar_lon(String var_lon) {
		this.var_lon = var_lon;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getMobile_shop() {
		return mobile_shop;
	}

	public void setMobile_shop(String mobile_shop) {
		this.mobile_shop = mobile_shop;
	}

	public String getHour_shop_begin() {
		return hour_shop_begin;
	}

	public void setHour_shop_begin(String hour_shop_begin) {
		this.hour_shop_begin = hour_shop_begin;
	}

	public String getHour_shop_end() {
		return hour_shop_end;
	}

	public void setHour_shop_end(String hour_shop_end) {
		this.hour_shop_end = hour_shop_end;
	}

	@Override
	public String toString() {
		return "ShopBaoYangObj{" +
				"id_shop='" + id_shop + '\'' +
				", name_shop='" + name_shop + '\'' +
				", addr_shop='" + addr_shop + '\'' +
				", num_star='" + num_star + '\'' +
				", num_eval='" + num_eval + '\'' +
				", num_order='" + num_order + '\'' +
				", upkeep_type='" + upkeep_type + '\'' +
				", var_lat='" + var_lat + '\'' +
				", var_lon='" + var_lon + '\'' +
				", distance='" + distance + '\'' +
				", mobile_shop='" + mobile_shop + '\'' +
				", hour_shop_begin='" + hour_shop_begin + '\'' +
				", hour_shop_end='" + hour_shop_end + '\'' +
				'}';
	}
}
