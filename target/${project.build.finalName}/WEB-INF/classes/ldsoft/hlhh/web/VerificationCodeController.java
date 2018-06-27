package ldsoft.hlhh.web;

import java.io.File;
import java.io.IOException;
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

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;
import ldsoft.hlhh.utils.ImageTools;
import ldsoft.hlhh.web.dao.cache.RedisVerificationCodeInfoDAO;
import ldsoft.hlhh.web.entity.VerificationCodeInfo;

@Controller
@RequestMapping("/verificationCode")
public class VerificationCodeController {

	static final Logger logger = LoggerFactory.getLogger(VerificationCodeController.class);

	private RedisVerificationCodeInfoDAO redisVerificationCodeInfoDAO;

	// 校验验证信息
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public String check(HttpServletRequest request) {

		// 判断UID是否失效
		String VerificationCodeUID = "";

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("VerificationCodeUID")) {
				VerificationCodeUID = cookie.getValue();
			}
		}

		// 用户清空浏览器cookie或cookie已过失效时间，点击验证图片失效
		if (VerificationCodeUID == "") {
			return "result:failed";
		}

		// 从redis中抓取验证信息
		VerificationCodeInfo verificationCodeInfo = redisVerificationCodeInfoDAO.get(VerificationCodeUID);
		if (verificationCodeInfo == null) {
			return "result:failed";
		}

		// 无论验证成功还是失败，验证一次后清除redis缓存，防止验证失败后机器自动发坐标反复尝试，或验证成功后cookie被他人窃取后再次通过验证
		// 清空redis缓存
		redisVerificationCodeInfoDAO.clear(VerificationCodeUID);
		int count = 0;

		// 格式：文字1：x1,y1,文字2：x2,y2
		String wordsInfo = request.getParameter("wordsInfo").toString();

		// 文字1传递值
		int x = Integer.valueOf(wordsInfo.split(",")[0]);
		int y = Integer.valueOf(wordsInfo.split(",")[1]);

		// 文字1标准值
		int x1 = Integer.valueOf(verificationCodeInfo.getWordsInfo()[0][1].split(",")[0]);
		int y1 = Integer.valueOf(verificationCodeInfo.getWordsInfo()[0][1].split(",")[1]);

		if (x + 10 > x1 && x < x1 + 30) {
			if (y + 10 > y1 && y < y1 + 30) {
				count++;
			}
		}

		if (count == 0) {
			return "failed";
		}

		// 文字2传递值
		int x2 = Integer.valueOf(wordsInfo.split(",")[2]);
		int y2 = Integer.valueOf(wordsInfo.split(",")[3]);

		// 文字2标准值
		int x3 = Integer.valueOf(verificationCodeInfo.getWordsInfo()[1][1].split(",")[0]);
		int y3 = Integer.valueOf(verificationCodeInfo.getWordsInfo()[1][1].split(",")[1]);

		if (x2 + 10 > x3 && x2 < x3 + 30) {
			if (y2 + 10 > y3 && y2 < y3 + 30) {
				count++;
			}
		}

		if (count == 1) {
			return "failed";
		} else {
			return "passed";
		}
	}

	// 生成验证码图片
	@RequestMapping(value = "/gen", method = RequestMethod.GET)
	@ResponseBody
	public void gen(HttpServletRequest request, HttpServletResponse response) {


		// redis验证码关键字
		String UID = UUID.randomUUID().toString();

		// 生成验证码
		VerificationCodeInfo verificationCodeInfo = ImageTools.genVerificationCode(
				request.getServletContext().getRealPath("/") + "images" + File.separator + "verificationCode");

		// 验证码信息属于临时信息，存入redis，5分钟之后自动失效

		// 去除VerificationCodeInfo中的bufferedImage，仅仅存储验证信息，该对象不需要存入redis浪费内存空间
		VerificationCodeInfo temp = new VerificationCodeInfo();
		temp.setWordsInfo(verificationCodeInfo.getWordsInfo());

		redisVerificationCodeInfoDAO = new RedisVerificationCodeInfoDAO();
		if (!redisVerificationCodeInfoDAO.set(UID, temp).equals("OK")) {
			logger.error("redis存储对象VerificationCodeInfo失败,程序不返回验证码图片直接返回");
			return;
		}

		// VerificationCodeUID通过cookie发送给客户端
		// 必须放在ImageIO.write前cookie才能发送给客户端
		Cookie cookie1 = new Cookie("VerificationCodeUID", UID);
		cookie1.setMaxAge(5 * 60); // 5分钟后失效
		cookie1.setPath(request.getContextPath());
		response.addCookie(cookie1);

		// 点击提示文字发给客户端 cookie中不能存储逗号
		Cookie cookie2 = new Cookie("VerificationCode",
				verificationCodeInfo.getWordsInfo()[0][0] + "_" + verificationCodeInfo.getWordsInfo()[1][0]);
		cookie2.setMaxAge(5 * 60); // 5分钟后失效
		cookie2.setPath(request.getContextPath());
		response.addCookie(cookie2);

		// 最后将二维码发送给客户端浏览器
		try {
			ImageIO.write(verificationCodeInfo.getBufferedImage(), "png", response.getOutputStream());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

}
