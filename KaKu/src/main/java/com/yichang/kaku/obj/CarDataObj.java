package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarDataObj implements Serializable {

	private String name_brand;
	private String image_brand;
	private String name_series;
	private String name_model;
	private String name_fuel;
    private String name_actuate;

	public String getName_brand() {
		return name_brand;
	}

	public void setName_brand(String name_brand) {
		this.name_brand = name_brand;
	}

	public String getImage_brand() {
		return image_brand;
	}

	public void setImage_brand(String image_brand) {
		this.image_brand = image_brand;
	}

	public String getName_series() {
		return name_series;
	}

	public void setName_series(String name_series) {
		this.name_series = name_series;
	}

	public String getName_model() {
		return name_model;
	}

	public void setName_model(String name_model) {
		this.name_model = name_model;
	}

	public String getName_fuel() {
		return name_fuel;
	}

	public void setName_fuel(String name_fuel) {
		this.name_fuel = name_fuel;
	}

	public String getName_actuate() {
		return name_actuate;
	}

	public void setName_actuate(String name_actuate) {
		this.name_actuate = name_actuate;
	}

	@Override
	public String toString() {
		return "CarDataObj{" +
				"name_brand='" + name_brand + '\'' +
				", image_brand='" + image_brand + '\'' +
				", name_series='" + name_series + '\'' +
				", name_model='" + name_model + '\'' +
				", name_fuel='" + name_fuel + '\'' +
				", name_actuate='" + name_actuate + '\'' +
				'}';
	}
}
