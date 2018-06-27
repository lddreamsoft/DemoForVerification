package ldsoft.hlhh.web.entity;


//网站会员类
public class WebSiteMember {
	
	private int id;
	private String name;
	private String password;
	private String type_id;
	private int sourceChannel;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public int getSourceChannel() {
		return sourceChannel;
	}
	public void setSourceChannel(int sourceChannel) {
		this.sourceChannel = sourceChannel;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
