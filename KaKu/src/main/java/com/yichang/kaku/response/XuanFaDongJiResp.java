package com.yichang.kaku.response;

import com.yichang.kaku.obj.CarData2Obj;

import java.io.Serializable;
import java.util.List;

public class XuanFaDongJiResp extends BaseResp implements Serializable {
    public List<CarData2Obj> data;
}
