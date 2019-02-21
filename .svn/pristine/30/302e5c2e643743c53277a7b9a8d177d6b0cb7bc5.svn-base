package com.shabro.rpc.service.location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shabro.rpc.entity.order.Order;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shabro.comm.util.BaseUtil;
import com.shabro.rpc.util.G;
import com.shabro.rpc.entity.location.CyzLocation;
import com.shabro.rpc.entity.location.CyzGps;
import com.shabro.rpc.entity.location.CyzLocationExt;
import com.shabro.rpc.dao.location.CyzGpsRepository;

import com.caucho.hessian.io.ThrowableSerializer;

@Service
public class CyzGpsServiceImpl implements CyzGpsService{
	private static Logger log = Logger.getLogger("locus");

	@Autowired
	private CyzGpsRepository<CyzGps> cyzGpsRepo;

	public void saveCyzGps(String cyzId, String orderId, Integer orderState, CyzLocationExt cyzLoc) {
		Update update = new Update();
		Query query = new Query();

		log.info("cyzId="+cyzId+" orderId="+orderId+" lon="+cyzLoc.getLon()+" lat="+cyzLoc.getLat()
				+" city="+cyzLoc.getCity()+" detail="+cyzLoc.getAddDetail()+" time="+cyzLoc.getLocationDate());

		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderId").is(orderId));
		try {
			List<CyzGps> lst = cyzGpsRepo.findByQuery(query);
			if (lst.isEmpty()) {
				CyzGps gps = new CyzGps(cyzId, orderId, orderState);
				List<CyzLocationExt> lstGps = new ArrayList<>();
				lstGps.add(cyzLoc);
				gps.setGpsTrack(lstGps);

				cyzGpsRepo.save(gps);
				return;
			} else {
				CyzGps gps = lst.get(0);
				// 比较 orderState 后更新
				if (gps != null && !gps.getOrderState().equals(orderState)) {
					update.set("orderState", orderState);
				}
			}

			update.addToSet("gpsTrack", cyzLoc);
			cyzGpsRepo.updateList(query, update);
		} catch (Exception e) {
			log.info("saveCyzGps fail, cyzId="+cyzId+" orderId="+orderId+" errormsg="+e.getMessage());
		}
		return;
	}


	public void removeCyzGps(CyzGps cyzGps) {
		cyzGpsRepo.remove(cyzGps);
	}

	public List<CyzGps> getCyzGpsByCyzId(String cyzId) {
		Query query = new Query();

//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String strDate = "2018-05-18 16:53:30";//format.format(date);
//		//query.addCriteria(Criteria.where("cyzId").is(cyzId));
//		//query.addCriteria(Criteria.where("gpsTrack.$.cyzId").is(cyzId));
//		//query.addCriteria(Criteria.where("gpsTrack.$.locationDate").gte(strDate));
//
//		DBObject dbObject = new BasicDBObject();
//		//dbObject.put("gpsTrack.city", "南岸区");
//		dbObject.put("gpsTrack.locationDate", new BasicDBObject("$gte", "2018052209"));
//		//dbObject.put("gpsTrack.locationDate", {"$gte": '2018-05-21 13:54:00'});
//		List<DBObject> lstAgg =  cyzGpsRepo.findAggregateUnwindMatch("cyzGps", "$gpsTrack", dbObject, null);
//
//		try {
//			Date now = format.parse(strDate);//new Date();
//			query.addCriteria(Criteria.where("cyzId").is(cyzId));
//					//.and("gpsTrack.$.cyzLocationExt.city").is("南岸区")
//					//.and("gpsTrack.$.cyzLocationExt.locateTime").gte(now));
//		}catch (Exception e) {
//			;
//		}

//		//query.addCriteria(Criteria.where("cyzId").is(cyzId).and("gpsTrack.cyzId").is(cyzId).and("gpsTrack.locationDate").gte(strDate));
//		//query.addCriteria(Criteria.where("cyzId").is(cyzId).and("gpsTrack.cyzId").is(cyzId));

		query.addCriteria(Criteria.where("cyzId").is(cyzId));

		List<CyzGps> lst = cyzGpsRepo.findByQuery(query);
		return lst;
	}

	public List<CyzGps> getCyzGpsByCyzIdOrderId(String cyzId, String orderId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId));
		query.addCriteria(Criteria.where("orderId").is(orderId));

		List<CyzGps> lst = cyzGpsRepo.findByQuery(query);
		return lst;
	}

	public List<CyzGps> getExecuteCyzGpsByCyzId(String cyzId) {
		List<Integer> lstState = new ArrayList<>();
		// 订单状态: 1.待支付 2.待输回执码-装货(运输) 3.待货主确认-交货完成，4.运输结束-完成订单
		lstState.add(G.ORDER_STATE_WAITPAY);
		lstState.add(G.ORDER_STATE_RETURNCODE);
		//lstState.add(G.ORDER_STATE_CONFIRM);

		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId));
		query.addCriteria(Criteria.where("orderState").in(lstState.toArray()));

		List<CyzGps> lst = cyzGpsRepo.findByQuery(query);
		return lst;
	}

	public List<CyzGps> getExecuteCyzGpsByCyzIdOrderId(String cyzId, String orderId) {
		List<Integer> lstState = new ArrayList<>();
		// 订单状态: 1.待支付 2.待输回执码-装货(运输) 3.待货主确认-交货完成，4.运输结束-完成订单
		lstState.add(G.ORDER_STATE_WAITPAY);
		lstState.add(G.ORDER_STATE_RETURNCODE);
		//lstState.add(G.ORDER_STATE_CONFIRM);

		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId));
		query.addCriteria(Criteria.where("orderId").is(orderId));
		query.addCriteria(Criteria.where("orderState").in(lstState.toArray()));

		List<CyzGps> lst = cyzGpsRepo.findByQuery(query);
		return lst;
	}

//	public void ensureIndexOfCyzLocation() {
//		cyzGpsRepo.ensureIndexOfCyzLocation();
//	}
}
