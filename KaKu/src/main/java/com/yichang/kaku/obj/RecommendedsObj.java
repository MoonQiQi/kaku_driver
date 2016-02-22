package com.yichang.kaku.obj;

import java.io.Serializable;

public class RecommendedsObj implements Serializable {
	
	private String phone_driver;
	private String time_recommended;
	private String points;
	private String name_driver;
	private String flag_advert;
	private String money_earnings;
	private String money_balance;


	@Override
	public String toString() {
		return "RecommendedsObj{" +
				"phone_driver='" + phone_driver + '\'' +
				", time_recommended='" + time_recommended + '\'' +
				", points='" + points + '\'' +
				", name_driver='" + name_driver + '\'' +
				", flag_advert='" + flag_advert + '\'' +
				", money_earnings='" + money_earnings + '\'' +
				", money_balance='" + money_balance + '\'' +
				'}';
	}

	public String getName_driver() {
		return name_driver;
	}

	public void setName_driver(String name_driver) {
		this.name_driver = name_driver;
	}

	public String getFlag_advert() {
		return flag_advert;
	}

	public void setFlag_advert(String flag_advert) {
		this.flag_advert = flag_advert;
	}

	public String getMoney_earnings() {
		return money_earnings;
	}

	public void setMoney_earnings(String money_earnings) {
		this.money_earnings = money_earnings;
	}

	public String getMoney_balance() {
		return money_balance;
	}

	public void setMoney_balance(String money_balance) {
		this.money_balance = money_balance;
	}

	public String getPhone_driver() {
		return phone_driver;
	}

	public void setPhone_driver(String phone_driver) {
		this.phone_driver = phone_driver;
	}

	public String getTime_recommended() {
		return time_recommended;
	}

	public void setTime_recommended(String time_recommended) {
		this.time_recommended = time_recommended;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

}
