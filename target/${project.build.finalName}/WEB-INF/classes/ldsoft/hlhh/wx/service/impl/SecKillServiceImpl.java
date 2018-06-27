package ldsoft.hlhh.wx.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import ldsoft.hlhh.wx.dao.SecKillDAO;
import ldsoft.hlhh.wx.dao.SecKillDetailsDAO;
import ldsoft.hlhh.wx.dto.Exposer;
import ldsoft.hlhh.wx.dto.SeckillExecution;
import ldsoft.hlhh.wx.entity.SecKill;
import ldsoft.hlhh.wx.exception.RepeatSecKillException;
import ldsoft.hlhh.wx.exception.SecKillCloseException;
import ldsoft.hlhh.wx.exception.SecKillException;
import ldsoft.hlhh.wx.service.SecKillService;

@Service
public class SecKillServiceImpl implements SecKillService {

	// MD5混淆字符串
	private String slat = "asdfadfaf2342zxcvsq34~!$@$@#~!@$!@!";

	// 引入slf4j对象
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private SecKillDAO secKillDAO;
	
	@Autowired
	private SecKillDetailsDAO secKillDetailsDAO;

	public List<SecKill> getSecKillList() {
		// TODO Auto-generated method stub
		return secKillDAO.queryAll(0, 4);
	}

	public SecKill getById(long secKillId) {
		// TODO Auto-generated method stub
		return secKillDAO.queryById(secKillId);
	}

	// 获取MD5编码格式的字符串
	private String getMD5(long secKillId) {
		String base = secKillId + "/" + slat;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}

	public Exposer exposeSecKillUrl(long secKillId) {

		String mD5 = this.getMD5(secKillId);

		return new Exposer(true, mD5, secKillId);
	}

	
	//使用注解声明事务,通过运行期异常回滚事务	
	@Transactional 
	public SeckillExecution executeSecKill(long secKillID, long userPhone, String md5)
			throws SecKillException, SecKillCloseException, RepeatSecKillException {
		// TODO Auto-generated method stub

		try {

			if (md5 == null || !md5.equals(this.getMD5(secKillID))) {
				// 秒杀ID和MD5码不匹配
				throw new SecKillException("secKillID does not match MD5.");
			}

			int result = secKillDAO.reduceNumber(secKillID, new Date());
			if (result == 0) {
				// 秒杀ID和MD5码不匹配
				throw new SecKillCloseException("secKill has ended.");
			} else {
				
				//System.out.println("enter here.");
				
				// 记录购买行为
				int insertCount = secKillDetailsDAO.insertSecKill(secKillID, userPhone);

				// 通过判断数据库记录是否存在来判断是否重复秒杀，
				// 为什么不根据session中的值判断？用户和session之间并不具备唯一的一对一关系。

				// session的

				if (insertCount <= 0) {
					// 重复秒杀
					throw new RepeatSecKillException("repeated secKill is not allowed.");
				} else {
					// SecKillDetails secKillDetails=
					System.out.println("秒杀成功！");

				}

			}
		} 
		
		catch(SecKillCloseException e1)
		{
			throw e1;
			
		}
		catch(RepeatSecKillException e2)
		{
			throw e2;
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(),e); //记录日志
			
			//将所有编译期异常转化为运行期异常，以便spring的声明式事务回滚
			throw new SecKillException("inner error"+e.getMessage());
			// TODO: handle exception
		}
		
		return null;

	}

}
