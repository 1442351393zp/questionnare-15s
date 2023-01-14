package com.infoac.cas.dto;

import java.util.List;

/**
 * @Author: SenjiePeng
 * @Description:
 * @Date: Created in  2019/7/9 10:57
 */
public class SelectUsersDTO {
    private  String code;
    private String msg;
    private String ztreeuser;
    private List<SelectDTO> userlist;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getZtreeuser() {
        return ztreeuser;
    }

    public void setZtreeuser(String ztreeuser) {
        this.ztreeuser = ztreeuser;
    }

    public List<SelectDTO> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<SelectDTO> userlist) {
        this.userlist = userlist;
    }
}
