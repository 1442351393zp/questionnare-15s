package com.infoac.cas.dto;

import java.util.ArrayList;
import java.util.List;

/**
* @author: Mr.Xu
* @Date: 2020/1/6 10:14
* @Description 问题详细信息类，前端展示数据使用
*/
public class QuestionDTO {
    //题目id
    private String subjectid;

    //题目内容
    private String topic;

    //问卷id
    private String reelid;

    //受访者id
    private String receiverid;

    //单选 多选
    private String subjectType;

    //是否必选
    private String mustItem;

    //题目备注
    private String remark;

    //答题编号
    private String answerRecordId;

    //所有选项
    private List<Option> options;

    //选项信息
    public class Option{
        //选项id
        private String optionsid;

        //选项内容
        private String optionsValue;

        //是否选中
        private String checked;

        public Option() {
        }

        public Option(String optionsid, String optionsValue, String checked) {
            this.optionsid = optionsid;
            this.optionsValue = optionsValue;
            this.checked = checked;
        }

        public String getOptionsid() {
            return optionsid;
        }

        public void setOptionsid(String optionsid) {
            this.optionsid = optionsid;
        }

        public String getOptionsValue() {
            return optionsValue;
        }

        public void setOptionsValue(String optionsValue) {
            this.optionsValue = optionsValue;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }
    }

    public QuestionDTO() {
    }

    public QuestionDTO(String subjectid, String topic, String reelid, String receiverid, String subjectType, String mustItem, String remark, String answerRecordId, List<Option> options) {
        this.subjectid = subjectid;
        this.topic = topic;
        this.reelid = reelid;
        this.receiverid = receiverid;
        this.subjectType = subjectType;
        this.mustItem = mustItem;
        this.remark = remark;
        this.answerRecordId = answerRecordId;
        this.options = options;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getAnswerRecordId() {
        return answerRecordId;
    }

    public void setAnswerRecordId(String answerRecordId) {
        this.answerRecordId = answerRecordId;
    }
}
