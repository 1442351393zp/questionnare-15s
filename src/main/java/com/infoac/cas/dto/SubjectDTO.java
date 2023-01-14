package com.infoac.cas.dto;

import java.util.List;

/**题
 * @author 
 * @version 1.0
 */
public class SubjectDTO  {
    
    private String subjectId;
    private String topic;//标题
    private String topicImgUrl;//标题中的图片地址
    private String topicVideoUrl;//标题中的视频路径
    private String remark;//备注
    private String subjectType;//题类型  1单选,2多选  4单行文本
    private String must;
    private String creatTime;//创建时间
    private String userId;//创建id
    private String pageId;//当前页
    private String userName;//创建人
    private List<OptionsDTO> optionsList;
    private String reelId;  //卷id
	private String subNo;     //题号
	private int countNum;   //统计时用的数量
	private int subjectnum;   //拖拽用的排序
    private String updatetime;//拖拽修改时间
    private String content;//答题记录对应GAPFILLING的填空
	private String type;//填空类型,1是题,2是选项
	
	private List<CountPicDTO> countPicDTO;//统计题的结果
	private List <TextAnswerDTO>  textanswer;  //文本内容
//	private List <String>  textAnswerName;  //文本内容答题人

//    public List<String> getTextAnswerName() {
//        return textAnswerName;
//    }
//
//    public void setTextAnswerName(List<String> textAnswerName) {
//        this.textAnswerName = textAnswerName;
//    }


	public List<TextAnswerDTO> getTextanswer() {
		return textanswer;
	}

	public void setTextanswer(List<TextAnswerDTO> textanswer) {
		this.textanswer = textanswer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getSubjectnum() {
        return subjectnum;
    }

    public void setSubjectnum(int subjectnum) {
        this.subjectnum = subjectnum;
    }

    public String getSubNo() {
		return subNo;
	}
	public void setSubNo(String subNo) {
		this.subNo = subNo;
	}
	public String getReelId() {
		return reelId;
	}
	public void setReelId(String reelId) {
		this.reelId = reelId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTopicImgUrl() {
		return topicImgUrl;
	}
	public void setTopicImgUrl(String topicImgUrl) {
		this.topicImgUrl = topicImgUrl;
	}
	public String getTopicVideoUrl() {
		return topicVideoUrl;
	}
	public void setTopicVideoUrl(String topicVideoUrl) {
		this.topicVideoUrl = topicVideoUrl;
	}
	public List<OptionsDTO> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(List<OptionsDTO> optionsList) {
		this.optionsList = optionsList;
	}
	public int getCountNum() {
		return countNum;
	}
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	public String getMust() {
		return must;
	}
	public void setMust(String must) {
		this.must = must;
	}

	public List<CountPicDTO> getCountPicDTO() {
		return countPicDTO;
	}

	public void setCountPicDTO(List<CountPicDTO> countPicDTO) {
		this.countPicDTO = countPicDTO;
	}
    
}
