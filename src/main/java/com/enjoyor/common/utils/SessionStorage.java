package com.enjoyor.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

public enum SessionStorage {
	INSTANCE;
	private Map<String, HttpSession> map = new HashMap<String, HttpSession>();
	private Map<String, String> map2 = new HashMap<String, String>();
	private Map<String, List<String>> map3 = new HashMap<String, List<String>>();
	
	public void set(String token, HttpSession session) {
		map.put(token, session);
	}
	public void set2(String token, String token2) {
		map2.put(token, token2);
	}
	public void setList(String token, List<String> list) {
		map3.put(token, list);
	}
	public HttpSession get2(String token) {
		HttpSession session=map.get(token);
		return session;
	}
	public List<String> get3(String token) {
		List<String> token2=map3.get(token);
		return token2;
	}	
	public HttpSession get(String token) {
		if (map.containsKey(token)) {
			return map.remove(token);
		}
		return null;
	}
	public void re(String token) {
		if (map2.containsKey(token)) {
			 map2.remove(token);
		}		
	}

	public void reList(String token) {
		if (map3.containsKey(token)) {
			 map3.remove(token);
		}		
	}
}