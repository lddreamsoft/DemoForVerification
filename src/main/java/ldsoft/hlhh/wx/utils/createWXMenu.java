package ldsoft.hlhh.wx.utils;

import org.json.JSONObject;

import ldsoft.hlhh.wx.entity.WXAccessToken;

public class createWXMenu {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WXAccessToken wxAccessToken = WXUtils.getWXAccessToken();


		// java对晚转换成json对象
		String wxMenu = JSONObject.wrap(WXUtils.initMenu()).toString();
		int result = WXUtils.createWXMenu(wxAccessToken.getToken(), wxMenu);
		
		
		if (result == 0) {
			System.out.println("OK");
		} else {
			System.out.println("错误码："+result);
		}
	}

}
