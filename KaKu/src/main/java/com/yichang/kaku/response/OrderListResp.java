package com.yichang.kaku.response;

import com.yichang.kaku.obj.OrderObj;

import java.io.Serializable;
import java.util.List;

public class OrderListResp extends BaseResp implements Serializable {
	public List<OrderObj> orders;
}
