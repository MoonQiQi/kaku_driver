package com.yichang.kaku.response;

import com.yichang.kaku.obj.Worker2Obj;

import java.io.Serializable;

public class HuJiaoFuWuResp extends BaseResp implements Serializable {
    public String id_upkeep_bill;
    public String price_goods;
    public String var_lon0;
    public String var_lat0;
    public Worker2Obj worker;

}
