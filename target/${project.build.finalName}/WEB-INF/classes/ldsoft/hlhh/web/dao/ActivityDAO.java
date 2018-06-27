package ldsoft.hlhh.web.dao;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.web.entity.Activity;

public interface ActivityDAO {

	int updateActivity(@Param("id") int id,@Param("name") String name,
					   @Param("startTime") Date startTime,@Param("endTime") Date endTime,
					   @Param("type") byte type,
					   @Param("times") int times,@Param("tag") String tag,
					   @Param("description") String description
					  );
	
	int insertActivity(@Param("name") String name,
			   		   @Param("startTime") Date startTime,@Param("endTime") Date endTime,
			   		   @Param("type") byte type,
			   		   @Param("times") int times,@Param("tag") String tag,
			   		   @Param("description") String description);

	int queryall(@Param("id") int id);

	Activity queryById(@Param("id") int id,@Param("currentTime") int currentTime);

	List<Activity> queryAll(@Param("offset") int offset, @Param("limit") int limit);

	int deleteActivity(@Param("id") int id);
}
