package com.shabro.rpc.entity.rpc;

//import com.shabro.rpc.entity.location.CyzLocation;
import com.shabro.rpc.entity.rpc.RpcEntity;

public class RpcCyzLocation extends RpcEntity {
	private String cyzId;
	private String city;
	private String addDetail;
	private Double lon; //经度
	private Double lat; //纬度
	private String locationDate;
	private String eqType;
	private double[] location;

	public RpcCyzLocation() {
	}

	public String getCyzId() {
		return cyzId;
	}
	public void setCyzId(String cyzId) {
		this.cyzId = cyzId;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAddDetail() {
		return addDetail;
	}
	public void setAddDetail(String addDetail) {
		this.addDetail = addDetail;
	}

	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getLocationDate() {
		return locationDate;
	}
	public void setLocationDate(String locationDate) {
		this.locationDate = locationDate;
	}

	public String getEqType() {
		return eqType;
	}
	public void setEqType(String eqType) {
		this.eqType = eqType;
	}

	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
}
