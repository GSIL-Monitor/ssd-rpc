package com.shabro.rpc.entity.order;

import com.shabro.rpc.entity.rpc.RpcCyzOrder;
import com.shabro.rpc.entity.mongodb.BasicEntity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 类描述：承运者中标订单的信息
 */
@Document 
public class Order extends BasicEntity {
	@Indexed(name="orderId", unique=false, dropDups=false)
	private String 	orderId;	// 订单ID
	@Indexed(name="orderState", unique=false, dropDups=false)
	private Integer orderState;	// 订单状态
	@Indexed(name="cyzId", unique=false, dropDups=false)
	private String 	cyzId;		// 承运者ID
	private String 	cyzTel;		// 承运者手机号
	private String 	fbzId;		// 发布者ID
	private String 	fbzTel;		// 发布者手机号
	private long 	createTime;	// 创建时间
	private long 	bidTime;	// 中标时间
	private long 	payTime;	// 支付时间
	private long 	signTime; 	// 确认收货时间

	public Order(RpcCyzOrder order) {
		this.orderId = order.getOrderId();
		this.orderState = order.getOrderState();
		this.cyzId = order.getCyzId();
		this.cyzTel = order.getCyzTel();
		this.fbzId = order.getFbzId();
		this.fbzTel = order.getFbzTel();
		this.createTime = order.getCreateTime();
		this.bidTime = order.getBidTime();
		this.payTime = order.getPayTime();
		this.signTime = order.getSignTime();
	}
	
	public Order() {
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getCyzId() {
		return cyzId;
	}
	public void setCyzId(String cyzId) {
		this.cyzId = cyzId;
	}

	public String getCyzTel() {
		return cyzTel;
	}
	public void setCyzTel(String cyzTel) {
		this.cyzTel = cyzTel;
	}

	public String getFbzId() {
		return fbzId;
	}
	public void setFbzId(String fbzId) {
		this.fbzId = fbzId;
	}

	public String getFbzTel() {
		return fbzTel;
	}
	public void setFbzTel(String fbzTel) {
		this.fbzTel = fbzTel;
	}

	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public long getBidTime() {
		return bidTime;
	}
	public void setBidTime(long bidTime) {
		this.bidTime = bidTime;
	}

	public long getPayTime() {
		return payTime;
	}
	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}
	
	public long getSignTime() {
		return signTime;
	}
	public void setSignTime(long signTime) {
		this.signTime = signTime;
	}
}
