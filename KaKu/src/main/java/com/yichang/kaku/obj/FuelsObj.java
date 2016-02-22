package com.yichang.kaku.obj;

import java.io.Serializable;

public class FuelsObj implements Serializable {
	
	private String id_fuel;
	private String name_fuel;
	private String can_check;

	public String getId_fuel() {
		return id_fuel;
	}

	public void setId_fuel(String id_fuel) {
		this.id_fuel = id_fuel;
	}

	public String getName_fuel() {
		return name_fuel;
	}

	public void setName_fuel(String name_fuel) {
		this.name_fuel = name_fuel;
	}

	public String getCan_check() {
		return can_check;
	}

	public void setCan_check(String can_check) {
		this.can_check = can_check;
	}

	@Override
	public String toString() {
		return "FuelsObj{" +
				"id_fuel='" + id_fuel + '\'' +
				", name_fuel='" + name_fuel + '\'' +
				", can_check='" + can_check + '\'' +
				'}';
	}
}
