package com.yichang.kaku.obj;

import java.io.Serializable;

public class CheTieOrderObj implements Serializable {

	private String id_advert_bill;
	private String name_advert;
	private String state_bill;
	private String price_bill;
	private String no_bill;
	private String type_pay;
	private String time_create;
	private String num_advert;
	private String image_advert;

	public String getId_advert_bill() {
		return id_advert_bill;
	}

	public void setId_advert_bill(String id_advert_bill) {
		this.id_advert_bill = id_advert_bill;
	}

	public String getName_advert() {
		return name_advert;
	}

	public void setName_advert(String name_advert) {
		this.name_advert = name_advert;
	}

	public String getState_bill() {
		return state_bill;
	}

	public void setState_bill(String state_bill) {
		this.state_bill = state_bill;
	}

	public String getPrice_bill() {
		return price_bill;
	}

	public void setPrice_bill(String price_bill) {
		this.price_bill = price_bill;
	}

	public String getNo_bill() {
		return no_bill;
	}

	public void setNo_bill(String no_bill) {
		this.no_bill = no_bill;
	}

	public String getType_pay() {
		return type_pay;
	}

	public void setType_pay(String type_pay) {
		this.type_pay = type_pay;
	}

	public String getTime_create() {
		return time_create;
	}

	public void setTime_create(String time_create) {
		this.time_create = time_create;
	}

	public String getNum_advert() {
		return num_advert;
	}

	public void setNum_advert(String num_advert) {
		this.num_advert = num_advert;
	}

	public String getImage_advert() {
		return image_advert;
	}

	public void setImage_advert(String image_advert) {
		this.image_advert = image_advert;
	}

	@Override
	public String toString() {
		return "CheTieOrderObj{" +
				"id_advert_bill='" + id_advert_bill + '\'' +
				", name_advert='" + name_advert + '\'' +
				", state_bill='" + state_bill + '\'' +
				", price_bill='" + price_bill + '\'' +
				", no_bill='" + no_bill + '\'' +
				", type_pay='" + type_pay + '\'' +
				", time_create='" + time_create + '\'' +
				", num_advert='" + num_advert + '\'' +
				", image_advert='" + image_advert + '\'' +
				'}';
	}
}
