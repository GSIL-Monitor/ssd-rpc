package com.shabro.rpc.service.order;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shabro.rpc.util.G;
import com.shabro.comm.util.BaseUtil;
import com.shabro.rpc.dao.order.OrderRepository;
import com.shabro.rpc.entity.order.Order;

@Service
public class OrderServiceImpl implements OrderService {
	private static Logger log = Logger.getLogger("locus");

	@Autowired
	private OrderRepository<Order> orderResp;
	
	public void saveOrder(Order order) {
		log.info("cyzId="+order.getCyzId()+" orderId="+order.getOrderId()+" bidTime="+order.getBidTime());

//		Order temp = getOrderById(order.getOrderId());
//		if (BaseUtil.objectNotNull(temp)) {
//			return;
//		}
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(order.getCyzId()).and("orderId").is(order.getOrderId()));
		List<Order> ret = orderResp.findByQuery(query);
		if (!ret.isEmpty()) {
			return;
		}

		orderResp.save(order);
	}
	
	public void updateOrderState(String cyzId, String orderId, Integer orderState) {
		log.info("cyzId="+cyzId+" orderId="+orderId+" orderState="+orderState);

		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderId").is(orderId));
		
		Update update = new Update();
		update.set("orderState", orderState);
		orderResp.updateFirst(query, update);
	}
	
	public void updateOrderStateAndSignTime(String cyzId, String orderId, Integer orderState, long signTime) {
		log.info("cyzId="+cyzId+" orderId="+orderId+" orderState="+orderState+" now="+signTime);

		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderId").is(orderId));
		
		Update update = new Update();
		update.set("orderState", orderState);
		update.set("signTime", signTime);
		orderResp.updateFirst(query, update);
	}

	public void updateOrderStateAndPayTime(String cyzId, String orderId, Integer orderState, long payTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderId").is(orderId));
		
		Update update = new Update();
		update.set("orderState", orderState);
		update.set("payTime", payTime);
		orderResp.updateFirst(query, update);
	}

	public void updateOrderStateAndBidTime(String cyzId, String orderId, Integer orderState, long bidTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderId").is(orderId));

		Update update = new Update();
		update.set("orderState", orderState);
		update.set("bidTime", bidTime);
		orderResp.updateFirst(query, update);
	}

	public void updateOrderStateAndCreateTime(String cyzId, String orderId, Integer orderState, long createTime) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderId").is(orderId));

		Update update = new Update();
		update.set("createTime", createTime);
		orderResp.updateFirst(query, update);
	}

	public void removeOrder(Order order) {
		orderResp.remove(order);
	}
	
	public Order getOrderById(String orderId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").is(orderId));
		List<Order> ret = orderResp.findByQuery(query);
		if (ret.isEmpty()) {
			return null;
		}

		Order order = ret.get(0);
		return order;
	}
		
	public List<Order> getOrderByIdList(List<String> orderIdList) {
		Query query = new Query();
		query.addCriteria(Criteria.where("orderId").in(orderIdList.toArray()));
		List<Order> ret = orderResp.findByQuery(query);
		return ret;
	}

	public List<Order> getExecuteOrderByCyzId(String cyzId) {
		//List<Integer> lstState = new ArrayList<>();
		// 订单状态: 1.待支付  2.待输回执码-装货(运输) 3.待货主确认-交货完成  4.运输结束-完成订单  6.订单已取消
		//lstState.add(G.ORDER_STATE_WAITPAY);
		//lstState.add(G.ORDER_STATE_RETURNCODE);
		//lstState.add(G.ORDER_STATE_CONFIRM);

		Date now = new Date();
		long time = now.getTime() / 1000;
		// 只查询20天以内的已付款订单
		time -= 20 * 24 * 3600;

		Query query = new Query();
		//query.addCriteria(Criteria.where("cyzId").is(cyzId));
		//query.addCriteria(Criteria.where("orderState").in(lstState.toArray()));
		query.addCriteria(Criteria.where("cyzId").is(cyzId).and("orderState").is(G.ORDER_STATE_RETURNCODE));
		query.addCriteria(Criteria.where("payTime").gte(time));
		List<Order> ret = orderResp.findByQuery(query);
		return ret;
	}

	public List<Order> getOrderByCyzId(String cyzId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("cyzId").is(cyzId));
		List<Order> ret = orderResp.findByQuery(query);
		return ret;
	}
}
