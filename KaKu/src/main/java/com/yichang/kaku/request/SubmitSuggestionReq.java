package com.yichang.kaku.request;

import java.io.Serializable;

public class SubmitSuggestionReq extends BaseReq implements Serializable {

	public String id_driver;
	public String content_suggest;
	public String type_suggest;

}
