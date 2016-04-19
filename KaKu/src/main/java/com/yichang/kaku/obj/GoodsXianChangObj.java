package com.yichang.kaku.obj;

import java.io.Serializable;

public class GoodsXianChangObj implements Serializable {
	
	private String num_shopcar;
	private String name_goods;
	private String price_goods;
	private String image_goods;

	public String getNum_shopcar() {
		return num_shopcar;
	}

	public void setNum_shopcar(String num_shopcar) {
		this.num_shopcar = num_shopcar;
	}

	public String getName_goods() {
		return name_goods;
	}

	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}

	public String getPrice_goods() {
		return price_goods;
	}

	public void setPrice_goods(String price_goods) {
		this.price_goods = price_goods;
	}

	public String getImage_goods() {
		return image_goods;
	}

	public void setImage_goods(String image_goods) {
		this.image_goods = image_goods;
	}

	@Override
	public String toString() {
		return "GoodsXianChangObj{" +
				"num_shopcar='" + num_shopcar + '\'' +
				", name_goods='" + name_goods + '\'' +
				", price_goods='" + price_goods + '\'' +
				", image_goods='" + image_goods + '\'' +
				'}';
	}
}
