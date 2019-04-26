package com.mcgj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.mcgj.util.ConstantUtil;
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
		Set<String> synnchronizedSet = Collections.synchronizedSet(rows);//转为线程安全的线程
//		Integer count = rows.size()/ConstantUtil.THREAD_COUNT;//多少条数据为一个线程
//		List<String> tempList = new ArrayList<>();
//		HANDLER_COUNT
		//达到指定数据开启一个线程
		int index = 1;
		//临时list
		List<String> list = new ArrayList<>();
		//开启指定数量线程处理
		for(String row : synnchronizedSet){
			index++;
			list.add(row);
			if(index > 100){
				index = 1;
				List<String> tempList = new ArrayList<>(list);
				new Thread(){
					public void run(){
						for(int i=0;i<tempList.size();i++){
							new ConversationChildChildHandler().analysisData(tempList.get(i),id);
						}
					}
				}.start();
				list = new ArrayList<>();
			}
		}
		//剩余的数据如果大于0同样进行处理
		if(list.size() > 0){
			for(int i=0;i<list.size();i++){
				new ConversationChildChildHandler().analysisData(list.get(i),id);
			}
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(956/10);
	}
}
