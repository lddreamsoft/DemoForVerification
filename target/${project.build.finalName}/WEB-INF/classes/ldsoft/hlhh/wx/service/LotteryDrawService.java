package ldsoft.hlhh.wx.service;


import ldsoft.hlhh.web.entity.Prize;

public interface LotteryDrawService {
	
	//活动抽奖
	Prize draw(int activityID,String openID,int memberID,boolean isWebSiteMember,boolean isFirstDrawToday,byte isSubscribed);
	
}
