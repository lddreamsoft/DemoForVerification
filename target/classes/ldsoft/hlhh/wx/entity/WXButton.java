package ldsoft.hlhh.wx.entity;

public class WXButton {

	private String name;
	private String type;
	private WXButton[] sub_button;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public WXButton[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(WXButton[] sub_button) {
		this.sub_button = sub_button;
	}
	
	
	
}
