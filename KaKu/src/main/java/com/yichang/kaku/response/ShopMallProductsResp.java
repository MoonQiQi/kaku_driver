package com.yichang.kaku.response;

import com.yichang.kaku.obj.ShopMallProductObj;

import java.io.Serializable;
import java.util.List;

public class ShopMallProductsResp extends BaseResp implements Serializable {
    public List<ShopMallProductObj> goods;
    public String num_shopcar;
}
