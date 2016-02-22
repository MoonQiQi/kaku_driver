package com.yichang.kaku.request;

import java.io.Serializable;

public class AddOilReq extends BaseReq implements Serializable {
	public String type_item;
	public String no_item;
	public String start;
	public String len;
	public String id_shop;
	public String id_drop;
	public String flag_type;

}
