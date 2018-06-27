package ldsoft.hlhh.web.entity;


//存储二维码登录信息
public class QRCodeLoginInfo {
	
	//private Date expireTime; //登录二维码失效时间 规则：生成时间+30分钟 redis对象的失效时间：30分钟
	private String openID;   //微信OpenID
	private int memberID;    //会员ID 
	

	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	

}
