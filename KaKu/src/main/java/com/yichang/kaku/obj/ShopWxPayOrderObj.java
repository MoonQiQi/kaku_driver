package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopWxPayOrderObj implements Serializable {
	
	private String price_order;
	private String name_order;
	private String no_order;
	private String notify_url;

	@Override
	public String toString() {
		return "ShopWxPayOrderObj{" +
				"price_order='" + price_order + '\'' +
				", name_order='" + name_order + '\'' +
				", no_order='" + no_order + '\'' +
				", notify_url='" + notify_url + '\'' +
				'}';
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getPrice_order() {
		return price_order;
	}

	public void setPrice_order(String price_order) {
		this.price_order = price_order;
	}

	public String getName_order() {
		return name_order;
	}

	public void setName_order(String name_order) {
		this.name_order = name_order;
	}
}
