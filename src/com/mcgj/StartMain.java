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
		StartMain.conversationType = 1;
		//处理贴吧数据
		new ConversationHanler().analysisData("http://tieba.baidu.com/f?kw=redis");
		
	}
}
