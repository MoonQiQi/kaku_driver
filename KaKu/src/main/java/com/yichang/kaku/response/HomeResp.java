package com.yichang.kaku.response;

import com.yichang.kaku.obj.GoodsObj;
import com.yichang.kaku.obj.HotActivityObj;
import com.yichang.kaku.obj.NewsObj;
import com.yichang.kaku.obj.RollsObj;

import java.io.Serializable;
import java.util.List;

public class HomeResp extends BaseResp implements Serializable {
    public String image_brand;
    public List<RollsObj> rolls;
    public List<GoodsObj> goods_hot;
    public List<NewsObj> newss;
    public String id_no;
    public String url_coupon;
    public String url_register;
    public List<HotActivityObj> activitys_hot;
}
