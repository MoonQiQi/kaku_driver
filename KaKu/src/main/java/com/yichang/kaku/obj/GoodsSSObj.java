package com.yichang.kaku.obj;

import java.io.Serializable;

public class GoodsSSObj implements Serializable {
	
	private String id_goods;
	private String name_goods;
	private String price_goods;
	private String image_goods;
	private String promotion_goods;
	private String num_exchange;
	private String flag_mail;
	private String num_eval;

	public String getId_goods() {
		return id_goods;
	}

	public void setId_goods(String id_goods) {
		this.id_goods = id_goods;
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

	public String getPromotion_goods() {
		return promotion_goods;
	}

	public void setPromotion_goods(String promotion_goods) {
		this.promotion_goods = promotion_goods;
	}

	public String getNum_exchange() {
		return num_exchange;
	}

	public void setNum_exchange(String num_exchange) {
		this.num_exchange = num_exchange;
	}

	public String getFlag_mail() {
		return flag_mail;
	}

	public void setFlag_mail(String flag_mail) {
		this.flag_mail = flag_mail;
	}

	public String getNum_eval() {
		return num_eval;
	}

	public void setNum_eval(String num_eval) {
		this.num_eval = num_eval;
	}

	@Override
	public String toString() {
		return "GoodsSSObj{" +
				"id_goods='" + id_goods + '\'' +
				", name_goods='" + name_goods + '\'' +
				", price_goods='" + price_goods + '\'' +
				", image_goods='" + image_goods + '\'' +
				", promotion_goods='" + promotion_goods + '\'' +
				", num_exchange='" + num_exchange + '\'' +
				", flag_mail='" + flag_mail + '\'' +
				", num_eval='" + num_eval + '\'' +
				'}';
	}
}
