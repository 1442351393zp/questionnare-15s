package com.infoac.cas.dto;

/**
* @author: Mr.Xu
* @Date: 2020/1/2 10:49
* @Description 回收数据表格sql返回
*/
public class ReceivedDataInfo {

    //答题记录id
    private String answerRecordId;

    //问卷id
    private String reelId;

    //答题开始时间
    private String startTime;

    //答题结束时间
    private String endTime;

    //答题人id
    private String userId;

    //题目id
    private String subjectId;

    //题目名称
    private String topic;

    //答案记录id
    private String anwserId;

    //答案选项id
    private String optionsId;

    //答案选项内容
    private String options;
    //文本内容
    private String textAnswer;

    //文本内容
    private String subjecttype;
    //选项具体类型
    private String isMultipleChoice;//当前多选是否是其他   是 0 ,否1   2单行文本 3单选

    public ReceivedDataInfo() {
    }

    public ReceivedDataInfo(String answerRecordId, String reelId, String startTime, String endTime, String userId, String subjectId, String topic, String anwserId, String optionsId, String options, String subjecttype) {
        this.answerRecordId = answerRecordId;
        this.reelId = reelId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.subjectId = subjectId;
        this.topic = topic;
        this.anwserId = anwserId;
        this.optionsId = optionsId;
        this.options = options;
        this.subjecttype = subjecttype;
    }

    public String getSubjecttype() {
        return subjecttype;
    }

    public void setSubjecttype(String subjecttype) {
        this.subjecttype = subjecttype;
    }

    public String getAnswerRecordId() {
        return answerRecordId;
    }

    public void setAnswerRecordId(String answerRecordId) {
        this.answerRecordId = answerRecordId;
    }

    public String getReelId() {
        return reelId;
    }

    public void setReelId(String reelId) {
        this.reelId = reelId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAnwserId() {
        return anwserId;
    }

    public void setAnwserId(String anwserId) {
        this.anwserId = anwserId;
    }

    public String getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(String optionsId) {
        this.optionsId = optionsId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public String getIsMultipleChoice() {
        return isMultipleChoice;
    }

    public void setIsMultipleChoice(String isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }
}
