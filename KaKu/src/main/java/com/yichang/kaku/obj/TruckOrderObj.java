package com.yichang.kaku.obj;

import java.io.Serializable;
import java.util.List;

public class TruckOrderObj implements Serializable {
//	只使用一下四个字段name_goods,num_shopcar,image_goods,price_goods;
	private List<ConfirmOrderProductObj> shopcar;

	private String id_bill;
//	订单状态（待付款A，待发货B，物流中C，已签收D，待评价E，已评价F，已取消G，已删除H退换货处理中Z）
	private String state_bill;
	private String price_bill;

	//列表支付
	private String no_bill;
	private String type_pay;

	@Override
	public String toString() {
		return "TruckOrderObj{" +
				"shopcar=" + shopcar +
				", id_bill='" + id_bill + '\'' +
				", state_bill='" + state_bill + '\'' +
				", price_bill='" + price_bill + '\'' +
				", no_bill='" + no_bill + '\'' +
				", type_pay='" + type_pay + '\'' +
				'}';
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

	public List<ConfirmOrderProductObj> getShopcar() {
		return shopcar;
	}

	public void setShopcar(List<ConfirmOrderProductObj> shopcar) {
		this.shopcar = shopcar;
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

	public String getPrice_bill() {
		return price_bill;
	}

	public void setPrice_bill(String price_bill) {
		this.price_bill = price_bill;
	}
}
