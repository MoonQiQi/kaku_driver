package com.yichang.kaku.response;

import com.yichang.kaku.obj.ShopMallProductCommentObj;
import com.yichang.kaku.obj.ShopMallProductDetailObj;

import java.io.Serializable;
import java.util.List;

public class ProductDetailInfoResp extends BaseResp implements Serializable {
	public ShopMallProductDetailObj goods;

	public String num_shopcar;

	public List<ShopMallProductCommentObj> evals;
}
