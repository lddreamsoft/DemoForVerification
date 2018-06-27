package ldsoft.hlhh.wx.utils;



import ldsoft.hlhh.wx.entity.WXAccessToken;

public class createWXQRCode {

	public static void main(String[] args) {

		
		
		WXAccessToken wxAccessToken = WXUtils.getWXAccessToken();
		//注意更改appID和appSecret，目前针对测试号
		WXUtils.createWXQRCode(wxAccessToken.getToken(), "南中医");
		
		
		
		//String a="415714-AC100220-17751178226-2";
		//System.out.println(a.split("-")[1].substring(0,2));
		
		
	
	}

}
