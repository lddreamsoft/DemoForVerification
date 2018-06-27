package ldsoft.hlhh.wx.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ldsoft.hlhh.wx.dao.WXAttentionDAO;
import ldsoft.hlhh.wx.entity.WXAttention;
import ldsoft.hlhh.wx.entity.WXNews;
import ldsoft.hlhh.wx.entity.WXNewsMessage;
import ldsoft.hlhh.wx.entity.WXTextMessage;
import ldsoft.hlhh.wx.enums.WXMessageTypeEnums;
import ldsoft.hlhh.wx.utils.WXUtils;
import ldsoft.hlhh.wx.utils.WXMessageUtils;

//微信服务器公共控制器
@Controller
@RequestMapping("/wx")
public class WXController {

	private final Logger logger = LoggerFactory.getLogger(WXController.class);

	@Autowired
	private WXAttentionDAO wxAttentionDAO;

	// 权限认证
	@RequestMapping(method = { RequestMethod.GET })
	public String auth(HttpServletRequest request, HttpServletResponse response) {

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();

			if (WXUtils.check(signature, timestamp, nonce)) {
				printWriter.print(echostr);
			}

		} catch (IOException e) {

			logger.error(e.getMessage(), e);
			return null;
		} finally {
			printWriter.close();
		}

		return null;
	}

	// 用户在微信公众号上的任何动作会发送POST请求到该地址,消息格式为xml
	@RequestMapping(method = { RequestMethod.POST })
	public String replyMessage(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter printWriter = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 一定要先定义编码格式，再response.getWriter()，否则回复的文字会变成乱码
			printWriter = response.getWriter();
			// 避免5秒内服务器无法响应，先直接回复空串给微信服务器
			printWriter.print("");
			printWriter.flush();
			// printWriter.close();
			// //这里不能关闭，会导致后面WXMessageUtils.xmlToMap中的输入流request.getInputStream();关闭

			Map<String, String> map = WXMessageUtils.xmlToMap(request);

			String message = null;

			String msgType = map.get("MsgType");

			if (WXMessageTypeEnums.TEXT.getMessageType().equals(msgType)) // 判断是否为文本消息
			{
				String content = "宝宝，哈哈哈！";

				if ("show me".equals(map.get("Content"))) {
					content = "";
					List<WXAttention> result = wxAttentionDAO.select();
					for (int i = 0; i < result.size(); i++) {
						content += "推广商家：" + result.get(i).getName().split("_")[1] + "\n推广人数："
								+ result.get(i).getQuantity() + "\n\n";
					}

					if (content != "") {
						content = content.substring(0, content.length() - 2);
					} else {
						content = "暂时没有商家使用推广二维码推广花海服务号";
					}

					// 发送文本消息给用户
					WXTextMessage wxTextMessage = new WXTextMessage();
					wxTextMessage.setFromUserName(map.get("ToUserName"));// from和to反过来
					wxTextMessage.setToUserName(map.get("FromUserName"));
					wxTextMessage.setMsgType(WXMessageTypeEnums.TEXT.getMessageType());
					wxTextMessage.setCreateTime(new Date().getTime());
					wxTextMessage.setContent(content);
					message = WXMessageUtils.textMessageToXML(wxTextMessage);

				} else if ("1".equals(map.get("Content"))) {
					content = "一等奖";
					// 发送文本消息给用户
					WXTextMessage wxTextMessage = new WXTextMessage();
					wxTextMessage.setFromUserName(map.get("ToUserName"));// from和to反过来
					wxTextMessage.setToUserName(map.get("FromUserName"));
					wxTextMessage.setMsgType(WXMessageTypeEnums.TEXT.getMessageType());
					wxTextMessage.setCreateTime(new Date().getTime());
					wxTextMessage.setContent(content);
					message = WXMessageUtils.textMessageToXML(wxTextMessage);
				} else if ("2".equals(map.get("Content"))) {

					// 发送图文消息给用户
					WXNewsMessage wxNewsMessage = new WXNewsMessage();
					List<WXNews> items = new ArrayList<WXNews>();
					WXNews wxNews = new WXNews();
					wxNews.setTitle("荷兰花海活动标题");
					wxNews.setDescription("荷兰花海活动描述");
					wxNews.setPicUrl("http://ld.ngrok.cc/hlhh_wx/images/activity.jpg");
					wxNews.setUrl("www.dfhlhh.com");
					items.add(wxNews);

					wxNews = new WXNews();
					wxNews.setTitle("荷兰花海活动标题");
					wxNews.setDescription("荷兰花海活动描述");
					wxNews.setPicUrl("http://ld.ngrok.cc/hlhh_wx/images/activity.jpg");
					wxNews.setUrl("www.dfhlhh.com");
					items.add(wxNews);

					wxNewsMessage.setFromUserName(map.get("ToUserName"));
					wxNewsMessage.setToUserName(map.get("FromUserName"));
					wxNewsMessage.setCreateTime(new Date().getTime());
					wxNewsMessage.setMsgType(WXMessageTypeEnums.NEWS.getMessageType());
					wxNewsMessage.setArticles(items);
					wxNewsMessage.setArticleCount(items.size());
					message = WXMessageUtils.newsMessageToXML(wxNewsMessage);
				}
			} else if (WXMessageTypeEnums.EVENT.getMessageType().equals(msgType)) {
				String event = map.get("Event");
				if (WXMessageTypeEnums.SUBSCRIBE.getMessageType().equals(event)) {

					// 关注事件
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("欢迎关注miss猫.\n\n");
					stringBuffer.append("尤其是宝宝.\n\n");

					if (!map.get("EventKey").equals("")) {
						stringBuffer.append("你是通过扫描推广二维码：\n" + map.get("EventKey").split("_")[1] + "\n关注的.\n");

						// 只有推广用户才写微信关注表
						int count = wxAttentionDAO.selectCount(map.get("FromUserName"));
						if (count > 0) {
							wxAttentionDAO.update(map.get("FromUserName"), map.get("EventKey"));
						} else {
							wxAttentionDAO.insert(map.get("FromUserName"), map.get("EventKey"));
						}
					}

					WXTextMessage temp = new WXTextMessage();
					temp.setFromUserName(map.get("ToUserName"));// from和to反过来
					temp.setToUserName(map.get("FromUserName"));
					temp.setMsgType(WXMessageTypeEnums.TEXT.getMessageType());
					temp.setCreateTime(new Date().getTime());
					temp.setContent(stringBuffer.toString());
					message = WXMessageUtils.textMessageToXML(temp);
				} else if (WXMessageTypeEnums.UNSUBSCRIBE.getMessageType().equals(event)) {
					wxAttentionDAO.delete(map.get("FromUserName"));
				}
			}
			if (message != null) {
				printWriter.print(message);
				printWriter.flush();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			printWriter.close();
		}
		return null;
	}

}
