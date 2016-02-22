package com.yichang.kaku.obj;

import java.io.Serializable;

public class MyLoveCarObj implements Serializable {

	private String name_brand;
	private String image_brand;
	private String name_series;
	private String name_model;
	private String name_power;
    private String id_driver_car;
    private String flag_approve;
    private String name_actuate;
    private String num_length;
    private String num_load;
    private String num_space;

	public String getNum_space() {
		return num_space;
	}

	public void setNum_space(String num_space) {
		this.num_space = num_space;
	}

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

	public String getName_power() {
		return name_power;
	}

	public void setName_power(String name_power) {
		this.name_power = name_power;
	}

	public String getId_driver_car() {
		return id_driver_car;
	}

	public void setId_driver_car(String id_driver_car) {
		this.id_driver_car = id_driver_car;
	}

	public String getFlag_approve() {
		return flag_approve;
	}

	public void setFlag_approve(String flag_approve) {
		this.flag_approve = flag_approve;
	}

	public String getName_actuate() {
		return name_actuate;
	}

	public void setName_actuate(String name_actuate) {
		this.name_actuate = name_actuate;
	}

	public String getNum_length() {
		return num_length;
	}

	public void setNum_length(String num_length) {
		this.num_length = num_length;
	}

	public String getNum_load() {
		return num_load;
	}

	public void setNum_load(String num_load) {
		this.num_load = num_load;
	}

	@Override
	public String toString() {
		return "MyLoveCarObj{" +
				"name_brand='" + name_brand + '\'' +
				", image_brand='" + image_brand + '\'' +
				", name_series='" + name_series + '\'' +
				", name_model='" + name_model + '\'' +
				", name_power='" + name_power + '\'' +
				", id_driver_car='" + id_driver_car + '\'' +
				", flag_approve='" + flag_approve + '\'' +
				", name_actuate='" + name_actuate + '\'' +
				", num_length='" + num_length + '\'' +
				", num_load='" + num_load + '\'' +
				", num_space='" + num_space + '\'' +
				'}';
	}
}
