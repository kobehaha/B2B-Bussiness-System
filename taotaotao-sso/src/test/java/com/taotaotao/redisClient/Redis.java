package com.taotaotao.redisClient;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

//单机版和集群版测试
public class Redis {

	// 先要打开客户端
	// 设置属性和命令一样
	// 关闭命令
	@Test
	public void redisTest() {

		Jedis jedis = new Jedis("192.168.254.128", 6379);
		jedis.set("key1", "jedisTestValue");
		String jedisValue = jedis.get("key1");
		System.out.println("redis key and value is " + "  " + jedisValue);
		jedis.close();
	}

	// redis连接池每次使用需要关闭，每次jedis使用需要close
	@Test
	public void redisPoolTest() {
		JedisPool jedisPool = new JedisPool("192.168.254.128", 6379);
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.get("key1"));
		jedis.close();
		jedisPool.close();
	}

	@Test
	public void redisClusterTest() {
		HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.254.128", 7001));
		nodes.add(new HostAndPort("192.168.254.128", 7002));
		nodes.add(new HostAndPort("192.168.254.128", 7003));
		nodes.add(new HostAndPort("192.168.254.128", 7004));
		nodes.add(new HostAndPort("192.168.254.128", 7005));
		nodes.add(new HostAndPort("192.168.254.128", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("key3", "10009");
		String string = cluster.get("key3");
		System.out.println(string);

		cluster.close();

	}

	// 集群
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
		String string = jedisCluster.get("key3");
		System.out.println(string);
		jedisCluster.close();
	}

	// 单机版
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = pool.getResource();
		String string = jedis.get("key3");
		System.out.println(string);
		jedis.close();
		pool.close();
	}

}
