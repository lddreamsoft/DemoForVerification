package ldsoft.hlhh.web.service;

import java.util.List;

import ldsoft.hlhh.web.entity.Prize;

public interface PrizeService {

	int updateOnlyPicturesOfPrize(Prize prize);
	
	int updatePrize(Prize prize);
	
	int insertPrize(Prize prize);
	
	Prize queryById(int id);	

	List<Prize> queryAll(int offset,int limit);
	
	int deletePrize(int id);
	
	List<Prize> selectAvailablePrizes(int activityID);
	   
}
