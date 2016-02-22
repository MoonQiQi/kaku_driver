package com.yichang.kaku.response;

import com.yichang.kaku.obj.Shops_wxzObj;

import java.io.Serializable;
import java.util.List;

public class SOSResp extends BaseResp implements Serializable {
    public List<Shops_wxzObj> shops;
    public String name_brand;
    public String mobile_brand;

}
