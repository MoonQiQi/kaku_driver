package com.yichang.kaku.request;

import com.yichang.kaku.tools.Utils;

import java.io.Serializable;

public class BaseReq implements Serializable {
	public String code;
	public String sid = Utils.getSid();
	public String id_driver = Utils.getIdDriver();
}
