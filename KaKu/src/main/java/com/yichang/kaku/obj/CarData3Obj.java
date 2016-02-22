package com.yichang.kaku.obj;

import java.io.Serializable;

public class CarData3Obj implements Serializable {
	
	private String id_model;
	private String id_actuate;
	private String step3_name1;
	private String step3_name2;
	private String flag_show;

	public String getId_model() {
		return id_model;
	}

	public void setId_model(String id_model) {
		this.id_model = id_model;
	}

	public String getId_actuate() {
		return id_actuate;
	}

	public void setId_actuate(String id_actuate) {
		this.id_actuate = id_actuate;
	}

	public String getStep3_name1() {
		return step3_name1;
	}

	public void setStep3_name1(String step3_name1) {
		this.step3_name1 = step3_name1;
	}

	public String getStep3_name2() {
		return step3_name2;
	}

	public void setStep3_name2(String step3_name2) {
		this.step3_name2 = step3_name2;
	}

	public String getFlag_show() {
		return flag_show;
	}

	public void setFlag_show(String flag_show) {
		this.flag_show = flag_show;
	}

	@Override
	public String toString() {
		return "CarData3Obj{" +
				"id_model='" + id_model + '\'' +
				", id_actuate='" + id_actuate + '\'' +
				", step3_name1='" + step3_name1 + '\'' +
				", step3_name2='" + step3_name2 + '\'' +
				", flag_show='" + flag_show + '\'' +
				'}';
	}
}
