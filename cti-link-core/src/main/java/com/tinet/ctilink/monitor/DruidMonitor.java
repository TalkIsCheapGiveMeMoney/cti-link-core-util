package com.tinet.ctilink.monitor;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 通过JMX暴露Druid连接池统计信息，用于监控
 * 
 * @author Jiangsl
 *
 */
public class DruidMonitor implements FactoryBean<DruidDataSource> {

	@Autowired
	private DruidDataSource dataSource;

	@Override
	public DruidDataSource getObject() throws Exception {
		return dataSource;
	}

	@Override
	public Class<?> getObjectType() {
		return DruidDataSource.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}