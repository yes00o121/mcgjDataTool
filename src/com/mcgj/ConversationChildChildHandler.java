package com.mcgj;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
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
//			System.out.println("正在处理的贴子页数:"+url);
			Document document = Jsoup.connect(url).timeout(ConstantUtil.TIME_OUT).get();
			//获取第一楼数据,该楼是楼主的数据
			String title = document.select(".core_title_txt").get(0).text().trim().replaceAll("\n", "");//贴子标题
//			String name = document.select(".icon_relative img").last().attr("username");//楼主
			Element element = document.select("#j_p_postlist").select(".l_post_bright").get(0);//获取楼主数据
			String name = element.select(".d_author").select(".d_name a").text();//层主名称
			String photo = element.select(".d_author").select(".p_author_face img").attr("src").replaceAll("&", "26%");//层主头像
//			String photo = document.select(".icon_relative img").attr("src").replaceAll("&", "26%");//头像
			if(name == null || "".equals(photo)) {
				System.out.println("..................");
			}
			//上传用户头像
			String photoId = contentHandler(photo);
			String content = document.select(".j_d_post_content").get(0).html();
			Element imgs = document.select(".j_d_post_content").get(0);
//			List<String> tempImages = new ArrayList<>();//临时存放已经变更的标签 
			content = this.contentFilter(imgs,content);
//			if(imgs.childNodeSize() > 0){
//				for(Node node:imgs.childNodes()){
//					String tempSrc = node.attr("src");
//					if(tempSrc != null && !"".equals(tempSrc)){
//						//将图片全部本地化
//						String fileId = contentHandler(tempSrc);
//						tempImages.add("<img src=\"http://127.0.0.1:8090/mcgj/common/image?imgId="+fileId+ "\">");
//						content = content.replaceFirst("<img.*?>", "yangchen_spider_token_1995_" + (tempImages.size() -1) );
//					}
//				}
//				//将变更的img替换回content中
//				for(int i = 0;i<tempImages.size(); i++){
//					content = content.replaceAll("yangchen_spider_token_1995_" + i, tempImages.get(i));
//				}
//			}
			//将用户进行上传,返回用户主键
			String userId = HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/spider/register", "userName=" + name + "&account=" + name + "&password=" + ConstantUtil.PASSWORD + "&photo=" + photoId);
			//插入贴子数据
//			System.out.println(id);
			String record = HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/spider/addConversationChild", "conversationId=" + id + "&userId=" + userId + "&title=" + title + "&content=" + content);
			//获取贴子id
			String conversationChildId = JSONObject.parseObject(record).getJSONObject("result").getString("id");
			System.out.println("插入贴子数据结束:"+conversationChildId);
			//避免请求过于频繁,每个楼层暂停0.1秒
//			Thread.sleep(100);
			//处理楼层数据
			this.analysisLCData(conversationChildId, url, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 处理楼层数据
	 * @param conversationChildId 贴子id
	 * @param url //贴吧id
	 * @param userName 楼主名称,楼层和楼主名称相同标识为楼主
	 */
	private void analysisLCData(String conversationChildId,String url,String userName){
		//解析url,判断是否达到常量中指定的解析页数,少于常量以实际页数为准
		try {
			//当前贴子的实际页数
			Integer currentPage = ConstantUtil.LCPAGE;
			Document document = Jsoup.connect(url).timeout(ConstantUtil.TIME_OUT).get();
			//获取当前贴子的实际页数
			String page = document.select(".l_reply_num span").get(1).text();
			if(Integer.parseInt(page) < ConstantUtil.LCPAGE){
				currentPage = Integer.parseInt(page);
			}
			//处理每页的楼层数据
			for(int i=0;i<currentPage;i ++){
				this.lcHandler(url + "?pn=" + (i+1), conversationChildId, userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理楼层数据
	 * @param url 
	 * @param conversationChildId 贴子id
	 * @param userName
	 */
	private void lcHandler(String url,String conversationChildId,String userName){
		try {
			Document document = Jsoup.connect(url).timeout(ConstantUtil.TIME_OUT).get();
			Elements elements = document.select("#j_p_postlist").select(".l_post_bright");//获取所有的楼层数据
			for(Element element:elements){
				String name = element.select(".d_author").select(".d_name a").text();//层主名称
				String photo = element.select(".d_author").select(".p_author_face img").attr("src");//层主头像
				String isManage = "0";//是否管理员
				if(userName.equals(name)){
					isManage = "1";
				}
				Element htmlEl = element.select(".d_post_content_main").select(".d_post_content").get(0);
				String content = this.contentFilter(htmlEl, htmlEl.html());//获取楼层内容
				//获取楼层的发言时间
				Elements sjElement = element.select(".d_post_content_main").select(".post-tail-wrap").select(".tail-info");
				String time = "";//时间戳
				//贴吧页面不同,时间的标签也不同,需要判断两种情况
				if(sjElement.size() > 0){
					//第一种写法
					time = sjElement.get(sjElement.size()-1).text();
				}else{
					//第二种写法
					String attr = element.select("div").attr("data-field");
					time = JSONObject.parseObject(attr).getJSONObject("content").getString("date");
					System.out.println(time);
				}
				Long formatTime = this.formatTime(time);//获取发言时间
				//上传用户信息
				String userPhoto = this.contentHandler(photo);//上传用户头像
				//注册用户获取主键
				String userId = HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/spider/register", "userName=" + name + "&account=" + name + "&password=" + ConstantUtil.PASSWORD + "&photo=" + userPhoto);
//				插入贴子楼层数据
				HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/spider/addConversationChildChild", "conversationChildId=" + conversationChildId + "&userId=" + userId + "&content=" + content+ "&isManage=" + isManage + "&time=" + formatTime);
//				System.out.println(name+ "---" + photo);
//				System.out.println(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理贴子内容,将所有的图片本地化
	 * @param content
	 * @return
	 */
	private String contentHandler(String url){
		return  HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/file/upLoadRemoteFile", "url=" + url);
	}
	
	/**
	 * 对楼层的内容进行过滤,图片本地化
	 * @param element
	 * @return
	 */
	private String contentFilter(Element element,String content){
		List<String> tempImages = new ArrayList<>();//临时存放已经变更的标签 
		if(element.childNodeSize() > 0){
			for(Node node:element.childNodes()){
				String tempSrc = node.attr("src");
				if(tempSrc != null && !"".equals(tempSrc)){
					//将图片全部本地化
					String fileId = contentHandler(tempSrc);
					tempImages.add("<img src=\""+ConstantUtil.ADDRESS+"/common/image?imgId="+fileId+ "\">");
					content = content.replaceFirst("<img.*?>", "yangchen_spider_token_1995_" + (tempImages.size() -1) );
				}
			}
			//将变更的img替换回content中
			for(int i = 0;i<tempImages.size(); i++){
				content = content.replaceAll("yangchen_spider_token_1995_" + i, tempImages.get(i));
			}
		}
		//获取内容,内容有一些非常奇怪的回复,比如携带框框,什么魔法卡之类的特殊回复,本地没有这些样式,所以只获取页面的文字和img标签即可,其他的内容都舍弃..
		return content.replaceAll("\n", "").replaceAll("<div.*?>|</div.*?>|<p.*?>|</p.*?>|<a.*?>|</a.*?>|<span.*?>|</span.*?>|<embed.*?>|</embed.*?>", "");
	}
	
	/**
	 * 格式化时间,转为时间戳
	 * @param time
	 * @return
	 */
	private long formatTime(String time){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.parse(time).getTime();
		} catch (Exception e) {
			throw new RuntimeException("时间转换异常...");
		}
	}
	
	public static void main(String[] args) {
//		new ConversationChildChildHandler().analysisData("https://tieba.baidu.com/p/6112554821", 119);
		new ConversationChildChildHandler().lcHandler("http://tieba.baidu.com/p/6102922657", "120", "");
//		new ConversationChildChildHandler().analysisLCData("88890350", "https://tieba.baidu.com/p/6112554821", "菲不寻常free");
//		new ConversationChildChildHandler().analysisLCData("88890350", "https://tieba.baidu.com/p/5968701334", "菲不寻常f...");
		
	}
}
