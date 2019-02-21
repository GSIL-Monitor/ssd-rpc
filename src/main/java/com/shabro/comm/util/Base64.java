package com.shabro.rpc.util;

public class Base64 {
	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null) {
			return null;
		} else {
			org.apache.commons.codec.binary.Base64 base = new org.apache.commons.codec.binary.Base64();
			String ss = new String(base.encode(s.getBytes()));
			ss = ss.replaceAll("\r|\n", "");
			return ss;
		}
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static String getFromBASE64(String s) {
		if (s == null) {
			return null;
		} else {
			org.apache.commons.codec.binary.Base64 base = new org.apache.commons.codec.binary.Base64();
			return new String(base.decode(s.getBytes()));
		}
	}
}
