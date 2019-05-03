package com.mcgj;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 工具启动类
 * @author 杨晨
 * @date 2019-04-24
 * @address 深圳
 */
public class StartMain extends JFrame{
	
	private JPanel panel = new JPanel();
	
	private JLabel lb1 = new JLabel("贴吧类型:");
	
	private JComboBox comboBox=new JComboBox();
	
	private JLabel lb2 = new JLabel("服务器地址:");
	
	private JTextField text1 = new JTextField(10);
	
	private JLabel lb3 = new JLabel("端口:");
	
	private JTextField text2 = new JTextField(4);
	
	private JLabel lb4 = new JLabel("贴吧地址:");
	
	private JTextField text3 = new JTextField(10);
	
	private JLabel lb5 = new JLabel("线程处理数(数字):");
	
	private JTextField text4 = new JTextField(4);
	
//	private JLabel lb6 = new JLabel("<html><body style=\"height:200px;\"><p align=\"center\">数据版本<br/>v1.0.0</p></body></html>");
	
	private JLabel lb6 = new JLabel("贴吧页数:....................");
	
	private JTextField text5 = new JTextField();
	
	public StartMain(){
		Map<String,Integer> boxMap = new HashMap<>();//下拉对象
		boxMap.put("动漫", 1);
		boxMap.put("电影", 2);
		boxMap.put("明星", 3);
		boxMap.put("体育", 4);
		boxMap.put("科技", 5);
		boxMap.put("文化", 6);
		boxMap.put("游戏", 7);
		boxMap.put("综艺", 8);
		boxMap.put("生活", 9);
		boxMap.put("地区", 10);
		boxMap.put("音乐", 11);
		boxMap.put("电视剧", 12);
		boxMap.put("其他", 13);
//		private JPanel panel = new JPanel();
		panel.add(lb1);
		comboBox.addItem("动漫");
		comboBox.addItem("电影");
		comboBox.addItem("明星");
		comboBox.addItem("体育");
		comboBox.addItem("科技");
		comboBox.addItem("文化");
		comboBox.addItem("游戏");
		comboBox.addItem("综艺");
		comboBox.addItem("生活");
		comboBox.addItem("地区");
		comboBox.addItem("音乐");
		comboBox.addItem("电视剧");
		comboBox.addItem("其他");
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()  == ItemEvent.SELECTED){
					StartMain.conversationType = boxMap.get(e.getItem().toString());
				}
			}
		});
		lb1.setHorizontalAlignment(SwingConstants.RIGHT);
		lb2.setHorizontalAlignment(SwingConstants.RIGHT);
		lb3.setHorizontalAlignment(SwingConstants.RIGHT);
		lb4.setHorizontalAlignment(SwingConstants.RIGHT);
		lb5.setHorizontalAlignment(SwingConstants.RIGHT);
		lb6.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(comboBox);
		panel.add(lb2);
		panel.add(text1);
		panel.add(lb3);
		panel.add(text2);
		panel.add(lb4);
		panel.add(text3);
		panel.add(lb5);
		panel.add(text4);
		panel.add(lb6);
		panel.add(text5);
		//面板容器
		Container container = this.getContentPane();
		container.add(panel, BorderLayout.NORTH);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setMinimumSize(new Dimension(800,600));
		this.setMaximumSize(new Dimension(800, 600));
		this.setLocationRelativeTo(null);
		this.setTitle("百度贴吧爬虫工具-yangchen_2019-04-26");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static Integer conversationType = 1;//贴吧类型 ,默认动漫
	
	public static void main(String[] args) {
		new StartMain();
//		new ConversationHanler().getHtml();
//		StartMain.conversationType = 1;
//		System.out.println("程序启动....");
		//处理贴吧数据
//		new ConversationHanler().analysisData("http://tieba.baidu.com/f?ie=utf-8&kw=%E7%BE%8E%E9%A3%9F&fr=search");
		
	}
}
