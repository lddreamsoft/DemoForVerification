package ldsoft.hlhh.wx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ldsoft.hlhh.web.dao.ActivityPrizeDAO;
import ldsoft.hlhh.web.dao.MemberDAO;
import ldsoft.hlhh.web.dao.SequenceDAO;
import ldsoft.hlhh.web.entity.Prize;
import ldsoft.hlhh.web.entity.WebSiteMember;
import ldsoft.hlhh.wx.dao.WinningRecordDAO;
import ldsoft.hlhh.wx.exception.ActivityDrawException;
import ldsoft.hlhh.wx.service.LotteryDrawService;
import ldsoft.hlhh.utils.Tools;

@Service
public class LotteryDrawServiceImpl implements LotteryDrawService {

	@Autowired
	private ActivityPrizeDAO activityPrizeDAO;

	@Autowired
	private WinningRecordDAO winningRecordDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private SequenceDAO sequenceDAO;

	@Override
	@Transactional
	public Prize draw(int activityID, String openID, int memberID, boolean isWebSiteMember, boolean isFirstDrawToday,
			byte isSubscribed) {

		int result;
		int currentMemberID = memberID;
		Prize prize = new Prize();
		boolean won = false;
		
		//不管当天有没有抽奖，先读取最新的会员积分
		//prize.setWebSiteMemberPointers(memberDAO.queryWebSiteMemberPoints(memberID));
		prize.setWebSiteMemberID(currentMemberID);

		// 1.未关注公众号用户每天第一次抽奖必中15会员积分=3次抽奖机会，不管活动设置中是否设置该奖项
		// 2.未关注公众号用户整个活期期间第一次抽奖必中20会员积分，不管活动设置中是否设置该奖项。必中后不参与后续抽奖
		if (isFirstDrawToday) {

			if (isWebSiteMember) {

				// 插入中奖记录表
				result = winningRecordDAO.insertWinningRecord(activityID, openID, "会员积分", "每日摇一摇赠送", 15, "", "");
				if (result != 1) {
					throw new ActivityDrawException("insert data to activitieswinningrecords failed.");
				}

				// 插入会员积分表
				result = memberDAO.updateMemberPointers(currentMemberID, 15);
				if (result != 1) {
					throw new ActivityDrawException("update member points for t_member_integral failed.");
				}

				//prize = new Prize();
				//prize.setWebSiteMemberPointers(15);//需要加上原始会员积分

			} else {
				
				// 自动注册网站会员
				// 增加会员积分表积分
				// 从会员积分表获取会员积分

				long maxWBMemberID = sequenceDAO.querySequence("maxWebSiteMemberID");
				sequenceDAO.updateSequence("maxWebSiteMemberID");

				// 不足6位补齐6位
				String prefix = "";
				for (int j = 0; j < 6 - String.valueOf(maxWBMemberID).length(); j++) {
					prefix += "0";
				}
				prefix += String.valueOf(maxWBMemberID);

				WebSiteMember webSiteMember = new WebSiteMember();
				webSiteMember.setId(-1);
				webSiteMember.setName("Y" + prefix);// 规则：Yxxxxxx
				webSiteMember.setPassword(Tools.getSimpleRandomString(6));// 初始密码：随机
				webSiteMember.setType_id("0");
				webSiteMember.setSourceChannel(2);

				// 插入会员表
				memberDAO.insertWebSiteMember(webSiteMember);
				if (webSiteMember.getId() == -1) {
					throw new ActivityDrawException("insert member to t_member_info failed.");
				} else {
					currentMemberID = webSiteMember.getId();
				}

				int memberPointers = 0;

				// 查询是否存在首次摇一摇中奖记录，不存在则直接赠送会员积分20，且不再参与后续抽奖
				if (winningRecordDAO.isFirstDrawOfActivity(activityID, openID) == null) {

					// 插入中奖记录表
					result = winningRecordDAO.insertWinningRecord(activityID, openID, "会员积分", "首次摇一摇赠送", 20, "", "");
					if (result != 1) {
						throw new ActivityDrawException("insert data to activitieswinningrecords failed.");
					}

					// 插入会员积分表
					memberPointers = 20;
					/*
					 * result = memberDAO.insertMemberPointers(currentMemberID,
					 * 20); if (result != 1) { throw new
					 * ActivityDrawException("insert member points to t_member_integral failed."
					 * ); }
					 */

					//prize = new Prize();
					// 虚拟必中积分奖品
					prize.setChance(-999);
					prize.setLimits(20);
					prize.setPictures("chest.png,");
					//prize.setWebSiteMemberPointers(20);//需要加上原始积分++
					won = true;
				}

				// 插入会员积分表
				result = memberDAO.insertMemberPointers(currentMemberID, 15 + memberPointers);
				if (result != 1) {
					throw new ActivityDrawException("insert member points to t_member_integral failed.");
				}

				// 插入中奖记录表
				result = winningRecordDAO.insertWinningRecord(activityID, openID, "会员积分", "每日摇一摇赠送", 15, "", "");
				if (result != 1) {
					throw new ActivityDrawException("insert data to activitieswinningrecords failed.");
				}

				// 更新微信表中的会员ID openID到了抽奖环节一定在表中存在
				result = memberDAO.updateWXMember(openID, webSiteMember.getId());
				if (result != 1) {
					throw new ActivityDrawException("update member id for t_wechat_member failed.");
				}

				

			}
		} 

		prize.setWebSiteMemberID(currentMemberID);
		
		// 如果用户为网站会员 ，扣减积分数，抽奖一次扣5分
		// if(isWebSiteMember) 到这里一定是网站会员
		{
			result = memberDAO.updateMemberPointers(currentMemberID, -5);
			if (result != 1) {
				// 积分余额不够,退出抽奖过程
				throw new ActivityDrawException("update member points for t_member_integral failed. 会员积分余额不够");
			}
			//prize.setWebSiteMemberPointers(prize.getWebSiteMemberPointers() - 5);
		}

		if (won)
			return prize;// 已中会员积分20，退出抽奖过程

		// 从数据库实时获取中奖概率（因库存和概率会不定时更新）
		// 并发抽奖时，可能出现查询时有库存，但扣减时库存不足，需要重新抽奖
		List<Prize> prizes = activityPrizeDAO.queryById(activityID, (byte) 0);// 结果已按中奖概率从低到高排序并只取库存>0的商品
		float randomValue = Tools.getRandomValue(); // 中奖结果

		for (int i = 0; i < prizes.size(); i++) {

			if (prizes.get(i).getStock() > 0) {
				// 库存大于0
				if (randomValue <= (isSubscribed==0?(prizes.get(i).getChance() / 100)+(prizes.get(i).getChance1() / 100):(prizes.get(i).getChance() / 100)+(prizes.get(i).getChance2() / 100))) {
					// 扣减库存，判断是否扣减成功，扣减成功则中奖，否则重新抽奖
					result = activityPrizeDAO.deductActivityPrizeStock(prizes.get(i).getId());
					if (result == 1) {

						String code = "";
						if (prizes.get(i).getLimits() == 0) {
							// 实物奖品，生成兑换码
							code = Tools.getSimpleRandomString(6);
						}

						result = winningRecordDAO.insertWinningRecord(activityID, openID, prizes.get(i).getName(),
								prizes.get(i).getDescription(), prizes.get(i).getLimits(), prizes.get(i).getPictures(),
								code);

						if (result == 1) {

							// 中奖，将结果存储至winningRecords
							/*
							 * if (prize == null) { prize = new Prize(); prize =
							 * prizes.get(i); prize.setStock(prize.getStock() -
							 * 1); //传递到客户端后未使用 } else {
							 */
							prize.setChance(5);//prizes.get(i).getChance() 设置固定值5
							prize.setDescription(prizes.get(i).getDescription());
							prize.setId(prizes.get(i).getId());
							prize.setLimits(prizes.get(i).getLimits());
							prize.setName(prizes.get(i).getName());
							prize.setOrderNumber(prizes.get(i).getOrderNumber());
							prize.setPictures(prizes.get(i).getPictures());
							prize.setStock(prizes.get(i).getStock() - 1); // 传递到客户端后未使用
							// }

							// 如果奖品为积分，判断是否为网站会员，如果是网站会员同时增加会员积分，增加失败回滚事务，记录日志++
							if (prizes.get(i).getLimits() != 0) {
								// winningRecordDAO.deleteWinningRecord(id);
								// if(isWebSiteMember)
								// {

								// 到这里一定是网站会员
								result = memberDAO.updateMemberPointers(currentMemberID, prizes.get(i).getLimits());
								if (result != 1) {
									throw new ActivityDrawException(
											"update member points for t_member_integral failed.");
								}

								//prize.setWebSiteMemberID(currentMemberID);
								// 获取最新的会员积分
								//prize.setWebSiteMemberPointers(memberDAO.queryWebSiteMemberPoints(currentMemberID));
							}

							break; // 退出循环
						} else {
							// 中奖记录表数据未插入成功，终止抽奖，回滚事务，记录日志++
							throw new ActivityDrawException("insert data to activitieswinningrecords failed.");
							// break;
						}
					} else {
						// 库存未扣减成功重新抽奖
						draw(activityID, openID, currentMemberID, isWebSiteMember, false, isSubscribed);// 是否当天抽奖固定传false
					}
				} else {
					// 未中此等奖
					continue;
				}
			} else {
				// 库存不够
				continue;
			}
		}

		return prize;
	}

}
