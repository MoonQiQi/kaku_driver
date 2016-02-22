package com.yichang.kaku.obj;

import java.io.Serializable;

public class CheTieDetailObj implements Serializable {
	public String id_advert;
	public String limit_num;
	public String price_advert;
	public String name_advert;
	public String promotion_advert;
	public String remark_content1;
	public String remark_content2;
	public String day_earnings;
	public String time_begin;
	public String time_end;
	public String sales_num;
	public String total_earnings;
	public String breaks_money;
	public String image_advert;
	public String url_advert;

	public String getId_advert() {
		return id_advert;
	}

	public void setId_advert(String id_advert) {
		this.id_advert = id_advert;
	}

	public String getLimit_num() {
		return limit_num;
	}

	public void setLimit_num(String limit_num) {
		this.limit_num = limit_num;
	}

	public String getPrice_advert() {
		return price_advert;
	}

	public void setPrice_advert(String price_advert) {
		this.price_advert = price_advert;
	}

	public String getName_advert() {
		return name_advert;
	}

	public void setName_advert(String name_advert) {
		this.name_advert = name_advert;
	}

	public String getPromotion_advert() {
		return promotion_advert;
	}

	public void setPromotion_advert(String promotion_advert) {
		this.promotion_advert = promotion_advert;
	}

	public String getRemark_content1() {
		return remark_content1;
	}

	public void setRemark_content1(String remark_content1) {
		this.remark_content1 = remark_content1;
	}

	public String getRemark_content2() {
		return remark_content2;
	}

	public void setRemark_content2(String remark_content2) {
		this.remark_content2 = remark_content2;
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

	public String getSales_num() {
		return sales_num;
	}

	public void setSales_num(String sales_num) {
		this.sales_num = sales_num;
	}

	public String getTotal_earnings() {
		return total_earnings;
	}

	public void setTotal_earnings(String total_earnings) {
		this.total_earnings = total_earnings;
	}

	public String getBreaks_money() {
		return breaks_money;
	}

	public void setBreaks_money(String breaks_money) {
		this.breaks_money = breaks_money;
	}

	public String getImage_advert() {
		return image_advert;
	}

	public void setImage_advert(String image_advert) {
		this.image_advert = image_advert;
	}

	public String getUrl_advert() {
		return url_advert;
	}

	public void setUrl_advert(String url_advert) {
		this.url_advert = url_advert;
	}

	@Override
	public String toString() {
		return "CheTieDetailObj{" +
				"id_advert='" + id_advert + '\'' +
				", limit_num='" + limit_num + '\'' +
				", price_advert='" + price_advert + '\'' +
				", name_advert='" + name_advert + '\'' +
				", promotion_advert='" + promotion_advert + '\'' +
				", remark_content1='" + remark_content1 + '\'' +
				", remark_content2='" + remark_content2 + '\'' +
				", day_earnings='" + day_earnings + '\'' +
				", time_begin='" + time_begin + '\'' +
				", time_end='" + time_end + '\'' +
				", sales_num='" + sales_num + '\'' +
				", total_earnings='" + total_earnings + '\'' +
				", breaks_money='" + breaks_money + '\'' +
				", image_advert='" + image_advert + '\'' +
				", url_advert='" + url_advert + '\'' +
				'}';
	}
}
