package com.shabro.rpc.service.location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Service;

import com.shabro.comm.util.BaseUtil;
import com.shabro.rpc.util.G;
import com.shabro.rpc.dao.location.CyzLocationRepositoryOld;
import com.shabro.rpc.entity.location.CyzLocation;


@Service
public class CyzLocationServiceImpl implements CyzLocationService {

	@Autowired
	private CyzLocationRepositoryOld<CyzLocation> cyzLocationRes;

	public List<CyzLocation> getCyzLocationByCity(String city) {
		Query query = new Query();
		if(BaseUtil.stringNotNull(city) && !city.equals(G.CITY_ALL)) {
			query.addCriteria(Criteria.where("city").is(city));
		}

		List<CyzLocation> ret = cyzLocationRes.findByQuery(query);
		return ret;
	}

//	public void udpateCyzLocationByCity(String city) {
//		Query query = new Query();
//		List<CyzLocation>  cyzList = cyzLocationRes.findByQuery(query);
//		for(CyzLocation l : cyzList){
//			double[] dArray = {l.getLon(),l.getLat()};
//			Query queryUpdate = new Query();
//			queryUpdate.addCriteria(Criteria.where("cyzId").is(l.getCyzId()));
//
//			Update update = new Update();
//			update.set("location",dArray);
//			cyzLocationRes.updateFirst(queryUpdate,update);
//		}
//	}


	public void saveCyzLocation(CyzLocation cyzLocation) {
		CyzLocation temp = getCyzLocationById(cyzLocation.getCyzId());
		if(BaseUtil.objectNotNull(temp)){
			cyzLocation.setId(new ObjectId(temp.getId()));
		}

		cyzLocationRes.save(cyzLocation);
	}

	public void removeCyzLocation(CyzLocation cyzLocation){
		cyzLocationRes.remove(cyzLocation);
	}

	public CyzLocation getCyzLocationById(String cyzId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId));
		List<CyzLocation>  ret = cyzLocationRes.findByQuery(query);
	
		if(ret.isEmpty()){
			return null;
		}

		CyzLocation retLoc = ret.get(0);
		return retLoc;
	}

	public List<CyzLocation> getCyzLocationByIdList(List<String> cyzIdList) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").in(cyzIdList.toArray()));
		List<CyzLocation> ret = cyzLocationRes.findByQuery(query);
		return ret;
	}

	public List<CyzLocation> getCyzCircleNearSortbyTime(String city, int limit,
								String longitude, String latitude, double maxDistance){
		Double lon = Double.valueOf(longitude);
		Double lat = Double.valueOf(latitude);
		Point point = new Point(lon.doubleValue(), lat.doubleValue());
		Query query = new Query(Criteria.where("location").near(point).maxDistance(maxDistance/111.12));
		if (null != city && city.equals("")){
			query.addCriteria(Criteria.where("city").is(city));
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = format.format(BaseUtil.getNearDay(-365));
		query.addCriteria(Criteria.where("locationDate").gte(strDate));

		query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "locationDate")));
		query.limit(limit);

		List<CyzLocation> lst = cyzLocationRes.findByQuery(query);
		return lst;
	}

	public void ensureIndexOfCyzLocation() {
		cyzLocationRes.getMongoTemplate().indexOps("cyzLocation").ensureIndex(new GeospatialIndex("location"));
	}
}
