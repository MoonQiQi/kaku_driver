package com.yichang.kaku.response;

import com.yichang.kaku.obj.ShopMallProductCommentObj;

import java.io.Serializable;
import java.util.List;

public class GetProductCommentListResp extends BaseResp implements Serializable {
	public List<ShopMallProductCommentObj> evals;
}
