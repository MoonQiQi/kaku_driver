package com.yichang.kaku.obj;

import java.io.Serializable;

public class TuiJianObj implements Serializable {
	
	private String phone_driver;
	private String flag_get;
	private String flag_recommended;

	public String getPhone_driver() {
		return phone_driver;
	}

	public void setPhone_driver(String phone_driver) {
		this.phone_driver = phone_driver;
	}

	public String getFlag_get() {
		return flag_get;
	}

	public void setFlag_get(String flag_get) {
		this.flag_get = flag_get;
	}

	public String getFlag_recommended() {
		return flag_recommended;
	}

	public void setFlag_recommended(String flag_recommended) {
		this.flag_recommended = flag_recommended;
	}

	@Override
	public String toString() {
		return "TuiJianObj{" +
				"phone_driver='" + phone_driver + '\'' +
				", flag_get='" + flag_get + '\'' +
				", flag_recommended='" + flag_recommended + '\'' +
				'}';
	}
}
