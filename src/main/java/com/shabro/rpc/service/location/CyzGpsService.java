package com.shabro.rpc.service.location;

import java.util.List;

import com.shabro.rpc.entity.location.CyzGps;
import com.shabro.rpc.entity.location.CyzLocationExt;

public interface CyzGpsService {
	public List<CyzGps> getCyzGpsByCyzId(String cyzId);

	public List<CyzGps> getCyzGpsByCyzIdOrderId(String cyzId, String orderId);

	public List<CyzGps> getExecuteCyzGpsByCyzId(String cyzId);

	public List<CyzGps> getExecuteCyzGpsByCyzIdOrderId(String cyzId, String orderId);

	public void saveCyzGps(String cyzId, String orderId, Integer orderState, CyzLocationExt cyzLoc);

	public void removeCyzGps(CyzGps cyzGps);
}
