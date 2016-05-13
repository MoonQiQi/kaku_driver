package com.yichang.kaku.response;

import com.yichang.kaku.obj.BaoYangShopObj;
import com.yichang.kaku.obj.BigOilObj;

import java.io.Serializable;
import java.util.List;

public class BaoYangListResp extends BaseResp implements Serializable {
    public BaoYangShopObj shop;
    public List<BigOilObj> item_oil_big;
    public List<BigOilObj> item_oil_small;
    public List<BigOilObj> item_filter;
    public String price_goods;
}
