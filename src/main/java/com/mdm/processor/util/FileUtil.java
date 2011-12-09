package com.mdm.processor.util;

import java.io.InputStream;
import java.nio.charset.Charset;

import com.mdm.processor.service.impl.PMStatic;

public final class FileUtil {

	public static InputStream getInputStream(String path) {

		InputStream inputStream = PMStatic.class.getClassLoader()
				.getResourceAsStream(path);

		return inputStream;

	}

	public static Charset getCharSet(String charSetName) {

		return Charset.forName("UTF-8");

	}

}
