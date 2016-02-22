package com.yichang.kaku.response;

import com.yichang.kaku.obj.PointHistoryObj;

import java.io.Serializable;
import java.util.List;

public class PointHistoryResp extends BaseResp implements Serializable {

	public List<PointHistoryObj> historys;
    public String point_now;
    public String point_his;
}
