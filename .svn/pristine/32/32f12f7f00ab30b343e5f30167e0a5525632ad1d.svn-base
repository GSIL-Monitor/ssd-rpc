package com.shabro.rpc.entity.location;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shabro.rpc.entity.mongodb.BasicEntityOld;

/**
 * 类描述：司机上报的最新GPS定位信息
 */
@Document 
public class CyzLocation extends BasicEntityOld {
	@Indexed
	private String cyzId;
	private String city;
	private Double lon; //经度
	private Double lat; //纬度
	private String addDetail;
	private String locationDate;
	private String eqType;
	private double[] location;


	public CyzLocation() {
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

	public String getAddDetail() {
		return addDetail;
	}
	public void setAddDetail(String addDetail) {
		this.addDetail = addDetail;
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
