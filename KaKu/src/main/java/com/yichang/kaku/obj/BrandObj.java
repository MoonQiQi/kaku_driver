package com.yichang.kaku.obj;

import java.io.Serializable;

public class BrandObj implements Serializable {
	
	private String name_brand;
	private String image_brand;

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

	@Override
	public String toString() {
		return "BrandObj{" +
				"name_brand='" + name_brand + '\'' +
				", image_brand='" + image_brand + '\'' +
				'}';
	}
}
