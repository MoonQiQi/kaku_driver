package com.yichang.kaku.obj;

import java.io.Serializable;

public class IllegalInfo implements Serializable {

	private String time;// 时间
	private String street;// 地点
	private String reason;// 原因
	private String degree;// 分数
	private String count;// 金钱

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "IllegalInfo{" +
				"time='" + time + '\'' +
				", street='" + street + '\'' +
				", reason='" + reason + '\'' +
				", degree='" + degree + '\'' +
				", count='" + count + '\'' +
				'}';
	}
}
