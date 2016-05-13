package com.yichang.kaku.request;

import java.io.Serializable;

public class BaoYangListReq extends BaseReq implements Serializable {
    public String id_shop;
    public String id_item;
    public String id_brand;
    public String var_lon;
    public String var_lat;
    public String type_item;
    public String start;
    public String len;
}
