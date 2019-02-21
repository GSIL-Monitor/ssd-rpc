package com.shabro.rpc.service.gpsTrack;

import com.shabro.rpc.entity.rpc.RpcCyzGps;
import com.shabro.rpc.entity.rpc.RpcCyzGpsTrack;
import com.shabro.rpc.entity.rpc.RpcCyzLocation;
import com.shabro.rpc.entity.rpc.RpcResult;

import java.util.List;

/**
 * 
 * @ClassName: GpsTrackService
 * @Description: 处理接单司机订单执行过程中的轨迹信息
 * @author
 * @date
 *
 */
//@Service("GpsTrackService")
public interface GpsTrackService {
	// 保存承运者的最新定位信息
	RpcResult<String> saveCyzLocation(RpcCyzLocation loc);

	// 查询承运者的最新定位信息
	RpcResult<RpcCyzLocation> getCyzLocationById(String cyzId);
	RpcResult<List<RpcCyzLocation>> getCyzLocationByIdList(List<String> cyzIdList);

	// 按定位城市查询承运者的最新定位信息
	RpcResult<List<RpcCyzLocation>> getCyzLocationByCity(String city) ;

	// 查询周边承运者最近一年内的定位信息
	RpcResult<List<RpcCyzLocation>> getCyzCircleNearSortbyTime(String city, int limit,
								String longitude, String latitude, double maxDistance);


	// 保存订单执行过程中的承运者定位信息
	RpcResult<String> saveGpsTrack(RpcCyzGps cyzGps);

	// 按承运者ID查询正执行订单承运者的定位信息
	RpcResult<List<RpcCyzGpsTrack>> getExecuteCyzGpsTrack(String cyzId);
	// 按订单号查询正执行订单承运者的定位信息
	RpcResult<RpcCyzGpsTrack> getExecuteCyzGpsTrackByOrderId(String cyzId, String orderId);


	// 按承运者ID查询承运者的定位信息
	RpcResult<List<RpcCyzGpsTrack>> getGpsTrack(String cyzId);
	// 按订单号查询承运者的定位信息
	RpcResult<RpcCyzGpsTrack> getGpsTrackByOrderId(String cyzId, String orderId);
}
