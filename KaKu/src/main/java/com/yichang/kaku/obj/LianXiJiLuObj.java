package com.yichang.kaku.obj;

import java.io.Serializable;

public class LianXiJiLuObj implements Serializable {

	private String name_contact;
	private String phone_contact;
	private String time_contact;

	public String getName_contact() {
		return name_contact;
	}

	public void setName_contact(String name_contact) {
		this.name_contact = name_contact;
	}

	public String getPhone_contact() {
		return phone_contact;
	}

	public void setPhone_contact(String phone_contact) {
		this.phone_contact = phone_contact;
	}

	public String getTime_contact() {
		return time_contact;
	}

	public void setTime_contact(String time_contact) {
		this.time_contact = time_contact;
	}

	@Override
	public String toString() {
		return "LianXiJiLuObj{" +
				"name_contact='" + name_contact + '\'' +
				", phone_contact='" + phone_contact + '\'' +
				", time_contact='" + time_contact + '\'' +
				'}';
	}
}
