package ldsoft.hlhh.web.entity;

import java.util.Date;

public class DrawInfo {
	
	private int memberID=-1;
	private int pointers; //会员积分
	private byte isSubscribed; //是否关注花海微信 
	private Date drawDate; //抽奖时间
	private int drawTimes; //可抽奖次数
	private long lastDrawTime;//最后一次抽奖的时间
	private int dropDrawTimes;//丢弃请求次数
	private boolean isWebSiteMember; //是否为网站会员 
	
	
	public int getMemberID() {
		return memberID;
	}
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}
	public int getPointers() {
		return pointers;
	}
	public void setPointers(int pointers) {
		this.pointers = pointers;
	}
	public byte getIsSubscribed() {
		return isSubscribed;
	}
	public void setIsSubscribed(byte isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	
	public int getDrawTimes() {
		return drawTimes;
	}
	public void setDrawTimes(int drawTimes) {
		this.drawTimes = drawTimes;
	}
	public int getDropDrawTimes() {
		return dropDrawTimes;
	}
	public void setDropDrawTimes(int dropDrawTimes) {
		this.dropDrawTimes = dropDrawTimes;
	}
	public long getLastDrawTime() {
		return lastDrawTime;
	}
	public void setLastDrawTime(long lastDrawTime) {
		this.lastDrawTime = lastDrawTime;
	}

	public boolean isWebSiteMember() {
		return isWebSiteMember;
	}
	public void setWebSiteMember(boolean isWebSiteMember) {
		this.isWebSiteMember = isWebSiteMember;
	}
	public Date getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(Date drawDate) {
		this.drawDate = drawDate;
	}

}
