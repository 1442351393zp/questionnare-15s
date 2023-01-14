package com.infoac.cas.dto;

/**
 * @Author: PengSenjie
 * @Description:
 * @Date: Created in  2019/7/10 15:44
 */
public class LoginRequestDTO {
    //用户名
    private String username;
    //密码
    private String password;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
