package com.shabro.rpc.service.blockchain;

import com.shabro.rpc.entity.rpc.RpcResult;

/**
 *
 * @ClassName: BlockChainService
 * @Description: 处理区块链信息
 * @author
 * @date
 *
 */
public interface BlockChainService {
	// 发布货源信息上链
	public RpcResult<String> createReqInfo(String json);

	// 货源信息变更上链
	public RpcResult<String> updateReqInfo(String reqId, String json);

	// 查询区块链货源信息
	public RpcResult<String> queryReqInfo(String reqId);
}
