package com.yichang.kaku.response;

import com.yichang.kaku.obj.SouSuoHotObj;

import java.io.Serializable;
import java.util.List;

public class SouSuoHotResp extends BaseResp implements Serializable {
    public List<SouSuoHotObj> hots;
}
