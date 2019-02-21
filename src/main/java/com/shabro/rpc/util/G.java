package com.shabro.rpc.util;

import com.shabro.comm.util.BaseUtil;

public class G {
	/**
	 * 运力管家App的Key (在极光推送控制台创建应用页面上获取)
	 */
	public static  String YLGJ_APPKEY = "b84db7e94b1c0fc4d4103b9b"; // 必填，每个应用都对应一个appKey
	/**
	 * App的MasterSecret (在极光推送控制台创建应用页面上获取)
	 */
	public static  String YLGJ_MASTERSECRET = "2506e56dcf312fd1d7aee153";// 必填，每个应用都对应一个masterSecret

	/**
	 * 货车导航手机版App的Key (在极光推送控制台创建应用页面上获取)
	 */
	public static  String HCDH_PHONE_APPKEY = "c16888eb3615b0f206d93b81"; // 必填，每个应用都对应一个appKey
	/**
	 * App的MasterSecret (在极光推送控制台创建应用页面上获取)
	 */
	public static  String HCDH_PHONE_MASTERSECRET = "bbb50ad6bfa3ee6a5e9e7ec7";// 必填，每个应用都对应一个masterSecret

	/**
	 * APP属性，TOKEN验证等判断使用
	 */
	// 货车导航
	public static String TRUCK_APP_TYPE = "TRUCK_APP";
	public static String TRUCK_APP_ID = "CYZ171205";
	// 运力管家
	public static String CARGO_APP_TYPE = "CARGO_APP";
	public static String CARGO_APP_ID = "FBZ171206";

	public static String CITY_ALL = "全国";

	//订单状态 0待选中--(发货方选中)---->1待支付--（发货方付款）-（司机拉货、送货）->2待回执码--（司机交货、索取回执码、输入回执码）-->3待确定--（发货方确定）-->4完成->货主评论，司机评论
	//             (发货方不选中)-->5未选中
	//			   (发货方取消)--->6已取消
	
	public static Integer ORDER_STATE_WAITACCEPT = 0; 		//待中选
	public static Integer ORDER_STATE_WAITPAY = 1; 			//待支付
	public static Integer ORDER_STATE_RETURNCODE = 2; 		//待输回执码
	public static Integer ORDER_STATE_CONFIRM = 3; 			//待货主确认
	public static Integer ORDER_STATE_FINISH = 4; 			//运输结束
	public static Integer ORDER_STATE_NOACCEPT = 5; 		//未中选
	public static Integer ORDER_STATE_CANCEL = 6; 			//已取消
	public static Integer ORDER_STATE_ORDERCREATE = 7; 		//已生成第三方支付订单
}
