package com.yichang.kaku.obj;

import java.io.Serializable;

public class OrderObj implements Serializable {
	private String state_order;
	private String flag_repair;
	private String name_order;
	private String goods_nums_order;
	private String price_order;
	private String time_order;
	private String time_end;
	private String time_service;
	private String type_service;
	private String name_shop;
	private String mobile_shop;
	private String goods_image_order;
	private String id_order;

	private String type_pay;
	private String no_order;

	@Override
	public String toString() {
		return "OrderObj{" +
				"state_order='" + state_order + '\'' +
				", flag_repair='" + flag_repair + '\'' +
				", name_order='" + name_order + '\'' +
				", goods_nums_order='" + goods_nums_order + '\'' +
				", price_order='" + price_order + '\'' +
				", time_order='" + time_order + '\'' +
				", time_end='" + time_end + '\'' +
				", time_service='" + time_service + '\'' +
				", type_service='" + type_service + '\'' +
				", name_shop='" + name_shop + '\'' +
				", mobile_shop='" + mobile_shop + '\'' +
				", goods_image_order='" + goods_image_order + '\'' +
				", id_order='" + id_order + '\'' +
				", type_pay='" + type_pay + '\'' +
				", no_order='" + no_order + '\'' +
				'}';
	}

	public String getType_pay() {
		return type_pay;
	}

	public void setType_pay(String type_pay) {
		this.type_pay = type_pay;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getState_order() {
		return state_order;
	}

	public void setState_order(String state_order) {
		this.state_order = state_order;
	}

	public String getFlag_repair() {
		return flag_repair;
	}

	public void setFlag_repair(String flag_repair) {
		this.flag_repair = flag_repair;
	}

	public String getName_order() {
		return name_order;
	}

	public void setName_order(String name_order) {
		this.name_order = name_order;
	}

	public String getGoods_nums_order() {
		return goods_nums_order;
	}

	public void setGoods_nums_order(String goods_nums_order) {
		this.goods_nums_order = goods_nums_order;
	}

	public String getPrice_order() {
		return price_order;
	}

	public void setPrice_order(String price_order) {
		this.price_order = price_order;
	}

	public String getTime_order() {
		return time_order;
	}

	public void setTime_order(String time_order) {
		this.time_order = time_order;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getTime_service() {
		return time_service;
	}

	public void setTime_service(String time_service) {
		this.time_service = time_service;
	}

	public String getType_service() {
		return type_service;
	}

	public void setType_service(String type_service) {
		this.type_service = type_service;
	}

	public String getName_shop() {
		return name_shop;
	}

	public void setName_shop(String name_shop) {
		this.name_shop = name_shop;
	}

	public String getMobile_shop() {
		return mobile_shop;
	}

	public void setMobile_shop(String mobile_shop) {
		this.mobile_shop = mobile_shop;
	}

	public String getGoods_image_order() {
		return goods_image_order;
	}

	public void setGoods_image_order(String goods_image_order) {
		this.goods_image_order = goods_image_order;
	}

	public String getId_order() {
		return id_order;
	}

	public void setId_order(String id_order) {
		this.id_order = id_order;
	}

}
