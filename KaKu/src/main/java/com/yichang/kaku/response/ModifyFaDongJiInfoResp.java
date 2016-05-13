package com.yichang.kaku.response;

import com.yichang.kaku.obj.ModifyCarObj;
import com.yichang.kaku.obj.SeriesObj;

import java.io.Serializable;
import java.util.List;

public class ModifyFaDongJiInfoResp extends BaseResp implements Serializable {
    public ModifyCarObj car;
    public List<SeriesObj> series;
}
