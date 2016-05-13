package com.yichang.kaku.obj;

import java.io.Serializable;

public class OilPingJiaObj implements Serializable {

	private String id_shop;
	private String tel_shop;
	private String name_shop;
	private String image_shop;
	private String num_order;
	private String num_star;
	private String flag_butter;
	private String flag_rescue;
	private String flag_tire_trade;
	private String flag_tire_repair;

	public String getId_shop() {
		return id_shop;
	}

	public void setId_shop(String id_shop) {
		this.id_shop = id_shop;
	}

	public String getTel_shop() {
		return tel_shop;
	}

	public void setTel_shop(String tel_shop) {
		this.tel_shop = tel_shop;
	}

	public String getName_shop() {
		return name_shop;
	}

	public void setName_shop(String name_shop) {
		this.name_shop = name_shop;
	}

	public String getImage_shop() {
		return image_shop;
	}

	public void setImage_shop(String image_shop) {
		this.image_shop = image_shop;
	}

	public String getNum_order() {
		return num_order;
	}

	public void setNum_order(String num_order) {
		this.num_order = num_order;
	}

	public String getNum_star() {
		return num_star;
	}

	public void setNum_star(String num_star) {
		this.num_star = num_star;
	}

	public String getFlag_butter() {
		return flag_butter;
	}

	public void setFlag_butter(String flag_butter) {
		this.flag_butter = flag_butter;
	}

	public String getFlag_rescue() {
		return flag_rescue;
	}

	public void setFlag_rescue(String flag_rescue) {
		this.flag_rescue = flag_rescue;
	}

	public String getFlag_tire_trade() {
		return flag_tire_trade;
	}

	public void setFlag_tire_trade(String flag_tire_trade) {
		this.flag_tire_trade = flag_tire_trade;
	}

	public String getFlag_tire_repair() {
		return flag_tire_repair;
	}

	public void setFlag_tire_repair(String flag_tire_repair) {
		this.flag_tire_repair = flag_tire_repair;
	}

	@Override
	public String toString() {
		return "OilPingJiaObj{" +
				"id_shop='" + id_shop + '\'' +
				", tel_shop='" + tel_shop + '\'' +
				", name_shop='" + name_shop + '\'' +
				", image_shop='" + image_shop + '\'' +
				", num_order='" + num_order + '\'' +
				", num_star='" + num_star + '\'' +
				", flag_butter='" + flag_butter + '\'' +
				", flag_rescue='" + flag_rescue + '\'' +
				", flag_tire_trade='" + flag_tire_trade + '\'' +
				", flag_tire_repair='" + flag_tire_repair + '\'' +
				'}';
	}
}
