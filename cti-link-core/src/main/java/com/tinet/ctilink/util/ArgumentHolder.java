package com.tinet.ctilink.util;

/**
 * 用于调用参数的封装，实现变长参数及不同类型参数的统一形式地传递
 * <p>
 * 文件名： AreaCodeUtil.java
 * <p>
 * Copyright (c) 2006-2010 T&I Net Communication CO.,LTD. All rights reserved.
 * 
 * @author 安静波
 * @since 1.0
 * @version 1.0
 */

public class ArgumentHolder {
	/* 参数类型数组 */
	protected Class[] cl;
	/* 参数对象数组 */
	protected Object[] args;
	protected int argc;

	public ArgumentHolder() {
		argc = 0;
		cl = new Class[0];
		args = new Object[0];
	}

	public ArgumentHolder(int argc) {
		this.argc = argc;
		cl = new Class[argc];
		args = new Object[argc];
	}

	public Class[] getArgumentClasses() {
		return cl;
	}

	public Object[] getArguments() {
		return args;
	}

	// 以下16个setArgument函数实现简单数据类型boolean,byte,
	// char,int,short,long,float,double的封装。为支持Invoker
	// 类dynaCall方法中getClass的调用，此处将简单数据类型
	// 转换为对应的对象，如boolean转换为Boolean
	public int setArgument(boolean b) {
		return this.setArgument(argc, new Boolean(b), Boolean.TYPE);
	}

	public int setArgument(int argnum, boolean b) {
		return this.setArgument(argnum, new Boolean(b), Boolean.TYPE);
	}

	public int setArgument(byte b) {
		return this.setArgument(argc, new Byte(b), Byte.TYPE);
	}

	public int setArgument(int argnum, byte b) {
		return this.setArgument(argnum, new Byte(b), Byte.TYPE);
	}

	public int setArgument(char c) {
		return this.setArgument(argc, new Character(c), Character.TYPE);
	}

	public int setArgument(int argnum, char c) {
		return this.setArgument(argnum, new Character(c), Character.TYPE);
	}

	public int setArgument(int i) {
		return this.setArgument(argc, new Integer(i), Integer.TYPE);
	}

	public int setArgument(int argnum, int i) {
		return this.setArgument(argnum, new Integer(i), Integer.TYPE);
	}

	public int setArgument(short s) {
		return this.setArgument(argc, new Short(s), Short.TYPE);
	}

	public int setArgument(int argnum, short s) {
		return this.setArgument(argnum, new Short(s), Short.TYPE);
	}

	public int setArgument(long l) {
		return this.setArgument(argc, new Long(l), Long.TYPE);
	}

	public int setArgument(int argnum, long l) {
		return this.setArgument(argnum, new Long(l), Long.TYPE);
	}

	public int setArgument(float f) {
		return this.setArgument(argc, new Float(f), Float.TYPE);
	}

	public int setArgument(int argnum, float f) {
		return this.setArgument(argnum, new Float(f), Float.TYPE);
	}

	public int setArgument(double d) {
		return this.setArgument(argc, new Double(d), Double.TYPE);
	}

	public int setArgument(int argnum, double d) {
		return this.setArgument(argnum, new Double(d), Double.TYPE);
	}

	// 以下2个setArgument函数实现对象的封装，
	public int setArgument(Object obj) {
		return this.setArgument(argc, obj, obj.getClass());
	}

	public int setArgument(int argnum, Object obj) {
		return this.setArgument(argnum, obj, obj.getClass());
	}

	/**
	 * 实现以对象形式提供的参数封装。 增加计数器argc的值、在cl和args中增加相应内容
	 */
	public int setArgument(int argnum, Object obj, Class c) {
		if (argnum >= args.length) {
			argc = argnum + 1;
			Class[] clExpanded = new Class[argc];
			Object[] argsExpanded = new Object[argc];
			System.arraycopy(cl, 0, clExpanded, 0, cl.length);
			System.arraycopy(args, 0, argsExpanded, 0, args.length);
			cl = clExpanded;
			args = argsExpanded;
		}
		args[argnum] = obj;
		cl[argnum] = c;
		return argnum;
	}
}
