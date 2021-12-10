package com.enjoyor.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.enjoyor.modules.monitor.controller.PipeFacController;
import com.enjoyor.modules.monitor.entity.PipeDataEntity;
import com.enjoyor.modules.monitor.service.MonitorDataService;
import com.enjoyor.modules.monitor.service.imp.MonitorDataServiceimp;

import redis.clients.jedis.Jedis;

@Component
@ServerEndpoint(value="/websocket/{hs}",configurator=GetHttpSessionConfigurator.class)
public class WebSocket {
	
	
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
 
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
 
    private static ConcurrentHashMap<String, WebSocket> webSocketSet2 = new ConcurrentHashMap<String, WebSocket>();
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    private HttpSession httpSession;
    
    private String hs = "";
    
    public Session getSession(){
		return this.session;
	}
    
    public HttpSession getHttpSession(){
		return this.httpSession;
	}
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "hs") String param,Session session,EndpointConfig config){
    	httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    	
    	hs = param;
    	
    	this.session = session;
    	
        //webSocketSet.add(this);     //加入set中
        webSocketSet2.put(param, this);
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }
 
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	if(!hs.equals("")){
        //webSocketSet.remove(this);  //从set中删除    	   	
        webSocketSet2.remove(hs);
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    	}
    }
 
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, HttpSession httpSession) {
        //System.out.println("来自客户端的消息:" + message+httpSession.toString());      
        //this.session=session;
        //群发消息
/*        for(WebSocket item: webSocketSet){
            try {
            	if(this==item){
                item.sendMessage(message);
            	}
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        
        try {
        	
			webSocketSet2.get(httpSession.toString()).sendMessage(message);
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}*/
    	
    	String sendUserno = message.split("#@")[1];
    	String sendMessage = message.split("#@")[0];
    	//System.out.println(webSocketSet2.get(sendUserno) );
    	try {
    	if (webSocketSet2.get(sendUserno) != null) {
    		webSocketSet2.get(sendUserno).sendMessage(sendMessage);
    	} else {
    	System.out.println("当前用户不在线");
    	closeSession(httpSession);
    	 }    	
    	 }catch (IOException e){
    		e.printStackTrace();
    	 }
    }
 
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
    	
        System.out.println("发生错误");
        error.printStackTrace();
    }
 
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        //this.session.getBasicRemote().sendText(message); 

        synchronized (session){
        this.session.getAsyncRemote().sendText(message);
        }
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }
 
    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }
    
    public void closeMessage(){
    	   	
    	this.httpSession.setAttribute("result", false);   
    }
    
    public void closeSession(HttpSession session){
    	MonitorDataService monitorDataService=new MonitorDataServiceimp();
    	session.setAttribute("websocket", false);
        monitorDataService.sendMessage(null,session);
    }
}