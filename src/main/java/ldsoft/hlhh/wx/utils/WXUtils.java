package ldsoft.hlhh.wx.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ldsoft.hlhh.utils.Tools;
import ldsoft.hlhh.wx.entity.WXAccessToken;
import ldsoft.hlhh.wx.entity.WXButton;
import ldsoft.hlhh.wx.entity.WXClickButton;
import ldsoft.hlhh.wx.entity.WXJSAPIInfo;
import ldsoft.hlhh.wx.entity.WXJSAPITicket;
import ldsoft.hlhh.wx.entity.WXMenu;
import ldsoft.hlhh.wx.entity.WXViewButton;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;

public class WXUtils {

	private final static Logger logger = LoggerFactory.getLogger(WXUtils.class);

	// 用于网站后台确认消息是否发自微信服务器，而不是第三方冒充(此口令在微信服务器上被配置)
	private static final String token = "5318f684444077fc6d32c996f79068cb"; // 服务号：5318f684444077fc6d32c996f79068cb
																			// 测试号：234xcvq23sdfasdfzxcv2159808

	// 微信测试帐号的appID和appSecret，用于微信服务器确认消息是否发自网站后台，而不是第三方冒充。
	private static final String appID = "wx8c25811b0ded7633"; // 服务号：wx8c25811b0ded7633
																// 测试号：wx7bac84c3b83d6313
	private static final String appSecret = "51124ddbfbe8881352cd41431dd04be8"; // 服务号：51124ddbfbe8881352cd41431dd04be8
																				// 测试号：65accc54563648e1d22a8a91e7054387

	private static final String WXQRCodeTicketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	private static final String WXQRCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	// 该token在获取之后2小时后失效，失效后需要重新调用该url获取
	private static final String WXAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ appID + "&secret=" + appSecret;
	private static final String WXMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 用于获取JS API Ticket
	private static final String WXJSAPITicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// 微信网页授权url，用于在页面中获取openID和用户昵称、图像等资料
	private static final String WXPageCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appID
			+ "&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	private static final String WXPageAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
			+ appID + "&secret=" + appSecret + "&code=CODE&grant_type=authorization_code";

	// 存储最后一次获取的wxAccessToken
	private static String lastWXAccessToken = "";

	// 存储最后一次获取的wxAccessToken对应的过期时间
	private static Date lastWXAccessTokenExpireTime;

	// 存储最后一次获取的JSAPI ticket
	private static String lastWXJSAPITicket = "";

	// 存储最后一次获取的JSAPI ticket对应的过期时间
	private static Date lastWXJSAPITicketExpireTime;

	public static String getWXJSAPITicketUrl(String accessToken) {
		return WXJSAPITicketUrl.replace("ACCESS_TOKEN", accessToken);
	}

	public static String getWXQRCodeTicketUrl(String accessToken) {
		return WXQRCodeTicketUrl.replace("ACCESS_TOKEN", accessToken);
	}

	public static String getWXPageAccessTokenUrl(String code) {
		return WXPageAccessTokenUrl.replace("CODE", code);
	}

	public static String getWXPageCodeUrl(String redirectUri, String scope, String state) {
		try {
			return WXPageCodeUrl.replace("REDIRECT_URI", URLEncoder.encode(redirectUri, "utf-8"))
					.replace("SCOPE", scope).replace("STATE", state);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	// 校验消息是否来自微信服务器
	public static boolean check(String signature, String timestamp, String nonce) {
		String[] temp = new String[] { token, timestamp, nonce };

		// 排序
		Arrays.sort(temp);

		// 生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < temp.length; i++) {
			content.append(temp[i]);
		}

		// sha1加密
		String result = Tools.getSHA1(content.toString());
		return result.equals(signature);
	}

	// 向微信服务器发送get请求
	public static JSONObject get(String url) {

		CloseableHttpResponse httpResponse = null;
		try {

			JSONObject jsonObject = null;
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpResponse = httpclient.execute(httpGet); // 抛出异常

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = new JSONObject(result);
			}

			EntityUtils.consume(entity);// 释放资源的其中一种方式
			return jsonObject;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}

	}

	// 获取带推广参数的公众号关注二维码
	public static void getImage(String url, String fileName) {

		CloseableHttpResponse httpResponse = null;
		try {

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpResponse = httpclient.execute(httpGet); // 抛出异常

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				byte[] data = EntityUtils.toByteArray(entity);
				Tools.byteArrayToImage(data, "D:\\" + fileName + ".jpg");
			}

			EntityUtils.consume(entity);// 释放资源的其中一种方式
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {

				logger.error(e.getMessage(), e);
			}
		}

	}

	// 向微信服务器发送POST请求 url为微信服务器api地址，data为向微信服务器发送的具体数据
	public static JSONObject post(String url, String data) {
		JSONObject jsonObject = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		CloseableHttpResponse httpResponse = null;

		try {

			httpPost.setEntity(new StringEntity(data, "UTF-8"));
			httpResponse = httpclient.execute(httpPost);

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = new JSONObject(result);
			}

			EntityUtils.consume(entity);
			return jsonObject;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;

		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {

				logger.error(e.getMessage(), e);
				return null;
			}
		}
	}

	// 获取微信access_token 不能频繁获取，每天上限2000，需要全局缓存
	public static WXAccessToken getWXAccessToken() {
		WXAccessToken wxAccessToken = new WXAccessToken();

		// 判断最后一次获取的accessToken是否过期，过期则重新获取，未过期则直接使用
		if (lastWXAccessToken.equals("")
				|| new Date(System.currentTimeMillis()).getTime() >= lastWXAccessTokenExpireTime.getTime()) {
			JSONObject jsonObject = get(WXAccessTokenUrl);
			if (jsonObject != null) {
				lastWXAccessToken = jsonObject.getString("access_token");
				// 计算过期时间
				lastWXAccessTokenExpireTime = new Date(
						System.currentTimeMillis() + jsonObject.getInt("expires_in") * 1000);
				wxAccessToken.setToken(lastWXAccessToken);
				wxAccessToken.setExpireTime(lastWXAccessTokenExpireTime);
			}
		} else {
			wxAccessToken.setToken(lastWXAccessToken);
			wxAccessToken.setExpireTime(lastWXAccessTokenExpireTime);
		}

		return wxAccessToken;
	}

	// 初始化自定义微信公众号菜单
	public static WXMenu initMenu() {
		WXMenu wxMenu = new WXMenu();

		WXClickButton wxClickButton = new WXClickButton();
		wxClickButton.setKey("1");
		wxClickButton.setName("菜单1");
		wxClickButton.setType("click");

		WXViewButton wxViewButton = new WXViewButton();

		wxViewButton.setUrl(getWXPageCodeUrl("http://i1f6318385.imwork.net/hlhhwx/wx/activities/luckyShake",
				"snsapi_base", "fromWX"));

		wxViewButton.setName("菜单2");
		wxViewButton.setType("view");

		WXClickButton wxClickButton1 = new WXClickButton();
		wxClickButton1.setKey("2");
		wxClickButton1.setName("扫码");
		wxClickButton1.setType("scancode_push");

		WXClickButton wxClickButton2 = new WXClickButton();
		wxClickButton2.setKey("3");
		wxClickButton2.setName("地理位置");
		wxClickButton2.setType("location_select");

		WXButton wxButton = new WXButton();
		wxButton.setName("菜单3");
		wxButton.setSub_button(new WXButton[] { wxClickButton1, wxClickButton2 });

		wxMenu.setButton(new WXButton[] { wxViewButton, wxViewButton, wxButton });

		return wxMenu;
	}

	public static int createWXMenu(String wxAccessToken, String wxMenu) {
		String url = WXMenuUrl.replace("ACCESS_TOKEN", wxAccessToken);

		int result = 0;

		JSONObject jsonObject = post(url, wxMenu);

		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}

		return result;
	}

	public static void createWXQRCode(String wxAccessToken, String sceneName) {
		String url = WXQRCodeTicketUrl.replace("ACCESS_TOKEN", wxAccessToken);

		String postData = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"SCENEName\"}}}";
		postData = postData.replace("SCENEName", sceneName);

		JSONObject jsonObject = post(url, postData);
		if (jsonObject != null) {
			if (jsonObject.getString("ticket") != null) {
				try {
					url = WXQRCodeUrl.replace("TICKET", URLEncoder.encode(jsonObject.getString("ticket"), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					logger.error(e.getMessage(),e);
				} catch (JSONException e) {
					logger.error(e.getMessage(),e);
				}
				getImage(url, sceneName);
			}
		}

	}

	// 获取微信JSAPI ticket 不能频繁获取 需要全局缓存
	private static WXJSAPITicket getWXJSAPITicket() {

		WXJSAPITicket wxJSAPITicket = new WXJSAPITicket();

		// 判断最后一次获取的JSAPI ticket是否过期，过期则重新获取，未过期则直接使用
		if (lastWXJSAPITicket.equals("")
				|| new Date(System.currentTimeMillis()).getTime() >= lastWXJSAPITicketExpireTime.getTime()) {
		

				WXAccessToken wxAccessToken = getWXAccessToken();

				JSONObject jsonObject = get(getWXJSAPITicketUrl(wxAccessToken.getToken()));
				if (jsonObject != null) {
					lastWXJSAPITicket = jsonObject.getString("ticket");
					// 计算过期时间
					lastWXJSAPITicketExpireTime = new Date(
							System.currentTimeMillis() + jsonObject.getInt("expires_in") * 1000);

					wxJSAPITicket.setTicket(lastWXJSAPITicket);
					wxJSAPITicket.setExpireTime(lastWXJSAPITicketExpireTime);
				}
			
		} else {
			wxJSAPITicket.setTicket(lastWXJSAPITicket);
			wxJSAPITicket.setExpireTime(lastWXJSAPITicketExpireTime);
		}

		return wxJSAPITicket;
	}

	// 获取JS API签名
	public static WXJSAPIInfo getWXJSAPIInfo(String url){
		WXJSAPIInfo wxJSAPIInfo = new WXJSAPIInfo();
		wxJSAPIInfo.setAppID(appID);
		String randomString = Tools.getRandomString(16);
		wxJSAPIInfo.setNonceStr(randomString);
		long randomTimeStamp = Long.valueOf(String.valueOf(System.currentTimeMillis()).substring(0, 10));
		wxJSAPIInfo.setTimeStamp(randomTimeStamp);
		String ticket = getWXJSAPITicket().getTicket();
		wxJSAPIInfo.setSignature(Tools.getSHA1("jsapi_ticket=" + ticket + "&noncestr=" + randomString + "&timestamp="
				+ String.valueOf(randomTimeStamp) + "&url=" + url));

		return wxJSAPIInfo;
	}

}
