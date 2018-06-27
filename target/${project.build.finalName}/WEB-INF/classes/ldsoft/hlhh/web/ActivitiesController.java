package ldsoft.hlhh.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import ldsoft.hlhh.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ldsoft.hlhh.web.entity.Activity;
import ldsoft.hlhh.web.entity.Prize;
import ldsoft.hlhh.web.service.impl.ActivityPrizeServiceImpl;
import ldsoft.hlhh.web.service.impl.ActivityServiceImpl;
import ldsoft.hlhh.web.service.impl.PrizeServiceImpl;

@Controller
@RequestMapping("/activities")
public class ActivitiesController {

	// 数据返回统一采用json格式

	private final Logger logger = LoggerFactory.getLogger(ActivitiesController.class);

	@Autowired
	private PrizeServiceImpl prizeService;

	@Autowired
	private ActivityServiceImpl activityService;

	@Autowired
	private ActivityPrizeServiceImpl activityPrizeService;

	@RequestMapping(value = "/generate/excelFile", method = RequestMethod.GET)
	@ResponseBody
	public void generateExcelFile(HttpServletRequest request, HttpServletResponse response) {

		try {
			// 传入参数格式：{"fileName":xxx,"sheetName":xxx,"title":"字段名称1,字段名称2,...",
			// }
			// 前端不能发送ajax请求，必须使用window.location.href=""的方式，不然无法弹出下载excel文件的对话框
			String fileName = request.getParameter("fileName") + ".xls";
			String sheetName = request.getParameter("sheetName");
			String[] title = request.getParameter("title").split(",");

			List<Prize> prizes = activityPrizeService.queryById(1, (byte) 1);

			String[][] values = new String[prizes.size()][];
			for (int i = 0; i < prizes.size(); i++) {
				values[i] = new String[title.length];
				// 将对象内容转换成string
				Prize prize = prizes.get(i);
				values[i][0] = prize.getOrderNumber() + "";
				values[i][1] = prize.getName();
				values[i][2] = prize.getPictures();
				values[i][3] = prize.getDescription();
				values[i][4] = prize.getLimits() + "";
				values[i][5] = prize.getStock() + "";
				values[i][6] = prize.getChance() + "";
				values[i][7] = prize.getChance1() + "";
				values[i][8] = prize.getChance2() + "";
			}

			HSSFWorkbook wb = Tools.getHSSFWorkbook(sheetName, title, values, null);

			// 将wb中的图片url转换成图片
			// luckyShake/20170302/201703021146436701576753953929.gif,

			BufferedImage bufferImg = null;
			String commonPath = request.getServletContext().getRealPath("/") + "upload"+File.separator+"images"+File.separator;

			// luckyShake/20170302/201703021146095384604244343574.png,

			// 遍历内存中的wb中第1个sheet中的所有行，将行中的图片url字符串转换成图片
			Iterator<Row> iteratorRow = wb.getSheetAt(0).rowIterator();
			while (iteratorRow.hasNext()) {
				Row row = iteratorRow.next();
				Iterator<Cell> iteratorCell = row.cellIterator();
				while (iteratorCell.hasNext()) {
					Cell cell = iteratorCell.next();
					if (cell.getStringCellValue().indexOf(".png") >= 0 || cell.getStringCellValue().indexOf(".gif") >= 0
							|| cell.getStringCellValue().indexOf(".jpg") >= 0
							|| cell.getStringCellValue().indexOf(".bmp") >= 0
							|| cell.getStringCellValue().indexOf(".jpeg") >= 0) {

						String imagePath = commonPath + cell.getStringCellValue().replace("/", File.separator).replace(",", "");

						cell.setCellValue("");// 清空图片url字符串

						ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

						bufferImg = ImageIO.read(new File(imagePath));
						ImageIO.write(bufferImg, "png", byteArrayOut);

						HSSFSheet sheet = wb.getSheetAt(0);
						// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
						HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

						/**
						 * 该构造函数有8个参数
						 * 前四个参数是控制图片在单元格的位置，分别是图片距离单元格left，top，right，bottom的像素距离
						 * 后四个参数，前两个表示图片左上角所在的cellNum和
						 * rowNum，后两个参数对应的表示图片右下角所在的cellNum和 rowNum，
						 * excel中的cellNum和rowNum的index都是从0开始的
						 */

						// 控制图片生成的位置 Row从0开始，cell从0开始 左上角和右下角的坐标不能一致，否则无法生成图片
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(),
								cell.getRowIndex(), (short) (cell.getColumnIndex() + 1), cell.getRowIndex() + 1);

						// HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
						// 0, 0,
						// (short) 0, 0, (short) 5, 5);

						patriarch.createPicture(anchor,
								wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

					}
				}
			}

			/*
			 * 写到本地文件 FileOutputStream fileOut = null; fileOut = new
			 * FileOutputStream("C:/output_Excel.xls"); wb.write(fileOut);
			 * fileOut.close();
			 */

			// 自动列宽
			for (int i = 0; i < wb.getSheetAt(0).getRow(0).getLastCellNum(); i++) {
				wb.getSheetAt(0).autoSizeColumn(i);
			}

			// 如果用户在浏览器选择直接打开excel文件而不是下载Excel，则浏览器又会再次向服务器发送一次同样的请求，导致服务器再次发送同样的excel给浏览器，并且报错远程主机（客户端）已经关闭连接，实际这次请求没有必要发送
			// response.reset();
			// response.setContentType("application/vnd.ms-excel;charset=utf-8");
			// 这句可以不加
			response.setHeader("Content-disposition",
					"attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8") + "");
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
		}

	}

	@RequestMapping(value = "/queryPrizeByPrizeID/{prizeID}", method = RequestMethod.GET)
	@ResponseBody
	public Prize queryPrizeByPrizeID(@PathVariable int prizeID) {
		return prizeService.queryById(prizeID);
	}

	@RequestMapping(value = "/selectAvailablePrizes/{activityID}", method = RequestMethod.GET)
	@ResponseBody
	public List<Prize> selectAvailablePrizes(@PathVariable int activityID) {
		return prizeService.selectAvailablePrizes(activityID);
	}

	@RequestMapping(value = "/insertActivityPrize/{activityID}/{prizeID}", method = RequestMethod.GET)
	@ResponseBody
	public int insertActivityPrize(@PathVariable int activityID, @PathVariable int prizeID) {
		return activityPrizeService.insertActivityPrize(activityID, prizeID);
	}

	@RequestMapping(value = "/deleteActivityPrize/{activityID}/{prizeID}", method = RequestMethod.GET)
	@ResponseBody
	public int deleteActivityPrize(@PathVariable int activityID, @PathVariable int prizeID) {
		return activityPrizeService.deleteActivityPrize(activityID, prizeID);
	}

	/*
	 * @RequestMapping(value =
	 * "/queryById/activityPrize/{id}/{fromMg}/{pageSize}/{pageNumber}",method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseBody public List<Prize>
	 * queryActivityPrizeByIdPageInfo(@PathVariable int id, @PathVariable byte
	 * fromMg,@PathVariable byte pageSize,@PathVariable byte pageNumber) {
	 * 
	 * return activityPrizeService.queryByIdPageInfo(id, fromMg, pageSize,
	 * pageNumber); }
	 */

	@RequestMapping(value = "/queryById/activityPrize/{id}/{fromMg}", method = RequestMethod.GET, produces = "application/json;charset=utf-8") // 必须加上produces，否则json字符串中的中文会变成乱码
	@ResponseBody
	public String queryActivityPrizeById(HttpServletRequest request, @PathVariable int id, @PathVariable byte fromMg) {

		// easyUI会针对设置的后台的url自动发送2个参数，page和rows，分别表示第几页和每页记录数。
		// 返回前需要封装分页数据格式：{"total":18,"rows":[{"a":1},{"b":1}]}给easyUI
		String result = Tools.getPagingData(activityPrizeService.queryCountById(id, fromMg),
				activityPrizeService.queryByIdPageInfo(id, fromMg, Integer.valueOf(request.getParameter("rows")),
						Integer.valueOf(request.getParameter("page"))));
		return result;
	}

	@RequestMapping(value = "/updateActivity", method = RequestMethod.POST)
	@ResponseBody
	public int updateActivity(HttpServletRequest request) {

		try {
			int result = 0;
			// try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			Activity activity = new Activity();
			activity.setId(Integer.valueOf(request.getParameter("id")));
			activity.setName(request.getParameter("name"));
			activity.setStartTime(sdf.parse(request.getParameter("startTime")));

			activity.setEndTime(sdf.parse(request.getParameter("endTime")));

			activity.setType(Byte.valueOf(request.getParameter("type")));
			activity.setTimes(Integer.valueOf(request.getParameter("times")));
			activity.setTag(request.getParameter("tag"));
			activity.setDescription(request.getParameter("description"));
			result = activityService.updateActivity(activity);

			return result;

		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			return 0;
		}

	}

	@RequestMapping(value = "/queryById/activity/{id}/{currentTime}", method = RequestMethod.GET)
	@ResponseBody
	public Activity queryActivityByIdCurTime(@PathVariable int id, @PathVariable int currentTime) {
		return activityService.queryById(id, currentTime);
	}

	@RequestMapping(value = "/queryById/activity/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Activity queryActivityById(@PathVariable int id) {
		return activityService.queryById(id, 0);
	}

	@RequestMapping(value = "/insertActivity", method = RequestMethod.POST)
	@ResponseBody
	public int insertActivity(HttpServletRequest request) {

		try {
			int result = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Activity activity = new Activity();
			activity.setName(request.getParameter("name"));
			activity.setStartTime(sdf.parse(request.getParameter("startTime")));
			activity.setEndTime(sdf.parse(request.getParameter("endTime")));
			activity.setType(Byte.valueOf(request.getParameter("type")));
			activity.setTimes(Integer.valueOf(request.getParameter("times")));
			activity.setTag(request.getParameter("tag"));
			activity.setDescription(request.getParameter("description"));
			result = activityService.insertActivity(activity);
			return result;
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			return 0;
		}

	}

	@RequestMapping(value = "/updateOnlyPicturesOfPrize", method = RequestMethod.POST)
	@ResponseBody
	public int updateOnlyPicturesOfPrize(HttpServletRequest request) {

		int result = 0;
		try {
			Prize prize = new Prize();
			prize.setId(Integer.valueOf(request.getParameter("id")));
			prize.setPictures(request.getParameter("pictures"));
			result = prizeService.updateOnlyPicturesOfPrize(prize);
			return result;

		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			return 0;
		}

	}

	@RequestMapping(value = "/updatePrize", method = RequestMethod.POST)
	@ResponseBody
	public int updatePrize(HttpServletRequest request) {

		// 校验数据是否正确++
		if (request.getParameter("pictures") == "") {
			return 0;
		}

		int result = 0;
		try {
			Prize prize = new Prize();
			prize.setId(Integer.valueOf(request.getParameter("id")));
			prize.setOrderNumber(Integer.valueOf(request.getParameter("orderNumber")));
			prize.setName(request.getParameter("name"));

			prize.setPictures(request.getParameter("pictures"));
			prize.setDescription(request.getParameter("description"));
			prize.setLimits(Integer.valueOf(request.getParameter("limits")));
			prize.setStock(Integer.valueOf(request.getParameter("stock")));
			prize.setChance(Float.valueOf(request.getParameter("chance")));
			prize.setChance1(Float.valueOf(request.getParameter("chance1")));
			prize.setChance2(Float.valueOf(request.getParameter("chance2")));

			result = prizeService.updatePrize(prize);
			return result;

		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			return 0;
		}

	}

	@RequestMapping(value = "/deletePrizes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public int deletePrizes(@PathVariable int id) {
		return prizeService.deletePrize(id);
	}

	@RequestMapping(value = "/queryAll/prize/{offset}/{limit}", method = RequestMethod.GET)
	@ResponseBody
	public List<Prize> queryAll(@PathVariable int offset, @PathVariable int limit) {
		return prizeService.queryAll(offset, limit);
	}

	// 未调用
	@RequestMapping(value = "/queryById/prize/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Prize queryById(@PathVariable int id) {
		return prizeService.queryById(id);
	}

	@RequestMapping(value = "/insertPrize", method = RequestMethod.POST)
	@ResponseBody
	public int insertPrize(HttpServletRequest request) {

		// 校验数据是否正确++
		if (request.getParameter("pictures") == "") {
			return 0;
		}

		try {
			Prize prize = new Prize();
			prize.setOrderNumber(Integer.valueOf(request.getParameter("orderNumber")));
			prize.setName(request.getParameter("name"));

			prize.setPictures(request.getParameter("pictures"));
			prize.setDescription(request.getParameter("description"));
			prize.setLimits(Integer.valueOf(request.getParameter("limits")));
			prize.setStock(Integer.valueOf(request.getParameter("stock")));
			prize.setChance(Float.valueOf(request.getParameter("chance")));
			prize.setChance1(Float.valueOf(request.getParameter("chance1")));
			prize.setChance2(Float.valueOf(request.getParameter("chance2")));
			prizeService.insertPrize(prize);
			return prize.getId();
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			return 0;
		}
	}

}
