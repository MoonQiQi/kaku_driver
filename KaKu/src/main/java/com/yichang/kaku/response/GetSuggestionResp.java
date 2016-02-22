package com.yichang.kaku.response;

import com.yichang.kaku.obj.SuggestionObj;

import java.io.Serializable;
import java.util.List;

public class GetSuggestionResp extends BaseResp implements Serializable {
    public List<SuggestionObj> suggests;
}
