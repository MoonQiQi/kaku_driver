package com.yichang.kaku.request;

import java.io.Serializable;

public class ValidateCodeReq extends BaseReq implements Serializable {
	public String id_driver;
	public String key_content;
}
