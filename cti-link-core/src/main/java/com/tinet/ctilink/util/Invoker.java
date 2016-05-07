package com.tinet.ctilink.util;

//--------------------------------------
//类Invoker
//实现函数的动态调用
//方法：
//dynaCall
//参数 Object c希望调用函数（方法）所在对象
//String m希望调用的方法名称
//ArgumentHolder a传递给被调用方法的参数
//-----------------------------------------
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Java 反射机制实现
 * <p>
 * 文件名： Invoker.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD. All rights reserved.
 * 
 * @author 安静波
 * @since 1.0
 * @version 1.0
 */

public class Invoker {
	static public Object dynaCall(Object c, String m, ArgumentHolder a) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		// 由于java支持方法名称的重载（同一类中出现多于一个的同名函数），
		// 所以getMethod方法使用两个参数：字符串形式的方法名称和数组形式
		// 的调用参数类列表。返回值为类Method的一个对象，该对象和将要被
		// 调用的方法相关联
		Method meth = c.getClass().getMethod(m, a.getArgumentClasses());

		// 通过类Method对象的invoke方法动态调用希望调用的方法
		return (meth.invoke(c, a.getArguments()));
	}

	/**
	 * 从一个对象里面根据属性名取值
	 * 
	 * @param name
	 *            属性名
	 * @param obj
	 *            对象
	 * @return
	 */
	public static Object getValueByName(String name, Object obj) {
		String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
		ArgumentHolder a = new ArgumentHolder();
		try {
			Object value = Invoker.dynaCall(obj, "get" + methodName, a);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
