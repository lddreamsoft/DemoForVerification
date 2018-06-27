package ldsoft.hlhh.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.web.entity.Member;

public interface MemberDAO1 {
	List<Member> selectMember(@Param("name") String name); 
	int insertMember(@Param("name") String name,@Param("password") String password,@Param("nickName") String nickName);
	int updateMember(@Param("name") String name,@Param("nickName") String nickName);
	int updateMemberPwd(@Param("name") String name,@Param("password") String password);
}
