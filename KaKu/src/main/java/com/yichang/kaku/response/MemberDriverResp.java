package com.yichang.kaku.response;

import com.yichang.kaku.obj.MemberDriverObj;

import java.io.Serializable;

public class MemberDriverResp extends BaseResp implements Serializable {
	public MemberDriverObj driver;
	public String flag_notice;
	public String fwdd_dfk;
	public String fwdd_daz;
	public String fwdd_dqr;
	public String fwdd_dpj;
	public String fwdd_fx;
	public String cpdd_dfk;
	public String cpdd_dsh;
	public String cpdd_dpj;
	public String cpdd_thh;
	public String num_coupon;
	public String customer_tel;
	//红包是否显示
	public String flag_show;
	public String url;
	//安卓H5页面
	public String url_0;


}
