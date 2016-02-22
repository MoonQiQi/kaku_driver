package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarData4Obj implements Serializable {
	
	private String id_car;
	private String step4_name1;
	private String step4_name2;
	private String step4_name3;

	public String getId_car() {
		return id_car;
	}

	public void setId_car(String id_car) {
		this.id_car = id_car;
	}

	public String getStep4_name1() {
		return step4_name1;
	}

	public void setStep4_name1(String step4_name1) {
		this.step4_name1 = step4_name1;
	}

	public String getStep4_name2() {
		return step4_name2;
	}

	public void setStep4_name2(String step4_name2) {
		this.step4_name2 = step4_name2;
	}

	public String getStep4_name3() {
		return step4_name3;
	}

	public void setStep4_name3(String step4_name3) {
		this.step4_name3 = step4_name3;
	}

	@Override
	public String toString() {
		return "CarData4Obj{" +
				"id_car='" + id_car + '\'' +
				", step4_name1='" + step4_name1 + '\'' +
				", step4_name2='" + step4_name2 + '\'' +
				", step4_name3='" + step4_name3 + '\'' +
				'}';
	}
}
