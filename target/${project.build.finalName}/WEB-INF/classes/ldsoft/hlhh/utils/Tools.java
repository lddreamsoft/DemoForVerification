package ldsoft.hlhh.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Tools {

	private final static Logger logger = LoggerFactory.getLogger(Tools.class);

	// 生成Excel
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {
		// 第一步，创建一个webbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();

		// style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		return wb;
	}

	// 将数据封装成easyUI DataGrid需要的分页格式：{"total":18,rows:[{"a":1},{"b":1}]}
	public static <T> String getPagingData(int total, List<T> rows) {
		JSONArray jsonArray = new JSONArray(rows);
		return "{\"total\":" + total + ",\"rows\":" + jsonArray.toString() + "}";
	}

	// 以UUID.randomUUID().hashCode()作为种子返回一个随机数
	public static float getRandomValue() {
		synchronized (Tools.class) {
			return (new Random(UUID.randomUUID().hashCode())).nextFloat();
		}
	}

	// 返回0-100之间的随机数
	public static int getRandomValueWithBound(int bound) {
		synchronized (Tools.class) {
			return (new Random(UUID.randomUUID().hashCode())).nextInt(bound);
		}
	}

	// 字节数组转换为图片
	public static void byteArrayToImage(byte[] data, String path) {
		if (data.length < 3 || path.equals(""))
			return;

		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
		}
		System.out.println("图片生成成功： " + path);

	}

	// 生成随机字符串,length表示生成字符串的长度
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 生成随机数字字符串
	public static String getSimpleRandomString(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 对字符串进行SHA1加密
	public static String getSHA1(String str) {

		try {
			if (str == null || str.length() == 0) {
				return "";
			}
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
		}

		return "";
	}

}
