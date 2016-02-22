package com.yichang.kaku.response;

import com.yichang.kaku.obj.ConfirmOrderProductObj;

import java.io.Serializable;
import java.util.List;

public class TruckOrderCommentResp extends BaseResp implements Serializable {
    public List<ConfirmOrderProductObj> shopcar;

}
