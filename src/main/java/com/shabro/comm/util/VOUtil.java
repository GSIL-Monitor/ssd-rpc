package com.shabro.comm.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.CollectionUtils;

/**
 * 
 * @author wanghan 2012-8-3 下午7:57:22
 * 
 */
public class VOUtil {

	/**
	 * 持久化po对象转化为前台VO对象
	 */
	
	public static <T, D, C> List<T> convertPOListToVOList(List<D> poList,
			Class<C> poClass, Class<T> voClass) throws Exception {
		
		int size = poList.size();
		List<T> voList = new ArrayList<T>(size);
		
		//获取VO对象的构造函数
		Constructor<T> voconstructor = voClass.getConstructor(new Class[] { poClass });
		
		for (int i = 0; i < size; i++) {
			voList.add(voconstructor.newInstance(new Object[] { poList.get(i) }));
		}
		return voList;
	}

	public static <T> HashMap<String, Object> constructorJsonMap(Long total,
			List<T> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		return map;
		
	}

	@SuppressWarnings("unchecked")
	public static <V, D> List<V> convertDomainListToTempList(List<D> domainList,
			Class<V> voClass) throws Exception {
		if (CollectionUtils.isEmpty(domainList)) {
			return new ArrayList<V>(0);
		} else {
			int size = domainList.size();
			List<V> tempList = new ArrayList<V>(size);
			Class<?> dClass = domainList.get(0).getClass();
			if (dClass == voClass) {
				return (List<V>) domainList;
			} else {
				
				// 获取temp对象的构造函数
				Constructor<V> voconstructor = voClass
						.getConstructor(new Class[] { dClass });
				
				for (int i = 0; i < size; i++) {
					tempList.add(voconstructor
							.newInstance(new Object[] { domainList.get(i) }));
				}
				return tempList;
			}
		}
	}


}
