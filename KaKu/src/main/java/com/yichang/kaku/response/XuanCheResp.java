package com.yichang.kaku.response;

import com.yichang.kaku.obj.CarData2Obj;
import com.yichang.kaku.obj.CarData3Obj;
import com.yichang.kaku.obj.CarData4Obj;
import com.yichang.kaku.obj.CarDataObj;

import java.io.Serializable;
import java.util.List;

public class XuanCheResp extends BaseResp implements Serializable {
	public List<CarData2Obj> datas2;
	public List<CarData3Obj> datas3;
	public List<CarData4Obj> datas4;
	public CarDataObj brand;
	public String flag_jump;
	public String id_car;
}
