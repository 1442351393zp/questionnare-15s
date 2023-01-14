package com.infoac.cas.dto;

import java.util.List;

/**
 * 项目里的填空
 */
public class OobjectDTO {

    private String sid;
    private String oid;
    private List<String> ssite;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List<String> getSsite() {
        return ssite;
    }

    public void setSsite(List<String> ssite) {
        this.ssite = ssite;
    }
}
