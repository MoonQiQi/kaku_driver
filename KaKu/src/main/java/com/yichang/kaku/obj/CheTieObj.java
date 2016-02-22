package com.yichang.kaku.obj;

import java.io.Serializable;

public class CheTieObj implements Serializable {

	private String id_advert;
	private String name_advert;
	private String day_earnings;
	private String time_begin;
	private String time_end;
	private String time_begin_sell;
	private String time_end_sell;
	private String num_driver;
	private String image_advert;
	private String total_num;
	private String sales_num;
	private String price_advert;
	private String day_continue;
	private String total_earnings;
	private String flag_type;
	private String sales_percent;

	public String getId_advert() {
		return id_advert;
	}

	public void setId_advert(String id_advert) {
		this.id_advert = id_advert;
	}

	public String getName_advert() {
		return name_advert;
	}

	public void setName_advert(String name_advert) {
		this.name_advert = name_advert;
	}

	public String getDay_earnings() {
		return day_earnings;
	}

	public void setDay_earnings(String day_earnings) {
		this.day_earnings = day_earnings;
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

	public String getTime_begin_sell() {
		return time_begin_sell;
	}

	public void setTime_begin_sell(String time_begin_sell) {
		this.time_begin_sell = time_begin_sell;
	}

	public String getTime_end_sell() {
		return time_end_sell;
	}

	public void setTime_end_sell(String time_end_sell) {
		this.time_end_sell = time_end_sell;
	}

	public String getNum_driver() {
		return num_driver;
	}

	public void setNum_driver(String num_driver) {
		this.num_driver = num_driver;
	}

	public String getImage_advert() {
		return image_advert;
	}

	public void setImage_advert(String image_advert) {
		this.image_advert = image_advert;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public String getSales_num() {
		return sales_num;
	}

	public void setSales_num(String sales_num) {
		this.sales_num = sales_num;
	}

	public String getPrice_advert() {
		return price_advert;
	}

	public void setPrice_advert(String price_advert) {
		this.price_advert = price_advert;
	}

	public String getDay_continue() {
		return day_continue;
	}

	public void setDay_continue(String day_continue) {
		this.day_continue = day_continue;
	}

	public String getTotal_earnings() {
		return total_earnings;
	}

	public void setTotal_earnings(String total_earnings) {
		this.total_earnings = total_earnings;
	}

	public String getFlag_type() {
		return flag_type;
	}

	public void setFlag_type(String flag_type) {
		this.flag_type = flag_type;
	}

	public String getSales_percent() {
		return sales_percent;
	}

	public void setSales_percent(String sales_percent) {
		this.sales_percent = sales_percent;
	}

	@Override
	public String toString() {
		return "CheTieObj{" +
				"id_advert='" + id_advert + '\'' +
				", name_advert='" + name_advert + '\'' +
				", day_earnings='" + day_earnings + '\'' +
				", time_begin='" + time_begin + '\'' +
				", time_end='" + time_end + '\'' +
				", time_begin_sell='" + time_begin_sell + '\'' +
				", time_end_sell='" + time_end_sell + '\'' +
				", num_driver='" + num_driver + '\'' +
				", image_advert='" + image_advert + '\'' +
				", total_num='" + total_num + '\'' +
				", sales_num='" + sales_num + '\'' +
				", price_advert='" + price_advert + '\'' +
				", day_continue='" + day_continue + '\'' +
				", total_earnings='" + total_earnings + '\'' +
				", flag_type='" + flag_type + '\'' +
				", sales_percent='" + sales_percent + '\'' +
				'}';
	}
}
