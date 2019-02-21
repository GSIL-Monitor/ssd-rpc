package com.shabro.rpc.dao.mongodb;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.shabro.rpc.entity.mongodb.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.data.mongodb.core.query.Order;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;
//import com.yunlihui.ssd.entity.mongodb.Sequence;


public class RepositorySupportOld<E extends Entity> implements RepositoryOld<E> {

	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public RepositorySupportOld() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class<E>) params[0];
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * 插入文档到集合中，如果记录不存在则插入，如果记录存在则忽略
	 */
	public void insert(E entity) {
		this.mongoTemplate.insert(entity);
	}

	public void insertAll(Collection<E> list) {
		this.mongoTemplate.insert(list, entityClass);
	}

	/**
	 * 是在文档不存在时插入，存在时则是更新
	 */
	public void save(E entity) {
		this.mongoTemplate.save(entity);
	}

	public void remove(E entity) {
		this.mongoTemplate.remove(entity);
	}

	public void removeByQuery(Query query) {
		this.mongoTemplate.remove(query, entityClass);
	}

	public List<E> findAll() {
		return this.mongoTemplate.findAll(entityClass);
	}

	public E findById(Object id) {
		return this.mongoTemplate.findById(id, entityClass);
	}

	public E findAndModify(Query query, Update update, FindAndModifyOptions options) {
		return this.mongoTemplate.findAndModify(query, update, options, entityClass);
	}

//	public Page<E> findAll(Pageable pageable) {
//		return findAll(new Query(), pageable);
//	}
//
//	public Page<E> findAll(Query query, Pageable pageable) {
//		return findAll(query, pageable, entityClass);
//	}

//	protected <T> Page<T> findAll(Query query, Pageable pageable, Class<T> clz) {
//		Long count = this.mongoTemplate.count(query, clz);
//		List<T> list = findByQuery(QueryUtils.applyPagination(query, pageable),
//									clz);
//		return new PageImpl<T>(list, pageable, count);
//	}
//
//	protected <T> Page<T> findAll(Query query, Pageable pageable, Class<T> clz, String collectionName) {
//		Long count = this.mongoTemplate.count(query, clz);
//		List<T> list = findByQuery(QueryUtils.applyPagination(query, pageable),
//									clz,
//									collectionName);
//		return new PageImpl<T>(list, pageable, count);
//	}

	public E findOne(Query query) {
		return this.mongoTemplate.findOne(query, entityClass);
	}

	public List<E> findByQuery(Query query) {
		if (query == null) {
			return Collections.emptyList();
		}
		return this.mongoTemplate.find(query, entityClass);
	}

	protected <T> List<T> findByQuery(Query query, Class<T> clz) {
		if (query == null) {
			return Collections.emptyList();
		}
		return this.mongoTemplate.find(query, clz);
	}

	protected <T> List<T> findByQuery(Query query, Class<T> clz, String collectionName) {
		if (query == null) {
			return Collections.emptyList();
		}
		return this.mongoTemplate.find(query, clz, collectionName);
	}

	protected WriteResult update(Query query, Update update) {
		return this.mongoTemplate.updateFirst(query, update, entityClass);
	}

	protected WriteResult updateAll(Query query, Update update) {
		return this.mongoTemplate.updateMulti(query, update, entityClass);
	}

	protected WriteResult upsert(Query query, Update update) {
		return this.mongoTemplate.upsert(query, update, entityClass);
	}

	protected long count(Query query) {
		return this.mongoTemplate.count(query, entityClass);
	}

	protected List<E> findDistinct(String collectionName, String key) {
		return this.mongoTemplate.getCollection(collectionName).distinct(key);
	}

	protected List<E> findDistinctByQuery(String collectionName, String key, DBObject query) {
		return this.mongoTemplate.getCollection(collectionName).distinct(key, query);
	}

	// db.sequence.findAndModify( {update:{$inc: {"cnt":1}}} ,{query:{
	// "collName":"Talk"}} ,{new:true} );
//	public long getSequence(String tableName) {
//		DBObject query = new BasicDBObject();
//		query.put("collName", tableName);
//		DBObject newDocument = new BasicDBObject();
//		newDocument.put("$inc", new BasicDBObject().append("cnt", 1));
//
//		// System.out.println(query.toString());
//		// System.out.println(newDocument.toString());
//
//		DBCollection db = this.mongoTemplate.getCollection("sequence");
//
//		DBObject ret = db.findAndModify(query, newDocument);
//		if (ret == null) {
//			Sequence sq = new Sequence();
//
//			sq.setCollName(tableName);
//			sq.setCnt(10000000000l);
//
//			this.mongoTemplate.insert(sq);
//			return 10000000000l;
//		} else {
//			return (Long) ret.get("cnt") + 1;
//		}
//	}

//	public List<E> findCircleNear(Point point, double maxDistance) {
//		return mongoTemplate.find(new Query(Criteria.where("location").near(point).maxDistance(maxDistance)),
//								entityClass);
//	}
//
//	public List<E> findCircleNearSort(String city, Object value, String sortby, int limit,
//							String location, Point point, double maxDistance, Date date) {
//		Query query = new Query(Criteria.where(location).near(point).maxDistance(maxDistance));
//		if (null != city && city.equals("")){
//			query.addCriteria(Criteria.where(city).is(value));
//		}
//		if (null != date) {
//			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String strDate = format.format(date);
//			query.addCriteria(Criteria.where("locationDate").gte(strDate));
//		}
//		//query.sort().on(sortby, order);
//		query.with(new Sort(new Order(Direction.DESC, sortby)));
//		query.limit(limit);
//		return mongoTemplate.find(query, entityClass);
//	}
//
//	public GeoResults<E> geoNear(Query query,Point point,double maxDistance, int limit) {
//		if(maxDistance > 0) {
//			return mongoTemplate.geoNear(NearQuery.near(point.getX(),point.getY()).
//							distanceMultiplier(G.EARTH_RADIUS).maxDistance(maxDistance).spherical(true).query(query).num(limit),
//					entityClass,"cyzLocation");
//		}
//		else {
//			return mongoTemplate.geoNear(NearQuery.near(point.getX(),point.getY()).
//							distanceMultiplier(G.EARTH_RADIUS).spherical(true).query(query).num(limit),
//					entityClass,"cyzLocation");
//		}
//	}

	public WriteResult updateFirst(Query query, Update update) {
		return this.mongoTemplate.updateFirst(query, update, entityClass);
	}

	public WriteResult updateMulti(Query query, Update update) {
		return this.mongoTemplate.updateMulti(query, update, entityClass);
	}

	public void updateList(Query query, Update update) {
		upsert(query, update);
	}


//	public void ensureIndexOfCyzLocation() {
//		this.mongoTemplate.indexOps("cyzLocation").ensureIndex(new GeospatialIndex("location"));
//	}
}
