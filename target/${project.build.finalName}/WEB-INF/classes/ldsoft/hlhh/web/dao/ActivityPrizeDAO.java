package ldsoft.hlhh.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import ldsoft.hlhh.web.entity.Prize;

public interface ActivityPrizeDAO {

	List<Prize> queryById(@Param("activityID") int activityID, @Param("fromMg") byte fromMg);

	int queryCountById(@Param("activityID") int activityID, @Param("fromMg") byte fromMg);
	
	List<Prize> queryByIdPageInfo(@Param("activityID") int activityID, @Param("fromMg") byte fromMg,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageNumber);

	int insertActivityPrize(@Param("activityID") int activityID, @Param("prizeID") int prizeID);

	int deleteActivityPrize(@Param("activityID") int activityID, @Param("prizeID") int prizeID);

	int deductActivityPrizeStock(@Param("prizeID") int prizeID);
}
