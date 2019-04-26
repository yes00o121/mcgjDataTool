package com.mcgj;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 工具启动类
 * @author 杨晨
 * @date 2019-04-24
 * @address 深圳
 */
public class StartMain extends JFrame{
	
	private JPanel panel = new JPanel();
	
	private JLabel lb1 = new JLabel("服务器地址:");
	
	private JComboBox comboBox=new JComboBox();
	
	public StartMain(){
		Map<String,String> boxMap = new HashMap<>();//下拉对象
		boxMap.put("动漫", "1");
		boxMap.put("电影", "2");
		boxMap.put("明星", "3");
		boxMap.put("体育", "4");
		boxMap.put("科技", "5");
		boxMap.put("文化", "6");
		boxMap.put("游戏", "7");
		boxMap.put("综艺", "8");
		boxMap.put("生活", "9");
		boxMap.put("地区", "10");
		boxMap.put("音乐", "11");
		boxMap.put("电视剧", "12");
		boxMap.put("其他", "13");
//		private JPanel panel = new JPanel();
		panel.add(lb1);
		comboBox.addItem("下拉测试");
		comboBox.addItem("第二个");
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("@@@@@@@@@@");
			}
		});
		panel.add(comboBox);
		//面板容器
		Container container = this.getContentPane();
		container.add(panel, BorderLayout.NORTH);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("百度贴吧爬虫工具-yangchen_2019-04-26");
		this.setVisible(true);
	}
	
	public static Integer conversationType;//贴吧类型
	
	public static void main(String[] args) {
		new StartMain();
//		new ConversationHanler().getHtml();
//		StartMain.conversationType = 1;
//		System.out.println("程序启动....");
		//处理贴吧数据
//		new ConversationHanler().analysisData("http://tieba.baidu.com/f?ie=utf-8&kw=杭州&fr=search");
		
	}
}
