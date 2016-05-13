package com.yichang.kaku.obj;

import java.io.Serializable;

public class DayObj implements Serializable {

	private String date;
	private String money;
	private String sign;
	private String sign0;
	private String sign1;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign0() {
		return sign0;
	}

	public void setSign0(String sign0) {
		this.sign0 = sign0;
	}

	public String getSign1() {
		return sign1;
	}

	public void setSign1(String sign1) {
		this.sign1 = sign1;
	}

	@Override
	public String toString() {
		return "DayObj{" +
				"date='" + date + '\'' +
				", money='" + money + '\'' +
				", sign='" + sign + '\'' +
				", sign0='" + sign0 + '\'' +
				", sign1='" + sign1 + '\'' +
				'}';
	}
}
