package com.yichang.kaku.obj;

import java.io.Serializable;

public class MyCheYuanObj implements Serializable {

	private String id_options;
	private String area_depart;
	private String area_arrive;
	private String time_pub;
	private String time_loading;
	private String remark_options;
	private String areas_hsbc;

	public String getId_options() {
		return id_options;
	}

	public void setId_options(String id_options) {
		this.id_options = id_options;
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

	public String getTime_loading() {
		return time_loading;
	}

	public void setTime_loading(String time_loading) {
		this.time_loading = time_loading;
	}

	public String getRemark_options() {
		return remark_options;
	}

	public void setRemark_options(String remark_options) {
		this.remark_options = remark_options;
	}

	public String getAreas_hsbc() {
		return areas_hsbc;
	}

	public void setAreas_hsbc(String areas_hsbc) {
		this.areas_hsbc = areas_hsbc;
	}

	@Override
	public String toString() {
		return "MyCheYuanObj{" +
				"id_options='" + id_options + '\'' +
				", area_depart='" + area_depart + '\'' +
				", area_arrive='" + area_arrive + '\'' +
				", time_pub='" + time_pub + '\'' +
				", time_loading='" + time_loading + '\'' +
				", remark_options='" + remark_options + '\'' +
				", areas_hsbc='" + areas_hsbc + '\'' +
				'}';
	}
}
