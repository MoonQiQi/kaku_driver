package com.yichang.kaku.response;

import com.yichang.kaku.obj.ShopCartProductObj;

import java.io.Serializable;
import java.util.List;

public class ShopCartProductsResp extends BaseResp implements Serializable {
    public List<ShopCartProductObj> shopcar;
}
