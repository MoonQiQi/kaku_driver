package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarListObj implements Serializable {
	
	private String name_brand;
	private String image_brand;
	private String name_series;
	private String name_model;
	private String image_model;
	private String name_power;
    private String id_car;
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

	public String getImage_model() {
		return image_model;
	}

	public void setImage_model(String image_model) {
		this.image_model = image_model;
	}

	public String getName_power() {
		return name_power;
	}

	public void setName_power(String name_power) {
		this.name_power = name_power;
	}

	public String getId_car() {
		return id_car;
	}

	public void setId_car(String id_car) {
		this.id_car = id_car;
	}

	public String getName_actuate() {
		return name_actuate;
	}

	public void setName_actuate(String name_actuate) {
		this.name_actuate = name_actuate;
	}

	@Override
	public String toString() {
		return "CarListObj{" +
				"name_brand='" + name_brand + '\'' +
				", image_brand='" + image_brand + '\'' +
				", name_series='" + name_series + '\'' +
				", name_model='" + name_model + '\'' +
				", image_model='" + image_model + '\'' +
				", name_power='" + name_power + '\'' +
				", id_car='" + id_car + '\'' +
				", name_actuate='" + name_actuate + '\'' +
				'}';
	}
}
