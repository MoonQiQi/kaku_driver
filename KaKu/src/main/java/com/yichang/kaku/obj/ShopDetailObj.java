package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopDetailObj implements Serializable {

	private String id_shop;
	private String image_shop;
	private String name_shop;
	private String addr_shop;
	private String num_star;
	private String num_eval;
	private String var_lat;
	private String var_lon;
	private String mobile_shop;
	private String hour_shop_begin;
	private String hour_shop_end;
	private String is_collection;
	private String image_shop_up;
	private String flag_type;
	private String num_bill;

	public String getId_shop() {
		return id_shop;
	}

	public void setId_shop(String id_shop) {
		this.id_shop = id_shop;
	}

	public String getImage_shop() {
		return image_shop;
	}

	public void setImage_shop(String image_shop) {
		this.image_shop = image_shop;
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

	public String getIs_collection() {
		return is_collection;
	}

	public void setIs_collection(String is_collection) {
		this.is_collection = is_collection;
	}

	public String getImage_shop_up() {
		return image_shop_up;
	}

	public void setImage_shop_up(String image_shop_up) {
		this.image_shop_up = image_shop_up;
	}

	public String getFlag_type() {
		return flag_type;
	}

	public void setFlag_type(String flag_type) {
		this.flag_type = flag_type;
	}

	public String getNum_bill() {
		return num_bill;
	}

	public void setNum_bill(String num_bill) {
		this.num_bill = num_bill;
	}

	@Override
	public String toString() {
		return "ShopDetailObj{" +
				"id_shop='" + id_shop + '\'' +
				", image_shop='" + image_shop + '\'' +
				", name_shop='" + name_shop + '\'' +
				", addr_shop='" + addr_shop + '\'' +
				", num_star='" + num_star + '\'' +
				", num_eval='" + num_eval + '\'' +
				", var_lat='" + var_lat + '\'' +
				", var_lon='" + var_lon + '\'' +
				", mobile_shop='" + mobile_shop + '\'' +
				", hour_shop_begin='" + hour_shop_begin + '\'' +
				", hour_shop_end='" + hour_shop_end + '\'' +
				", is_collection='" + is_collection + '\'' +
				", image_shop_up='" + image_shop_up + '\'' +
				", flag_type='" + flag_type + '\'' +
				", num_bill='" + num_bill + '\'' +
				'}';
	}
}
