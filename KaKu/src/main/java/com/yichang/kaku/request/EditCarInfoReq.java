package com.yichang.kaku.request;

import java.io.Serializable;

public class EditCarInfoReq extends BaseReq implements Serializable {
    public String id_brand;//品牌id
    public String data_series;//车系
    public String data_model;//车型
    public String data_actuate;//驱动形式
    public String data_fuel;//燃油种类
    public String data_emissions;//排放标砖
    public String data_power;//最大马力
    public String data_engine;//发动机


}
