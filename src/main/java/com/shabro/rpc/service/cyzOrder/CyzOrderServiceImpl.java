package com.shabro.rpc.service.cyzOrder;

import com.shabro.rpc.entity.order.Order;
import com.shabro.rpc.entity.rpc.RpcCyzOrder;
import com.shabro.rpc.entity.rpc.RpcResult;
import com.shabro.rpc.service.order.OrderService;
import com.shabro.rpc.util.G;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CyzOrderServiceImpl implements CyzOrderService {
	private static Logger log = Logger.getLogger("locus");

	@Autowired
	private OrderService orderService;

	public RpcResult<String> saveCyzOrder(RpcCyzOrder order) {
		orderService.saveOrder(new Order(order));
		return RpcResult.newSuccess("saveCyzOrder success");
	}

	public RpcResult<String> updateCyzOrderState(String cyzId, String orderId, Integer orderState) {
		orderService.updateOrderState(cyzId, orderId, orderState);
		return RpcResult.newSuccess("udpateOrderState success");
	}

	public RpcResult<String> updateCyzOrderStateAndTime(String cyzId, String orderId, Integer orderState, long time) {
		log.info("cyzId="+cyzId+" orderId="+orderId+" orderState="+orderState+" now="+time);

		if (orderState.equals(G.ORDER_STATE_FINISH)
		 || orderState.equals(G.ORDER_STATE_CANCEL)
		 || orderState.equals(G.ORDER_STATE_CONFIRM) ) {
			orderService.updateOrderStateAndSignTime(cyzId, orderId, orderState, time);
			return RpcResult.newSuccess("udpateOrderStateAndSignTime success, orderState=" +orderState);
		} else if (orderState.equals(G.ORDER_STATE_RETURNCODE)) {
			orderService.updateOrderStateAndPayTime(cyzId, orderId, orderState, time);
			return RpcResult.newSuccess("udpateOrderStateAndPayTime success, orderState=" +orderState);
		} else if (orderState.equals(G.ORDER_STATE_WAITPAY)) {
			orderService.updateOrderStateAndBidTime(cyzId, orderId, orderState, time);
			return RpcResult.newSuccess("updateOrderStateAndBidTime success, orderState=" +orderState);
		} else if (orderState.equals(G.ORDER_STATE_WAITACCEPT)) {
			orderService.updateOrderStateAndCreateTime(cyzId, orderId, orderState, time);
			return RpcResult.newSuccess("updateOrderStateAndCreateTime success, orderState=" +orderState);
		} else {
			return RpcResult.newFailure(-1, "invalid orderState=" +orderState);
		}
	}

	public RpcResult<RpcCyzOrder> getCyzOrder(String orderId) {
		Order order = orderService.getOrderById(orderId);
		if (order != null) {
			return RpcResult.newSuccess(initRpcCyzOrder(order));
		} else {
			return RpcResult.newFailure(-1, "getCyzOrder no data");
		}
	}

	public RpcResult<List<RpcCyzOrder>> getCyzOrderByIdList(List<String> orderIdList) {
		RpcResult<List<RpcCyzOrder>> result = new RpcResult<>();
		List<RpcCyzOrder> rpcOrder = new ArrayList<>();

		List<Order> lstOrder = orderService.getOrderByIdList(orderIdList);
		for (Order order : lstOrder) {
			rpcOrder.add(initRpcCyzOrder(order));
		}

		result.setCode(0);
		result.setMessage("success");
		result.setObject(rpcOrder);
		return result;
	}

	public RpcResult<List<RpcCyzOrder>> getCyzExecuteOrderByCyzId(String cyzId) {
		RpcResult<List<RpcCyzOrder>> result = new RpcResult<>();
		List<RpcCyzOrder> rpcOrder = new ArrayList<>();

		List<Order> lstOrder = orderService.getExecuteOrderByCyzId(cyzId);
		for (Order order : lstOrder) {
			rpcOrder.add(initRpcCyzOrder(order));
		}

		result.setCode(0);
		result.setMessage("success");
		result.setObject(rpcOrder);
		return result;
	}

	public RpcResult<List<RpcCyzOrder>> getCyzOrderByCyzId(String cyzId){
		RpcResult<List<RpcCyzOrder>> result = new RpcResult<>();
		List<RpcCyzOrder> rpcOrder = new ArrayList<>();

		List<Order> lstOrder = orderService.getOrderByCyzId(cyzId);
		for (Order order : lstOrder) {
			rpcOrder.add(initRpcCyzOrder(order));
		}

		result.setCode(0);
		result.setMessage("success");
		result.setObject(rpcOrder);
		return result;
	}

	private RpcCyzOrder initRpcCyzOrder(Order order) {
		RpcCyzOrder data = new RpcCyzOrder();
		data.setOrderId(order.getOrderId());
		data.setOrderState(order.getOrderState());
		data.setCyzId(order.getCyzId());
		data.setCyzTel(order.getCyzTel());
		data.setFbzId(order.getFbzId());
		data.setFbzTel(order.getFbzTel());
		data.setCreateTime(order.getCreateTime());
		data.setBidTime(order.getBidTime());
		data.setPayTime(order.getPayTime());
		data.setSignTime(order.getSignTime());
		return data;
	}
}
