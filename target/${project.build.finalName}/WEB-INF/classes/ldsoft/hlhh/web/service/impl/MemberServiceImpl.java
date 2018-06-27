package ldsoft.hlhh.web.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ldsoft.hlhh.web.dao.MemberDAO;
import ldsoft.hlhh.web.entity.DrawInfo;
import ldsoft.hlhh.web.entity.WebSiteMember;
import ldsoft.hlhh.web.service.MemberService;


@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO memberDAO;

	@Override
	public DrawInfo queryMemberPoints(String openID) {
		return memberDAO.queryMemberPoints(openID);
	}

	@Override
	public int insertWXMember(String openID) {
		return memberDAO.insertWXMember(openID);
	}

	@Override
	public int updateMemberPointers(int memberID, int memberPointers) {
		return memberDAO.updateMemberPointers(memberID, memberPointers);
	}

	@Override
	public int insertWebSiteMember(WebSiteMember webSiteMember) {
		return memberDAO.insertWebSiteMember(webSiteMember);
	}

	@Override
	public DrawInfo isSubscribed(String openID) {
		return memberDAO.isSubscribed(openID);
	}
	
}
