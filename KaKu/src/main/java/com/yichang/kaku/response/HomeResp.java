package com.yichang.kaku.response;

import com.yichang.kaku.obj.GoodsObj;
import com.yichang.kaku.obj.NewsObj;
import com.yichang.kaku.obj.RollsObj;
import com.yichang.kaku.obj.SeckillObj;
import com.yichang.kaku.obj.Shops_wxzObj;

import java.io.Serializable;
import java.util.List;

public class HomeResp extends BaseResp implements Serializable {
	public String oil_price;
	public String now_date;
	public String image_brand;
	public List<RollsObj> rolls;
	public List<Shops_wxzObj> shops;
	public List<GoodsObj> goods;
	public List<NewsObj> newss;
	public List<SeckillObj> seckills;
	public String time_now;
	public String note;
	public String flag_enter;
	public String nums_sale;
}
