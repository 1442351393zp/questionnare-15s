package com.infoac.cas.dto;

import java.util.stream.Stream;

public class MessageVO {
    private String  rsltcode;
    private String  rsltmsg;
    private String   result;

    public String getRsltcode() {
        return rsltcode;
    }

    public void setRsltcode(String rsltcode) {
        this.rsltcode = rsltcode;
    }

    public String getRsltmsg() {
        return rsltmsg;
    }

    public void setRsltmsg(String rsltmsg) {
        this.rsltmsg = rsltmsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
