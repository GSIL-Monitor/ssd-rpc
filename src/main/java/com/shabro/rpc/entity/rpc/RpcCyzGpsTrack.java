package com.shabro.rpc.entity.rpc;

import java.util.List;


/**
 * 类描述：承运者GPS轨迹
 */
public class RpcCyzGpsTrack extends RpcEntity {
	private String cyzId;
	private String orderId;
	private Integer orderState;
	private List<RpcCyzLocationExt> gpsTrack;

	public RpcCyzGpsTrack() {
	}

	public String getCyzId() {
		return cyzId;
	}
	public void setCyzId(String cyzId) {
		this.cyzId = cyzId;
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
	
	public List<RpcCyzLocationExt> getGpsTrack() {
		return gpsTrack;
	}
	public void setGpsTrack(List<RpcCyzLocationExt> gpsTrack) {
		this.gpsTrack = gpsTrack;
	}
}
