package com.shabro.rpc.service.blockchain;

import com.shabro.rpc.entity.rpc.RpcResult;

import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;


@Service
public class BlockChainServiceImpl implements BlockChainService {
	private static Logger log = Logger.getLogger("locus");
	//private static final Logger log = LoggerFactory.getLogger(BlockChainServiceImpl.class);

//	public RpcResult<String> getName() {
//		RpcResult<String> result = new RpcResult<>();
//		result.setCode(0);
//		result.setMessage("success");
//		result.setObject("rhwayfun");
//		System.out.println("====getName====");
//		return result;
//	}

	// 发布货源信息上链
	public RpcResult<String> createReqInfo(String json) {
		RpcResult<String> result = new RpcResult<>();

		//SsdClient client = new SsdClient();
//		long start = System.currentTimeMillis();
//		String res = BlockChainUtil.getClient().WriteInfo(json);
//		long end = System.currentTimeMillis();
//		//String res = client.WriteInfo(json);
//		if ("writeinfo success".equals(res)) {
//			result.setCode(0);
//			result.setMessage("success");
//			log.info("WriteInfo time:"+(end-start)+" "+res);
//		} else {
//			result.setCode(1);
//			result.setMessage("fail");
//			log.error("WriteInfo fail, time:"+(end-start));
//		}
//
//		if (res != null) {
//			result.setObject(res);
//			System.out.println("WriteInfo:"+res);
//		}
//		System.out.println("WriteInfo interval:"+(end-start));
		return result;
	}

	// 货源信息变更上链
	public RpcResult<String> updateReqInfo(String reqId, String json) {
		RpcResult<String> result = new RpcResult<>();
		String [] param = {reqId,json};

		//SsdClient client = new SsdClient();
//		long start = System.currentTimeMillis();
//		String res = BlockChainUtil.getClient().UpdateInfo(param);
//		long end = System.currentTimeMillis();
//		//String res = client.UpdateInfo(param);
//		if ("updateinfo success".equals(res)) {
//			result.setCode(0);
//			result.setMessage("success");
//			log.info("UpdateInfo time:"+(end-start)+" "+res);
//		} else {
//			result.setCode(1);
//			result.setMessage("fail");
//			log.error("UpdateInfo fail, time:"+(end-start));
//		}
//		if (res != null) {
//			result.setObject(res);
//			System.out.println("UpdateInfo:"+res);
//		}
//		System.out.println("UpdateInfo interval:"+(end-start));
		return result;
	}

	// 查询区块链货源信息
	public RpcResult<String> queryReqInfo(String reqId) {
		RpcResult<String> result = new RpcResult<>();

		//SsdClient client = new SsdClient();
//		long start = System.currentTimeMillis();
//		String res = BlockChainUtil.getClient().QueryInfo(reqId);
//		long end = System.currentTimeMillis();
//		//String res = client.QueryInfo(reqId);
//		if (BaseUtil.stringNotNull(res)) {
//			result.setCode(0);
//			result.setMessage("success");
//			result.setObject(res);
//			System.out.println("QueryInfo:"+res);
//			log.info("QueryInfo time:"+(end-start)+" "+res);
//		} else {
//			result.setCode(1);
//			result.setMessage("fail");
//			log.error("QueryInfo fail, time:"+(end-start));
//		}
//		System.out.println("QueryInfo interval:"+(end-start));
		return result;
	}
}
