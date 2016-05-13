package com.yichang.kaku.obj;

import java.io.Serializable;

public class ModifyCarObj implements Serializable {

	private String id_brand;
	private String id_car_series;
	private String car_no;
	private String time_road;
	private String image_no;
	private String travel_mileage;
	private String flag_default;
	private String image_brand;
	private String series_engine;
	private String name_engine;
	private String power;
	private String name_brand;
	private String emissions;
	private String car_code;
	private String seriesname;
	private String id_data;
	private String car_no_name;

	public String getId_brand() {
		return id_brand;
	}

	public void setId_brand(String id_brand) {
		this.id_brand = id_brand;
	}

	public String getId_car_series() {
		return id_car_series;
	}

	public void setId_car_series(String id_car_series) {
		this.id_car_series = id_car_series;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getTime_road() {
		return time_road;
	}

	public void setTime_road(String time_road) {
		this.time_road = time_road;
	}

	public String getImage_no() {
		return image_no;
	}

	public void setImage_no(String image_no) {
		this.image_no = image_no;
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

	public String getImage_brand() {
		return image_brand;
	}

	public void setImage_brand(String image_brand) {
		this.image_brand = image_brand;
	}

	public String getSeries_engine() {
		return series_engine;
	}

	public void setSeries_engine(String series_engine) {
		this.series_engine = series_engine;
	}

	public String getName_engine() {
		return name_engine;
	}

	public void setName_engine(String name_engine) {
		this.name_engine = name_engine;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getName_brand() {
		return name_brand;
	}

	public void setName_brand(String name_brand) {
		this.name_brand = name_brand;
	}

	public String getEmissions() {
		return emissions;
	}

	public void setEmissions(String emissions) {
		this.emissions = emissions;
	}

	public String getCar_code() {
		return car_code;
	}

	public void setCar_code(String car_code) {
		this.car_code = car_code;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public String getId_data() {
		return id_data;
	}

	public void setId_data(String id_data) {
		this.id_data = id_data;
	}

	public String getCar_no_name() {
		return car_no_name;
	}

	public void setCar_no_name(String car_no_name) {
		this.car_no_name = car_no_name;
	}

	@Override
	public String toString() {
		return "ModifyCarObj{" +
				"id_brand='" + id_brand + '\'' +
				", id_car_series='" + id_car_series + '\'' +
				", car_no='" + car_no + '\'' +
				", time_road='" + time_road + '\'' +
				", image_no='" + image_no + '\'' +
				", travel_mileage='" + travel_mileage + '\'' +
				", flag_default='" + flag_default + '\'' +
				", image_brand='" + image_brand + '\'' +
				", series_engine='" + series_engine + '\'' +
				", name_engine='" + name_engine + '\'' +
				", power='" + power + '\'' +
				", name_brand='" + name_brand + '\'' +
				", emissions='" + emissions + '\'' +
				", car_code='" + car_code + '\'' +
				", seriesname='" + seriesname + '\'' +
				", id_data='" + id_data + '\'' +
				", car_no_name='" + car_no_name + '\'' +
				'}';
	}
}
