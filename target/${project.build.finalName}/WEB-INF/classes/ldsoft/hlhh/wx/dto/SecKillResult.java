package ldsoft.hlhh.wx.dto;

//所有ajax请求的返回类型全部是 :SecKillResult，封装json结果
public class SecKillResult<T> {
	
	private boolean success;
	
	private T data;
	
	private String error;
	
	

	public SecKillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}

	public SecKillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	} 
	
	
	
	

}
