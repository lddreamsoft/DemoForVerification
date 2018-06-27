package ldsoft.hlhh.wx.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ldsoft.hlhh.web.entity.DrawInfo;
import ldsoft.hlhh.web.entity.Prize;
import ldsoft.hlhh.web.service.MemberService;
import ldsoft.hlhh.wx.dao.cache.RedisDrawInfoDAO;
import ldsoft.hlhh.wx.entity.WinningRecord;
import ldsoft.hlhh.wx.service.LotteryDrawService;
import ldsoft.hlhh.wx.service.WinningRecordService;
import ldsoft.hlhh.wx.utils.WXUtils;

@Controller
@RequestMapping("/wx/activities")

// url规则："/模块/资源/集合"
public class LuckyShakeController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private WinningRecordService winningRecordService;

	@Autowired
	private LotteryDrawService lotteryDrawService;

	private RedisDrawInfoDAO redisDrawInfoDAO = new RedisDrawInfoDAO();

	// 获取当日已抽奖次数
	@RequestMapping(value = "/luckyShake/getDrawTimesOfToday/{activityID}/{openID}", method = RequestMethod.GET)
	@ResponseBody
	public int getDrawTimesOfToday(@PathVariable String openID) {

		int result = 3; // 默认赠送抽奖次数

		if (redisDrawInfoDAO.getDrawInfo(openID) == null) {
			// 今日未抽奖
			return result;
		} else {

			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			if (fmt.format(redisDrawInfoDAO.getDrawInfo(openID).getDrawDate()).toString()
					.equals(fmt.format(new Date()).toString())) {// 格式化为相同格式
				return redisDrawInfoDAO.getDrawInfo(openID).getDrawTimes();
			} else {
				return result;
			}
		}
	}

	// 当日未抽奖
	private Prize neverDrawToday(String openID) {
		Prize result = new Prize();
		
		/*
		DrawInfo tempDrawInfo = memberService.isSubscribed(openID);
		if (tempDrawInfo != null) 
		{
			isSubscribed=tempDrawInfo.getIsSubscribed();
		}*/

		DrawInfo drawInfo = new DrawInfo();
		drawInfo.setDrawDate(new Date());
		drawInfo.setLastDrawTime(System.currentTimeMillis());
		drawInfo.setIsSubscribed((byte)0);//默认未关注
		drawInfo.setDrawTimes(3);//默认3次免费抽奖机会

		DrawInfo tempMember = memberService.queryMemberPoints(openID);
		if (tempMember != null) {
			drawInfo.setIsSubscribed(tempMember.getIsSubscribed());
				if (tempMember.getPointers() != -99999) {
					drawInfo.setDrawTimes((int) Math.floor(tempMember.getPointers() / 5));
					drawInfo.setWebSiteMember(true);
					drawInfo.setMemberID(tempMember.getMemberID()); // 该值在一天内可能会发生变化
				}
		}

		redisDrawInfoDAO.setDrawInfo(openID, drawInfo);

		// 扣减次数  判断是否为未关注用户，未关注用户因存在每日3次免费抽奖次数，直接进入抽奖环节
		if (drawInfo.getDrawTimes() > 0||drawInfo.getIsSubscribed()==0) {
			
			// 扣除次数
			if(drawInfo.getDrawTimes() > 0)
			{
				drawInfo.setDrawTimes(drawInfo.getDrawTimes() - 1);
			}	

			// 抽奖
			result = lotteryDrawService.draw(1, openID, drawInfo.getMemberID(), drawInfo.isWebSiteMember(), true,
					drawInfo.getIsSubscribed());
			if (result != null) // 中奖
			{

				// 抽奖之后一定是会员
				drawInfo.setWebSiteMember(true);
				drawInfo.setMemberID(result.getWebSiteMemberID());

				// 可能抽中积分，从数据库中获取最新的会员积分并更新内存中的drawTimes
				//drawInfo.setDrawTimes((int) Math.floor(memberService.queryMemberPoints(openID).getPointers()/ 5));
				long memberPointers=memberService.queryMemberPoints(openID).getPointers();
				drawInfo.setDrawTimes((int) Math.floor(memberPointers/ 5));
				result.setWebSiteMemberPointers(memberPointers);
			}

			// 抽奖后再次更新最后一次抽奖时间，防止重复抽奖
			drawInfo.setLastDrawTime(System.currentTimeMillis());

			redisDrawInfoDAO.setDrawInfo(openID, drawInfo);

		} else {
			result.setChance(-1);// 当日次数用尽标志
		}

		return result;

	}
	


	// synchronized 这个必须要加上，否则请求会发送过于频繁
	@RequestMapping(value = "/luckyShake/draw/{activityID}/{openID}", method = RequestMethod.GET)
	@ResponseBody
	public synchronized Prize drawEntry(@PathVariable String openID) {

		// 同时支持用户间断摇动和连续不间断摇动，中奖后前端弹出模态对话框，用户无法摇动，未中奖次数足够可一直摇动
		Prize result = new Prize();
		// Calendar.getInstance();

		if (redisDrawInfoDAO.getDrawInfo(openID) != null) {
			System.out.println("当前毫秒：" + System.currentTimeMillis());
			System.out.println("最后一次请求的毫秒：" + redisDrawInfoDAO.getDrawInfo(openID).getLastDrawTime());
			System.out.println(
					"差值：" + (System.currentTimeMillis() - redisDrawInfoDAO.getDrawInfo(openID).getLastDrawTime()));
		}

		if (redisDrawInfoDAO.getDrawInfo(openID) == null) {
			// 从未抽奖，设置默认值
			result = neverDrawToday(openID);

		} else if (System.currentTimeMillis() - redisDrawInfoDAO.getDrawInfo(openID).getLastDrawTime() < 2000) {

			// 距离上次成功处理接收间隔小于2秒的请求丢弃
			result.setChance(-2); // 同一个人摇一摇的间隔小于1秒，丢弃请求
			return result;
		} else {
			DrawInfo drawInfo = new DrawInfo();

			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			if (fmt.format(redisDrawInfoDAO.getDrawInfo(openID).getDrawDate()).toString()
					.equals(fmt.format(new Date()).toString())) {// 格式化为相同格式

				// 今天已抽奖，从缓存中获取抽奖信息
				drawInfo.setDrawDate(redisDrawInfoDAO.getDrawInfo(openID).getDrawDate());
				drawInfo.setDrawTimes(redisDrawInfoDAO.getDrawInfo(openID).getDrawTimes());//默认内存中的抽奖次数，因积分可能会变化，该值不一定正确
				drawInfo.setLastDrawTime(System.currentTimeMillis());
				drawInfo.setWebSiteMember(redisDrawInfoDAO.getDrawInfo(openID).isWebSiteMember());
				drawInfo.setIsSubscribed((byte) 0); // 默认未关注

				// drawInfo.setMemberID(redisDrawInfoDAO.getDrawInfo(openID).getMemberID());//不属于自身控制的外部信息可能会发生变化，不能从缓存中获取
				DrawInfo tempMember = memberService.queryMemberPoints(openID);
				if (tempMember != null) {
					drawInfo.setIsSubscribed(tempMember.getIsSubscribed());
					if (tempMember.getPointers() != -99999) {
						drawInfo.setDrawTimes((int) Math.floor(tempMember.getPointers() / 5));
						drawInfo.setWebSiteMember(true);
						drawInfo.setMemberID(tempMember.getMemberID()); // 该值在一天内可能会发生变化
					}
				}

				if (redisDrawInfoDAO.getDrawInfo(openID).getDrawTimes() > 0) // 存在抽奖次数
				{
					// 扣除次数
					drawInfo.setDrawTimes(redisDrawInfoDAO.getDrawInfo(openID).getDrawTimes() - 1);

					// 抽奖
					result = lotteryDrawService.draw(1, openID, drawInfo.getMemberID(), drawInfo.isWebSiteMember(),
							false, drawInfo.getIsSubscribed());

					if (result != null) // 中奖
					{
						// 抽奖之后一定是会员
						drawInfo.setWebSiteMember(true);
						drawInfo.setMemberID(result.getWebSiteMemberID());

						// 可能抽中积分，从数据库中获取最新的会员积分并更新内存中的drawTimes
						long memberPointers=memberService.queryMemberPoints(openID).getPointers();
						drawInfo.setDrawTimes((int) Math.floor(memberPointers/ 5));
						result.setWebSiteMemberPointers(memberPointers);
					}

					// 抽奖后再次更新最后一次抽奖时间，防止重复抽奖
					drawInfo.setLastDrawTime(System.currentTimeMillis());

					// openIDDrawInfo.put(openID, member);
					redisDrawInfoDAO.setDrawInfo(openID, drawInfo);

				} else {
					result.setChance(-1);// 当日次数用尽标志
				}
			} else {
				// 今天未抽奖
				result = neverDrawToday(openID);
			}
		}

		return result;
	}

	@RequestMapping(value = "/luckyShake/winningRecord/{activityID}/{openID}", method = RequestMethod.GET)
	@ResponseBody
	public List<WinningRecord> queryWinningRecordByOpenID(@PathVariable int activityID, @PathVariable String openID) {
		return winningRecordService.queryByActIDOpenID(activityID, openID);
	}

	@RequestMapping(value = "/luckyShake/winningRecord/{activityID}/{offset}/{limit}", method = RequestMethod.GET)
	@ResponseBody
	public List<WinningRecord> queryWinningRecord(@PathVariable int activityID, @PathVariable int offset,
			@PathVariable int limit) {
		return winningRecordService.queryAll(activityID, offset, limit);
	}

	@RequestMapping(value = "/luckyShake")
	public String luckyShake(HttpServletRequest request, Model model) {

		// 用户点击菜单发请求到微信服务器获取code，然后把返回的code送到该回调地址，code用于获取微信WebAccessToken，WebAccessToken用于获取openID
		// code获取成功redirect_uri/?code=CODE&state=STATE
		// code获取失败redirect_uri?state=STATE

		if (request.getParameter("state") != null) {
			// 请求来自微信客户端
			if (request.getParameter("state").equals("fromWX")) {
				if (request.getParameter("code") != null) {
					// 通过code获取openID

					// 发请求到微信服务器
					JSONObject jsonObject = WXUtils.get(WXUtils.getWXPageAccessTokenUrl(request.getParameter("code")));

					if (jsonObject != null && jsonObject.getString("openid") != null) {
						model.addAttribute("openID", jsonObject.getString("openid")); // 设置值
					} else {
						// 未获取到openID
						model.addAttribute("openID", "didn't get openID from WX"); // 设置值
					}

					// 同时获取微信JSAPI信息发送给客户端

					// 这里必须获取完整带参数的url，否则报签名错误
					StringBuffer fullUrl = request.getRequestURL(); // 请求路径
					String queryString = request.getQueryString(); // 查询参数
					if (!queryString.equals("")) {
						fullUrl.append("?");
						fullUrl.append(queryString);
					}

					model.addAttribute("WXJSAPIInfo", WXUtils.getWXJSAPIInfo(fullUrl.toString()));
				}

			}
		}

		return "luckyShake/luckyShake";
	}

}
