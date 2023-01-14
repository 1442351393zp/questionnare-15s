package com.infoac.cas.dto;

import java.util.List;

public class OrganVO {
    //机构id
    private String id;
    //父id
    private String pid;
    //机构名称
    private String orname;
    //所在区域
    private String orarea;
    //机构代码
    private String orcode;
    //机构分类
    private String orsort;
    //排序码v
    private String orrank;
    //选种状态
    private String checked;
    private List<OrganVO> children;
    //图标
    private String iconSkin="zi";
    //人
    private String nickname;
    private String organid;
    private String username;
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public List<OrganVO> getChildren() {
        return children;
    }

    public void setChildren(List<OrganVO> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOrname() {
        return orname;
    }

    public void setOrname(String orname) {
        this.orname = orname;
    }

    public String getOrarea() {
        return orarea;
    }

    public void setOrarea(String orarea) {
        this.orarea = orarea;
    }

    public String getOrcode() {
        return orcode;
    }

    public void setOrcode(String orcode) {
        this.orcode = orcode;
    }

    public String getOrsort() {
        return orsort;
    }

    public void setOrsort(String orsort) {
        this.orsort = orsort;
    }

    public String getOrrank() {
        return orrank;
    }

    public void setOrrank(String orrank) {
        this.orrank = orrank;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
