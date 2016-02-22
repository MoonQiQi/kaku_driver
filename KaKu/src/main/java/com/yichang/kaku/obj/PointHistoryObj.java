package com.yichang.kaku.obj;

import java.io.Serializable;

public class PointHistoryObj implements Serializable {

	private String point;
	private String time_his;
	private String remark_his;
	private String flag_type;

	@Override
	public String toString() {
		return "PointHistoryObj{" +
				"point='" + point + '\'' +
				", time_his='" + time_his + '\'' +
				", remark_his='" + remark_his + '\'' +
				", flag_type='" + flag_type + '\'' +
				'}';
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getTime_his() {
		return time_his;
	}

	public void setTime_his(String time_his) {
		this.time_his = time_his;
	}

	public String getRemark_his() {
		return remark_his;
	}

	public void setRemark_his(String remark_his) {
		this.remark_his = remark_his;
	}

	public String getFlag_type() {
		return flag_type;
	}

	public void setFlag_type(String flag_type) {
		this.flag_type = flag_type;
	}
}
