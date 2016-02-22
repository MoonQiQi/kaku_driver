package com.yichang.kaku.response;

import com.yichang.kaku.obj.MemberMsgObj;

import java.io.Serializable;
import java.util.List;

public class MemberMsgResp extends BaseResp implements Serializable {

	public List<MemberMsgObj> notices;

}
