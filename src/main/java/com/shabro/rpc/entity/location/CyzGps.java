package com.shabro.rpc.entity.location;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shabro.rpc.entity.mongodb.BasicEntity;

/**
 * 类描述：承运者GPS轨迹
 */
@Document 
public class CyzGps extends BasicEntity {
	@Indexed(name="cyzId", unique=false, dropDups=false) // value= IndexDirection, //@Indexed
	private String cyzId;
	@Indexed(name="orderId", unique=false, dropDups=false)
	private String orderId;
	@Indexed(name="orderState", unique=false, dropDups=false)
	private Integer orderState;
	private List<CyzLocationExt> gpsTrack;

	public CyzGps (String cyzId, String orderId, Integer orderState) {
		this.cyzId = cyzId;
		this.orderId = orderId;
		this.orderState = orderState;
	}

	public CyzGps() {
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
	
	public List<CyzLocationExt> getGpsTrack() {
		return gpsTrack;
	}
	public void setGpsTrack(List<CyzLocationExt> gpsTrack) {
		this.gpsTrack = gpsTrack;
	}
}
