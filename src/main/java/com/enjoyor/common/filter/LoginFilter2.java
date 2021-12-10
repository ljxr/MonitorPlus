package com.enjoyor.common.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter2 implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	String passUrl = "";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		HashMap<String, String> sessiontoken = new HashMap<String, String>();
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "*");
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Headers",
				"Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
		String url = req.getQueryString();
		String token2 = "";
		String token3 = "";
		System.out.println("url:" + url);
		if (url != null) {
			token2 = url.split("=")[1];
			token3 = url.split("=")[0]; // token2参数名
			System.out.println("token2:" + token2);
		}
		if (token2 != null && "token2".equals(token3)) {
			System.out.println("再准备转回去");
			res.sendRedirect("http://localhost:8080/ajuserSecurity/ctoken?token2=" + token2 + "&path=" + req.getScheme()
					+ "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath());
			return;
		}

		if ("token3".equals(token3)) {
			System.out.println("效验成功！！");
			session.setAttribute("isLogin", true);
			sessiontoken.put(session.getId(), token2);
			res.sendRedirect("http://localhost:8081/ajuserSecurity/login.html");
			return;
		}

		if (session.getAttribute("isLogin") != null) {
			System.out.println(session.getAttribute("isLogin"));
			chain.doFilter(request, response);
			return;
		} else if (req.getRequestURL().indexOf(passUrl) >= 0) {
			chain.doFilter(request, response);
			return;
		} else {
			// 跳转至sso认证中心
			System.out.println("跳转中心");
			res.sendRedirect("http://localhost:8080/ajuserSecurity/ctoken?path=" + req.getScheme() + "://"
					+ req.getServerName() + ":" + req.getServerPort() + req.getContextPath());
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		passUrl = arg0.getInitParameter("passUrl");

	}

}
