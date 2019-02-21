package com.shabro.rpc.dao.mongodb;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.shabro.rpc.entity.mongodb.BasicEntity;
import com.shabro.rpc.entity.location.CyzLocationExt;

public class RepositorySupport<E extends BasicEntity> implements Repository<E> {
	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	//public RepositorySupportOld() {
	public RepositorySupport() {
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
	public long getSequence(String tableName) {
		return 0;
	}

	public WriteResult updateFirst(Query query, Update update) {
		return this.mongoTemplate.updateFirst(query, update, entityClass);
	}

	public WriteResult updateMulti(Query query, Update update) {
		return this.mongoTemplate.updateMulti(query, update, entityClass);
	}

	public void updateList(Query query, Update update) {
		upsert(query, update);
	}

	public List<DBObject> findAggregateUnwindMatch(String collName, String listName, DBObject where, Class<E> clz) {

//		Aggregation agg = Aggregation.newAggregation(
//				Aggregation.match(Criteria.where("gpsTrack.city").is("南岸区")),
//				Aggregation.project("courses"),
//				Aggregation.unwind("courses")
//				//Aggregation.group("courses").count().as("count"),
//				//Aggregation.project("count").and("courses").previousOperation(),
//				//Aggregation.sort(Sort.Direction.DESC, "count")
//		);
//		List<DBObject> list =(List<DBObject>)this.mongoTemplate.getCollection(collName).aggregate(agg).results();
		//AggregationResults<SubjectCount> groupResults = mongoTemplate.aggregate(agg

		/* 创建 $unwind 操作, 用于切分数组*/
		DBObject unwind2 = new BasicDBObject("$unwind", "$gpsTrack");

///* Group操作*/
//		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject("locationDate", "$gpsTrack.locationDate"));//.append("city", "$gpsTrack.city"));
//		DBObject group = new BasicDBObject("$group", groupFields);
//
///* Reshape Group Result*/
//		DBObject projectFields = new BasicDBObject();
//		projectFields.put("locationDate", "$_id.locationDate");
//		projectFields.put("city", "$gpsTrack.city");
//		projectFields.put("cyzId", "$cyzId");
//		DBObject project = new BasicDBObject("$project", projectFields);
//
///* 将结果push到一起*/
//		DBObject groupAgainFields = new BasicDBObject("_id", "$locationDate");
//		//groupAgainFields.put("city", new BasicDBObject("$push", "$city"));
//		//groupAgainFields.put("cyzId", new BasicDBObject("$push", "$cyzId"));
//		DBObject reshapeGroup = new BasicDBObject("$group", groupAgainFields);

/* 查看Group结果 */
		//List<DBObject> list = (List<DBObject>)this.mongoTemplate.getCollection(collName).aggregate(unwind2, group, project, reshapeGroup).results();


		DBObject match = new BasicDBObject("$match", where);
		DBObject unwind = new BasicDBObject("$unwind", listName);
		//DBObject unwind =  new BasicDBObject("$unwind", listName).append("includeArrayIndex", "arrayIndex"));
		DBObject projFields = new BasicDBObject();
		projFields.put("locationDate", "$gpsTrack.locationDate");
		projFields.put("locateTime", "$gpsTrack.locateTime");
		projFields.put("location", "$gpsTrack.location");
		projFields.put("city", "$gpsTrack.city");
		projFields.put("orderId", "$orderId");
		projFields.put("cyzId", "$cyzId");
		DBObject proj = new BasicDBObject("$project", projFields);

		List<DBObject> list = null;
		list = (List<DBObject>)this.mongoTemplate.getCollection(collName).aggregate(unwind, match, proj).results();

		for(DBObject doc: list) {
			CyzLocationExt r = new CyzLocationExt();
			String str = doc.get("location").toString();
			r.setLocateTime((Long)doc.get("locateTime"));
			r.setLocationDate((String)doc.get("locationDate"));
			//double[] dCordiante = (double[])doc.get("location");
		}

		/* Group操作*/
		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject("locateTime", "$gpsTrack.locateTime")
															.append("city", "$gpsTrack.city")
															.append("locationDate", "$gpsTrack.locationDate")
															.append("orderId", "$orderId"));//.append("city", "$gpsTrack.city"));
		//groupFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);

		/* Reshape Group Result*/
		DBObject projectFields = new BasicDBObject();
		projectFields.put("orderId", "$_id.orderId");
		projectFields.put("locationDate", "$_id.locationDate");
		projectFields.put("locateTime", "$_id.locateTime");
		projectFields.put("city", "$_id.city");

//		projFields.put("locateTime", "$gpsTrack.locateTime");
//		projectFields.put("city", "$gpsTrack.city");
//		projFields.put("location", "$gpsTrack.location");
//		projFields.put("orderId", "$orderId");
//		projFields.put("cyzId", "$cyzId");
//		projectFields.put("info", new BasicDBObject("locationDate","$_id.locationDate")
//				.append("locateTime", "$gpsTrack.locateTime")
//				.append("city", "$gpsTrack.city"));
		DBObject project = new BasicDBObject("$project", projectFields);

		/* 第二次Group操作, 将结果push到一起*/
		DBObject groupAgainFields = new BasicDBObject("_id", "locateTime"); // $orderId


		//groupAgainFields.put("$first", new BasicDBObject("$push", "$info"));
		DBObject secondGroup = new BasicDBObject("$group", groupAgainFields);


		DBObject sortFields = new BasicDBObject();
		sortFields.put("locateTime", -1);
		//DBObject sortFields = new BasicDBObject("locateTime", 1);
		DBObject sort = new BasicDBObject("$sort", sortFields);//排序
		DBObject limit = new BasicDBObject("$limit", 5);

		list = (List<DBObject>)this.mongoTemplate.getCollection(collName).aggregate(sort, limit)
									.results();

//		db.msds_accessrecord.aggregate([
//			{$group: {
//				_id: {
//					"month": {"$month": "$addtime"},
//					"url": "$url"
//				}
//			}},
//			{$group: {_id:"$_id.month", uv: {$sum: 1}}},
//			{$sort: {"_id":1}}
//		]);

//
//		// 利用$project拼装group需要的数据，包含optCode列、processTime列
//		DBObject fields = new BasicDBObject("cyzId", 1); // 接口
//		fields.put("city", 1);// 耗时
//		fields.put("locationDate", 1);// 省份
//		fields.put("locateTime", 1);// 渠道
//		DBObject project = new BasicDBObject("$project", fields);
//
//		// 利用$group进行分组
//		DBObject _group = new BasicDBObject("city", "$city");
//		_group.put("cyzId", "$cyzId");
//		_group.put("locationDate", "$locationDate");
//		_group.put("locateTime", "$locateTime");
//
//		DBObject groupFields = new BasicDBObject("_id", _group);
//		DBObject group = new BasicDBObject("$group", groupFields);
		//List<DBObject> list = (List<DBObject>)this.mongoTemplate.getCollection(collName).aggregate(match, project, group).results();



		//List<DBObject> list =(List<DBObject>)this.mongoTemplate.getCollection(collName).aggregate(match).results();
		return list;
		//AggregationResults<E> result = this.mongoTemplate.getCollection(collName).aggregate(clz, match);
		//AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(agg, BasicDBObject.class);
		//result.getMappedResults()
		//List<E> list = this.mongoTemplate.getCollection(collName).aggregate(match).results();
		//return result.getMappedResults();
	}
}
