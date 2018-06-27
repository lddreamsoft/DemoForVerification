package ldsoft.hlhh.wx.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ldsoft.hlhh.wx.dao.cache.RedisDAO;
import ldsoft.hlhh.wx.dto.Exposer;
import ldsoft.hlhh.wx.dto.SecKillResult;
import ldsoft.hlhh.wx.dto.SeckillExecution;
import ldsoft.hlhh.wx.entity.SecKill;
import ldsoft.hlhh.wx.enums.secKillEnums;
import ldsoft.hlhh.wx.exception.RepeatSecKillException;
import ldsoft.hlhh.wx.exception.SecKillCloseException;
import ldsoft.hlhh.wx.service.SecKillService;

@Controller
@RequestMapping("/seckill")

// url规则："/模块/资源/集合"
public class SecKillController {

	@Autowired
	private SecKillService secKillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {

		RedisDAO redisDAO=new RedisDAO("192.168.2.251",6379);
		
		SecKill secKill=new SecKill();
		secKill.setSeckill_id(100);
		secKill.setName("haha from server1");		
		redisDAO.setSecKill(secKill);
		
		List<SecKill> secKills=new ArrayList<SecKill>();
		secKills.add(redisDAO.getSecKill(100));
		
		model.addAttribute("list", secKills); //设置值
		return "list";
	}

	@RequestMapping(value = "/{secKillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("secKillId") Long secKillId, Model model) {
		if (secKillId == null) {
			return "redirect:/seckill/list";
		}

		SecKill secKill = secKillService.getById(secKillId);
		if (secKill == null) {
			return "forward:/seckill/list";
		}

		model.addAttribute("seckill", secKill);
		return "details";
	}

	@RequestMapping(value = "/{secKillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })

	// 加上该注解则Spring自动把返回类型封装成json格式
	@ResponseBody
	public SecKillResult<Exposer> exposer(long secKillId) {

		SecKillResult<Exposer> result;

		try {

			Exposer exposer = secKillService.exposeSecKillUrl(secKillId);
			result = new SecKillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			// TODO: handle exception
			// 记录日志
			result = new SecKillResult<Exposer>(false, e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{secKillId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SecKillResult<SeckillExecution> execute(@PathVariable("secKillId") Long secKillId,
			@PathVariable("md5") String md5, @CookieValue(value = "userPhone", required = false) Long userPhone)
	// 用户的cookie中不一定有userPhone值，如果这里不设require=false，则spring mvc会报错
	{
		//SecKillResult<SeckillExecution> result;

		if (userPhone == null) {
			return new SecKillResult<SeckillExecution>(false, "未注册");
		}

		try {
			SeckillExecution seckillExecution = secKillService.executeSecKill(secKillId, userPhone, md5);
			return new SecKillResult<SeckillExecution>(true, seckillExecution);
		} catch (SecKillCloseException e) {
			// TODO: handle exception

			SeckillExecution seckillExecution = new SeckillExecution(secKillId, secKillEnums.SECKILL_END);
			return new SecKillResult<SeckillExecution>(false, seckillExecution);
		} catch (RepeatSecKillException e) {

			SeckillExecution seckillExecution = new SeckillExecution(secKillId, secKillEnums.SECKILL_REPEAT);
			return new SecKillResult<SeckillExecution>(false, seckillExecution);
		} catch (Exception e) {
			// TODO: handle exception

			SeckillExecution seckillExecution = new SeckillExecution(secKillId, secKillEnums.SECKILL_INNER_ERROR);
			return new SecKillResult<SeckillExecution>(false, seckillExecution);
		}

	}

	// 获取服务器时间
	public SecKillResult<Long> ServerTime() {

		return new SecKillResult<Long>(true, new Date().getTime());
	}

}
