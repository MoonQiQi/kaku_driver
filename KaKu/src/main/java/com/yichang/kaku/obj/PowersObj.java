package com.yichang.kaku.obj;

import java.io.Serializable;

public class PowersObj implements Serializable {
	
	private String id_power;
	private String name_power;
	private String can_check;

	public String getId_power() {
		return id_power;
	}

	public void setId_power(String id_power) {
		this.id_power = id_power;
	}

	public String getName_power() {
		return name_power;
	}

	public void setName_power(String name_power) {
		this.name_power = name_power;
	}

	public String getCan_check() {
		return can_check;
	}

	public void setCan_check(String can_check) {
		this.can_check = can_check;
	}

	@Override
	public String toString() {
		return "PowersObj{" +
				"id_power='" + id_power + '\'' +
				", name_power='" + name_power + '\'' +
				", can_check='" + can_check + '\'' +
				'}';
	}
}
