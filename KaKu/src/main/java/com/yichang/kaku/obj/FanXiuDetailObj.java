package com.yichang.kaku.obj;

import java.io.Serializable;

public class FanXiuDetailObj implements Serializable {

	private String describe_repair;
	private String image_repair;
	private String time_repair;
	private String id_repair;
	private String tel_repair;

	public String getDescribe_repair() {
		return describe_repair;
	}

	public void setDescribe_repair(String describe_repair) {
		this.describe_repair = describe_repair;
	}

	public String getImage_repair() {
		return image_repair;
	}

	public void setImage_repair(String image_repair) {
		this.image_repair = image_repair;
	}

	public String getTime_repair() {
		return time_repair;
	}

	public void setTime_repair(String time_repair) {
		this.time_repair = time_repair;
	}

	public String getId_repair() {
		return id_repair;
	}

	public void setId_repair(String id_repair) {
		this.id_repair = id_repair;
	}

	public String getTel_repair() {
		return tel_repair;
	}

	public void setTel_repair(String tel_repair) {
		this.tel_repair = tel_repair;
	}

	@Override
	public String toString() {
		return "FanXiuDetailObj{" +
				"describe_repair='" + describe_repair + '\'' +
				", image_repair='" + image_repair + '\'' +
				", time_repair='" + time_repair + '\'' +
				", id_repair='" + id_repair + '\'' +
				", tel_repair='" + tel_repair + '\'' +
				'}';
	}
}
