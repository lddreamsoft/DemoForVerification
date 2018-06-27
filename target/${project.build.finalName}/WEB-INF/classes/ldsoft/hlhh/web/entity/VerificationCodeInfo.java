package ldsoft.hlhh.web.entity;

import java.awt.image.BufferedImage;

//用于存储验证码信息
public class VerificationCodeInfo {

	//文字1，和文字2坐标及顺序
	private String[][] wordsInfo;
	
	//图片本身
	private BufferedImage bufferedImage;

	public String[][] getWordsInfo() {
		return wordsInfo;
	}

	public void setWordsInfo(String[][] wordsInfo) {
		this.wordsInfo = wordsInfo;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
}
