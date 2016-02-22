package com.yichang.kaku.request;

import java.io.Serializable;

public class GenerateOrderReq extends BaseReq implements Serializable {
	public String id_driver;
	public String name_addr;
	public String phone_addr;
	public String addr;
	public String type_pay;
	public String type_invoice;
	public String type1_invoice;
	public String var_invoice;
	public String phone_invoice;
	public String email_invoice;

	@Override
	public String toString() {
		return "GenerateOrderReq{" +
				"id_driver='" + id_driver + '\'' +
				", name_addr='" + name_addr + '\'' +
				", phone_addr='" + phone_addr + '\'' +
				", addr='" + addr + '\'' +
				", type_pay='" + type_pay + '\'' +
				", type_invoice='" + type_invoice + '\'' +
				", type1_invoice='" + type1_invoice + '\'' +
				", var_invoice='" + var_invoice + '\'' +
				", phone_invoice='" + phone_invoice + '\'' +
				", email_invoice='" + email_invoice + '\'' +
				", point_bill='" + point_bill + '\'' +
				", price_goods='" + price_goods + '\'' +
				", price_point='" + price_point + '\'' +
				", price_bill='" + price_bill + '\'' +
				", flag_seckill='" + flag_seckill + '\'' +
				", id_goods='" + id_goods + '\'' +
				", money_balance='" + money_balance + '\'' +
				'}';
	}

	public String point_bill;
	public String price_goods;
	public String price_point;
	public String price_bill;

	public String flag_seckill;
	public String id_goods;

	public String money_balance;

}
