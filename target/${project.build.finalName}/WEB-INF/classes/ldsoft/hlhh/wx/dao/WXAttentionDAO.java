package ldsoft.hlhh.wx.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import ldsoft.hlhh.wx.entity.WXAttention;

public interface WXAttentionDAO {
	
	List<WXAttention> selectAllWXAttention(); 
	int selectWXAttention(@Param("openID") String openID); 
	int insertWXAttention(@Param("openID") String openID,@Param("name") String name);
	int updateWXAttention(@Param("openID") String openID,@Param("name") String name);
	int deleteWXAttention(@Param("openID") String openID);
}
