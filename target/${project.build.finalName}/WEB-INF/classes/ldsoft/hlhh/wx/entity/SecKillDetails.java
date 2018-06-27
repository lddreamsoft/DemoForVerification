package ldsoft.hlhh.wx.entity;

import java.sql.Date;


public class SecKillDetails {
	
	private SecKill seckill; //此处为1对1的关系，如果是1对多的关系，这里应该是声明一个list<seckill>

	public SecKill getSeckill() {
		return seckill;
	}

	public void setSeckill(SecKill seckill) {
		this.seckill = seckill;
	}


	private int seckill_id;
	

	private int user_phone;
	
	private int state;
	
	private Date create_time;

	public int getSeckill_id() {
		return seckill_id;
	}

	public void setSeckill_id(int seckill_id) {
		this.seckill_id = seckill_id;
	}

	public int getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(int user_phone) {
		this.user_phone = user_phone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Override
	public String toString() {
		return "SecKillDetails [seckill=" + seckill + ", seckill_id=" + seckill_id + ", user_phone=" + user_phone
				+ ", state=" + state + ", create_time=" + create_time + "]";
	}
	
	
	
}
