package com.infoac.cas.dto;

import java.util.List;

/**
 * 统计报表
 * Description:
 * @author lenovo
 * @Date:2020年4月2日下午6:34:59
 */

@SuppressWarnings("serial")
public class CountPicDTO extends ModuleEntity implements java.io.Serializable{
	
	private String rId;
	private String title;
	private String pageId;
	private String topic;
	private String options;
	private String optionsId;
	private String countNum;//总数
	private String num;//个数 
	private String answerRecordId;
	private String percentage;
	private List<String> textAnswer;
	private String isMultipleChoice;//当前多选是否是其他   是  0 ,否 1   2单行文本    3  单选
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getOptionsId() {
		return optionsId;
	}
	public void setOptionsId(String optionsId) {
		this.optionsId = optionsId;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getAnswerRecordId() {
		return answerRecordId;
	}
	public void setAnswerRecordId(String answerRecordId) {
		this.answerRecordId = answerRecordId;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public List<String> getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(List<String> textAnswer) {
		this.textAnswer = textAnswer;
	}

	public String getIsMultipleChoice() {
		return isMultipleChoice;
	}

	public void setIsMultipleChoice(String isMultipleChoice) {
		this.isMultipleChoice = isMultipleChoice;
	}
}
