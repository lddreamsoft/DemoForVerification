package ldsoft.hlhh.wx.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import ldsoft.hlhh.wx.entity.WXAttention;

public interface WXAttentionDAO {
	
	List<WXAttention> select(); 
	int selectCount(@Param("openID") String openID); 
	int insert(@Param("openID") String openID,@Param("name") String name);
	int update(@Param("openID") String openID,@Param("name") String name);
	int delete(@Param("openID") String openID);
}
