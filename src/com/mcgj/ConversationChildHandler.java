package com.mcgj;

import java.util.Set;

import com.mcgj.util.HtmlUtil;

/**
 * 处理贴子数据
 * @author 杨晨
 * @date 2019-04-25
 * @address 深圳
 */
public class ConversationChildHandler {
	
	/**
	 * 处理贴子数据
	 * @param url 贴吧地址
	 * @param page 页数
	 * @param id 贴吧主键
	 * @return
	 */
	public Integer analysisData(String url,Integer id) {
//		http://tieba.baidu.com/f?kw=redis&ie=utf-8&pn=50
		//获取指定页数的贴子地址
		Set<String> rows = HtmlUtil.getHtmlRows(url);
		for(String row : rows){
			
		}
		return null;
	}
}
