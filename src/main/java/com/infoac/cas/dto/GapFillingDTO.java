package com.infoac.cas.dto;

import java.util.List;

/**填空设置表
 * @author 
 */
public class GapFillingDTO  {
    
    private String fillinId;
    private String textType;//暂不用.文本类型0不限，1数字，2日期，3电子邮箱，4中文，5英文，6网址，7身份证号码，8手机号码，9电话号码
    private String atMostChar;//暂不用
    private String content;//填写内容
    private String createTime;//创建时间
    private String subjectId;
    private String optionsId;
    private String recordId;
    private String userId;
    private int site;
    private String rId;
    private String type;//类型,1是题,2是选项
    private List<String> contentList;

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getFillinId() {
		return fillinId;
	}
	public void setFillinId(String fillinId) {
		this.fillinId = fillinId;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public String getAtMostChar() {
		return atMostChar;
	}
	public void setAtMostChar(String atMostChar) {
		this.atMostChar = atMostChar;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getOptionsId() {
		return optionsId;
	}
	public void setOptionsId(String optionsId) {
		this.optionsId = optionsId;
	}

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }
}
