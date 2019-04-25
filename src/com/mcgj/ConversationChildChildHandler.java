package com.mcgj;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.mcgj.util.ConstantUtil;
import com.mcgj.util.HttpClientUtil;

/**
 * 处理贴子的楼层数据,一楼是楼主数据
 * @author 杨晨
 * @date 2019-04-25
 * @address 深圳
 */
public class ConversationChildChildHandler {

	/**
	 * 处理楼层数据,贴子数据也在这里处理,外部无法获取用户头像等数据
	 * @param url
	 * @param id
	 */
	public void analysisData(String url,Integer id){
		try {
			Document document = Jsoup.connect(url).get();
			//获取第一楼数据,该楼是楼主的数据
			String title = document.select(".core_title_txt").get(0).text();//贴子标题
			System.out.println(title);
//			String name = document.select(".louzhubiaoshi").get(0).attr("author");
			String name = document.select(".icon_relative img").attr("username");//楼主
			String photo = document.select(".icon_relative img").attr("src").replaceAll("&", "26%");//头像
			System.out.println(name);
			System.out.println(photo);
			String content = document.select(".j_d_post_content").get(0).html();
			Element imgs = document.select(".j_d_post_content").get(0);
			List<String> tempImages = new ArrayList<>();//临时存放已经变更的标签 
			if(imgs.childNodeSize() > 0){
				for(Node node:imgs.childNodes()){
					String tempSrc = node.attr("src");
					if(tempSrc != null && !"".equals(tempSrc)){
						//将图片全部本地化
						String fileId = HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/file/upLoadRemoteFile", "url=" + tempSrc);
						System.out.println(fileId);
						tempImages.add("<img src=\"http://127.0.0.1:8090/mcgj/common/image?imgId="+fileId+ "\">");
						content = content.replaceFirst("<img.*?>", "yangchen_spider_token_1995_" + (tempImages.size() -1) );
					}
				}
				//将变更的img替换回content中
				for(int i = 0;i<tempImages.size(); i++){
					content = content.replaceAll("yangchen_spider_token_1995_" + i, tempImages.get(i));
				}
			}
			
			System.out.println(content);
//			System.out.println(content.replace("<img.*?>","11"));
//			System.out.println(content.replaceAll("<img.*?>", "000"));
			
//			System.out.println(attr);
//			System.out.println(document.select(".j_d_post_content img").size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 处理贴子内容,将所有的图片本地化
	 * @param content
	 * @return
	 */
	private String contentHandler(String content){
		return null;
	}
	public static void main(String[] args) {
		new ConversationChildChildHandler().analysisData("https://tieba.baidu.com/p/6112314583", null);
	}
}
