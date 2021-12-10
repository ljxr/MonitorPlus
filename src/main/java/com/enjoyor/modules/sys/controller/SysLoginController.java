 package com.enjoyor.modules.sys.controller;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enjoyor.common.constant.AuthConst;
import com.enjoyor.common.utils.R;
import com.enjoyor.common.utils.SessionStorage;
import com.enjoyor.modules.sys.entity.SysUserEntity;
import com.enjoyor.modules.sys.service.SysMenuService;
import com.enjoyor.modules.sys.shiro.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import sun.misc.BASE64Decoder;


/**
 * 登录相关
 * 
 */
@Controller
public class SysLoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysMenuService sysMenuService;
	
/*	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}*/
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req,HttpServletResponse res)throws IOException {

/*		String tokenu=SessionStorage.INSTANCE.get3("token");
		String username=tokenu.split("-")[0].split("_")[0];
		String password=tokenu.split("-")[0].split("_")[1];*/

		HttpSession session = req.getSession();
		String token2=(String)session.getAttribute(AuthConst.TOKEN);
		//String path=(String)session.getAttribute("path");
		BASE64Decoder decoder=new BASE64Decoder();
		String username=token2.split("-")[0].split("_")[0];
	    String password=token2.split("-")[0].split("_")[1];
	    String decode=new String(decoder.decodeBuffer(password));
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, decode);
			subject.login(token);
		}catch (UnknownAccountException e) {
			return "/404.html";
		}catch (IncorrectCredentialsException e) {
			return "/404.html";
		}catch (LockedAccountException e) {
			return "/404.html";
		}catch (AuthenticationException e) {
			return "/404.html";
		}
		String loginUrl = (String)session.getAttribute("loginUrl");
		//System.err.println(loginUrl);
		return "redirect:"+loginUrl;
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
	
	/**
	 * 获取账号的权限
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuPerms", method = {RequestMethod.GET})
    public String getMenuPerms(HttpServletRequest request, HttpServletResponse response) {
		String userName = (String) request.getParameter("userName");
		List<String> permsList = sysMenuService.getMenuPerms(userName);
		String result = "";
		for(String perms : permsList){
			if(null != perms){
				result += perms;
				result += ",";
			}
		}
		return result;
    }
	
}
