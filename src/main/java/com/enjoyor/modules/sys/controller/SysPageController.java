package com.enjoyor.modules.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 */
@Controller
public class SysPageController {
	
	@RequestMapping("modules/{module}/{url}.html")
	public String module(@PathVariable("module") String module, @PathVariable("url") String url){
		return "modules/" + module + "/" + url + ".html";
	}

	@RequestMapping("{url}.html")
	public String url(@PathVariable("url") String url){
		return url + ".html";
	}

	@RequestMapping("/")
	public String index(){
		return "index.html";
	}
}
