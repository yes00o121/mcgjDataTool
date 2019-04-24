package com.mcgj;

/**
 * 工具启动类
 * @author 杨晨
 * @date 2019-04-24
 * @address 深圳
 */
public class StartMain {
	
	public static Integer conversationType;//贴吧类型
	
	public static void main(String[] args) {
//		new ConversationHanler().getHtml();
		BaseHandler conversation = new ConversationHanler();
		StartMain.conversationType = 1;
		Object object = conversation.analysisData("http://tieba.baidu.com/f?ie=utf-8&kw=%E6%A1%90%E8%B0%B7%E7%BE%8E%E7%8E%B2&fr=search");
//		Integer conversationId = conversation.addData(object);
		
	}
}
