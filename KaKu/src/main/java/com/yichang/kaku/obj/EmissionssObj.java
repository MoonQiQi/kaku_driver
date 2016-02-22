package com.yichang.kaku.obj;

import java.io.Serializable;

public class EmissionssObj implements Serializable {
	
	private String id_emissions;
	private String name_emissions;
	private String can_check;

	public String getCan_check() {
		return can_check;
	}

	public void setCan_check(String can_check) {
		this.can_check = can_check;
	}

	public String getId_emissions() {
		return id_emissions;
	}

	public void setId_emissions(String id_emissions) {
		this.id_emissions = id_emissions;
	}

	public String getName_emissions() {
		return name_emissions;
	}

	public void setName_emissions(String name_emissions) {
		this.name_emissions = name_emissions;
	}

	@Override
	public String toString() {
		return "EmissionssObj{" +
				"id_emissions='" + id_emissions + '\'' +
				", name_emissions='" + name_emissions + '\'' +
				", can_check='" + can_check + '\'' +
				'}';
	}
}
