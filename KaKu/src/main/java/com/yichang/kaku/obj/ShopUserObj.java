package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopUserObj implements Serializable {
	
	private String name_user;
	private String phone_user;

	public String getName_user() {
		return name_user;
	}

	public void setName_user(String name_user) {
		this.name_user = name_user;
	}

	public String getPhone_user() {
		return phone_user;
	}

	public void setPhone_user(String phone_user) {
		this.phone_user = phone_user;
	}

	@Override
	public String toString() {
		return "ShopUserObj{" +
				"name_user='" + name_user + '\'' +
				", phone_user='" + phone_user + '\'' +
				'}';
	}
}
