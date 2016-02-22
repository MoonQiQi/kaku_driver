package com.yichang.kaku.obj;

import java.io.Serializable;

public class MyCarObj implements Serializable {
	
	private String id_driver_car;
	private String nick_name;
	private String nick_name1;

	public String getNick_name1() {
		return nick_name1;
	}

	public void setNick_name1(String nick_name1) {
		this.nick_name1 = nick_name1;
	}

	private String image_brand;
	private String time_production;
	private String travel_mileage;
	private String flag_default;
	private String flag_check;

	@Override
	public String toString() {
		return "MyCarObj{" +
				"id_driver_car='" + id_driver_car + '\'' +
				", nick_name='" + nick_name + '\'' +
				", image_brand='" + image_brand + '\'' +
				", time_production='" + time_production + '\'' +
				", travel_mileage='" + travel_mileage + '\'' +
				", flag_default='" + flag_default + '\'' +
				", flag_check='" + flag_check + '\'' +
				'}';
	}

	public String getFlag_check() {
		return flag_check;
	}

	public void setFlag_check(String flag_check) {
		this.flag_check = flag_check;
	}

	public String getId_driver_car() {
		return id_driver_car;
	}

	public void setId_driver_car(String id_driver_car) {
		this.id_driver_car = id_driver_car;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nickname) {
		this.nick_name = nickname;
	}

	public String getImage_brand() {
		return image_brand;
	}

	public void setImage_brand(String image_brand) {
		this.image_brand = image_brand;
	}

	public String getTime_production() {
		return time_production;
	}

	public void setTime_production(String time_production) {
		this.time_production = time_production;
	}

	public String getTravel_mileage() {
		return travel_mileage;
	}

	public void setTravel_mileage(String travel_mileage) {
		this.travel_mileage = travel_mileage;
	}

	public String getFlag_default() {
		return flag_default;
	}

	public void setFlag_default(String flag_default) {
		this.flag_default = flag_default;
	}
}
