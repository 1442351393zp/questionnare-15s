package com.infoac.cas.dto;

import java.util.Objects;

/**
 * 问卷状态实体
 */
public class QuestionStateDTO {
    private  String  username;  //用户姓名
    private  String  userid;  //用户id
    private  String  orname;  //组织结构名称
    private  String  orgid;   //组织机构id


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrname() {
        return orname;
    }

    public void setOrname(String orname) {
        this.orname = orname;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionStateDTO that = (QuestionStateDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(userid, that.userid) &&
                Objects.equals(orname, that.orname) &&
                Objects.equals(orgid, that.orgid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userid, orname, orgid);
    }
}
