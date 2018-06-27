package ldsoft.hlhh.web.service;

import java.util.List;

import ldsoft.hlhh.web.entity.Prize;

public interface ActivityPrizeService {

	// fromMg是否来自管理后台,用于决定是否查询库存>0的商品
	List<Prize> queryById(int activityID, byte fromMg);

	int queryCountById(int activityID, byte fromMg);
	
	List<Prize> queryByIdPageInfo(int activityID, byte fromMg, int pageSize, int pageNumber);

	int insertActivityPrize(int activityID, int prizeID);

	int deleteActivityPrize(int activityID, int prizeID);

}
