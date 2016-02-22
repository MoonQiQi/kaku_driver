package com.yichang.kaku.obj;

import java.io.Serializable;

public class MemberCouponObj implements Serializable {
private String time_get,flag_used,money_coupon,money_limit,time_start,time_end,content_coupon;

	@Override
	public String toString() {
		return "MemberCouponObj{" +
				"time_get='" + time_get + '\'' +
				", flag_used='" + flag_used + '\'' +
				", money_coupon='" + money_coupon + '\'' +
				", money_limit='" + money_limit + '\'' +
				", time_start='" + time_start + '\'' +
				", time_end='" + time_end + '\'' +
				", content_coupon='" + content_coupon + '\'' +
				'}';
	}

	public String getTime_get() {
		return time_get;
	}

	public void setTime_get(String time_get) {
		this.time_get = time_get;
	}

	public String getFlag_used() {
		return flag_used;
	}

	public void setFlag_used(String flag_used) {
		this.flag_used = flag_used;
	}

	public String getMoney_coupon() {
		return money_coupon;
	}

	public void setMoney_coupon(String money_coupon) {
		this.money_coupon = money_coupon;
	}

	public String getMoney_limit() {
		return money_limit;
	}

	public void setMoney_limit(String money_limit) {
		this.money_limit = money_limit;
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

	public String getContent_coupon() {
		return content_coupon;
	}

	public void setContent_coupon(String content_coupon) {
		this.content_coupon = content_coupon;
	}
}
