package ldsoft.hlhh.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.web.entity.Prize;

public interface PrizeDAO {

	int updateOnlyPicturesOfPrize(@Param("id") int id,@Param("pictures") String pictures);
	
	int updatePrize(Prize prize);

	int insertPrize(Prize prize);
	
	int queryall(@Param("id") int id);

	Prize queryById(@Param("id") int id);

	List<Prize> queryAll(@Param("offset") int offset, @Param("limit") int limit);

	int deletePrize(@Param("id") int id);
	
	List<Prize> selectAvailablePrizes(int activityID);
}
