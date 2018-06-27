package ldsoft.hlhh.web.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ldsoft.hlhh.web.dao.ActivityPrizeDAO;
import ldsoft.hlhh.web.entity.Prize;
import ldsoft.hlhh.web.service.ActivityPrizeService;


@Service
public class ActivityPrizeServiceImpl implements ActivityPrizeService{
	
	@Autowired
	private ActivityPrizeDAO activityPrizeDAO;

	@Override
	public List<Prize> queryById(int activityID,byte fromMg) {
		return activityPrizeDAO.queryById(activityID,fromMg);
	}

	@Override
	public int insertActivityPrize(int activityID, int prizeID) {
		return activityPrizeDAO.insertActivityPrize(activityID, prizeID);
	}

	@Override
	public int deleteActivityPrize(int activityID, int prizeID) {
		return activityPrizeDAO.deleteActivityPrize(activityID, prizeID);
	}

	@Override
	public int queryCountById(int activityID, byte fromMg) {

		return activityPrizeDAO.queryCountById(activityID, fromMg);
	}
	
	@Override
	public List<Prize> queryByIdPageInfo(int activityID, byte fromMg, int pageSize, int pageNumber) {

		return activityPrizeDAO.queryByIdPageInfo(activityID, fromMg, (pageNumber-1)*pageSize, pageSize);
	}

	
	
}
