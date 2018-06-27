package ldsoft.hlhh.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ldsoft.hlhh.web.dao.ActivityDAO;
import ldsoft.hlhh.web.entity.Activity;
import ldsoft.hlhh.web.service.ActivityService;


@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	private ActivityDAO activityDAO;

	@Override
	public int updateActivity(Activity activity) {
		return activityDAO.updateActivity(activity.getId(), activity.getName(), activity.getStartTime(), activity.getEndTime(), activity.getType(),activity.getTimes(), activity.getTag(), activity.getDescription());
	}

	@Override
	public int insertActivity(Activity activity) {
		return activityDAO.insertActivity(activity.getName(), activity.getStartTime(), activity.getEndTime(), activity.getType(),activity.getTimes(), activity.getTag(), activity.getDescription());
	}

	@Override
	public Activity queryById(int id,int currentTime) {
		return activityDAO.queryById(id,currentTime);
	}

	@Override
	public List<Activity> queryAll(int offset, int limit) {
		return activityDAO.queryAll(offset,limit);
	}

	@Override
	public int deleteActivity(int id) {
		return activityDAO.deleteActivity(id);
	}

	
	
}
