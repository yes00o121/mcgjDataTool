package com.mcgj.util;

import java.util.HashSet;
import java.util.Set;

import javax.swing.plaf.SliderUI;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	/**
	 *获取指定页数的贴地址
	 * @param url
	 * @param limit
	 * @return
	 */
	public static Set<String> getHtmlRows(String url){
		
		try {
			Set<String> set = new HashSet<>();
			for(int i = 0;i<ConstantUtil.PAGE-1;i++){
				//避免被百度发现爬虫,睡个0.05秒
				Thread.sleep(50);
				Document document = Jsoup.connect(url + "&pn=" + i*ConstantUtil.PN).timeout(ConstantUtil.TIME_OUT).get();
//				获取当前页数下的所有贴子地址和标题
				Elements select = document.select(".j_th_tit  a[href]");
				for(Element link:select){
					set.add(ConstantUtil.URL + link.attr("href"));
				}
			}
			System.out.println(ConstantUtil.PAGE + "页,共找到"+set.size() + "个贴子");
			return set;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 特殊字符处理
	 * @param str
	 * @return
	 */
	private String charHandler(String str) {
		return str.replaceAll("&","26%").replaceAll("%2F", "/");
	}
	
	public static void main(String[] args) {
		HtmlUtil.getHtmlRows("https://tieba.baidu.com/f?ie=utf-8&kw=%E5%8A%A8%E6%BC%AB&fr=search");
	}
}
