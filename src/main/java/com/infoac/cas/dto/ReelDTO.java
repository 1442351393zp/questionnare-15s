package com.infoac.cas.dto;

@SuppressWarnings("serial")
public class ReelDTO extends ModuleEntity implements java.io.Serializable{
	
	private String rId;
	private String title;
	private String endLanguage;
	private String userId;
	private String createTime;
	private String reelStatus;
	private String delFlag;//废纸楼
	private String delReelFlag;//逻辑删除,还没写
	private String startLanguage;  //
	private String showNo;   //显示问题编号  0是  1否
	private String anonymous;    //是否匿名 0是  1否
	private String onceChance;   //一次机会
	private String initiate;   //发起
	private String retrieve;    //回收即将超时提醒 0是  1否
	private String isLimit;    //限定人员
	private String endTime;    //结束时间
	private String setup;     //设置
	private String folderId;//文件夹id
	private String updateTime;
	private String pageId;//传值单独使用
	private String myyear;//nian
	private String expire;//即将过期消息提醒,0是发送过提醒,1否
    private String canal;   //发布渠道,0是,1否,               falg0
    private String canalOnline;   //发布渠道,在线:  0是,1否     falg1
    private String canalEmail;   //发布渠道,邮件:   0是,1否     falg2
    private String canalMsn;   //发布渠道,即时通讯:  0是,1否     falg3
    private String canalText;   //发布渠道描述
	private String istype;//模板类型标志
	private String typeid;//模板类型id

    private String bksetting;   //皮肤设置

    public String getDelReelFlag() {
        return delReelFlag;
    }

    public void setDelReelFlag(String delReelFlag) {
        this.delReelFlag = delReelFlag;
    }

    public String getBksetting() {
        return bksetting;
    }

    public void setBksetting(String bksetting) {
        this.bksetting = bksetting;
    }


    public String getCanalText() {
        return canalText;
    }

    public void setCanalText(String canalText) {
        this.canalText = canalText;
    }

    public String getCanalOnline() {
        return canalOnline;
    }

    public void setCanalOnline(String canalOnline) {
        this.canalOnline = canalOnline;
    }

    public String getCanalEmail() {
        return canalEmail;
    }

    public void setCanalEmail(String canalEmail) {
        this.canalEmail = canalEmail;
    }

    public String getCanalMsn() {
        return canalMsn;
    }

    public void setCanalMsn(String canalMsn) {
        this.canalMsn = canalMsn;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }


    public String getMyyear() {
        return myyear;
    }

    public void setMyyear(String myyear) {
        this.myyear = myyear;
    }

    public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getShowNo() {
		return showNo;
	}
	public void setShowNo(String showNo) {
		this.showNo = showNo;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public String getOnceChance() {
		return onceChance;
	}
	public void setOnceChance(String onceChance) {
		this.onceChance = onceChance;
	}
	public String getInitiate() {
		return initiate;
	}
	public void setInitiate(String initiate) {
		this.initiate = initiate;
	}
	public String getRetrieve() {
		return retrieve;
	}
	public void setRetrieve(String retrieve) {
		this.retrieve = retrieve;
	}
	public String getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSetup() {
		return setup;
	}
	public void setSetup(String setup) {
		this.setup = setup;
	}
	
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEndLanguage() {
		return endLanguage;
	}
	public void setEndLanguage(String endLanguage) {
		this.endLanguage = endLanguage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReelStatus() {
		return reelStatus;
	}
	public void setReelStatus(String reelStatus) {
		this.reelStatus = reelStatus;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getStartLanguage() {
		return startLanguage;
	}
	public void setStartLanguage(String startLanguage) {
		this.startLanguage = startLanguage;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getIstype() {
		return istype;
	}

	public void setIstype(String istype) {
		this.istype = istype;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
}
