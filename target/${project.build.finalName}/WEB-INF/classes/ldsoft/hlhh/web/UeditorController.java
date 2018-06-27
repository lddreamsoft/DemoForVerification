package ldsoft.hlhh.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/js/ueditor") // 杩欓噷鐨勫�紆editor鐨刢onfig.json涓殑鍊间竴鑷达紝鍚﹀垯鎺т欢鍔犺浇浼氭彁绀轰笂浼犳湇鍔℃棤娉曚娇鐢�

public class UeditorController {

	private final Logger logger = LoggerFactory.getLogger(UeditorController.class);

	/* 涓婁紶鍥剧墖 */
	@RequestMapping(value = "/fileUpload")
	public void config(HttpServletRequest req, HttpServletResponse res) {

		try {
			res.setContentType("application/json");
			res.setCharacterEncoding("utf-8");
			res.setHeader("Content-Type", "text/html");
			String rootPath = "";// req.getSession().getServletContext().getRealPath("/")
			String exec = new ActionEnter(req, rootPath).exec();

			PrintWriter printWriter = res.getWriter();
			printWriter.write(exec);
			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

}
