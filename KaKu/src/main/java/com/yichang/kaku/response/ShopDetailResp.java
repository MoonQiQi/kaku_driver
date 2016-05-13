package com.yichang.kaku.response;

import com.yichang.kaku.obj.ShopDetailObj;
import com.yichang.kaku.obj.ShopServiceObj;

import java.io.Serializable;
import java.util.List;

public class ShopDetailResp extends BaseResp implements Serializable {
    public ShopDetailObj shop;
    public List<ShopServiceObj> services;
}
