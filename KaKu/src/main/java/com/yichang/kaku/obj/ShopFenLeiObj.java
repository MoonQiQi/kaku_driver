package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopFenLeiObj implements Serializable {

	private String id_service;
	private String name_service;
	private String flag_choose;
	private String image_service;

	public String getId_service() {
		return id_service;
	}

	public void setId_service(String id_service) {
		this.id_service = id_service;
	}

	public String getName_service() {
		return name_service;
	}

	public void setName_service(String name_service) {
		this.name_service = name_service;
	}

	public String getFlag_choose() {
		return flag_choose;
	}

	public void setFlag_choose(String flag_choose) {
		this.flag_choose = flag_choose;
	}

	public String getImage_service() {
		return image_service;
	}

	public void setImage_service(String image_service) {
		this.image_service = image_service;
	}

	@Override
	public String toString() {
		return "ShopFenLeiObj{" +
				"id_service='" + id_service + '\'' +
				", name_service='" + name_service + '\'' +
				", flag_choose='" + flag_choose + '\'' +
				", image_service='" + image_service + '\'' +
				'}';
	}
}
