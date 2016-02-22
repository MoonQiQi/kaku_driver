package com.yichang.kaku.request;

import java.io.Serializable;

public class CheckWithDrawCodeReq extends BaseReq implements Serializable {
	public String id_driver;
	public String pay_pass;
	public String sign;

}
