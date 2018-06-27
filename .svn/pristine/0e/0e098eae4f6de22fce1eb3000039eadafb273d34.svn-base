package ldsoft.hlhh.wx.utils;



import ldsoft.hlhh.wx.entity.WXAccessToken;

public class createWXQRCode {

	public static void main(String[] args) {

		WXAccessToken wxAccessToken = WXUtils.getWXAccessToken();

		
		//注意更改appID和appSecret，目前针对测试号
		WXUtils.createWXQRCode(wxAccessToken.getToken(), "相亲会");
		
		
		
		/*正确的返回格式
		 * {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm
3sUw==","expire_seconds":60,"url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"}
 		*/
	
	}

}
