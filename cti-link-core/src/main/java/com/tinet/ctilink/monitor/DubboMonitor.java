package com.tinet.ctilink.monitor;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.store.DataStore;

/**
 * 通过JMX暴露Dubbo线程池统计信息，用于监控
 * 
 * @author Jiangsl
 *
 */
public class DubboMonitor {

	public int getActiveCount() {
		return getPool().getActiveCount();
	}

	public int getPoolSize() {
		return getPool().getPoolSize();
	}

	public int getLargestPoolSize() {
		return getPool().getLargestPoolSize();
	}

	public long getCompletedTaskCount() {
		return getPool().getCompletedTaskCount();
	}

	public int getMaxPoolSize() {
		return getPool().getMaximumPoolSize();
	}

	/**
	 * 获取Dubbo Provider的线程池
	 * 
	 * @return
	 */
	private ThreadPoolExecutor getPool() {
		ThreadPoolExecutor pool = null;
		DataStore dataStore = ExtensionLoader.getExtensionLoader(DataStore.class).getDefaultExtension();
		Map<String, Object> executors = dataStore.get(Constants.EXECUTOR_SERVICE_COMPONENT_KEY);

		for (Map.Entry<String, Object> entry : executors.entrySet()) {
			ExecutorService executor = (ExecutorService) entry.getValue();
			if (executor != null && executor instanceof ThreadPoolExecutor) {
				pool = (ThreadPoolExecutor) executor;
				break;
			}
		}
		return pool;
	}

}
