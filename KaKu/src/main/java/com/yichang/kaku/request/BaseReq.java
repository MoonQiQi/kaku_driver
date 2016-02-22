package com.yichang.kaku.request;

import java.io.Serializable;

import com.yichang.kaku.tools.Utils;

public class BaseReq implements Serializable {
	public String code;
	public String sid = Utils.getSid();
}
