package com.yichang.kaku.obj;

import java.io.Serializable;

public class ZhaoHuoObj implements Serializable {

	private String id_supply;
	private String area_depart;
	private String area_arrive;
	private String time_pub;
	private String remark_supply;
	private String phone_supply;
	private String focus_supply;
	private String image_roll;
	private String url_roll;
	private String name_roll;

	public String getId_supply() {
		return id_supply;
	}

	public void setId_supply(String id_supply) {
		this.id_supply = id_supply;
	}

	public String getArea_depart() {
		return area_depart;
	}

	public void setArea_depart(String area_depart) {
		this.area_depart = area_depart;
	}

	public String getArea_arrive() {
		return area_arrive;
	}

	public void setArea_arrive(String area_arrive) {
		this.area_arrive = area_arrive;
	}

	public String getTime_pub() {
		return time_pub;
	}

	public void setTime_pub(String time_pub) {
		this.time_pub = time_pub;
	}

	public String getRemark_supply() {
		return remark_supply;
	}

	public void setRemark_supply(String remark_supply) {
		this.remark_supply = remark_supply;
	}

	public String getPhone_supply() {
		return phone_supply;
	}

	public void setPhone_supply(String phone_supply) {
		this.phone_supply = phone_supply;
	}

	public String getFocus_supply() {
		return focus_supply;
	}

	public void setFocus_supply(String focus_supply) {
		this.focus_supply = focus_supply;
	}

	public String getImage_roll() {
		return image_roll;
	}

	public void setImage_roll(String image_roll) {
		this.image_roll = image_roll;
	}

	public String getUrl_roll() {
		return url_roll;
	}

	public void setUrl_roll(String url_roll) {
		this.url_roll = url_roll;
	}

	public String getName_roll() {
		return name_roll;
	}

	public void setName_roll(String name_roll) {
		this.name_roll = name_roll;
	}

	@Override
	public String toString() {
		return "ZhaoHuoObj{" +
				"id_supply='" + id_supply + '\'' +
				", area_depart='" + area_depart + '\'' +
				", area_arrive='" + area_arrive + '\'' +
				", time_pub='" + time_pub + '\'' +
				", remark_supply='" + remark_supply + '\'' +
				", phone_supply='" + phone_supply + '\'' +
				", focus_supply='" + focus_supply + '\'' +
				", image_roll='" + image_roll + '\'' +
				", url_roll='" + url_roll + '\'' +
				", name_roll='" + name_roll + '\'' +
				'}';
	}
}
