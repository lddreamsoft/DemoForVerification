package ldsoft.hlhh.web.dao.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import ldsoft.hlhh.web.entity.QRCodeLoginInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//用于在redis中缓存二维码是否登录信息
public class RedisQRCodeLoginInfoDAO {

	private final Logger logger = LoggerFactory.getLogger(RedisQRCodeLoginInfoDAO.class);
	private JedisPool jedisPool;
	private RuntimeSchema<QRCodeLoginInfo> qrCodeLoginInfoSchema = RuntimeSchema.createFrom(QRCodeLoginInfo.class);
	private String password;
	private String key = "QRCodeLogin:"; // 业务场景
	
	//目录分隔符只能使用File.separator，\\只能在windows中使用

	/*
	 * // 必须加上网络超时的设置 public Jedis getJedis() { int timeoutCount = 0; while
	 * (true) // 如果是网络超时则多试几次 { try { Jedis jedis = jedisPool.getResource();
	 * return jedis; } catch (Exception e) { //
	 * 底层原因是SocketTimeoutException，不过redis已经捕捉且抛出JedisConnectionException，不继承于前者
	 * if (e instanceof JedisConnectionException || e instanceof
	 * SocketTimeoutException) { timeoutCount++; //
	 * log.warn("getJedis timeoutCount={}", timeoutCount); if (timeoutCount > 3)
	 * { break; } } else { // log.warn("jedisInfo。NumActive=" + //
	 * RedisPool.getBalancePool().getNumActive() + ", NumIdle=" // +
	 * RedisPool.getNumIdle() + ", NumWaiters=" // + RedisPool.getNumWaiters() +
	 * ", isClosed=" // + RedisPool.isClosed()); // log.error("getJedis error",
	 * e); break; } } } return null; }
	 */

	public RedisQRCodeLoginInfoDAO() {

		// 从配置文件中读取ip,port,password
		Properties properties = new Properties();
		FileInputStream fileInputStream;
		try {
			// 获取类路径
			fileInputStream = new FileInputStream(
					this.getClass().getResource("/").getPath() + File.separator+"config"+File.separator+"redis.properties");

			properties.load(fileInputStream);
			this.password = properties.getProperty("password");

			JedisPoolConfig config = new JedisPoolConfig();
			this.jedisPool = new JedisPool(config, properties.getProperty("ip"),
					Integer.valueOf(properties.getProperty("port").trim()), 10000, properties.getProperty("password"),
					0);
			fileInputStream.close();

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}

		// 以下配置需要使用时开启

		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		// config.setBlockWhenExhausted(true);

		// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		// config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

		// 是否启用pool的jmx管理功能, 默认true
		// config.setJmxEnabled(true);

		// 是否启用后进先出, 默认true
		// config.setLifo(true);

		// 最大空闲连接数, 默认8个
		// config.setMaxIdle(8);

		// 最大连接数, 默认8个
		// config.setMaxTotal(8);

		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		// config.setMaxWaitMillis(-1);

		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		// config.setMinEvictableIdleTimeMillis(1800000);

		// 最小空闲连接数, 默认0
		// config.setMinIdle(0);

		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		// config.setNumTestsPerEvictionRun(3);

		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
		// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
		// config.setSoftMinEvictableIdleTimeMillis(1800000);

		// 在获取连接的时候检查有效性, 默认false
		// config.setTestOnBorrow(false);

		// 在空闲时检查有效性, 默认false
		// config.setTestWhileIdle(false);

		// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		// config.setTimeBetweenEvictionRunsMillis(-1);

	}

	public QRCodeLoginInfo get(String UID) {
		try {
			// 从redis连接池获取连接
			Jedis jedis = this.jedisPool.getResource();

			if (!this.password.equals("")) {
				// 如果redis设置了密码，这里必须 加上密码认证
				jedis.auth(this.password);
			}

			try {
				// redis并没有实现序列化操作,需要引用第3方序列化插件
				byte[] qrCodeLoginInfoBytes = jedis.get((this.key + UID).getBytes());
				if (qrCodeLoginInfoBytes != null) {
					// 获取redis缓存中的值
					QRCodeLoginInfo qrCodeLoginInfo = qrCodeLoginInfoSchema.newMessage();
					// 利用第三方工具反序列化成对象
					ProtostuffIOUtil.mergeFrom(qrCodeLoginInfoBytes, qrCodeLoginInfo, qrCodeLoginInfoSchema);
					return qrCodeLoginInfo;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return null;
	}

	// 缓存UID对应的登录信息
	public String set(String UID, QRCodeLoginInfo qrCodeLoginInfo) {
		try {
			// 从redis连接池获取连接
			Jedis jedis = this.jedisPool.getResource();

			if (!this.password.equals("")) {
				// 如果redis设置了密码，这里必须 加上密码认证
				jedis.auth(this.password);
			}

			try {
				byte[] qrCodeLoginInfoBytes = ProtostuffIOUtil.toByteArray(qrCodeLoginInfo, qrCodeLoginInfoSchema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

				// 5分钟后失效 单位为秒
				return jedis.setex((this.key + UID).getBytes(), 5 * 60, qrCodeLoginInfoBytes);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			} finally {
				jedis.close();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}
}
