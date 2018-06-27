package ldsoft.hlhh.wx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ldsoft.hlhh.wx.dao.WinningRecordDAO;
import ldsoft.hlhh.wx.entity.WinningRecord;
import ldsoft.hlhh.wx.service.WinningRecordService;

@Service
public class WinningRecordServiceImpl implements WinningRecordService {

	@Autowired
	private WinningRecordDAO winningRecordDAO;

	@Override
	public int updateWinningRecord(WinningRecord winningRecord) {
		
		return 0;
	}

	@Override
	public int insertWinningRecord(WinningRecord winningRecord) {
		return 0;
	}

	@Override
	public WinningRecord queryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WinningRecord> queryAll(int activityID,int offset, int limit) {
		return winningRecordDAO.queryAll(activityID,offset, limit);
	}

	@Override
	public int deleteWinningRecord(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<WinningRecord> queryByActIDOpenID(int activityID,String openID) {
		// TODO Auto-generated method stub
		return winningRecordDAO.queryByActIDOpenID(activityID,openID);
	}
	
	
}
