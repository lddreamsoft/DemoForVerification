package ldsoft.hlhh.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import ldsoft.hlhh.utils.ImageTools;
import ldsoft.hlhh.web.dao.cache.RedisQRCodeLoginInfoDAO;
import ldsoft.hlhh.web.entity.QRCodeLoginInfo;
import ldsoft.hlhh.wx.utils.WXUtils;

@Controller
@RequestMapping("/QRCode")
public class QRCodeController {

	private final Logger logger = LoggerFactory.getLogger(QRCodeController.class);
	
	//安全性：必须防止cookie被它人窃取
	//@Autowired
	//private WXMemberDAO wxMemberDAO;
	private RedisQRCodeLoginInfoDAO redisQRCodeLoginInfoDAO = new RedisQRCodeLoginInfoDAO();

	// 为管理后台生成登录二维码图片，用户通过微信扫描该二维码后完成登录，不需要输入用户名、密码、验证码等
	@RequestMapping(value = "/genQRCodeForMg", method = RequestMethod.GET)
	@ResponseBody
	public void genQRCodeForMg(HttpServletRequest request, HttpServletResponse response)  {


		// 不管图片有没有失效，每次请求都重新生成新的登录二维码并更新UID给浏览器,UID存于浏览器cookie
		// 无须在本地硬盘存储真实图片，直接发送Image给浏览器
		
		try
		{

			// 生成登录二维码对应的UID
			String UID = UUID.randomUUID().toString();
			// 生成登录二维码
			BufferedImage bufferedImage = ImageTools
					.genQRCode(
							WXUtils.getWXPageCodeUrl("http://i1f6318385.imwork.net/hlhh/login/loginToMg?UID="+UID, "snsapi_base",
									"fromWX"),
							512, 512, request.getServletContext().getRealPath("/") + "images"+File.separator+"logo.png");

			// 二维码登录信息属于临时信息，存入redis，5分钟之后自动失效
			QRCodeLoginInfo qrCodeLoginInfo = new QRCodeLoginInfo();
			redisQRCodeLoginInfoDAO.set(UID, qrCodeLoginInfo);

			// QRCodeUID通过cookie发送给客户端 必须放在ImageIO.write前cookie才能发送给客户端
			Cookie cookie = new Cookie("QRCodeUID", UID);
			cookie.setMaxAge(5 * 60); // 5分钟后失效
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);

			// 最后将二维码发送给客户端浏览器
			ImageIO.write(bufferedImage, "png", response.getOutputStream());
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(),e);
		}
		

	}

	// 查询UID对应的二维码是否失效
	@RequestMapping(value = "/isUIDExpired", method = RequestMethod.GET)
	@ResponseBody
	public String isUIDExpired(HttpServletRequest request) {

		// 判断UID是否失效
		String QRCodeUID = "";

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("QRCodeUID")) {
				QRCodeUID = cookie.getValue();
			}
		}

		//用户清空浏览器cookie或cookie已过失效时间，登录二维码失效
		if(QRCodeUID=="")
		{
			return "Expired:" + "1";	
		}	

		// 判断UID是否失效
		if(redisQRCodeLoginInfoDAO.get(QRCodeUID)==null)
		{
			return "Expired:" + "1";	
		}
		else
		{
			return "Expired:" + "0";
		}
	}

	

}
