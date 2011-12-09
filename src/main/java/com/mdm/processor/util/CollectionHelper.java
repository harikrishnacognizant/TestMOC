package com.mdm.processor.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionHelper {
	private CollectionHelper() {
	}

	/**
	 * 
	 DOCUMENT ME!
	 * 
	 * @param c
	 * 
	 * @return
	 */
	public static boolean isNullorEmpty(Collection c) {
		return (c == null) || c.isEmpty();
	}

	/**
	 * 
	 DOCUMENT ME!
	 * 
	 * @param array
	 * 
	 * @return
	 */
	public static boolean isNullorEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	/**
	 * 
	 DOCUMENT ME!
	 * 
	 * @param m
	 * 
	 * @return
	 */
	public static boolean isNullorEmpty(Map m) {
		return (m == null) || m.isEmpty();
	}

	/**
	 * @param list
	 * @param array
	 */
	public static void addArraysToList(List list, Object[] array) {
		if (list == null)
			return;
		if (!CollectionHelper.isNullorEmpty(array)) {
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
		}
	}

	public static boolean isHavingKey(Map m, String key) {
		if (!isNullorEmpty(m)) {
			return m.containsKey(key);
		}
		return false;
	}

	public static boolean isHavingValue(Map m, String key) {
		if (!isNullorEmpty(m)) {
			if (isHavingKey(m, key)) {
				return m.get(key) != null;
			}
		}
		return false;
	}
}
