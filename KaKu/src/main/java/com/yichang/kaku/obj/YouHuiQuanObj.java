package com.yichang.kaku.obj;

import java.io.Serializable;

public class YouHuiQuanObj implements Serializable {
private String id_coupon,type_coupon,content_coupon,money_coupon,time_start,time_end;

	public String getId_coupon() {
		return id_coupon;
	}

	public void setId_coupon(String id_coupon) {
		this.id_coupon = id_coupon;
	}

	public String getType_coupon() {
		return type_coupon;
	}

	public void setType_coupon(String type_coupon) {
		this.type_coupon = type_coupon;
	}

	public String getContent_coupon() {
		return content_coupon;
	}

	public void setContent_coupon(String content_coupon) {
		this.content_coupon = content_coupon;
	}

	public String getMoney_coupon() {
		return money_coupon;
	}

	public void setMoney_coupon(String money_coupon) {
		this.money_coupon = money_coupon;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	@Override
	public String toString() {
		return "YouHuiQuanObj{" +
				"id_coupon='" + id_coupon + '\'' +
				", type_coupon='" + type_coupon + '\'' +
				", content_coupon='" + content_coupon + '\'' +
				", money_coupon='" + money_coupon + '\'' +
				", time_start='" + time_start + '\'' +
				", time_end='" + time_end + '\'' +
				'}';
	}
}
