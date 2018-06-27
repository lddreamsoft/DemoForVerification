package ldsoft.hlhh.web.entity;



public class Prize {

	private int id;
	private int orderNumber;
	private String name;
	private String pictures;
	private String description;
	private String code; //兑换码
	private int limits=0;//会员积分默认为0
	private int stock;
	private float chance;
	private float chance1;
	private float chance2;
	
	private int webSiteMemberID=-1;
	private long webSiteMemberPointers=-1;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String picture) {
		this.pictures = picture;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public float getChance() {
		return chance;
	}
	public void setChance(float chance) {
		this.chance = chance;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getLimits() {
		return limits;
	}
	public void setLimits(int limits) {
		this.limits = limits;
	}
	
	
	public int getWebSiteMemberID() {
		return webSiteMemberID;
	}
	public void setWebSiteMemberID(int webSiteMemberID) {
		this.webSiteMemberID = webSiteMemberID;
	}
	
	
	public long getWebSiteMemberPointers() {
		return webSiteMemberPointers;
	}
	public void setWebSiteMemberPointers(long webSiteMemberPointers) {
		this.webSiteMemberPointers = webSiteMemberPointers;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public float getChance1() {
		return chance1;
	}
	public void setChance1(float chance1) {
		this.chance1 = chance1;
	}
	public float getChance2() {
		return chance2;
	}
	public void setChance2(float chance2) {
		this.chance2 = chance2;
	}
	
	
	}
