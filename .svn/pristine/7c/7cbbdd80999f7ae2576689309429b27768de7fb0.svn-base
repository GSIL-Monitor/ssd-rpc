package com.shabro.rpc.service.cyzOrder;

import com.shabro.rpc.entity.rpc.RpcCyzOrder;
import com.shabro.rpc.entity.rpc.RpcResult;

import java.util.List;

/**
 * 
 * @ClassName: CyzOrderService
 * @Description: 处理承运者的订单信息
 * @author
 * @date
 *
 */
public interface CyzOrderService {
	// 保存承运者的订单信息
	RpcResult<String> saveCyzOrder(RpcCyzOrder order);
	
	// 更新承运者的订单信息
	RpcResult<String> updateCyzOrderState(String cyzId, String orderId, Integer orderState);
	RpcResult<String> updateCyzOrderStateAndTime(String cyzId, String orderId, Integer orderState, long time);

	// 按承运者Id查询承运者执行中订单的信息
	RpcResult<List<RpcCyzOrder>> getCyzExecuteOrderByCyzId(String cyzId);
	// 按承运者Id查询承运者的订单信息
	RpcResult<List<RpcCyzOrder>> getCyzOrderByCyzId(String cyzId);

	// 按订单号查询承运者的订单信息
	RpcResult<RpcCyzOrder> getCyzOrder(String orderId);
	RpcResult<List<RpcCyzOrder>> getCyzOrderByIdList(List<String> orderIdList);
}
