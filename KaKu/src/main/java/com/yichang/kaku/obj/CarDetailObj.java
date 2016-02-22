package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarDetailObj implements Serializable {

	private String image_brand;
	private String nick_name;
	private String time_production;
	private String travel_mileage;

	@Override
	public String toString() {
		return "CarDetailObj{" +
				"image_brand='" + image_brand + '\'' +
				", nick_name='" + nick_name + '\'' +
				", time_production='" + time_production + '\'' +
				", travel_mileage='" + travel_mileage + '\'' +
				'}';
	}

	public String getImage_brand() {
		return image_brand;
	}

	public void setImage_brand(String image_brand) {
		this.image_brand = image_brand;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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
}
