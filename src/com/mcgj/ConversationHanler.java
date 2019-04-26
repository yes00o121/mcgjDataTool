package com.mcgj;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONObject;
import com.mcgj.entity.Conversation;
import com.mcgj.util.ConstantUtil;
import com.mcgj.util.HttpClientUtil;

/**
 * 解析贴吧数据,返回主键
 * @author 杨晨
 *
 */
public class ConversationHanler{
	
	public void analysisData(String url) {
		try {
			System.out.println("开始解析贴吧:" + url);
			Conversation conversation = new Conversation();
			Document document = Jsoup.connect(url).timeout(ConstantUtil.TIME_OUT).get();
			//获取贴吧的头像
			Element photoEl = document.select(".card_head_img").get(0);
			String photoStr = photoEl.attr("src").contains("tb1.bdstatic.com") ? "" : photoEl.attr("src").replaceAll("&", "%26");
			if(photoStr.split("src=").length > 1){
				conversation.setPhoto(photoStr.split("src=")[1]);
			}else{
				conversation.setPhoto(photoStr);
			}
			//获取贴吧横幅
			Element banner = document.select("#forum-card-banner").get(0);
			String bannerStr = banner.attr("src").contains("tb1.bdstatic.com") ? "" : banner.attr("src").replaceAll("&", "%26");
			if(bannerStr.split("src=").length > 1){
				conversation.setCardBanner(bannerStr.split("src=")[1]);
			}else{
				conversation.setCardBanner(bannerStr);
			}
			//获取贴吧签名
			Element autograph = document.select(".card_slogan").get(0);
			conversation.setAutograph(autograph.text());
			//获取贴吧类型
			conversation.setConversationType(StartMain.conversationType);
			//设置贴吧名称
			Element name = document.select(".card_title_fname").get(0);
			String conversationName = name.text().substring(0, name.text().length()-1);
			conversation.setConversationName(conversationName);
			String result = HttpClientUtil.sendPost(ConstantUtil.ADDRESS + "/spider/addConversation", "conversationName="+conversation.getConversationName() + "&conversationType=" + conversation.getConversationType() + "&background=" + conversation.getBackground() + "&cardBanner=" + conversation.getCardBanner() + "&autograph=" + conversation.getAutograph() + "&photo=" +conversation.getPhoto());
			JSONObject json = JSONObject.parseObject(result);
			JSONObject parse = json.getJSONObject("result");
			//处理贴子数据
			new ConversationChildHandler().analysisData(url,parse.getIntValue("id"));
//			return parse.getInteger("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
