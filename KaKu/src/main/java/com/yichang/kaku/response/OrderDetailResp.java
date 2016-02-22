package com.yichang.kaku.response;

import com.yichang.kaku.obj.OrderDetailObj;
import com.yichang.kaku.obj.ShopUserObj;
import com.yichang.kaku.obj.WuLiaoObj;

import java.io.Serializable;
import java.util.List;

public class OrderDetailResp extends BaseResp implements Serializable {

    public ShopUserObj shop_user;
	public List<WuLiaoObj> items;
    public OrderDetailObj order;
}
