package com.yichang.kaku.obj;

import java.io.Serializable;

public class BankCardObj implements Serializable {
	


	private String card_bank;
	private String name_bank;
	private String area_bank;
	private String flag_default;
	private String id_driver_bank;

	@Override
	public String toString() {
		return "BankCardObj{" +
				"card_bank='" + card_bank + '\'' +
				", name_bank='" + name_bank + '\'' +
				", area_bank='" + area_bank + '\'' +
				", flag_default='" + flag_default + '\'' +
				", id_driver_bank='" + id_driver_bank + '\'' +
				'}';
	}

	public String getId_driver_bank() {
		return id_driver_bank;
	}

	public void setId_driver_bank(String id_driver_bank) {
		this.id_driver_bank = id_driver_bank;
	}

	public String getCard_bank() {
		return card_bank;
	}

	public void setCard_bank(String card_bank) {
		this.card_bank = card_bank;
	}

	public String getName_bank() {
		return name_bank;
	}

	public void setName_bank(String name_bank) {
		this.name_bank = name_bank;
	}

	public String getArea_bank() {
		return area_bank;
	}

	public void setArea_bank(String area_bank) {
		this.area_bank = area_bank;
	}

	public String getFlag_default() {
		return flag_default;
	}

	public void setFlag_default(String flag_default) {
		this.flag_default = flag_default;
	}
}
