package com.yichang.kaku.response;

import com.yichang.kaku.obj.PingJiaObj;

import java.io.Serializable;
import java.util.List;

public class PingJiaResp extends BaseResp implements Serializable {
	public List<PingJiaObj> evals;
}
