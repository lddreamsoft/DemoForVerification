package ldsoft.hlhh.web.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

//ignore "bytes" when return json format
//存储上传文件的元数据
@JsonIgnoreProperties({ "bytes" })
public class FileMeta {

	//显示在html中的名称
	private String displayName;
	
	//存储在磁盘上的物理文件名
	private String fileName;
	private String fileSize;
	private String fileType;

	private byte[] bytes;

	
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}


}
