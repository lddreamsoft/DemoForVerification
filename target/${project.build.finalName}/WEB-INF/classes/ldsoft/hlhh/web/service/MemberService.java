package ldsoft.hlhh.web.service;


import ldsoft.hlhh.web.entity.DrawInfo;
import ldsoft.hlhh.web.entity.WebSiteMember;

public interface MemberService {

	DrawInfo isSubscribed(String openID);
	
	//根据openID查询会员可用积分
	DrawInfo queryMemberPoints(String openID);
	
	int insertWXMember(String openID);
	
	int updateMemberPointers(int memberID,int memberPointers);
	
	int insertWebSiteMember(WebSiteMember member);
}
