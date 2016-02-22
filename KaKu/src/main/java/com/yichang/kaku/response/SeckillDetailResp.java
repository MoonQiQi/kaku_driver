package com.yichang.kaku.response;

import com.yichang.kaku.obj.SeckillDetailObj;
import com.yichang.kaku.obj.ShopMallProductCommentObj;

import java.io.Serializable;
import java.util.List;

public class SeckillDetailResp extends BaseResp implements Serializable {
	public List<SeckillDetailObj> seckills;
	public  List<ShopMallProductCommentObj> evals0;
	public  List<ShopMallProductCommentObj> evals1;
	public  List<ShopMallProductCommentObj> evals2;
}
