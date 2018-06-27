package ldsoft.hlhh.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ldsoft.hlhh.web.entity.FileMeta;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

@Controller
@RequestMapping("/jquery")

public class JqueryFileUploadController {

	private final Logger logger = LoggerFactory.getLogger(JqueryFileUploadController.class);

	/* 涓婁紶鍥剧墖 */
	/***************************************************
	 * URL: /rest/controller/upload upload(): receives files
	 * 
	 * @param request
	 *            : MultipartHttpServletRequest auto passed
	 * @param response
	 *            : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 * @throws FileNotFoundException
	 * @throws IOException
	 ****************************************************/
	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {

		try {
			// 鏍规嵁绫诲瀷:type寤虹珛瀛愮洰褰�
			String type = request.getParameter("type");

			LinkedList<FileMeta> files = new LinkedList<FileMeta>();
			FileMeta fileMeta = null;

			// 1. build an iterator
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = null;

			// 2. get each file
			while (itr.hasNext()) {

				// 2.1 get next MultipartFile
				mpf = request.getFile(itr.next());

				fileMeta = new FileMeta();

				// 灏嗕腑鏂囨枃浠跺悕涓殑涔辩爜杞崲涓簎tf-8鏍煎紡
				fileMeta.setDisplayName(new String(mpf.getOriginalFilename().getBytes("ISO-8859-1"), "utf-8"));

				fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
				fileMeta.setFileType(mpf.getContentType());
				// fileMeta.setBytes(mpf.getBytes());//杩欏彞浼氬鑷翠笂浼犲鏂囦欢鏃跺嚭鐜帮細java.util.ConcurrentModificationException

				// 褰撳墠鏃ユ湡鏃堕棿
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

				// 褰撳墠鏃ユ湡
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

				// 鏂囦欢鎵╁睍鍚�
				// 鍥剧墖鏂囦欢鍚嶄腑鍙兘鍖呭惈鐗规畩瀛楃.锛宨ndexOf鏀规垚lastIndexOf
				// mpf.getOriginalFilename().lastIndexOf(".");
				String suffix = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."),
						mpf.getOriginalFilename().length());

				String randomFileName = sdf1.format(new Date()) + (Math.random() * 10000 + "").replace(".", "")
						+ suffix;
				fileMeta.setFileName(sdf2.format(new Date()) + "/thumbnail." + randomFileName);

				// 鍙戝竷鍒發inux鍚庨渶瑕佹敼鍙樿矾寰�
				// 鍒涘缓鐩綍锛屾枃浠朵繚瀛樿嚦褰撳ぉ鐨勭洰褰曚笅銆�

				String dir = request.getServletContext().getRealPath("/") + File.separator+"upload"+File.separator+"images"+File.separator + type + File.separator
						+ sdf2.format(new Date());
				File tempFile = new File(dir);
				if (!tempFile.exists() && !tempFile.isDirectory()) {
					tempFile.mkdirs(); // mkdir浼氬け璐�
				}

				String lstg_ImagePath1 = dir + File.separator + randomFileName;
				File destinationDir = new File(dir + File.separator);

				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(dir + File.separator + randomFileName));
				// 鍒涘缓鍥剧墖缂╃暐鍥撅紝鐢ㄤ簬鍦ㄥ鎴风棰勮鍥剧墖

				Thumbnails.of(lstg_ImagePath1).size(200, 200).outputQuality(1).toFiles(destinationDir,
						Rename.PREFIX_DOT_THUMBNAIL);

				// 2.4 add to files
				files.add(fileMeta);
			}
			// result will be like this
			// [{"fileName":"app_engine-85x77.png","fileSize":"8
			// Kb","fileType":"image/png"},...]

			// IE娴忚鍣ㄧ郴鍒楀吋瀹规�у鐞嗭紝鍚﹀垯鐩存帴杩斿洖json鏍煎紡鏁版嵁鍦ㄥ鎴风浼氭彁绀轰笅杞借json鏂囦欢
			if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) {

				// 蹇呴』璁剧疆瀛楃闆嗭紝鍚﹀垯鏂囦欢鍚嶄細涔辩爜
				response.setContentType("text/html;charset=UTF-8;");

				// 鎵嬪伐鏋勯�爅son鏍煎紡瀛楃涓�

				JSONArray jsonArray = new JSONArray();
				for (FileMeta temp : files) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("displayName", temp.getDisplayName());
					jsonObject.put("fileName", temp.getFileName());
					jsonObject.put("fileSize", temp.getFileSize());
					jsonObject.put("fileType", temp.getFileType());
					jsonArray.put(jsonObject);
				}

				String lstg_Temp = jsonArray.toString().replace("[", "").replace("]", "");
				response.getWriter().print(lstg_Temp);
			}

			return files;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	/* 娉ㄦ剰璁剧疆spel琛ㄨ揪寮忥紝鍚﹀垯xxx.jpg浼氳鎴柇鎴恱xx */
	@RequestMapping(value = "/delete/{fileName:.+}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFile(HttpServletRequest request, @PathVariable String fileName) {

		String type = request.getParameter("type");

		String result = "";
		int step = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String dir = request.getServletContext().getRealPath("/") + File.separator+"upload"+File.separator+"images"+File.separator + type + File.separator
				+ sdf.format(new Date());

		// 鍒犻櫎缂╃暐鍥�
		File thumbnailFile = new File(dir + File.separator+"thumbnail." + fileName);

		if (thumbnailFile.isFile() && thumbnailFile.exists()) {
			thumbnailFile.delete();
			step += 1;
		}

		// 鍒犻櫎鍘熸枃浠�
		File originalFile = new File(dir + File.separator + fileName);

		if (originalFile.isFile() && originalFile.exists()) {
			originalFile.delete();
			step += 1;
		}

		if (step == 2) {
			result = "Success";
		} else {
			result = "Failed:err-100";
		}

		return result;
	}

	// 瑁佸壀鍥剧墖
	/* 娉ㄦ剰璁剧疆spel琛ㄨ揪寮忥紝鍚﹀垯xxx.jpg浼氳鎴柇鎴恱xx */
	@RequestMapping(value = "/crop/{fileName:.+}/{cropRegion}", method = RequestMethod.GET)
	@ResponseBody
	public String cropFile(HttpServletRequest request, @PathVariable String fileName, @PathVariable String cropRegion) {

		try {

			String type = request.getParameter("type");

			String result = "";
			int step = 0;

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

			// 鏂囦欢鎵╁睍鍚�
			String suffix = fileName.substring(fileName.indexOf("."), fileName.length());
			String randomFileName = fileName.substring(0, 8) + (Math.random() * 10000 + "").replace(".", "") + suffix;

			// 201702281141357398674955168383.png
			String dir = request.getServletContext().getRealPath("/") + "upload"+File.separator+"images"+File.separator + type + File.separator
					+ fileName.substring(0, 8); // 瑁佸壀鐨勬枃浠朵笉涓�瀹氭槸褰撳ぉ鐨勬枃浠�,浠ヤ笂浼犵殑鏂囦欢鍚嶄负鍑�

			String destinationFilePath = dir + File.separator + randomFileName;

			// 1.瀵瑰師鏂囦欢杩涜瑁佸壀骞朵繚瀛樻垚鏂版枃浠�.2.鍒犻櫎鍘熸枃浠� 3.鍒犻櫎鍘熸枃浠剁殑缂╃暐鍥�
			// 4.瀵硅鍓悗鐨勬柊鏂囦欢鐢熸垚缂╃暐鍥�
			String originalFilePath = dir + File.separator + fileName;

			// 1.瑁佸壀鍘熸枃浠跺苟淇濆瓨鎴愭柊鏂囦欢锛屼繚瀛樻椂鏇存崲鏂囦欢鍚嶈В鍐虫祻瑙堝櫒缂撳瓨鍥剧墖闂
			File originalFile = new File(originalFilePath);

			if (originalFile.isFile() && originalFile.exists()) {

				String[] arr_cropRegion = cropRegion.split(",");

				// Rectangle rect=new Rectangle();
				// Region reg=new Region();
				// Math.rou
				// Float.valueOf(arr_cropRegion[0]).intValue();
				// arr_cropRegion[0].cou

				// 鍏堟牴鎹搴︽棆杞�
				int t = 0;
				if (Integer.valueOf(arr_cropRegion[4]) != 0) {
					t++;
					Thumbnails.of(originalFilePath).scale(1).rotate(Integer.valueOf(arr_cropRegion[4])).outputQuality(1)
							.toFile(destinationFilePath);
				}

				int x, y, width, height;

				x = Math.round(Float.valueOf(arr_cropRegion[0]));
				y = Math.round(Float.valueOf(arr_cropRegion[1]));
				width = Math.round(Float.valueOf(arr_cropRegion[2]));
				height = Math.round(Float.valueOf(arr_cropRegion[3]));

				Thumbnails.of(t == 0 ? originalFilePath : destinationFilePath).sourceRegion(x, y, width, height)
						.size(width, height).outputQuality(1).toFile(destinationFilePath);

				// thumbnailFile.delete();
				step += 1;
			}

			// 2.鍒犻櫎鍘熸枃浠�
			if (originalFile.isFile() && originalFile.exists()) {
				originalFile.delete();
				step += 1;
			}

			// 3.鍒犻櫎鍘熸枃浠剁殑缂╃暐鍥�
			File thumbnailFile = new File(dir + File.separator+"thumbnail." + fileName);

			if (thumbnailFile.isFile() && thumbnailFile.exists()) {
				thumbnailFile.delete();
				step += 1;
			}

			File destinationDir = new File(dir + File.separator);

			// 4.瀵硅鍓悗鐨勬柊鏂囦欢鐢熸垚缂╃暐鍥�

			Thumbnails.of(destinationFilePath).size(360, 360).outputQuality(1).toFiles(destinationDir,
					Rename.PREFIX_DOT_THUMBNAIL);
			step += 1;

			if (step == 4) {
				result = randomFileName;// 杩斿洖瑁佸壀鍚庣殑鏂囦欢鍚�
			} else {
				result = "Failed:err-101";
			}

			return result;

		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			return null;
			
		}
	}

}
