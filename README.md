# cti-link-core-util


如果使用redisService，需要在spring-core.xml中配置：

	<!-- 定义Redis连接池 -->
	<bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
		<property name="maxTotal" value="20" />
		<property name="maxIdle" value="20" />
		<property name="minIdle" value="5" />
	</bean>

	<!-- 定义Spring Redis连接工厂 -->
	<bean id="redisConnectionFactory" class="org.springframework.data.redis.connection.jedis.CtiLinkJedisConnectionFactory">
		<property name="hostName" value="${redis.url}" />
		<property name="port" value="${redis.port}" />
		<property name="database" value="0" />
		<property name="usePool" value="true" />
		<property name="poolConfig" ref="jedisPoolConfig" />
	</bean>

	<bean id="redisService" class="com.tinet.ctilink.cache.RedisService" >
		<property name="connectionFactory" ref="redisConnectionFactory" />
	</bean>
