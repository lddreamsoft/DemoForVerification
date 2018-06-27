package ldsoft.hlhh.wx.dao.cache;


import java.net.SocketTimeoutException;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import ldsoft.hlhh.wx.entity.SecKill;
import ldsoft.hlhh.wx.entity.WXAccessToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


public class RedisDAO
{

	//注入spring管理的redis配置文件
	//private JedisPoolConfig jedisPoolConfig; 密码配置并不在连接池中
	
	private JedisPool jedisPool;	
	private RuntimeSchema<SecKill> schemaSecKill=RuntimeSchema.createFrom(SecKill.class);
	private RuntimeSchema<WXAccessToken> schemaWXAccessToken=RuntimeSchema.createFrom(WXAccessToken.class);
	
	
	//必须加上网络超时的设置
	public Jedis getJedis()  
	{  
	    int timeoutCount = 0;  
	    while (true) // 如果是网络超时则多试几次  
	    {  
	        try  
	        {  
	            Jedis jedis = jedisPool.getResource();  
	            return jedis;  
	        } catch (Exception e)  
	        {  
	            // 底层原因是SocketTimeoutException，不过redis已经捕捉且抛出JedisConnectionException，不继承于前者  
	            if (e instanceof JedisConnectionException || e instanceof SocketTimeoutException)  
	            {  
	                timeoutCount++;  
	                //log.warn("getJedis timeoutCount={}", timeoutCount);  
	                if (timeoutCount > 3)  
	                {  
	                    break;  
	                }  
	            }else  
	            {  
//	                log.warn("jedisInfo。NumActive=" + RedisPool.getBalancePool().getNumActive() + ", NumIdle="  
//	                        + RedisPool.getNumIdle() + ", NumWaiters="  
//	                        + RedisPool.getNumWaiters() + ", isClosed="  
//	                        + RedisPool.isClosed());  
//	                log.error("getJedis error", e);  
	                break;  
	            }  
	        }  
	    }  
	    return null;  
	}  
	
	public RedisDAO(String ip,int port)
	{					
		JedisPoolConfig config = new JedisPoolConfig();
		//以下配置需要使用时开启
		
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		//config.setBlockWhenExhausted(true);
		
		//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		//config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		 
		//是否启用pool的jmx管理功能, 默认true
		//config.setJmxEnabled(true);
		
		//是否启用后进先出, 默认true
		//config.setLifo(true);
		
		//最大空闲连接数, 默认8个
		//config.setMaxIdle(8);
		
		//最大连接数, 默认8个
		//config.setMaxTotal(8);
		
		//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
		//config.setMaxWaitMillis(-1);
		
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		//config.setMinEvictableIdleTimeMillis(1800000);
		 
		//最小空闲连接数, 默认0
		//config.setMinIdle(0);
		
		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		//config.setNumTestsPerEvictionRun(3);
		
		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		//config.setSoftMinEvictableIdleTimeMillis(1800000);
		
		//在获取连接的时候检查有效性, 默认false
		//config.setTestOnBorrow(false);
		 
		//在空闲时检查有效性, 默认false
		//config.setTestWhileIdle(false);
		
		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		//config.setTimeBetweenEvictionRunsMillis(-1);
		
		jedisPool=new JedisPool(config,ip,port,10000,"redispasswordfromld",0);	
	}
	
	//从redis获取微信accessToken
	public WXAccessToken getWXAccessToken()
	{

		WXAccessToken wxAccessToken;
		try{
			//从redis连接池获取连接
			Jedis jedis =getJedis();
			jedis.auth("redispasswordfromld"); //如果redis设置了密码，这里必须 加上密码认证
			try
			{
				String key="WXAccessToken";
				//redis并没有实现序列化操作,需要引用第3方序列化插件
				byte[]  bytes=jedis.get(key.getBytes());				
				if(bytes!=null)
				{
				    //获取redis缓存中的值 					
					wxAccessToken=schemaWXAccessToken.newMessage();
					//利用第三方工具反序列化成对象
					ProtostuffIOUtil.mergeFrom(bytes, wxAccessToken, schemaWXAccessToken);
				}
				else {
					return null;
				}
			}
			finally
			{
				jedis.close();				
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return wxAccessToken;
	}
	
	public SecKill getSecKill(long secKillId)
	{
		try{
			//从redis连接池获取连接
			Jedis jedis =getJedis();
			jedis.auth("redispasswordfromld"); //如果redis设置了密码，这里必须 加上密码认证
			try
			{
				String key="secKillId:"+secKillId;
				//redis并没有实现序列化操作,需要引用第3方序列化插件
				byte[]  bytes=jedis.get(key.getBytes());				
				if(bytes!=null)
				{
				    //获取redis缓存中的值 					
					SecKill secKill=schemaSecKill.newMessage();
					//利用第三方工具反序列化成对象
					ProtostuffIOUtil.mergeFrom(bytes, secKill, schemaSecKill);
					return secKill;				
				}				
			}
			finally
			{
				jedis.close();				
			}
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	
	//将微信AccessToken对象存储至redis
	public String setWXAccessToken(WXAccessToken wxAccessToken)
	{
		String result="";
		
		try 
		{
			//从redis连接池获取连接
			Jedis jedis =getJedis();
			jedis.auth("redispasswordfromld"); //如果redis设置了密码，这里必须 加上密码认证
			try
			{
				String key="WXAccessToken";
				byte[] bytes=ProtostuffIOUtil.toByteArray(wxAccessToken, schemaWXAccessToken, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				jedis.setex(key.getBytes(), 2*60*60, bytes); //2小时后缓存失效，注意：具体失效时间以wxAccessToken对象内的失效时间为准
				result="Success";
			}
			finally
			{
				jedis.close();				
			}
			
		}catch(Exception e)
		{
			result="Failed";
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	//缓存对象
	public String setSecKill(SecKill secKill)
	{
		try 
		{
			//从redis连接池获取连接
			//Jedis jedis=jedisPool.getResource();
			
			Jedis jedis =getJedis();
			jedis.auth("redispasswordfromld"); //如果redis设置了密码，这里必须加上密码认证
			try
			{
				String key="secKillId:"+secKill.getSeckill_id();
				byte[] bytes=ProtostuffIOUtil.toByteArray(secKill, schemaSecKill, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				String result=jedis.setex(key.getBytes(), 60*60, bytes); //1小时后缓存失效

				return result;
			}
			finally
			{
				jedis.close();				
			}
			
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return null;
	}
}
