package com.yichang.kaku.obj;

import java.io.Serializable;

public class DiscoveryCommentsObj implements Serializable {
	
	private String name_driver;
	private String head;

	@Override
	public String toString() {
		return "DiscoveryCommentsObj{" +
				"name_driver='" + name_driver + '\'' +
				", head='" + head + '\'' +
				", time_comment='" + time_comment + '\'' +
				", content_comment='" + content_comment + '\'' +
				'}';
	}

	public String getTime_comment() {
		return time_comment;
	}

	public void setTime_comment(String time_comment) {
		this.time_comment = time_comment;
	}

	private String time_comment;
	private String content_comment;

	public String getName_driver() {
		return name_driver;
	}

	public void setName_driver(String name_driver) {
		this.name_driver = name_driver;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}


	public String getContent_comment() {
		return content_comment;
	}

	public void setContent_comment(String content_comment) {
		this.content_comment = content_comment;
	}
}
