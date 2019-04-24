package com.mcgj;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mcgj.entity.Conversation;
import com.mcgj.util.HttpClientUtil;

/**
 * 解析贴吧数据,返回主键
 * @author 杨晨
 *
 */
public class ConversationHanler implements BaseHandler{

	@Override
	public Object analysisData(String url) {
		try {
			
			Conversation conversation = new Conversation();
			Connection connect = Jsoup.connect(url);
			Document document = connect.get();
			//获取贴吧的头像
			Element photoEl = document.select(".card_head_img").get(0);
			conversation.setPhoto(photoEl.attr("src").contains("tb1.bdstatic.com") ? "" : photoEl.attr("src").replaceAll("&", "%26"));
			//获取贴吧横幅
			Element banner = document.select("#forum-card-banner").get(0);
			conversation.setCardBanner(banner.attr("src").contains("tb1.bdstatic.com") ? "" : banner.attr("src").replaceAll("&", "%26"));
			//获取贴吧签名
			Element autograph = document.select(".card_slogan").get(0);
			conversation.setAutograph(autograph.text());
			//获取贴吧类型
			conversation.setConversationType(StartMain.conversationType);
			//设置贴吧名称
			Element name = document.select(".card_title_fname").get(0);
			String conversationName = name.text().substring(0, name.text().length()-1);
			conversation.setConversationName(conversationName);
			Integer conversationId = this.getConversationByName(conversationName);
			if(conversationId > 0){
				return conversationId;
			}
			System.out.println(conversation.getCardBanner());
			System.out.println(conversation.getPhoto());
			String result = HttpClientUtil.sendPost(address + "/spider/addConversation", "conversationName="+conversation.getConversationName() + "&conversationType=" + conversation.getConversationType() + "&background=" + conversation.getBackground() + "&cardBanner=" + conversation.getCardBanner() + "&autograph=" + conversation.getAutograph() + "&photo=" +conversation.getPhoto());
			System.out.println(result);
			//插入贴吧数据
			/*
			System.out.println(photoEl.attr("src"));
			System.out.println(banner.attr("src"));
			System.out.println(autograph.text());
			System.out.println(StartMain.conversationType);
			System.out.println(name.text().substring(0, name.text().length()-1));
			*/
			//获取当前页数下的所有贴子地址和标题
			/*
			Elements select = document.select(".j_th_tit  a[href]");
			for(Element link:select){
				System.out.println(link.attr("href"));
				System.out.println(link.text());
			}
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据贴吧名称获取贴吧数据
	 * @return
	 */
	private Integer getConversationByName(String name){
		return 0;
	}
	
	
	@Override
	public Integer addData(Object data) {
		return null;
	}

}
