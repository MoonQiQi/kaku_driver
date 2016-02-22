package com.yichang.kaku.obj;

import java.io.Serializable;

public class SeckillObj implements Serializable {

	@Override
	public String toString() {
		return "SeckillObj{" +
				"id_goods='" + id_goods + '\'' +
				", time_begin='" + time_begin + '\'' +
				", time_end='" + time_end + '\'' +
				", price_seckill='" + price_seckill + '\'' +
				", num_seckill='" + num_seckill + '\'' +
				", price_goods='" + price_goods + '\'' +
				", name_goods='" + name_goods + '\'' +
				", image_goods='" + image_goods + '\'' +
				'}';
	}

	public String getId_goods() {
		return id_goods;
	}

	public void setId_goods(String id_goods) {
		this.id_goods = id_goods;
	}

	public String getTime_begin() {
		return time_begin;
	}

	public void setTime_begin(String time_begin) {
		this.time_begin = time_begin;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getPrice_seckill() {
		return price_seckill;
	}

	public void setPrice_seckill(String price_seckill) {
		this.price_seckill = price_seckill;
	}

	public String getNum_seckill() {
		return num_seckill;
	}

	public void setNum_seckill(String num_seckill) {
		this.num_seckill = num_seckill;
	}

	public String getPrice_goods() {
		return price_goods;
	}

	public void setPrice_goods(String price_goods) {
		this.price_goods = price_goods;
	}

	public String getName_goods() {
		return name_goods;
	}

	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}

	public String getImage_goods() {
		return image_goods;
	}

	public void setImage_goods(String image_goods) {
		this.image_goods = image_goods;
	}

	private String id_goods;
	private String time_begin;
	private String time_end;
	private String price_seckill;
	private String num_seckill;
	private String price_goods;
	private String name_goods;
	private String image_goods;


}
