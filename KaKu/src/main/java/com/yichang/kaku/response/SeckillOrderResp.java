package com.yichang.kaku.response;

import com.yichang.kaku.obj.ConfirmOrderProductObj;
import com.yichang.kaku.obj.OrderAddressObj;

import java.io.Serializable;
import java.util.List;

public class SeckillOrderResp extends BaseResp implements Serializable {

    public OrderAddressObj addr;
    public List<ConfirmOrderProductObj> shopcar;

    public String point_limit;
    public String price_goods;
    public String price_point;
    public String price_bill;


}
