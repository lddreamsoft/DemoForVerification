package ldsoft.hlhh.wx.service;

import java.util.List;

import ldsoft.hlhh.wx.entity.WinningRecord;



public interface WinningRecordService {
	
	int updateWinningRecord(WinningRecord winningRecord);
	
	int insertWinningRecord(WinningRecord winningRecord);
	
	WinningRecord queryById(int id);	
	
	List<WinningRecord> queryByActIDOpenID(int activityID,String openID);	

	List<WinningRecord> queryAll(int activityID,int offset,int limit);
	
	int deleteWinningRecord(int id);
}
