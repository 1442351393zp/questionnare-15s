package com.infoac.cas.dto;


/**
 * 答题人
 * @author 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AnswerUserDTO extends ModuleEntity implements java.io.Serializable {
    
    private String createDate;//创建时间
    private String answerDate;//答题时间
    private String userId;//创建id
    private String reelId;  //卷id
    private String answerUserId;  //受调查者id
	private String title;    //标题
	private String reelStatus;    //状态
    private String endLanguage;//结束语
    private String endTime;//答题结束时间
    private String recordId;//记录表id
    private String createPeo;//发布人

    public String getCreatePeo() {
        return createPeo;
    }

    public void setCreatePeo(String createPeo) {
        this.createPeo = createPeo;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndLanguage() {
        return endLanguage;
    }

    public void setEndLanguage(String endLanguage) {
        this.endLanguage = endLanguage;
    }

    public String getReelStatus() {
        return reelStatus;
    }

    public void setReelStatus(String reelStatus) {
        this.reelStatus = reelStatus;
    }

    public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getReelId() {
		return reelId;
	}
	public void setReelId(String reelId) {
		this.reelId = reelId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswerUserId() {
		return answerUserId;
	}
	public void setAnswerUserId(String answerUserId) {
		this.answerUserId = answerUserId;
	}
}
