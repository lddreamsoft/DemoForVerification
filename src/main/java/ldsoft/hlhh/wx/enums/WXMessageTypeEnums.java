package ldsoft.hlhh.wx.enums;

//微信消息类型
public enum WXMessageTypeEnums {
	TEXT("text"), NEWS("news"), IMAGE("image"), VOICE("voice"), VEDIO("video"), LINK("link"), LOCATION(
			"location"), EVENT(
					"event"), SUBSCRIBE("subscribe"), UNSUBSCRIBE("unsubscribe"), CLICK("CLICK"), VIEW("VIEW");
	private final String messageType;

	WXMessageTypeEnums(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return this.messageType;
	}
}