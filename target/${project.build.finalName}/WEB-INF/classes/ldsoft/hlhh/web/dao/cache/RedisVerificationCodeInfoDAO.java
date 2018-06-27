package ldsoft.hlhh.web.dao.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import ldsoft.hlhh.web.entity.VerificationCodeInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//用于在redis中缓存验证码信息
public class RedisVerificationCodeInfoDAO {

	private final Logger logger = LoggerFactory.getLogger(RedisVerificationCodeInfoDAO.class);

	private JedisPool jedisPool;

	private RuntimeSchema<VerificationCodeInfo> verificationCodeInfoSchema = RuntimeSchema
			.createFrom(VerificationCodeInfo.class);
	private String password;
	private String key = "VerificationCode:"; // 业务场景

	// 清除redis缓存
	public void clear(String UID) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.del(this.key + UID);
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			jedis.close();
		}
	}

	public RedisVerificationCodeInfoDAO() {

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

			// Jedis jedis = jedisPool.getResource();
			// 构造时不能获取连接，具体使用时再获取连接，使用完毕后需关闭连接

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

	public VerificationCodeInfo get(String UID) {

		Jedis jedis = null;

		try {
			VerificationCodeInfo verificationCodeInfo = null;

			// 从redis连接池获取连接
			jedis = jedisPool.getResource();

			if (!this.password.equals("")) {
				// 如果redis设置了密码，这里必须 加上密码认证
				jedis.auth(this.password);
			}

			// redis并没有实现序列化操作,需要引用第3方序列化插件
			byte[] verificationCodeInfoBytes = jedis.get((this.key + UID).getBytes());
			if (verificationCodeInfoBytes != null) {
				// 获取redis缓存中的值
				verificationCodeInfo = verificationCodeInfoSchema.newMessage();
				// 利用第三方工具反序列化成对象
				ProtostuffIOUtil.mergeFrom(verificationCodeInfoBytes, verificationCodeInfo, verificationCodeInfoSchema);
				return verificationCodeInfo;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			jedis.close();
		}

		return null;

	}

	// 缓存UID对应的登录信息 成功redis返回字符串：OK
	public String set(String UID, VerificationCodeInfo verificationCodeInfo) {

		Jedis jedis = null;

		try {
			// 从redis连接池获取连接
			jedis = jedisPool.getResource();

			if (!this.password.equals("")) {
				// 如果redis设置了密码，这里必须 加上密码认证
				jedis.auth(this.password);
			}

			byte[] verificationCodeInfoBytes = ProtostuffIOUtil.toByteArray(verificationCodeInfo,
					verificationCodeInfoSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

			// 验证码5分钟后失效 单位为秒
			return jedis.setex((this.key + UID).getBytes(), 5 * 60, verificationCodeInfoBytes);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			jedis.close();
		}

	}
}
