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



/**
 * 客户端注销filter
 * 
 *
 */
public class LogoutFilter implements Filter {
	private FilterConfig config;

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();	
		String logoutUrl = config.getInitParameter(AuthConst.LOGOUT_URL);
		String token = (String) session.getAttribute(AuthConst.TOKEN);
		
		// 主动注销，即子系统提供的注销请求
		if ("/MonitorPlus/out".equals(request.getRequestURI())) {
			
			// 向认证中心发送注销请求
			Map<String, String> params = new HashMap<String, String>();
			params.put(AuthConst.LOGOUT_REQUEST, token);
			HTTPUtil.post(logoutUrl,params);
			// 注销后重定向
			response.sendRedirect("/MonitorPlus/index.html");
			// 注销本地会话
			session = SessionStorage.INSTANCE.get(token);
			SessionStorage.INSTANCE.reList(token);
			//SessionStorage.INSTANCE.re("token");
			if (session != null) {
				session.invalidate();
			}
			return;
		}
		chain.doFilter(req, res);
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
}