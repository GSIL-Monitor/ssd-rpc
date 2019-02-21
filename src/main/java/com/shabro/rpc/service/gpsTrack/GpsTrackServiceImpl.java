package com.shabro.rpc.service.gpsTrack;


import com.shabro.rpc.entity.location.*;
import com.shabro.rpc.entity.rpc.RpcCyzGps;
import com.shabro.rpc.entity.rpc.RpcCyzGpsTrack;
import com.shabro.rpc.entity.rpc.RpcCyzLocation;
import com.shabro.rpc.entity.rpc.RpcCyzLocationExt;
import com.shabro.rpc.service.location.CyzGpsService;
import com.shabro.rpc.service.location.CyzLocationService;
import com.shabro.rpc.entity.rpc.RpcResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.alibaba.dubbo.config.annotation.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: GpsTrackServiceImpl
 * @Description:
 * @author
 * @date
 *
 */
@Service
public class GpsTrackServiceImpl implements GpsTrackService {
	private static Logger log = Logger.getLogger("locus");

	@Autowired
	private CyzGpsService gpsService;

	@Autowired
	private CyzLocationService locationService;

	// ============处理司机最新定位信息============
	public RpcResult<String> saveCyzLocation(RpcCyzLocation location) {
		CyzLocation loc = initCyzLocation(location);
		locationService.saveCyzLocation(loc);
		return RpcResult.newSuccess("saveCyzLocation success");
	}

	// 按定承运者ID查询承运者的最新定位信息
	public RpcResult<RpcCyzLocation> getCyzLocationById(String cyzId) {
		RpcResult<RpcCyzLocation> result = new RpcResult<>();
		CyzLocation loc = locationService.getCyzLocationById(cyzId);
		if (loc != null) {
			//System.out.println(" cyzId=" +loc.getCyzId()+ " locationDate=" +loc.getLocationDate());
			return RpcResult.newSuccess(initRpcCyzLocation(loc));
		} else {
			return RpcResult.newFailure(-1, "getCyzLocationById fail");
		}
	}

	// 按定承运者列表查询承运者的最新定位信息
	public RpcResult<List<RpcCyzLocation>> getCyzLocationByIdList(List<String> cyzIdList) {
		RpcResult<List<RpcCyzLocation>> result = new RpcResult<>();
		List<CyzLocation> lst = locationService.getCyzLocationByIdList(cyzIdList);

		List<RpcCyzLocation> lstLoc = new ArrayList<>();
		for (CyzLocation loc : lst) {
			lstLoc.add(initRpcCyzLocation(loc));
		}

		result.setCode(0);
		result.setMessage("success");
		result.setObject(lstLoc);
		return result;
	}

	// 按定位城市查询承运者的最新定位信息
	public RpcResult<List<RpcCyzLocation>> getCyzLocationByCity(String city) {
		RpcResult<List<RpcCyzLocation>> result = new RpcResult<>();
		List<CyzLocation> lst = locationService.getCyzLocationByCity(city);

		List<RpcCyzLocation> lstLoc = new ArrayList<>();
		for (CyzLocation loc : lst) {
			lstLoc.add(initRpcCyzLocation(loc));
		}

		result.setCode(0);
		result.setMessage("success");
		result.setObject(lstLoc);
		return result;
	}

	// 查询周边承运者最近一年内的定位信息
	public RpcResult<List<RpcCyzLocation>> getCyzCircleNearSortbyTime(String city, int limit,
											String longitude, String latitude, double maxDistance) {
		RpcResult<List<RpcCyzLocation>> result = new RpcResult<>();
		List<CyzLocation> lst = locationService.getCyzCircleNearSortbyTime(city, limit, longitude, latitude, maxDistance);

		List<RpcCyzLocation> lstLoc = new ArrayList<>();
		for (CyzLocation loc : lst) {
			lstLoc.add(initRpcCyzLocation(loc));
		}

		result.setCode(0);
		result.setMessage("success");
		result.setObject(lstLoc);
		return result;
	}

	private CyzLocation initCyzLocation(RpcCyzLocation rpcLoc) {
		CyzLocation loc = new CyzLocation();
		loc.setCyzId(rpcLoc.getCyzId());
		loc.setCity(rpcLoc.getCity());
		loc.setAddDetail(rpcLoc.getAddDetail());
		loc.setLon(rpcLoc.getLon());
		loc.setLat(rpcLoc.getLat());
		loc.setLocationDate(rpcLoc.getLocationDate());
		loc.setEqType(rpcLoc.getEqType());
		loc.setLocation(rpcLoc.getLocation());
		return loc;
	}


	// ============处理接单司机轨迹============
	// 保存订单执行过程中的承运者定位信息
	public RpcResult<String> saveGpsTrack(RpcCyzGps cyzGps) {
		if (cyzGps == null) {
			return RpcResult.newFailure(-1, "saveGpsTrack cyzGps is null");
		}

		RpcResult<String> result = new RpcResult<>();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(cyzGps.getLocateTime() * 1000);
		String locationDate = format.format(date);

		double[] dCordiante = new double[2];
		dCordiante[0] = cyzGps.getLon();
		dCordiante[1] = cyzGps.getLat();

		CyzLocationExt locExt = new CyzLocationExt(locationDate, dCordiante, cyzGps);
		try {
			gpsService.saveCyzGps(cyzGps.getCyzId(), cyzGps.getOrderId(), cyzGps.getOrderState(), locExt);
		} catch (Exception e) {
			log.info("saveGpsTrack fail, cyzId="+cyzGps.getCyzId()+" orderId="+cyzGps.getOrderId()+" errormsg="+e.getMessage());
		}

		result.setCode(0);
		result.setMessage("success");
		//result.setObject("saveGpsTrack");
		return result;
	}

	// 按承运者ID查询正执行订单承运者的定位信息
	public RpcResult<List<RpcCyzGpsTrack>> getExecuteCyzGpsTrack(String cyzId) {
		RpcResult<List<RpcCyzGpsTrack>> result = new RpcResult<>();
		List<RpcCyzGpsTrack> lstGpsTrack = getExecuteCyzGpsTrack(cyzId, null);
		if (lstGpsTrack.size() > 0) {
			result.setCode(0);
			result.setMessage("success");
			result.setObject(lstGpsTrack);
			return result;
		} else {
			return RpcResult.newFailure(-1, "getExecuteCyzGpsTrack no data");
		}
	}

	// 按订单号查询正执行订单承运者的定位信息
	public RpcResult<RpcCyzGpsTrack> getExecuteCyzGpsTrackByOrderId(String cyzId, String orderId) {
		RpcResult<RpcCyzGpsTrack> result = new RpcResult<>();
		List<RpcCyzGpsTrack> lstGpsTrack = getExecuteCyzGpsTrack(cyzId, orderId);
		if (lstGpsTrack.size() > 0) {
			RpcCyzGpsTrack gpsTrack = lstGpsTrack.get(0);
			result.setCode(0);
			result.setMessage("success");
			result.setObject(gpsTrack);
			return result;
		} else {
			return RpcResult.newFailure(-1, "getExecuteCyzGpsTrackByOrderId no data");
		}
	}

	// 按承运者ID查询承运者的定位信息
	public RpcResult<List<RpcCyzGpsTrack>> getGpsTrack(String cyzId) {
		RpcResult<List<RpcCyzGpsTrack>> result = new RpcResult<>();
		List<RpcCyzGpsTrack> lstGpsTrack = getGpsTrack(cyzId, null);
		if (lstGpsTrack.size() > 0) {
			result.setCode(0);
			result.setMessage("success");
			result.setObject(lstGpsTrack);
			return result;
		} else {
			return RpcResult.newFailure(-1, "getGpsTrack no data");
		}
	}

	// 按订单号查询承运者的定位信息
	public RpcResult<RpcCyzGpsTrack> getGpsTrackByOrderId(String cyzId, String orderId) {
		RpcResult<RpcCyzGpsTrack> result = new RpcResult<>();
		List<RpcCyzGpsTrack> lstGpsTrack = getGpsTrack(cyzId, orderId);
		if (lstGpsTrack.size() > 0) {
			RpcCyzGpsTrack gpsTrack = lstGpsTrack.get(0);
			result.setCode(0);
			result.setMessage("success");
			result.setObject(gpsTrack);
			return result;
		} else {
			return RpcResult.newFailure(-1, "getGpsTrackByOrderId no data");
		}
	}

	private  List<RpcCyzGpsTrack> getExecuteCyzGpsTrack(String cyzId, String orderId) {
		List<RpcCyzGpsTrack> lstGpsTrack = new ArrayList<>();
		List<CyzGps> lst = null;
		if (orderId != null) {
			lst = gpsService.getExecuteCyzGpsByCyzIdOrderId(cyzId, orderId);
		} else {
			lst = gpsService.getExecuteCyzGpsByCyzId(cyzId);
		}
		if (lst.isEmpty()) {
			return lstGpsTrack;
		}

		return convertGpsTrack(lst);
	}

	private  List<RpcCyzGpsTrack> getGpsTrack(String cyzId, String orderId) {
		List<RpcCyzGpsTrack> lstGpsTrack = new ArrayList<>();
		List<CyzGps> lst = null;
		if (orderId != null) {
			lst = gpsService.getCyzGpsByCyzIdOrderId(cyzId, orderId);
		} else {
			lst = gpsService.getCyzGpsByCyzId(cyzId);
		}
		if (lst.isEmpty()) {
			return lstGpsTrack;
		}

		return convertGpsTrack(lst);
	}

	private  List<RpcCyzGpsTrack> convertGpsTrack(List<CyzGps> lst) {
		List<RpcCyzGpsTrack> lstGpsTrack = new ArrayList<>();
		if (lst.isEmpty())  {
			return lstGpsTrack;
		}

		for (CyzGps gps : lst) {
			RpcCyzGpsTrack gpsTrack = new RpcCyzGpsTrack();
			gpsTrack.setCyzId(gps.getCyzId());
			gpsTrack.setOrderId(gps.getOrderId());
			gpsTrack.setOrderState(gps.getOrderState());

			RpcCyzLocationExt rpcExt = null;
			List<RpcCyzLocationExt> rpcGpsTrack = new ArrayList<>();
			List<CyzLocationExt> gpsTrackTmp = gps.getGpsTrack();
			for (CyzLocationExt ext : gpsTrackTmp) {
				rpcExt = new RpcCyzLocationExt();
				rpcExt.setCity(ext.getCity());
				rpcExt.setAddDetail(ext.getAddDetail());
				rpcExt.setLon(ext.getLon());
				rpcExt.setLat(ext.getLat());
				rpcExt.setLocationDate(ext.getLocationDate());
				rpcExt.setLocateTime(ext.getLocateTime());
				rpcExt.setLocation(ext.getLocation());
				rpcGpsTrack.add(rpcExt);
			}

			gpsTrack.setGpsTrack(rpcGpsTrack);
			lstGpsTrack.add(gpsTrack);
		}

		return lstGpsTrack;
	}

	private RpcCyzLocation initRpcCyzLocation(CyzLocation loc) {
		RpcCyzLocation rpcLoc = new RpcCyzLocation();
		rpcLoc.setCyzId(loc.getCyzId());
		rpcLoc.setCity(loc.getCity());
		rpcLoc.setAddDetail(loc.getAddDetail());
		rpcLoc.setLon(loc.getLon());
		rpcLoc.setLat(loc.getLat());
		rpcLoc.setLocationDate(loc.getLocationDate());
		rpcLoc.setEqType(loc.getEqType());
		rpcLoc.setLocation(loc.getLocation());
		return rpcLoc;
	}
}
