package com.infoac.cas.dto;

/**
* @author: Mr.Xu
* @Date: 2020/1/3 16:56
* @Description 题目答案关系。查询结果映射实体类
*/
public class SubjectAnswerDTO {

    //题目id
    private String subjectid;

    //题目内容
    private String topic;

    //选项id
    private String optionsid;

    //选项内容
    private String options;

    //问卷id
    private String reelid;

    //受访者id
    private String receiverid;

    //受访者Name---取answer表（值按是否匿名存储）
    private String receiverName;

    //单选 多选
    private String subjectType;

    //是否必选
    private String mustItem;

    //是否选中
    private String checked;

    //题目备注
    private String remark;

    //答题记录编号
    private String answerRecordId;


    //文本内容
    private String  textAnswer;

    //当前多选是否是其他   是  0 ,否 1   2单行文本    3  单选
    private String  isMultipleChoice;

    public SubjectAnswerDTO() {
    }

    public SubjectAnswerDTO(String subjectid, String topic, String optionsid, String options, String reelid, String receiverid, String receiverName, String subjectType, String mustItem, String checked, String remark, String answerRecordId,String isMultipleChoice) {
        this.subjectid = subjectid;
        this.topic = topic;
        this.optionsid = optionsid;
        this.options = options;
        this.reelid = reelid;
        this.receiverid = receiverid;
        this.receiverName = receiverName;
        this.subjectType = subjectType;
        this.mustItem = mustItem;
        this.checked = checked;
        this.remark = remark;
        this.answerRecordId = answerRecordId;
        this.isMultipleChoice = isMultipleChoice;
    }

    public String getIsMultipleChoice() {
        return isMultipleChoice;
    }

    public void setIsMultipleChoice(String isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOptionsid() {
        return optionsid;
    }

    public void setOptionsid(String optionsid) {
        this.optionsid = optionsid;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getReelid() {
        return reelid;
    }

    public void setReelid(String reelid) {
        this.reelid = reelid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getMustItem() {
        return mustItem;
    }

    public void setMustItem(String mustItem) {
        this.mustItem = mustItem;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAnswerRecordId() {
        return answerRecordId;
    }

    public void setAnswerRecordId(String answerRecordId) {
        this.answerRecordId = answerRecordId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }
}
