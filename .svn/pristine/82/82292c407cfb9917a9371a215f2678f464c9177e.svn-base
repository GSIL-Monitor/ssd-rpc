package com.shabro.rpc.service.location;

import java.util.List;
import java.util.Map;

import com.shabro.rpc.entity.location.CyzLocation;

public interface CyzLocationService {

	public List<CyzLocation> getCyzLocationByCity(String city);

	//public void udpateCyzLocationByCity(String city);

	public void saveCyzLocation(CyzLocation cyzLocation);

	public void removeCyzLocation(CyzLocation cyzLocation);
	
	public CyzLocation getCyzLocationById(String cyzId);
	
	public List<CyzLocation> getCyzLocationByIdList(List<String> cyzIdList);

	public List<CyzLocation> getCyzCircleNearSortbyTime(String city, int limit, String longitude, String latitude, double maxDistance);

	//public void ensureIndexOfCyzLocation();
}
