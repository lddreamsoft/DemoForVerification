package ldsoft.hlhh.wx.enums;

public enum secKillEnums {
	
//定义枚举。
	SECKILL_SUCCESS(1,"秒杀成功"),
	SECKILL_END(0,"秒杀结束"),
	SECKILL_REPEAT(2,"重复秒杀"),
	SECKILL_INNER_ERROR(3,"未知错误");
	
	
 private int state;
	
	

	private String info;
	
	
	private secKillEnums(int state, String info) {
		this.state = state;
		this.info = info;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public static secKillEnums stateOf(int index)
	{
		for(secKillEnums secKill:values())
		{
			if(secKill.getState()==index)
			{
				return secKill;
			}
			
		}
		
		return null;
		
	}
}
