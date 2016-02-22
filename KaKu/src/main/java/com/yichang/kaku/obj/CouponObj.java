package com.yichang.kaku.obj;

import java.io.Serializable;

public class CouponObj implements Serializable {
	
	private String type_coupon;
	private String content_coupon;

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

	@Override
	public String toString() {
		return "CouponObj{" +
				"type_coupon='" + type_coupon + '\'' +
				", content_coupon='" + content_coupon + '\'' +
				'}';
	}
}
