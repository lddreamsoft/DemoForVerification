package ldsoft.hlhh.wx.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.wx.entity.WXMember;

public interface WXMemberDAO {
	
	List<WXMember> select(@Param("openID") String openID);

	int insert(@Param("openID") String openID,@Param("isSubscribed") String isSubscribed);

	int update(@Param("openID") String openID,@Param("isSubscribed") String isSubscribed);
	
	int delete(@Param("openID") String openID);
}
