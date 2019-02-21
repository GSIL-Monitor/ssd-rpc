package com.shabro.rpc.controller.location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shabro.comm.util.BaseUtil;

import com.shabro.rpc.entity.location.CyzLocationExt;
import com.shabro.rpc.entity.location.CyzLocation;
import com.shabro.rpc.entity.location.CyzGps;
import com.shabro.rpc.entity.order.Order;
import com.shabro.rpc.service.location.CyzLocationService;
import com.shabro.rpc.service.location.CyzGpsService;
import com.shabro.rpc.service.order.OrderService;

@Controller
public class CyzLocationController{
	
	@Autowired
	private CyzLocationService locationService;

	@Autowired
	private CyzGpsService gpsService;

	@Autowired
	private OrderService orderService;

	// 暂时都按空车上报了, 后续需要考虑按是否有执行订单分开记录位置信息
	@RequestMapping(method = RequestMethod.GET, value = "/cyz/saveLocation")
	@ResponseBody
	public Map<String, String> saveCyzLocation(String cyzId, String lon, String lat,
								String city, String detail, int orderState){
		Map<String, String> payload = new HashMap<String, String>();

		Double lon2 = new Double(lon);
		Double lat2 = new Double(lat);
		double[] dCordiante = new double[2];
		dCordiante[0] = lon2;
		dCordiante[1] = lat2;

		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String locationDate = format.format(now);

		CyzLocationExt loc = new CyzLocationExt();
		loc.setLon(lon2);
		loc.setLat(lat2);
		loc.setLocation(dCordiante);
		loc.setCity(city);
		loc.setAddDetail(detail);
		loc.setLocationDate(locationDate);
		loc.setLocateTime(now.getTime() / 1000);

		//System.out.println("cyzId="+cyzId+"lon="+lon+"lat="+lat+"city="+city+"detail="+detail);

		List<Order> lstOrder = null;
		try {
			lstOrder = orderService.getExecuteOrderByCyzId(cyzId);
			if (lstOrder != null && lstOrder.size() > 0) {
				for (Order order : lstOrder) {
					gpsService.saveCyzGps(order.getCyzId(), order.getOrderId(), orderState, loc);
					System.out.println("saveCyzLocation gpsTrack orderId="+order.getOrderId()+" lon="+lon+" lat="+lat+" time="+locationDate);
				}
			}
			if(BaseUtil.stringNotNull(cyzId)){
				CyzLocation location = new CyzLocation();
				locationDate = format2.format(now);

				location.setCyzId(cyzId);
				location.setLon(lon2);
				location.setLat(lat2);
				location.setLocation(dCordiante);
				location.setCity(city);
				location.setAddDetail(detail);
				location.setLocationDate(locationDate);
				location.setEqType("0");

				locationService.saveCyzLocation(location);
				payload.put("state", "0");
				payload.put("message", "保存司机位置成功");
				return payload;
			}
		} catch (Exception e) {
			e.printStackTrace();
			payload.put("message", e.getMessage());
		}

		payload.put("state", "1");
		return payload;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cyz/getGpsTrack")
	@ResponseBody
	public Map<String, Object> queryCyzTrack(String cyzId, String orderId) {
		Map<String, Object> payload = new HashMap<String, Object>();
		List<CyzGps> lst = null;
		if (BaseUtil.stringNotNull(cyzId)) {
			if (BaseUtil.stringNotNull(orderId)) {
				lst = gpsService.getCyzGpsByCyzIdOrderId(cyzId, orderId);
			} else {
				lst = gpsService.getCyzGpsByCyzId(cyzId);
			}
		}
		payload.put("CyzGps", lst);
		payload.put("state", "0");
		payload.put("message", "查询成功");
		return payload;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cyz/queryOrder")
	@ResponseBody
	public Map<String, Object> queryCyzOrder(String cyzId, String orderId) {
		Map<String, Object> payload = new HashMap<String, Object>();
		if (BaseUtil.stringNotNull(orderId)) {
			Order order = orderService.getOrderById(orderId);
			if (order != null) {
				payload.put("order", order);
				payload.put("message", "查询成功");
			} else {
				payload.put("message", "没有数据");
			}
		} else if (BaseUtil.stringNotNull(cyzId)) {
			List<Order> lst = orderService.getOrderByCyzId(cyzId);
			payload.put("orderList", lst);
			payload.put("message", "查询成功");
		}

		payload.put("state", "0");
		return payload;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cyz/updateOrderState")
	@ResponseBody
	public Map<String, Object> updateOrderState(String cyzId, String orderId, int orderState, long time){
		Map<String, Object> payload = new HashMap<String, Object>();
		if (orderState > 1 &&  orderState < 7 && BaseUtil.stringNotNull(cyzId) && BaseUtil.stringNotNull(orderId)) {
			try {
				if (orderState == 3) {
					orderService.updateOrderState(cyzId, orderId, orderState);
				} else if (orderState == 2) {
					orderService.updateOrderStateAndPayTime(cyzId, orderId, orderState, time);
				} else {
					orderService.updateOrderStateAndSignTime(cyzId, orderId, orderState, time);
				}
				payload.put("state", "0");
				payload.put("message", "成功");
				return payload;
			} catch (Exception e) {
				e.printStackTrace();
				payload.put("message", e.getMessage());
			}
		}

		payload.put("state", "1");
		return payload;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cyz/getByCity")
	@ResponseBody
	public Map<String, Object> getCyzByCity(String city){
		Map<String, Object> payload = new HashMap<String, Object>();
		if(BaseUtil.stringNotNull(city)){
			//Map<String, CyzLocation> locationList = locationService.getCyzLocationByCity(city);
			//payload.put("list", locationList);
			payload.put("state", "0");
			payload.put("message", "查询成功");
			
		}else{
			payload.put("state", "1");
			payload.put("message", "查询失败");
		}
		return payload;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cyz/getById")
	@ResponseBody
	public Map<String, Object> getCyzById(String cyzId){
		Map<String, Object> payload = new HashMap<String, Object>();
		if(BaseUtil.stringNotNull(cyzId)){
			CyzLocation location = locationService.getCyzLocationById(cyzId);
			payload.put("location", location);
			payload.put("state", "0");
			payload.put("message", "查询成功");
			
		}else{
			payload.put("state", "1");
			payload.put("message", "查询失败");
		}
		return payload;
	}

//	@RequestMapping(method = RequestMethod.GET, value = "/cyz/saveGpsTrackById")
//	@ResponseBody
//	public Map<String, Object> saveGpsTrackById(String cyzId){
//		Map<String, Object> payload = new HashMap<String, Object>();
//		if(BaseUtil.stringNotNull(cyzId)){
//
//			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date now = new Date();
//			String locationDate = format.format(now);
//			Double lon = new Double(106.586); // 106.58628
//			Double lat = new Double(29.5370); // 29.537097
//			CyzLocation location = new CyzLocation();
//
//			//106.58628,29.537097
//			location.setCyzId(cyzId);
//			location.setLon(lon);
//			location.setLat(lat);
//			location.setCity("南岸区");//南岸区
//
//			location.setAddDetail("detail");
//			location.setLocationDate(locationDate);
//			location.setEqType("0");
//
//			double[] dCordiante = new double[2];
//			dCordiante[0] = lon;
//			dCordiante[1] = lat;
//			location.setLocation(dCordiante);
//
//			//CyzLocationExt locExt = new CyzLocationExt(now, location);
//			//gpsService.saveCyzGps(cyzId, locExt);
//
//			gpsService.getCyzGpsByCyzId(cyzId);
//
//			payload.put("location", location);
//			payload.put("state", "0");
//			payload.put("message", "查询成功");
//
//		}else{
//			payload.put("state", "1");
//			payload.put("message", "查询失败");
//		}
//		return payload;
//	}
//
//	@RequestMapping(method = RequestMethod.GET, value = "/cyz/saveCyzOrder")
//	@ResponseBody
//	public String saveCyzOrder(String cyzId){
//		Order data = new Order();
//		data.setOrderId("B201810101013528220"); // B201810101015428220 B201810101013528220
//		data.setOrderState(1);
//		data.setCyzId("81191c90-3536-4030-bc57-090df65ac5c9"); // 78c82cbf-1185-4144-84e8-deac80e642eb
//		data.setCyzTel("13368073015");
//		data.setFbzId("1e91f10d-9eef-43a7-8622-d9a00aa22035");
//		data.setFbzTel("18580591317");
//
//		Date now = new Date();
//
////		data.setCreateTime(now.getTime()/1000);
////		data.setBidTime(now.getTime()/1000 + 60);
////		orderService.saveOrder(data);
//
//		orderService.udpateOrderStateAndSignTime("81191c90-3536-4030-bc57-090df65ac5c9","B201810101015428220", 2, now.getTime()/1000);
//		orderService.udpateOrderStateAndSignTime("81191c90-3536-4030-bc57-090df65ac5c9","B201810101013528220", 2, now.getTime()/1000);
//
////		Order order = orderService.getOrderById("B201810101013528220");
////		System.out.println(" bid=" +order.getOrderId()+ " create=" +order.getCreateTime()+ " bid=" +order.getBidTime()+ " sign=" +order.getSignTime() );
//
//		List<String> lstOrderId = new ArrayList<>();
//		lstOrderId.add("B201810101013528220");
//		lstOrderId.add("B201810101015428220");
//		List<Order> lst = orderService.getOrderByIdList(lstOrderId);
//
//		lst = orderService.getExecuteOrderByCyzId("78c82cbf-1185-4144-84e8-deac80e642eb");
//
//		return "OK";
//	}
}
