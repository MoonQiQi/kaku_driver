package com.yichang.kaku.response;

import com.yichang.kaku.obj.EngineObj;
import com.yichang.kaku.obj.SeriesObj;

import java.io.Serializable;
import java.util.List;

public class FaDongJiInfoResp extends BaseResp implements Serializable {
    public String name_brand;
    public String image_brand;
    public EngineObj engine;
    public List<SeriesObj> series;
}
