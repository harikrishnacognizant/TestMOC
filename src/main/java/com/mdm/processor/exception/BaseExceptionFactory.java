package com.mdm.processor.exception;

public class BaseExceptionFactory {

	public BaseExceptionFactory factory = new BaseExceptionFactory();
	private BaseExceptionFactory(){}
	
	public static BaseException getInstance(Exception ex){
		return new BaseException(ex);
	}
	
}
