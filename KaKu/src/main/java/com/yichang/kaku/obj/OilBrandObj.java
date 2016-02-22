package com.yichang.kaku.obj;

import java.io.Serializable;

public class OilBrandObj implements Serializable {
	
	private String id_brand_oil;
	private String name_brand_oil;
	private String image_brand_oil;
	private String flag_check;

	public String getId_brand_oil() {
		return id_brand_oil;
	}

	public void setId_brand_oil(String id_brand_oil) {
		this.id_brand_oil = id_brand_oil;
	}

	public String getImage_brand_oil() {
		return image_brand_oil;
	}

	public void setImage_brand_oil(String image_brand_oil) {
		this.image_brand_oil = image_brand_oil;
	}

	public String getName_brand_oil() {
		return name_brand_oil;
	}

	public void setName_brand_oil(String name_brand_oil) {
		this.name_brand_oil = name_brand_oil;
	}

	public String getFlag_check() {
		return flag_check;
	}

	public void setFlag_check(String flag_check) {
		this.flag_check = flag_check;
	}

	@Override
	public String toString() {
		return "OilBrandObj{" +
				"id_brand_oil='" + id_brand_oil + '\'' +
				", name_brand_oil='" + name_brand_oil + '\'' +
				", image_brand_oil='" + image_brand_oil + '\'' +
				", flag_check='" + flag_check + '\'' +
				'}';
	}
}
