package com.mcgj.entity;

/**
 * 贴吧对象
 * @author 杨晨
 * @date 219-04-24
 * @address 深圳
 *
 */
public class Conversation extends BaseEntity{
	
	private String conversationName;//贴吧名称
	
	private Integer createUserId = 1;//创建用户id,贴吧默认都是管理员创建
	
	private Integer conversationType;//贴吧类型
	
	private String photo;//贴吧头像
	
	private String background;//贴吧背景图片
	
	private String cardBanner;//贴吧横幅
	
	private String autograph;//贴吧签名
	
	private Integer status = 1;//有效状态

	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getConversationType() {
		return conversationType;
	}

	public void setConversationType(Integer conversationType) {
		this.conversationType = conversationType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getCardBanner() {
		return cardBanner;
	}

	public void setCardBanner(String cardBanner) {
		this.cardBanner = cardBanner;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
