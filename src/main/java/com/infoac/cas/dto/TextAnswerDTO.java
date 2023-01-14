package com.infoac.cas.dto;

public class TextAnswerDTO {
    private String optionsId;  //选项id
    private String textAnswer;  //文本答案
    private String textAnswerName;  //文本答案人

    public String getTextAnswerName() {
        return textAnswerName;
    }

    public void setTextAnswerName(String textAnswerName) {
        this.textAnswerName = textAnswerName;
    }

    public String getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(String optionsId) {
        this.optionsId = optionsId;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }
}
