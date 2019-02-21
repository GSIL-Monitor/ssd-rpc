package com.shabro.rpc.service.order;

import java.util.List;

import com.shabro.rpc.entity.order.Order;

public interface OrderService {
	public void saveOrder(Order order);
	
	public void updateOrderState(String cyzId, String orderId, Integer orderState);
	
	public void updateOrderStateAndSignTime(String cyzId, String orderId, Integer orderState, long signTime);

	public void updateOrderStateAndPayTime(String cyzId, String orderId, Integer orderState, long payTime);

	public void updateOrderStateAndBidTime(String cyzId, String orderId, Integer orderState, long bidTime);

	public void updateOrderStateAndCreateTime(String cyzId, String orderId, Integer orderState, long createTime);

	public void removeOrder(Order order);
	
	public Order getOrderById(String orderId);
		
	public List<Order> getOrderByIdList(List<String> orderIdList);

	public List<Order> getExecuteOrderByCyzId(String cyzId);

	public List<Order> getOrderByCyzId(String cyzId);
}
