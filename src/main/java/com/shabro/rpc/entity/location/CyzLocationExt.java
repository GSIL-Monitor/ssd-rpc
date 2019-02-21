package com.shabro.rpc.entity.location;

import java.util.Date;

import com.shabro.rpc.entity.rpc.RpcCyzGps;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 类描述：执行订单过程中上报的GPS轨迹
 */
public class CyzLocationExt {
	@Indexed
	private String city;
	private String addDetail;
	private Double lon; //经度
	private Double lat; //纬度
	private String locationDate;
	private long locateTime;
	private double[] location;

	public CyzLocationExt(String  strDate, double[] location,  RpcCyzGps loc) {
		this.city = loc.getCity();
		this.addDetail = loc.getAddDetail();
		this.lon = loc.getLon();
		this.lat = loc.getLat();

		this.locationDate = strDate;
		this.locateTime = loc.getLocateTime();
		this.location = location;
	}

	public CyzLocationExt() {
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

	public long getLocateTime() {
		return locateTime;
	}
	public void setLocateTime(long locateTime) {
		this.locateTime = locateTime;
	}

	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}

}
