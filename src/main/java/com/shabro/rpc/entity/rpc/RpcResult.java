package com.shabro.rpc.entity.rpc;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.PrintWriter;

/**    
int code，接口调用成功=0，错误码=其他值
T object，具体返回值
String error，字符串错误码，可选
String message，错误消息，可选，error=message可以配置成属性文件
Exception exception，异常消息，可选
 */
public class RpcResult<T> implements Serializable {
	/**
	 * 接口调用成功，不需要返回对象
	 */
	public static <T> RpcResult<T> newSuccess(){
		RpcResult<T> result = new RpcResult<>();
		result.setCode(0);
		return result;
	}
	
	/**
	 * 接口调用成功，有返回对象
	 */
	public static <T> RpcResult<T> newSuccess(T object) {
		RpcResult<T> result = new RpcResult<>();
		result.setCode(0);
		result.setObject(object);
		return result;
	}
	
	/**
	 * 接口调用失败，有错误码和描述，没有返回对象
	 */
	public static <T> RpcResult<T> newFailure(int code, String message){
		RpcResult<T> result = new RpcResult<>();
		result.setCode(code!=0 ? code : -1);
		result.setMessage(message);
		return result;
	}
	
	/**
	 * 接口调用失败，有错误字符串码和描述，没有返回对象
	 */
	public static <T> RpcResult<T> newFailure(String error, String message){
		RpcResult<T> result = new RpcResult<>();
		result.setCode(-1);
		result.setError(error);
		result.setMessage(message);
		return result;
	}
	
	/**
	 * 转换或复制错误结果
	 */
	public static <T> RpcResult<T> newFailure(RpcResult<?> failure){
		RpcResult<T> result = new RpcResult<>();
		result.setCode(failure.getCode()!=0 ? failure.getCode() : -1);
		result.setError(failure.getError());
		result.setMessage(failure.getMessage());
		result.setException(failure.getException());
		return result;
	}
	
	/**
	 * 接口调用失败，返回异常信息
	 */
	public static <T> RpcResult<T> newException(Exception e){
		RpcResult<T> result = new RpcResult<>();
		result.setCode(-1);
		result.setException(e);
		result.setMessage(e.getMessage());
		return result;
	}
	
	private int code;
	private T object;
	private String error;
	private String message;
	private Exception exception;
	
	/** 判断返回结果是否成功 */
	public boolean success() {
		return code == 0;
	}
	/** 判断返回结果是否有结果对象 */
	public boolean hasObject() {
		return code==0 && object!=null;
	}
	/** 判断返回结果是否有异常 */
	public boolean hasException() {
		return exception != null;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String toString() {
		StringBuilder result = new StringBuilder("RpcResult");
		if(object!=null) result.append("<"+object.getClass().getSimpleName()+">");
		result.append(": {code="+code);
		if(object!=null) result.append(", object="+object);
		if(error!=null) result.append(", error="+error);
		if(message!=null) result.append(", message="+message);
		if(exception!=null) {
			StringWriter stringWriter = new StringWriter();
			exception.printStackTrace(new PrintWriter(stringWriter));
			result.append(", exception="+stringWriter.toString());
		}
		result.append(" }");
		return result.toString();
	}
}