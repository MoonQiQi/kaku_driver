package com.yichang.kaku.request;

import java.io.Serializable;

public class SendTruckOrderCommentReq extends BaseReq implements Serializable {
	public String id_driver;
	public String id_goods;
	public String id_bill;
	public String star_eval;
	public String content_eval;
	public String image_eval;
}
