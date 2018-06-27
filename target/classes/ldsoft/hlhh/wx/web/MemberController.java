package ldsoft.hlhh.wx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ldsoft.hlhh.web.entity.DrawInfo;
import ldsoft.hlhh.web.service.MemberService;


@Controller
@RequestMapping("/wx/member")

// url规则："/模块/资源/集合"
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/queryMemberPoints/{openID}", method = RequestMethod.GET)
	@ResponseBody
	public DrawInfo queryMemberPoints(@PathVariable String openID) {
		return memberService.queryMemberPoints(openID);
	}
	
	@RequestMapping(value = "/insertWXMember/{openID}", method = RequestMethod.GET)
	@ResponseBody
	public int insertWXMember(@PathVariable String openID) {
		return memberService.insertWXMember(openID);
	}
	
	@RequestMapping(value = "/updateMemberPointers/{memberID}/{memberPointers}", method = RequestMethod.GET)
	@ResponseBody
	public int updateMemberPointers(@PathVariable int memberID,@PathVariable int memberPointers) {
		return memberService.updateMemberPointers(memberID, memberPointers);
	}
	
}
