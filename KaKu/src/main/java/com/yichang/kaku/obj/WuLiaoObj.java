package com.yichang.kaku.obj;

import java.io.Serializable;

public class WuLiaoObj implements Serializable {
	
	private String name_item;
	private String price_item;
	private String image_item;
	private String num_item;

	public String getName_item() {
		return name_item;
	}

	public void setName_item(String name_item) {
		this.name_item = name_item;
	}

	public String getPrice_item() {
		return price_item;
	}

	public void setPrice_item(String price_item) {
		this.price_item = price_item;
	}

	public String getImage_item() {
		return image_item;
	}

	public void setImage_item(String image_item) {
		this.image_item = image_item;
	}

	public String getNum_item() {
		return num_item;
	}

	public void setNum_item(String num_item) {
		this.num_item = num_item;
	}

	@Override
	public String toString() {
		return "WuLiaoObj{" +
				"name_item='" + name_item + '\'' +
				", price_item='" + price_item + '\'' +
				", image_item='" + image_item + '\'' +
				", num_item='" + num_item + '\'' +
				'}';
	}
}
