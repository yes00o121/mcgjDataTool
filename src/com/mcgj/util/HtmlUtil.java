package com.mcgj.util;

/**
 * html工具
 * @author 杨晨
 * @date 2019-04-24
 * @address 深圳
 *
 */
public class HtmlUtil {
	
	public static String getHtml(String url){
		if(url == null || !url.contains("http")){
			throw new RuntimeException(MessageUtil.URL_NOT_HTTP);
		}
		return HttpClientUtil.sendGet(url, null);
	}
	public static void main(String[] args) {
		System.out.println(HtmlUtil.getHtml("https://blog.csdn.net/Late_newbie/article/details/79504354"));
	}
}
