package ldsoft.hlhh.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;
import ldsoft.hlhh.web.entity.VerificationCodeInfo;

import java.awt.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageTools {

	private final static Logger logger = LoggerFactory.getLogger(ImageTools.class);

	// Java的图片操作似乎都要转成bufferImage对象
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;
	private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
	private static final int FRAME_WIDTH = 0;
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

	// 获取图片base64编码
	public static String getImgBase64Code(String imagePath) {

		InputStream in = null;
		byte[] data = null;
		try {
			// 读取图片字节数组
			in = new FileInputStream(imagePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
			return new String(Base64.encodeBase64(data));
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			return null;
		}

	}

	// 获取随机简体汉字
	public static String getRandomChinese() {

		String str = null;
		int hightPos, lowPos; // 定义高低位
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
		lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
		byte[] b = new byte[2];
		b[0] = (new Integer(hightPos).byteValue());
		b[1] = (new Integer(lowPos).byteValue());

		// 转成中文
		try {
			str = new String(b, "GBK");
			return str;
		} catch (UnsupportedEncodingException e) {
			
			logger.error(e.getMessage(),e);
			return null;
		} 

	}

	/**
	 * 旋转并且画出指定字符串
	 * 
	 * @param s
	 *            需要旋转的字符串
	 * @param x
	 *            字符串的x坐标
	 * @param y
	 *            字符串的Y坐标
	 * @param g
	 *            画笔g
	 * @param degree
	 *            旋转的角度
	 */
	public static void RotateString(String s, int x, int y, Graphics g, int degree) {
		Graphics2D g2d = (Graphics2D) g.create();
		// 平移原点到图形环境的中心 ,这个方法的作用实际上就是将字符串移动到某一个位置
		g2d.translate(x - 1, y + 3);
		// 旋转文本
		g2d.rotate(degree * Math.PI / 180);
		// 特别需要注意的是,这里的画笔已经具有了上次指定的一个位置,所以这里指定的其实是一个相对位置
		g2d.drawString(s, 0, 0);
	}

	/**
	 * 
	 * @param width
	 * @param height
	 * @param many
	 * @param g
	 */
	public static void CreateRandomPoint(int width, int height, int many, Graphics g) {
		// 随机产生干扰点
		for (int i = 0; i < many; i++) {
			int x = new Random().nextInt(width);
			int y = new Random().nextInt(height);
			g.setColor(getColor());
			g.drawOval(x, y, 1, 1);
		}
	}

	/***
	 * @return 随机返回一种颜色
	 */
	public static Color getColor() {
		int R = (int) (Math.random() * 255);
		int G = (int) (Math.random() * 255);
		int B = (int) (Math.random() * 255);
		return new Color(R, G, B);
	}

	/**
	 * // 随机产生干扰线条
	 * 
	 * @param width
	 * @param height
	 * @param minMany
	 *            最少产生的数量
	 * @param g
	 */
	public static void createRandomLine(int width, int height, int minMany, Graphics g) {
		for (int i = 0; i < new Random().nextInt(minMany) + 5; i++) {
			int x1 = new Random().nextInt(width) % 15;
			int y1 = new Random().nextInt(height);
			int x2 = (int) (new Random().nextInt(width) % 40 + width * 0.7);
			int y2 = new Random().nextInt(height);
			g.setColor(getColor());
			g.drawLine(x1, y1, x2, y2);
		}
	}

	public static VerificationCodeInfo genVerificationCode(String srcImagePath) {

		VerificationCodeInfo verificationCodeInfo = new VerificationCodeInfo();

		// 格式为：["文字1","x坐标,y坐标"]
		String[][] wordsInfo = new String[2][2];

		// 0.复杂文字 避免被OCR识别
		// 1.随机文字(随机笔划)
		// 2.随机字体
		// 3.随机颜色
		// 4.随机描边
		// 5.随机位置
		// 6.随机背景图 250*150px
		// 7.图片为base64格式
		// 8.随机旋转方向

		/*
		 * String[] words = new String[] { "浠", "酏", "眑", "氤", "狺", "屓", "浯",
		 * "宯", "鸶", "訕", "菖", "铫", "訜", "笰", "晜", "翑", "翈", "鄅", "孲", "梻", "罥",
		 * "絜", "翗", "蛳", "猒", "萮", "溠", "貰", "喸", "甯", "剷", "辏", "蒭", "毂", "詤",
		 * "虡", "滅", "翜", "圔", "蓥", "躵", "蔒", "斠", "銞", "孷", "踂", "賏", "嵼", "碡",
		 * "歍", "諔", "霃", "暷", "頞", "槼", "翭", "澒", "銾", "劌", "寯", "螴", "膬", "黕",
		 * "駧", "憥", "薎", "篭", "髷", "熻", "薳", "禯", "濮", "嬪", "謩", "謕", "豯", "闈",
		 * "嬱", "駶", "鹪", "癚", "雟", "鼖", "嶲", "襘", "鵞", "鼩", "皨", "鬵", "嚞", "艣",
		 * "璷", "夒", "孼", "鼭", "軂", "鵱", "麕", "鏆", "鬌" };
		 */

		String[] fonts = new String[] {"华文隶书","华文中宋","仿宋","华文宋体","华文行楷","华文琥珀","黑体","新宋体","微软雅黑","方正兰亭超细黑简体","华文楷体","华文细黑","方正舒体",
				"隶书","华文彩云","宋体","楷体","华文新魏","华文仿宋","方正姚体","幼圆"};
		

		// 随机背景图
		// 将本地图片读取到bufferedImage,同时还可以将网络图片读取到bufferedImage，因本地图片读取速度优于网络图片，所以存在本地图片的情况下优先读取本地图片，
		// 读取网络图片： URL url = new URL(imgName); return ImageIO.read(url);
		// 读取本地图片： ImageIO.read(new File(imagePath));

		try {
			BufferedImage bufferedImage;
			bufferedImage = ImageIO
					.read(new File(srcImagePath + File.separator + Tools.getRandomValueWithBound(100) + ".jpg"));

			// 生成随机文字1
			String randomWord1 = getRandomChinese();

			// 生成随机文字2
			String randomWord2 = getRandomChinese();

			// 随机文字1和随机文字2不能是同一文字
			while (randomWord1.equals(randomWord2)) {
				randomWord2 = getRandomChinese();
			}

			// 生成随机文字1位置 需要考虑文字的宽度和高度，必须保证文字绘制在图片内
			int randomX1 = Tools.getRandomValueWithBound(bufferedImage.getWidth()); // 随机位置1X坐标
			if (randomX1 + 50 > bufferedImage.getWidth()) {
				randomX1 = randomX1 - 50;
			} else if (randomX1 < 50) {
				randomX1 = 50;
			}

			int randomY1 = Tools.getRandomValueWithBound(bufferedImage.getHeight());
			if (randomY1 + 50 > bufferedImage.getHeight()) {
				randomY1 = randomY1 - 50;
			} else if (randomY1 < 50) {
				randomY1 = 50;
			}

			// 生成随机文字2位置
			int randomX2 = Tools.getRandomValueWithBound(bufferedImage.getWidth()); // 随机位置2X坐标
			if (randomX2 + 50 > bufferedImage.getWidth()) {
				randomX2 = randomX2 - 50;
			} else if (randomX2 < 50) {
				randomX2 = 50;
			}

			int randomY2 = Tools.getRandomValueWithBound(bufferedImage.getHeight()); // 随机位置2Y坐标
			if (randomY2 + 50 > bufferedImage.getHeight()) {
				randomY2 = randomY2 - 50;
			} else if (randomY2 < 50) {
				randomY2 = 50;
			}

			double threshold = 50;// 控制2个随机汉字之间的距离

			// 文字1和文字2位置不能重叠 判断元素位置是否较近 开根号((x1-x2)的平方+(y1-y2)的平方)
			while (Math.sqrt(Math.pow(randomX1 - randomX2, 2) + Math.pow(randomY1 - randomY2, 2)) <= threshold) {
				randomX2 = Tools.getRandomValueWithBound(bufferedImage.getWidth()); // 随机位置2X坐标
				if (randomX2 + 50 > bufferedImage.getWidth()) {
					randomX2 = randomX2 - 50;
				} else if (randomX2 < 50) {
					randomX2 = 50;
				}

				randomY2 = Tools.getRandomValueWithBound(bufferedImage.getHeight()); // 随机位置2Y坐标

				if (randomY2 + 50 > bufferedImage.getHeight()) {
					randomY2 = randomY2 - 50;
				} else if (randomY2 < 50) {
					randomY2 = 50;
				}
			}

			Graphics2D g = bufferedImage.createGraphics();

			// 随机颜色
			g.setColor(getColor());

			// 随机字体
			 String randomFont =
			 fonts[Tools.getRandomValueWithBound(fonts.length)];
			 g.setFont(new Font(randomFont, Font.PLAIN, 30));

			// 随机文字1随机旋转角度
			int degree1 = Tools.getRandomValueWithBound(90);
			// 随机文字2随机旋转角度
			int degree2 = Tools.getRandomValueWithBound(90);

			// 旋转随机文字1
			RotateString(randomWord1, randomX1, randomY1, g, degree1);

			wordsInfo[0][0] = randomWord1; // 文字
			wordsInfo[0][1] = randomX1 + "," + randomY1; // 文字坐标

			// 旋转随机文字2
			RotateString(randomWord2, randomX2, randomY2, g, degree2);

			wordsInfo[1][0] = randomWord2; // 文字
			wordsInfo[1][1] = randomX2 + "," + randomY2; // 文字坐标

			// 随机文字1描边
			RotateString(randomWord1, randomX1 + 1, randomY1, g, degree1);
			RotateString(randomWord1, randomX1 - 1, randomY1, g, degree1);

			// g.drawString(randomWord1, randomX1 + 1, randomY1);
			// g.drawString(randomWord1, randomX1 - 1, randomY1);
			// g.drawString(randomWord1, randomX1, randomY1+1);
			// g.drawString(randomWord1, randomX1, randomY1-1);

			// 随机文字2描边
			RotateString(randomWord2, randomX2 + 1, randomY2, g, degree2);
			RotateString(randomWord2, randomX2 - 1, randomY2, g, degree2);
			// g.drawString(randomWord2, randomX2 + 1, randomY2);
			// g.drawString(randomWord2, randomX2 - 1, randomY2);
			// g.drawString(randomWord2, randomX2, randomY2+1);
			// g.drawString(randomWord2, randomX2, randomY2-1);

			g.dispose();

			verificationCodeInfo.setWordsInfo(wordsInfo);
			verificationCodeInfo.setBufferedImage(bufferedImage);

			// 输出最终的验证码图片
			// String imagePath = desImagePath + System.currentTimeMillis() +
			// ".jpg";
			// ImageIO.write(bufferedImage, "jpg", new File(imagePath));
			// result[3][0] = getImgBase64Code(imagePath);
			// System.out.println("完成。");

		} catch (IOException e) {
			
			logger.error(e.getMessage(),e);
			return null;
			
		}

		return verificationCodeInfo;

	}

	// 返回一律返回BufferedImage，然后由ImageIO来决定是写入本地磁盘还是写入浏览器response对象
	public static BufferedImage genQRCode(String content, int width, int height, String srcImagePath) {

		try {
			BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, true);
			int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
			for (int i = 0; i < scaleImage.getWidth(); i++) {
				for (int j = 0; j < scaleImage.getHeight(); j++) {
					srcPixels[i][j] = scaleImage.getRGB(i, j);
				}
			}
			Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
			hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

			// 生成二维码
			BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);
			// 二维矩阵转为一维像素数组
			int halfW = matrix.getWidth() / 2;
			int halfH = matrix.getHeight() / 2;
			int[] pixels = new int[width * height];

			for (int y = 0; y < matrix.getHeight(); y++) {
				for (int x = 0; x < matrix.getWidth(); x++) {
					// 左上角颜色,根据自己需要调整颜色范围和颜色
					if (x > 0 && x < 170 && y > 0 && y < 170) {
						Color color = new Color(231, 144, 56);
						int colorInt = color.getRGB();
						pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
					} else if (x > width - 170 && x <= width && y > 0 && y < 170) {
						// 右上角颜色
						Color color = new Color(231, 144, 56);
						int colorInt = color.getRGB();
						pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
					}
					// 读取图片
					else if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH
							&& y > halfH - IMAGE_HALF_WIDTH && y < halfH + IMAGE_HALF_WIDTH) {
						// 进入logo图片内部，使用logo图片像素点
						pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
					} else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
							&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
							|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
									&& y > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
									&& y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
							|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
									&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH
									&& y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH)
							|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
									&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH
									&& y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {

						// 二维码四周白色边框，自定图案绘制区域
						pixels[y * width + x] = 0xfffffff;

					} else {

						// 二维码真实信息区域，完成颜色的绘制
						int num1 = (int) (255 - (255.0) / matrix.getHeight() * (y + 1));
						int num2 = (int) (181 - (181.0 - 182.0) / matrix.getHeight() * (y + 1));
						int num3 = (int) (5 - (5.0) / matrix.getHeight() * (y + 1));
						Color color = new Color(num1, num2, num3);
						int colorInt = color.getRGB();
						// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
						pixels[y * width + x] = matrix.get(x, y) ? colorInt : 16777215;
						// 0x000000:0xffffff
					}
				}
			}
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			image.getRaster().setDataElements(0, 0, width, height, pixels);
			return image;

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) {

		try {
			double ratio = 0.0; // 缩放比例
			File file = new File(srcImageFile);
			BufferedImage srcImage = ImageIO.read(file);
			Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
				if (srcImage.getHeight() > srcImage.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				destImage = op.filter(srcImage, null);
			}
			if (hasFiller) {
				// 补白
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphic = image.createGraphics();
				graphic.setColor(Color.white);
				graphic.fillRect(0, 0, width, height);
				if (width == destImage.getWidth(null))
					graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null),
							destImage.getHeight(null), Color.white, null);
				else
					graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
							destImage.getHeight(null), Color.white, null);
				graphic.dispose();
				destImage = image;
			}

			return (BufferedImage) destImage;

		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			return null;
			
		}
		
	}

	// 合并图片，找到源图片中心点左上角位置后在目标图片中画小图片。
	public static final String mergeImage(String srcImgPath, String desImgPath, String resultImgPath) {
		
		String result;
		
		try {
			// 找到大图片中心点左上角位置后在大图片中画小图片。
			BufferedImage big = ImageIO.read(new File(srcImgPath));
			BufferedImage small = ImageIO.read(new File(desImgPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, "png", new File(resultImgPath));
			
			return "OK";
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			return null;
			
		}
	}

	// 根据图片尺寸居中裁剪
	public static final void cutCenterImage(String src, String dest, int w, int h) {

		try {
			Iterator iterator = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = (ImageReader) iterator.next();
			InputStream in = new FileInputStream(src);
			ImageInputStream iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			int imageIndex = 0;
			Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2,
					(reader.getHeight(imageIndex) - h) / 2, w, h);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "png", new File(dest));
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			
		}
	}

	// 图片裁剪二分之一
	public static final void cutHalfImage(String src, String dest) {

		try {
			Iterator iterator = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = (ImageReader) iterator.next();
			InputStream in = new FileInputStream(src);
			ImageInputStream iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			int imageIndex = 0;
			int width = reader.getWidth(imageIndex) / 2;
			int height = reader.getHeight(imageIndex) / 2;
			Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "png", new File(dest));
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			
		}
	}

	// 图片裁剪通用接口
	public static final void cutImage(String src, String dest, int x, int y, int w, int h) {

		try {
			Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
			ImageReader reader = (ImageReader) iterator.next();
			InputStream in = new FileInputStream(src);
			ImageInputStream iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(x, y, w, h);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, "jpg", new File(dest));
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			
		}

	}

	// 图片缩放
	public static void zoomImage(String src, String dest, int w, int h) {

		try {
			double wr = 0, hr = 0;
			File srcFile = new File(src);
			File destFile = new File(dest);
			BufferedImage bufImg = ImageIO.read(srcFile);
			Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
			wr = w * 1.0 / bufImg.getWidth();
			hr = h * 1.0 / bufImg.getHeight();
			AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
			Itemp = ato.filter(bufImg, null);
			ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
		}
	}

	public static void main(String[] args) {

		logger.error("hahaha");
		
		/*
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

	    FileAppender fileAppender = new FileAppender();
	    fileAppender.setContext(loggerContext);
	    fileAppender.setName("timestamp");
	    // set the file name
	    fileAppender.setFile("/logs/" + System.currentTimeMillis()+".log");

	    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
	    encoder.setContext(loggerContext);
	    encoder.setPattern("%r %thread %level - %msg%n");
	    encoder.start();

	    fileAppender.setEncoder(encoder);
	    fileAppender.start();

	    // attach the rolling file appender to the logger of your choice
	    Logger logbackLogger = loggerContext.getLogger("Main");
	    ((ch.qos.logback.classic.Logger) logbackLogger).addAppender(fileAppender);

	    // OPTIONAL: print logback internal status messages
	    StatusPrinter.print(loggerContext);

	    // log something
	    logbackLogger.debug("hello");
	    */

		// genVerificationCode("D:\\EclipseWorkspace\\hlhh\\src\\main\\webapp\\images\\verificationCode",
		// "D:\\background1\\");

		// cutCenterImage("d:\\result.png", "d:\\QRCode.png", 376, 376);
		// mergeImage("d:\\logo.png","d:\\QRCode.png","d:\\final.png");

		// 生成二维码:依次为内容(不支持中文),宽,高,中间图标路径,储存路径
		// 生成图像大小宽高不能小于500，否则会影响图像质量
		// try {
		// genQRCode("http://www.baidu.com/", 512, 512,
		// "D:\\logo.png","D:\\result.png");
		// } catch (WriterException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// 批量裁剪图片
		/*
		 * File file = new File("D:\\background"); if (file.exists()) { int
		 * i=76; for (File tempFile : file.listFiles()) {
		 * cutImage("D:\\background\\"+tempFile.getName(),"D:\\background1\\
		 * "+i+".jpg",250,150,250,150); i++; } System.out.println("完成."); }
		 */

		/*
		 * //批量改名 File file = new File("D:\\background1"); if (file.exists()) {
		 * int i=1; for (File tempFile : file.listFiles()) {
		 * tempFile.renameTo(new
		 * File("D:\\background\\"+i+".jpg")); i++; } System.out.println("完成.");
		 * }
		 */
	}
}
