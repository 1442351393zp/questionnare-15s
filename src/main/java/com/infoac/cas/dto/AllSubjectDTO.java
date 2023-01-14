package com.infoac.cas.dto;

import java.util.List;

/**所有题的dto
 * @author 
 * @version 1.0
 */
public class AllSubjectDTO  {
	
	private String rId;
	private String title;
	private String endLanguage;
	private String userId;
	private String createTime;
	private String reelStatus;//卷的状态,0开始,1暂停
	private String delFlag;
	private String startLanguage;
    private String updateTime;
    private String folderId;
	
	private String showNo;   //显示问题编号 0时 1否
	private String anonymous;    //是否匿名 0是  1否
	private String onceChance;   //一次机会 0是  1否
	private String initiate;   //发起消息提醒 1否
	private String retrieve;    //问卷回收消息提醒 1否
	private String isLimit;    //限定人员
	private String endTime;    //结束时间
	private String setup;     //设置
	private int pageMaxNo;     //页最大号
	private String receiverId;     //受访者id
	
	private String num;//统计报表单个对options选择的数据统计
	private String percentage;//占比
	
	private String recycleNum;//列表页面回收量
	private String pageNum;//页数数量
	private String subjectNum;//题数量

	private List<PageDTO> pageList;
    private List<SubjectDTO> subjectList;
    private PageDTO pageDTO;
    
    private String startTime;//开始时间过滤查询
    private String endsTime;//结束时间过滤查询
    private String canalText;   //发布渠道描述

    private String bksetting;   //皮肤设置
    private String newRellStatus;   //新可答问卷图标,0是新  1否
    private String createPeo;//发布人
	private List <String>  textanswer;  //文本内容

    public String getCreatePeo() {
        return createPeo;
    }

    public void setCreatePeo(String createPeo) {
        this.createPeo = createPeo;
    }

    public String getNewRellStatus() {
        return newRellStatus;
    }

    public void setNewRellStatus(String newRellStatus) {
        this.newRellStatus = newRellStatus;
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
	public int getPageMaxNo() {
		return pageMaxNo;
	}
	public void setPageMaxNo(int pageMaxNo) {
		this.pageMaxNo = pageMaxNo;
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
	public List<PageDTO> getPageList() {
		return pageList;
	}
	public void setPageList(List<PageDTO> pageList) {
		this.pageList = pageList;
	}
	public List<SubjectDTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<SubjectDTO> subjectList) {
		this.subjectList = subjectList;
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
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public PageDTO getPageDTO() {
		return pageDTO;
	}
	public void setPageDTO(PageDTO pageDTO) {
		this.pageDTO = pageDTO;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getRecycleNum() {
		return recycleNum;
	}
	public void setRecycleNum(String recycleNum) {
		this.recycleNum = recycleNum;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getSubjectNum() {
		return subjectNum;
	}
	public void setSubjectNum(String subjectNum) {
		this.subjectNum = subjectNum;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndsTime() {
		return endsTime;
	}
	public void setEndsTime(String endsTime) {
		this.endsTime = endsTime;
	}

	public List<String> getTextanswer() {
		return textanswer;
	}

	public void setTextanswer(List<String> textanswer) {
		this.textanswer = textanswer;
	}
}
