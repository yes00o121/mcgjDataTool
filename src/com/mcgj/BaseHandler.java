package com.mcgj;

import java.io.Serializable;

//基础处理
public interface BaseHandler extends Serializable{
	
	public String address = "http://127.0.0.1:8090/mcgj";
	
	/**
	 * 解析数据
	 * @param html
	 * @return
	 */
	public Object analysisData(String html);
	
	/**
	 * 插入数据,返回主键
	 * @param url
	 */
	public Integer addData(Object data);
	
	
}
