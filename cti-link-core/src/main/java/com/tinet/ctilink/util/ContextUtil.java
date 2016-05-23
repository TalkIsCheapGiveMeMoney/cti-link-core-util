package com.tinet.ctilink.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Jiangsl
 *
 */
public class ContextUtil implements ApplicationListener<ContextRefreshedEvent> {

	private static ApplicationContext applicationContext;

	/**
	 * 获取spring的ApplicationContext。
	 * 
	 * @return Spring的ApplicationContext.
	 */
	public static ApplicationContext getContext() {
		return applicationContext;
	}

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		ContextUtil.applicationContext = event.getApplicationContext();
	}

	/**
	 * 获取类型为type的对象
	 * 
	 * @param type
	 * @return
	 */
	public static <T> T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}

}