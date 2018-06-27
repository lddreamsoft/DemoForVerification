package ldsoft.hlhh.wx.service;

import java.util.List;

import ldsoft.hlhh.wx.dto.Exposer;
import ldsoft.hlhh.wx.dto.SeckillExecution;
import ldsoft.hlhh.wx.entity.SecKill;
import ldsoft.hlhh.wx.exception.RepeatSecKillException;
import ldsoft.hlhh.wx.exception.SecKillCloseException;
import ldsoft.hlhh.wx.exception.SecKillException;


public interface SecKillService {
	
	List<SecKill> getSecKillList();
	
    SecKill getById(long secKillId);
    
    //秒杀开启后获取秒杀接口地址，否则输出系统时间和秒杀时间
    Exposer exposeSecKillUrl(long secKillId);
    
    /*md5为加密后的URL*/
    SeckillExecution executeSecKill(long secKillID,long userPhone,String md5)
    throws SecKillException,SecKillCloseException,RepeatSecKillException;
}
