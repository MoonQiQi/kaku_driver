package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarData2Obj implements Serializable {
	
	private String id_series;
	private String id_fuel;
	private String step2_name1;
	private String step2_name2;
	private String flag_show;

	public String getId_series() {
		return id_series;
	}

	public void setId_series(String id_series) {
		this.id_series = id_series;
	}

	public String getId_fuel() {
		return id_fuel;
	}

	public void setId_fuel(String id_fuel) {
		this.id_fuel = id_fuel;
	}

	public String getStep2_name2() {
		return step2_name2;
	}

	public void setStep2_name2(String step2_name2) {
		this.step2_name2 = step2_name2;
	}

	public String getStep2_name1() {
		return step2_name1;
	}

	public void setStep2_name1(String step2_name1) {
		this.step2_name1 = step2_name1;
	}

	public String getFlag_show() {
		return flag_show;
	}

	public void setFlag_show(String flag_show) {
		this.flag_show = flag_show;
	}

	@Override
	public String toString() {
		return "CarData2Obj{" +
				"id_series='" + id_series + '\'' +
				", id_fuel='" + id_fuel + '\'' +
				", step2_name1='" + step2_name1 + '\'' +
				", step2_name2='" + step2_name2 + '\'' +
				", flag_show='" + flag_show + '\'' +
				'}';
	}
}
