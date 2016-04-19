package com.yichang.kaku.request;

import java.io.Serializable;

public class ShopMallProductsReq extends BaseReq implements Serializable {
	public String start;
	public String len;
	public String sort;
	public String type_goods;
	public String id_driver;
}
