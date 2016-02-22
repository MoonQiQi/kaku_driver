package com.yichang.kaku.response;

import com.yichang.kaku.obj.AreaObj;

import java.io.Serializable;
import java.util.List;

public class AreaResp extends BaseResp implements Serializable {
    public List<AreaObj> areas;
}
