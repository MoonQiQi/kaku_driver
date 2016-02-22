package com.yichang.kaku.obj;

import java.io.Serializable;

public class MemberMsgObj implements Serializable {

	private String name_notice;
	private String content;
	private String time_pub;
	private String id_order;

/*	private String type_order;*/
	private String state_order;

/*	public String getType_order() {
		return type_order;
	}

	public void setType_order(String type_order) {
		this.type_order = type_order;
	}*/

	public String getState_order() {
		return state_order;
	}

	public void setState_order(String state_order) {
		this.state_order = state_order;
	}

	@Override
	public String toString() {
		return "MemberMsgObj{" +
				"name_notice='" + name_notice + '\'' +
				", content='" + content + '\'' +
				", time_pub='" + time_pub + '\'' +
				", id_order='" + id_order + '\'' +
				'}';
	}

	public String getName_notice() {
		return name_notice;
	}

	public void setName_notice(String name_notice) {
		this.name_notice = name_notice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime_pub() {
		return time_pub;
	}

	public void setTime_pub(String time_pub) {
		this.time_pub = time_pub;
	}

	public String getId_order() {
		return id_order;
	}

	public void setId_order(String id_order) {
		this.id_order = id_order;
	}
}
