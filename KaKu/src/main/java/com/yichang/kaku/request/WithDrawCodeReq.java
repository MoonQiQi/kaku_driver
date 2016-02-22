package com.yichang.kaku.request;

import java.io.Serializable;

public class WithDrawCodeReq extends BaseReq implements Serializable {
	public String id_driver;
	public String vcode;
	public String pay_pass;

}
