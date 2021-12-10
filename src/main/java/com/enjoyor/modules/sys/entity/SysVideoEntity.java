package com.enjoyor.modules.sys.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 视频信息
 * 
 */
public class SysVideoEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 视频名称
	 */	
	private String name;
	/**
	 * 视频ip
	 */
	private String ip;

	/**
	 * 端口号
	 */
	private String port;
	/**
	 * 通道号
	 */	
	private String channel;
	/**
	 * 用户名
	 */	
	private String username;
	/**
	 * 密码
	 */	
	private String password;
	/**
	 * 平台顺序号
	 */	
	private String url;
	/**
	 * 区域编号
	 */	
	private String areaId;
	
	private String areaName;
	/**
	 * 点位编号
	 */	
	private String siteId;
	
	private String siteName;
	/**
	 * 分页参数
	 */	
	private Boolean open;

	private List<?> list;
	/**
	 * 点位坐标
	 */	
	private double x;
	
    private double y;
    /**
	 * 供水/排水/污水视频
	 */	
    private String type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
}
