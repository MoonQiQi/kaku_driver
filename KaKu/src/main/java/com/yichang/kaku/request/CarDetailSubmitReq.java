package com.yichang.kaku.request;

import java.io.Serializable;

public class CarDetailSubmitReq extends BaseReq implements Serializable {
	public String id_driver;
	public String id_driver_car;
	public String id_car;
	public String time_production;
	public String travel_mileage;
}
