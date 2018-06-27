package ldsoft.hlhh.web;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ldsoft.hlhh.wx.utils.WXUtils;

@Controller
@RequestMapping("/login")
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	// 登录到管理后台
	@RequestMapping(value = "/loginToMg", method = RequestMethod.GET)
	@ResponseBody
	public void loginToMg(HttpServletRequest request) {

		// 只要用户通过扫描登录二维码进入到该方法，就认为二维码是合法的，浏览器主动发起请求判断二维码是否过期存在2秒延迟，用户扫描的二维可能已经过期，但既然已经被用户扫描，就认为是合法的二维码。
		// 扫码登录流程：
		// 1、浏览器生成登录二维码，后台写入用户登录状态表，有效期30分钟，二维码中的信息包括：1、二维码登录controller地址（网站前台和管理后台生成不一样的contoller地址）。2、二维码全局唯一标识(UID)。
		// 2、用户使用微信扫描登录二维码，请求controller地址，查找openID对应的memberID是否存在，存在则带UID写入用户登录状态表
		// 3、浏览器每隔1秒查询查询用户登录状态表是否包含UID对应的登录信息，存在则跳转至登录后页面
		// 4、浏览器每隔1秒查询登录二维码是否失效，失效则提示用户：二维码已失效，请点击刷新

		// 用户扫描登录二维码后发请求到微信服务器并获取code，然后把返回的code送到该回调地址，code用于获取微信WebAccessToken，WebAccessToken用于获取openID
		// code获取成功redirect_uri/?code=CODE&state=STATE
		// code获取失败redirect_uri?state=STATE

		try {
			if (request.getParameter("state") != null) {
				// 请求来自微信客户端
				if (request.getParameter("state").equals("fromWX")) {
					if (request.getParameter("code") != null) {
						// 通过code获取openID

						// 发请求到微信服务器
						JSONObject jsonObject;

						jsonObject = WXUtils.get(WXUtils.getWXPageAccessTokenUrl(request.getParameter("code")));

						if (jsonObject != null && jsonObject.getString("openid") != null) {

							// 判断openID对应的memberID是否存在，存在则自动登录，登录成功后立马销毁redis中的UID对象，防止被它人窃取客户端cookie后再次登录

							// 1.判断openID在wxMember表中是否存在？不存在则新增。存在则去wxwebmember表中memberID，可能找到不只一个memberID

							/*
							 * List<WXMember>
							 * wxMember=wxMemberDAO.select(jsonObject.getString(
							 * "openid")); if(wxMember.size()==0) {
							 * wxMemberDAO.insert(jsonObject.getString("openid")
							 * , "0"); }
							 */

						} else {

						}

						
					}

				}
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
