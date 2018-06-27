package ldsoft.hlhh.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


//请求拦截器，用于判断用户浏览器是否需要升级

public class UpgradeBrowserHandlerInterceptor implements   HandlerInterceptor     {

	//拦截前处理
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		//判断用户浏览器版本
		String browserVersion=request.getHeader( "USER-AGENT" ).toLowerCase();
				
		if(browserVersion.indexOf("msie 8.0")>-1||browserVersion.indexOf("msie 7.0")>-1||browserVersion.indexOf("msie 6.0")>-1)
		{
			//ie6,7,8跳转至升级浏览器页面 		
			response.sendRedirect(request.getContextPath()+"/upgradeBrowser.html");		
		}
				
		System.out.println("拦截1次。");
		return false;
	}

	//拦截后处理
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		
		// TODO Auto-generated method stub
		//System.out.println("2");
		
	}

	//全部完成后处理
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	
		//System.out.println("3");
	}

}
