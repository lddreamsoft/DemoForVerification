package ldsoft.hlhh.web.dao;

import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.web.entity.DrawInfo;
import ldsoft.hlhh.web.entity.WebSiteMember;

public interface MemberDAO {
	
	DrawInfo isSubscribed(@Param("openID") String openID);
	
	DrawInfo queryMemberPoints(@Param("openID") String openID);
	
	long queryWebSiteMemberPoints(@Param("memberID") int memberID);
	
	int insertWXMember(@Param("openID") String openID);
	
	int updateMemberPointers(@Param("memberID") int memberID,@Param("memberPointers") int memberPointers);
	
	int insertWebSiteMember(WebSiteMember webSiteMember);
	
	int insertMemberPointers(@Param("memberID") int memberID,@Param("memberPointers") int memberPointers);
	
	int updateWXMember(@Param("openID") String openID,@Param("memberID") int memberID);
}
