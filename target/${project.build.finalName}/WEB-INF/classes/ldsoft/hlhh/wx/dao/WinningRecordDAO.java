package ldsoft.hlhh.wx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ldsoft.hlhh.wx.entity.WinningRecord;

public interface WinningRecordDAO {

	int updateWinningRecord();

	//默认received值为0:表示未兑换，1表示已兑换，createdTime即为中奖时间
	int insertWinningRecord(
							@Param("activityID") int activityID,
						    @Param("openID") String openID,
						    @Param("prizeName") String prizeName,
						    @Param("prizeDescription") String prizeDescription,
						    @Param("prizeMemberPointers") int prizeMemberPointers,
						    @Param("prizePictures") String prizePictures,
						    @Param("prizeCode") String prizeCode
						  ); 
	
	int queryall(@Param("id") int id);

	WinningRecord queryById(@Param("id") int id);
	
	List<WinningRecord> queryByActIDOpenID(@Param("activityID") int activityID,@Param("openID") String openID);
	
	List<WinningRecord> queryAll(@Param("activityID") int activityID,@Param("offset") int offset, @Param("limit") int limit);
	
	WinningRecord isFirstDrawOfActivity(@Param("activityID") int activityID,@Param("openID") String openID);

	int deleteWinningRecord(@Param("id") int id);
}
