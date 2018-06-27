package ldsoft.hlhh.wx.dao;


import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.wx.entity.SecKillDetails;

public interface SecKillDetailsDAO {

	int insertSecKill(@Param("secKillId") long secKillId,@Param("userPhone") long userPhone);
	
	SecKillDetails queryByIdWithSecKill(long secKillId);
	
}
