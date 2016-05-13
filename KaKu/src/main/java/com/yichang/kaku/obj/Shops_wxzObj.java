package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class Shops_wxzObj implements Serializable {

	private String id_shop;
	private String name_brands;
	private String image_shop;
	private String num_bill;
	private String name_shop;
	private String addr_shop;
	private String var_lat;
	private String var_lon;
	private String num_star;
	private String mobile_shop;
	private String image_shop_up;
	private String distance;
	public List<String> name_services;

	public String getId_shop() {
		return id_shop;
	}

	public void setId_shop(String id_shop) {
		this.id_shop = id_shop;
	}

	public String getName_brands() {
		return name_brands;
	}

	public void setName_brands(String name_brands) {
		this.name_brands = name_brands;
	}

	public String getImage_shop() {
		return image_shop;
	}

	public void setImage_shop(String image_shop) {
		this.image_shop = image_shop;
	}

	public String getNum_bill() {
		return num_bill;
	}

	public void setNum_bill(String num_bill) {
		this.num_bill = num_bill;
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

	public String getNum_star() {
		return num_star;
	}

	public void setNum_star(String num_star) {
		this.num_star = num_star;
	}

	public String getMobile_shop() {
		return mobile_shop;
	}

	public void setMobile_shop(String mobile_shop) {
		this.mobile_shop = mobile_shop;
	}

	public String getImage_shop_up() {
		return image_shop_up;
	}

	public void setImage_shop_up(String image_shop_up) {
		this.image_shop_up = image_shop_up;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}


	@Override
	public String toString() {
		return "Shops_wxzObj{" +
				"id_shop='" + id_shop + '\'' +
				", name_brands='" + name_brands + '\'' +
				", image_shop='" + image_shop + '\'' +
				", num_bill='" + num_bill + '\'' +
				", name_shop='" + name_shop + '\'' +
				", addr_shop='" + addr_shop + '\'' +
				", var_lat='" + var_lat + '\'' +
				", var_lon='" + var_lon + '\'' +
				", num_star='" + num_star + '\'' +
				", mobile_shop='" + mobile_shop + '\'' +
				", image_shop_up='" + image_shop_up + '\'' +
				", distance='" + distance + '\'' +
				", name_services=" + name_services +
				'}';
	}
}
