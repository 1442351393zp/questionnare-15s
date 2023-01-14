package com.infoac.cas.dto;

import java.util.List;

public class OrganAnswerVO {
    private  String orgid;
    private  String orgname;
    private  List<QuestionStateDTO> questionStateDTOList;

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public List<QuestionStateDTO> getQuestionStateDTOList() {
        return questionStateDTOList;
    }

    public void setQuestionStateDTOList(List<QuestionStateDTO> questionStateDTOList) {
        this.questionStateDTOList = questionStateDTOList;
    }
}
