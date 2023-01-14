package com.infoac.cas.dto;

import java.util.List;

/**
 * 统计答案
 * @author 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AnswerDTO extends ModuleEntity implements java.io.Serializable {
    
	private String id;
    private String[] pageIds;
    private String pageId;
    private String[] subjectIds;
    private String subjectId;
    private String[] optionsIds;
    private String optionsId;
    private String createDate;//创建时间
    private String userId;//创建id
    private String reelId;  //卷id
    private String receiverId;  //受访者id
    private String receiverName;  //受访者name
	//保存答题记录使用
	//答题花费时长---时间戳
	private String timeConsuming;
	//答题开始时间
	private String startTime;
	//答题结束时间
	private String endTime;
    private String textAnswer;
	private String answerRecordId;//答题记录编号，表示同一个人多次答题


     private List<OobjectDTO> oobjects;//选项填空
     private String oobjectstr;//选项填空
	 List<TextAnswerDTO>  AnswerList; //文本选项集合
     private  String   textAnswerList;  //文本和选项id

	public List<OobjectDTO> getOobjects() {
		return oobjects;
	}

	public void setOobjects(List<OobjectDTO> oobjects) {
		this.oobjects = oobjects;
	}

	public String getOobjectstr() {
		return oobjectstr;
	}

	public void setOobjectstr(String oobjectstr) {
		this.oobjectstr = oobjectstr;
	}

	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String[] getPageIds() {
		return pageIds;
	}
	public void setPageIds(String[] pageIds) {
		this.pageIds = pageIds;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String[] getSubjectIds() {
		return subjectIds;
	}
	public void setSubjectIds(String[] subjectIds) {
		this.subjectIds = subjectIds;
	}
	public String[] getOptionsIds() {
		return optionsIds;
	}
	public void setOptionsIds(String[] optionsIds) {
		this.optionsIds = optionsIds;
	}
	public String getOptionsId() {
		return optionsId;
	}
	public void setOptionsId(String optionsId) {
		this.optionsId = optionsId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getReelId() {
		return reelId;
	}
	public void setReelId(String reelId) {
		this.reelId = reelId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimeConsuming() {
		return timeConsuming;
	}

	public void setTimeConsuming(String timeConsuming) {
		this.timeConsuming = timeConsuming;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getAnswerRecordId() {
		return answerRecordId;
	}

	public void setAnswerRecordId(String answerRecordId) {
		this.answerRecordId = answerRecordId;
	}

	public List<TextAnswerDTO> getAnswerList() {
		return AnswerList;
	}

	public void setAnswerList(List<TextAnswerDTO> answerList) {
		AnswerList = answerList;
	}

	public String getTextAnswerList() {
		return textAnswerList;
	}

	public void setTextAnswerList(String textAnswerList) {
		this.textAnswerList = textAnswerList;
	}

	public String getTextAnswer() {
		return textAnswer;
	}

	public void setTextAnswer(String textAnswer) {
		this.textAnswer = textAnswer;
	}
}
