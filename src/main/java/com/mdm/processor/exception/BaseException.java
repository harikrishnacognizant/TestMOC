package com.mdm.processor.exception;

public class BaseException extends RuntimeException implements IBaseException{

	Throwable raisedException;

	public BaseException(Exception ex) {
		raisedException = ex;
	}

	public Throwable getRaisedException() {
		return raisedException;
	}

	public void setRaisedException(Throwable raisedException) {
		this.raisedException = raisedException;
	}	
	
}
