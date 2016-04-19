package com.yichang.kaku.obj;

import java.io.Serializable;

public class TruckOrderDetailObj implements Serializable {
	private String id_bill;
	private String state_bill;
	private String no_bill;
	private String type_pay;
	private String type_invoice;
	private String type1_invoice;
	private String var_invoice;
	private String name_addr;
	private String phone_addr;
	private String addr;
	private String price_goods;
	private String price_point;
	private String price_bill;
	private String time_create;
	private String money_balance;
	private String type_receive;

	public String getMoney_balance() {
		return money_balance;
	}

	public void setMoney_balance(String money_balance) {
		this.money_balance = money_balance;
	}

	public String getId_bill() {
		return id_bill;
	}

	public void setId_bill(String id_bill) {
		this.id_bill = id_bill;
	}

	public String getState_bill() {
		return state_bill;
	}

	public void setState_bill(String state_bill) {
		this.state_bill = state_bill;
	}

	public String getNo_bill() {
		return no_bill;
	}

	public void setNo_bill(String no_bill) {
		this.no_bill = no_bill;
	}

	public String getType_pay() {
		return type_pay;
	}

	public void setType_pay(String type_pay) {
		this.type_pay = type_pay;
	}

	public String getType_invoice() {
		return type_invoice;
	}

	public void setType_invoice(String type_invoice) {
		this.type_invoice = type_invoice;
	}

	public String getType1_invoice() {
		return type1_invoice;
	}

	public void setType1_invoice(String type1_invoice) {
		this.type1_invoice = type1_invoice;
	}

	public String getVar_invoice() {
		return var_invoice;
	}

	public void setVar_invoice(String var_invoice) {
		this.var_invoice = var_invoice;
	}

	public String getName_addr() {
		return name_addr;
	}

	public void setName_addr(String name_addr) {
		this.name_addr = name_addr;
	}

	public String getPhone_addr() {
		return phone_addr;
	}

	public void setPhone_addr(String phone_addr) {
		this.phone_addr = phone_addr;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPrice_goods() {
		return price_goods;
	}

	public void setPrice_goods(String price_goods) {
		this.price_goods = price_goods;
	}

	public String getPrice_point() {
		return price_point;
	}

	public void setPrice_point(String price_point) {
		this.price_point = price_point;
	}

	public String getPrice_bill() {
		return price_bill;
	}

	public void setPrice_bill(String price_bill) {
		this.price_bill = price_bill;
	}

	public String getTime_create() {
		return time_create;
	}

	public void setTime_create(String time_create) {
		this.time_create = time_create;
	}

	public String getType_receive() {
		return type_receive;
	}

	public void setType_receive(String type_receive) {
		this.type_receive = type_receive;
	}

	@Override
	public String toString() {
		return "TruckOrderDetailObj{" +
				"id_bill='" + id_bill + '\'' +
				", state_bill='" + state_bill + '\'' +
				", no_bill='" + no_bill + '\'' +
				", type_pay='" + type_pay + '\'' +
				", type_invoice='" + type_invoice + '\'' +
				", type1_invoice='" + type1_invoice + '\'' +
				", var_invoice='" + var_invoice + '\'' +
				", name_addr='" + name_addr + '\'' +
				", phone_addr='" + phone_addr + '\'' +
				", addr='" + addr + '\'' +
				", price_goods='" + price_goods + '\'' +
				", price_point='" + price_point + '\'' +
				", price_bill='" + price_bill + '\'' +
				", time_create='" + time_create + '\'' +
				", money_balance='" + money_balance + '\'' +
				", type_receive='" + type_receive + '\'' +
				'}';
	}
}
