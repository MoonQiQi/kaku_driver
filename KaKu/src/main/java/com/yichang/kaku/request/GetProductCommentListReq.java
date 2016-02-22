package com.yichang.kaku.request;

import java.io.Serializable;

public class GetProductCommentListReq extends BaseReq implements Serializable {
	public String id_goods;
	public String start;
	public String len;
}
