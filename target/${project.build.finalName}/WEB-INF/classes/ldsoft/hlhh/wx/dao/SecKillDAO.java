package ldsoft.hlhh.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.wx.entity.SecKill;

import java.util.Date;

public interface SecKillDAO {

	//扣库存
	int reduceNumber(@Param("secKillId") long secKillId, @Param("secKillTime") Date secKillTime);
	
	SecKill queryById(long secKillId);
	
	//在多于1个参数的情况下，必须指定@Param
	List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
	
}
