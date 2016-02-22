package com.yichang.kaku.obj;

import java.io.Serializable;

public class ShopCarObj implements Serializable {
	
	private String id_item;
	private String name_item;
	private String image_item;
	private String num_item;
	private String price_item;
	private String no_item;
	private String type_item;

	public String getId_item() {
		return id_item;
	}

	public void setId_item(String id_item) {
		this.id_item = id_item;
	}

	public String getName_item() {
		return name_item;
	}

	public void setName_item(String name_item) {
		this.name_item = name_item;
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

	public String getPrice_item() {
		return price_item;
	}

	public void setPrice_item(String price_item) {
		this.price_item = price_item;
	}

	public String getNo_item() {
		return no_item;
	}

	public void setNo_item(String no_item) {
		this.no_item = no_item;
	}

	public String getType_item() {
		return type_item;
	}

	public void setType_item(String type_item) {
		this.type_item = type_item;
	}

	@Override
	public String toString() {
		return "ShopCarObj{" +
				"id_item='" + id_item + '\'' +
				", name_item='" + name_item + '\'' +
				", image_item='" + image_item + '\'' +
				", num_item='" + num_item + '\'' +
				", price_item='" + price_item + '\'' +
				", no_item='" + no_item + '\'' +
				", type_item='" + type_item + '\'' +
				'}';
	}
}
