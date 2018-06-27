package ldsoft.hlhh.web.service;

import java.util.List;

import ldsoft.hlhh.web.entity.Activity;

public interface ActivityService {

	
	int updateActivity(Activity activity);
	
	int insertActivity(Activity activity);
	
	Activity queryById(int id,int currentTime);	

	List<Activity> queryAll(int offset,int limit);
	
	int deleteActivity(int id);
	   
}
