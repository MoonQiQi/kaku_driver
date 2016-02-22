package com.yichang.kaku.obj;

import java.io.Serializable;

public class EvalObj implements Serializable {
	
	private String name_driver;
	private String time_eval;
	private String content_eval;
	private String star_eval;

	public String getName_driver() {
		return name_driver;
	}

	public void setName_driver(String name_driver) {
		this.name_driver = name_driver;
	}

	public String getTime_eval() {
		return time_eval;
	}

	public void setTime_eval(String time_eval) {
		this.time_eval = time_eval;
	}

	public String getContent_eval() {
		return content_eval;
	}

	public void setContent_eval(String content_eval) {
		this.content_eval = content_eval;
	}

	public String getStar_eval() {
		return star_eval;
	}

	public void setStar_eval(String star_eval) {
		this.star_eval = star_eval;
	}

	@Override
	public String toString() {
		return "EvalObj{" +
				"name_driver='" + name_driver + '\'' +
				", time_eval='" + time_eval + '\'' +
				", content_eval='" + content_eval + '\'' +
				", star_eval='" + star_eval + '\'' +
				'}';
	}
}
