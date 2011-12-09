package com.mdm.processor.exception;

public class BaseExceptionUtil {

	public static String printStackTrace(Exception ex) {
		StringBuffer strBuf = new StringBuffer("");
		StackTraceElement[] steArr = ex.getStackTrace();
		Throwable th = ex.fillInStackTrace();
		if (steArr != null && steArr.length > 0) {
			strBuf.append(th);
			for (StackTraceElement ste : steArr) {
				strBuf.append(ste.toString());
				strBuf.append("\n");
			}
			return strBuf.toString();
		} else
			return strBuf.toString();
	}

}
