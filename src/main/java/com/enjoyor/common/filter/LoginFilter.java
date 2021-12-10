package com.enjoyor.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.enjoyor.common.constant.AuthConst;
import com.enjoyor.common.utils.HTTPUtil;
import com.enjoyor.common.utils.SessionStorage;
import com.enjoyor.modules.sys.shiro.ShiroUtils;

import sun.misc.BASE64Decoder;



/**
 * 客户端登录filter
 * 
 *
 */
public class LoginFilter implements Filter {
	private FilterConfig config;

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();		
		String uri = request.getRequestURI();			
		
		// js css png jpg等文件放行
		if(uri.contains(".png")||uri.contains(".js")||uri.contains(".css")||uri.contains(".jpg")||uri.contains(".woff2")||uri.contains(".woff")||uri.contains(".ttf")||uri.contains(".ico")||uri.contains(".index.html")){
			chain.doFilter(req, res);
			return;
		}
		
		// 被动注销，即从认证中心发送的注销请求
		String token2 = request.getParameter(AuthConst.LOGOUT_REQUEST);
		if (token2 != null && !"".equals(token2)) {
			session = SessionStorage.INSTANCE.get(token2);
			 SessionStorage.INSTANCE.reList(token2);
			//SessionStorage.INSTANCE.re("token");
			if (session != null) {
				session.invalidate();
			}
			chain.doFilter(req, res);
			return;
		}
		
		// 建立局部会话，证明已经登录，放行
		if (session.getAttribute(AuthConst.IS_LOGIN)!= null) {		
			chain.doFilter(req, res);
			return;

		}
		
		// 从认证中心回跳的带有token的请求，有效则放行
		String token = request.getParameter(AuthConst.TOKEN);	
		Map<String, String> params = new HashMap<String, String>();	
		if (token != null) {    //去认证中心效验
			params.put(AuthConst.TOKEN, token);
			boolean result2=HTTPUtil.post("http://192.168.101.10:8080/BasePlatForm/check", params);
		    if (!result2) {	
		    	
		    	response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "="+request.getScheme() +"://" + request.getServerName()
		        + ":" + request.getServerPort() + uri);
		        return;
		    }
		    /*if(){
		    	session.setAttribute("path", "modules/sys/index.html");
		    }else{
		    	session.setAttribute("path", null);
		    }*/
		    session.setAttribute("loginUrl", request.getScheme()+"://" + request.getServerName()
	        + ":" + request.getServerPort() +uri);
			session.setAttribute(AuthConst.IS_LOGIN, true);
			session.setAttribute(AuthConst.TOKEN, token);
			session.setMaxInactiveInterval(480*60);
			// 存储，用于注销
			SessionStorage.INSTANCE.set(token, session);
			//SessionStorage.INSTANCE.set2("token", token);
			chain.doFilter(req, res);
			return;
		}

		// 重定向至登录页面，并附带当前请求地址
		response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "="+request.getScheme()+"://" + request.getServerName()
        + ":" + request.getServerPort() +uri);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
}