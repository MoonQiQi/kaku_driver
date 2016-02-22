package com.yichang.kaku.obj;

import java.io.Serializable;

public class Shops_mapObj implements Serializable {
	
	private String id_shop;
	private String name_shop;
	private String var_lat;
	private String var_lon;

	public String getId_shop() {
		return id_shop;
	}

	public void setId_shop(String id_shop) {
		this.id_shop = id_shop;
	}

	public String getName_shop() {
		return name_shop;
	}

	public void setName_shop(String name_shop) {
		this.name_shop = name_shop;
	}

	public String getVar_lat() {
		return var_lat;
	}

	public void setVar_lat(String var_lat) {
		this.var_lat = var_lat;
	}

	public String getVar_lon() {
		return var_lon;
	}

	public void setVar_lon(String var_lon) {
		this.var_lon = var_lon;
	}

	@Override
	public String toString() {
		return "Shops_mapObj{" +
				"id_shop='" + id_shop + '\'' +
				", name_shop='" + name_shop + '\'' +
				", var_lat='" + var_lat + '\'' +
				", var_lon='" + var_lon + '\'' +
				'}';
	}
}
