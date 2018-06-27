package ldsoft.hlhh.wx.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

import ldsoft.hlhh.wx.entity.WXNews;
import ldsoft.hlhh.wx.entity.WXNewsMessage;
import ldsoft.hlhh.wx.entity.WXTextMessage;

public class WXMessageUtils {

	private final static Logger logger = LoggerFactory.getLogger(WXMessageUtils.class);

	// 将微信发送过来的xml消息转换成java map对象
	public static Map<String, String> xmlToMap(HttpServletRequest request) {

		try {
			Map<String, String> map = new HashMap<String, String>();

			SAXReader reader = new SAXReader();

			ServletInputStream servletInputStream = request.getInputStream();

			Document document = reader.read(servletInputStream);

			Element rootElement = document.getRootElement();

			List<Element> list = rootElement.elements();

			for (Element e : list) {
				map.put(e.getName(), e.getText());
			}

			servletInputStream.close();
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	// 将文本消息对象内容转换成xml发送给微信
	public static String textMessageToXML(WXTextMessage wxTextMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", wxTextMessage.getClass());
		return xStream.toXML(wxTextMessage);
	}

	// 将图文消息对象内容转换成xml发送给微信
	public static String newsMessageToXML(WXNewsMessage wxNewsMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", wxNewsMessage.getClass());
		xStream.alias("item", new WXNews().getClass());
		return xStream.toXML(wxNewsMessage);
	}

	// 将微信发送过来的XML格式的文本消息转换成文本消息对象
	public static WXTextMessage xmlToTextMessage(HttpServletRequest request) {

		try {
			SAXReader reader = new SAXReader();

			InputStream inputStream = request.getInputStream();
			Document document = reader.read(inputStream);

			Element rootElement = document.getRootElement();

			List<Element> list = rootElement.elements();

			WXTextMessage wxTextMessage = new WXTextMessage();
			for (Element e : list) {

				if (e.getName().equals("FromUserName")) {
					wxTextMessage.setFromUserName(e.getText());
				}

				if (e.getName().equals("ToUserName")) {
					wxTextMessage.setToUserName(e.getText());
				}

				if (e.getName().equals("CreateTime")) {
					wxTextMessage.setCreateTime(Long.parseLong(e.getText()));
				}

				if (e.getName().equals("MsgType")) {
					wxTextMessage.setMsgType(e.getText());
				}

				if (e.getName().equals("MsgId")) {
					wxTextMessage.setMsgId(e.getText());
				}

				if (e.getName().equals("Content")) {
					wxTextMessage.setContent(e.getText());
				}
			}

			inputStream.close();

			return wxTextMessage;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}
}
