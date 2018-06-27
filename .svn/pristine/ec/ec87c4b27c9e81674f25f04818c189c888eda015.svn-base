package ldsoft.hlhh.wx.dto;


//dto用于web层和service层之间传输数据
public class Exposer {
	
	private boolean isStarted;
	
    private String MD5;
    
    private long secKillId;
    
    private long now;
    
    private long Start;
    
    private long End;   

	public Exposer(boolean isStarted, String mD5, long secKillId) {
		
		this.isStarted = isStarted;
		MD5 = mD5;
		this.secKillId = secKillId;
	}
	
	
	

	public Exposer(boolean isStarted, long now, long start, long end) {
		
		this.isStarted = isStarted;
		this.now = now;
		Start = start;
		End = end;
	}

	public Exposer(boolean isStarted, long secKillId) {
		
		this.isStarted = isStarted;
		this.secKillId = secKillId;
	}
	
	
	

	public boolean isStarted() {
		return isStarted;
	}




	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}




	public String getMD5() {
		return MD5;
	}




	public void setMD5(String mD5) {
		MD5 = mD5;
	}




	public long getSecKillId() {
		return secKillId;
	}




	public void setSecKillId(long secKillId) {
		this.secKillId = secKillId;
	}




	public long getNow() {
		return now;
	}




	public void setNow(long now) {
		this.now = now;
	}




	public long getStart() {
		return Start;
	}




	public void setStart(long start) {
		Start = start;
	}




	public long getEnd() {
		return End;
	}




	public void setEnd(long end) {
		End = end;
	}




	@Override
	public String toString() {
		return "Exposer [isStarted=" + isStarted + ", MD5=" + MD5 + ", secKillId=" + secKillId + ", now=" + now
				+ ", Start=" + Start + ", End=" + End + "]";
	}
    
    
}
