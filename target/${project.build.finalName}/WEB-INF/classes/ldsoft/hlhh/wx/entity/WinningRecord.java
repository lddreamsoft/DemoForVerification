package ldsoft.hlhh.wx.entity;

import java.util.Date;

public class WinningRecord {
	
	private int id;
	private int activityID;
	private String openID;
	private String name;
	private String description;
	private String pictures;
	private String code;
	private byte received;
	private String receivedDesc;
	private int quantity;
	private String limits;
	
	private int prizeMemberPointers;
	
	private int prizeID;
	private Date createdTime;
	private Date modifiedTime;
	
	private String activityRules;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public int getPrizeID() {
		return prizeID;
	}
	public void setPrizeID(int prizeID) {
		this.prizeID = prizeID;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public int getActivityID() {
		return activityID;
	}
	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}
	public String getActivityRules() {
		return activityRules;
	}
	public void setActivityRules(String activityRules) {
		this.activityRules = activityRules;
	}
	public byte getReceived() {
		return received;
	}
	public void setReceived(byte received) {
		this.received = received;
	}
	public String getReceivedDesc() {
		return receivedDesc;
	}
	public void setReceivedDesc(String receivedDesc) {
		this.receivedDesc = receivedDesc;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getLimits() {
		return limits;
	}
	public void setLimits(String limits) {
		this.limits = limits;
	}
	public int getPrizeMemberPointers() {
		return prizeMemberPointers;
	}
	public void setPrizeMemberPointers(int prizeMemberPointers) {
		this.prizeMemberPointers = prizeMemberPointers;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	
}
