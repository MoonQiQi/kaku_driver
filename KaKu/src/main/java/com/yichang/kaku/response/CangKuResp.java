package com.yichang.kaku.response;

import com.yichang.kaku.obj.CangKuObj;

import java.io.Serializable;
import java.util.List;

public class CangKuResp extends BaseResp implements Serializable {
    public List<CangKuObj> items;
}
