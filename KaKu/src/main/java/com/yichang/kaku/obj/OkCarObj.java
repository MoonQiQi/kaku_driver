package com.yichang.kaku.obj;

import java.io.Serializable;

public class OkCarObj implements Serializable {
	
	private String name_brand;
	private String image_brand;
	private String name_series;
	private String name_model;
	private String image_model;
	private String name_power;
    private String name_actuate;
    private String name_fuel;
    private String name_emissions;
	private String id_car;

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

	public String getName_actuate() {
		return name_actuate;
	}

	public void setName_actuate(String name_actuate) {
		this.name_actuate = name_actuate;
	}

	public String getName_fuel() {
		return name_fuel;
	}

	public void setName_fuel(String name_fuel) {
		this.name_fuel = name_fuel;
	}

	public String getName_emissions() {
		return name_emissions;
	}

	public void setName_emissions(String name_emissions) {
		this.name_emissions = name_emissions;
	}

	public String getId_car() {
		return id_car;
	}

	public void setId_car(String id_car) {
		this.id_car = id_car;
	}

	@Override
	public String toString() {
		return "OkCarObj{" +
				"name_brand='" + name_brand + '\'' +
				", image_brand='" + image_brand + '\'' +
				", name_series='" + name_series + '\'' +
				", name_model='" + name_model + '\'' +
				", image_model='" + image_model + '\'' +
				", name_power='" + name_power + '\'' +
				", name_actuate='" + name_actuate + '\'' +
				", name_fuel='" + name_fuel + '\'' +
				", name_emissions='" + name_emissions + '\'' +
				", id_car='" + id_car + '\'' +
				'}';
	}
}
