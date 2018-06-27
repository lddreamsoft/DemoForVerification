package ldsoft.hlhh.wx.dto;

import ldsoft.hlhh.wx.entity.SecKill;
import ldsoft.hlhh.wx.enums.secKillEnums;

public class SeckillExecution {
	
	private long secKillId;
	
	//秒杀结果状态
	private secKillEnums seckillenums; 
	
	//状态标识
	private int stateinfo;
	
	//秒杀成功对象
	private SecKill secKill;

	public long getSecKillId() {
		return secKillId;
	}

	public void setSecKillId(long secKillId) {
		this.secKillId = secKillId;
	}	

	public secKillEnums getSeckillenums() {
		return seckillenums;
	}

	public void setSeckillenums(secKillEnums seckillenums) {
		this.seckillenums = seckillenums;
	}

	public int getStateinfo() {
		return stateinfo;
	}

	public void setStateinfo(int stateinfo) {
		this.stateinfo = stateinfo;
	}

	public SecKill getSecKill() {
		return secKill;
	}

	public void setSecKill(SecKill secKill) {
		this.secKill = secKill;
	}

	public SeckillExecution(long secKillId, int state, int stateinfo, SecKill secKill) {
		//super();
		this.secKillId = secKillId;
		//this.state = state;
		this.stateinfo = stateinfo;
		this.secKill = secKill;
	}

	public SeckillExecution(long secKillId, secKillEnums seckillenums) {
		super();
		this.secKillId = secKillId;
		this.seckillenums = seckillenums;
	}

	
	
	
}
