package com.enjoyor.common.utils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.shiro.SecurityUtils;

import com.enjoyor.common.constant.AuthConst;


/**
 * Application Lifecycle Listener implementation class LogoutListener
 *
 */
@WebListener
public class LogoutListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public LogoutListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	
    	String token = (String)arg0.getSession().getAttribute(AuthConst.TOKEN);
    	
    	SessionStorage.INSTANCE.get(token);
    	
    	SessionStorage.INSTANCE.reList(token);
		//SessionStorage.INSTANCE.re("token");
    	SecurityUtils.getSubject().logout();
    }
}
