package com.yichang.kaku.obj;

import java.io.Serializable;

public class YueObj implements Serializable {

	private String flag_type;
	private String business_type;
	private String create_time;
	private String num_money;
	private String remark_money;

	public String getFlag_type() {
		return flag_type;
	}

	public void setFlag_type(String flag_type) {
		this.flag_type = flag_type;
	}

	public String getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getNum_money() {
		return num_money;
	}

	public void setNum_money(String num_money) {
		this.num_money = num_money;
	}

	public String getRemark_money() {
		return remark_money;
	}

	public void setRemark_money(String remark_money) {
		this.remark_money = remark_money;
	}

	@Override
	public String toString() {
		return "YueObj{" +
				"flag_type='" + flag_type + '\'' +
				", business_type='" + business_type + '\'' +
				", create_time='" + create_time + '\'' +
				", num_money='" + num_money + '\'' +
				", remark_money='" + remark_money + '\'' +
				'}';
	}
}
