package com.yichang.kaku.request;

import java.io.Serializable;

public class CheckCodeReq extends BaseReq implements Serializable {
	public String id_driver;
	public String code_recommended;
}
