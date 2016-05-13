package com.yichang.kaku.response;

import com.yichang.kaku.obj.CarObj;
import com.yichang.kaku.obj.ShopFenLeiObj;
import com.yichang.kaku.obj.Shops_wxzObj;

import java.io.Serializable;
import java.util.List;

public class PinPaiFuWuZhanResp extends BaseResp implements Serializable {
    public String flag_type;
    public String name_brand;
    public String mobile_brand;
    public String flag_enter;
    public List<Shops_wxzObj> shops;
    public List<ShopFenLeiObj> services;
    public CarObj car;
}
