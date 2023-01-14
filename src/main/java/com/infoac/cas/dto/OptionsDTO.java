package com.infoac.cas.dto;

/**选项
 * @author 
 * @version 1.0
 */
public class OptionsDTO  {
    
    private String optionsId;
    private String options;//选项
    private String optionImgUrl;//图片地址
    private String optionVideoUrl;//视频地址
    private String subjectId;//题主键id
	private String isMultipleChoice;//当前多选是否是其他   是 0 ,否1   2单行文本 3单选
    private String orderId;
    private String reelId;  //卷id
    private String countNum;//回收量
    private String percentage;//占比
	private String createTime;
	private Integer optionnum;//选项顺序
    private String content;//答题记录对应GAPFILLING的填空
	private String type;//填空类型,1是题,2是选项


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
	public String getReelId() {
		return reelId;
	}
	public void setReelId(String reelId) {
		this.reelId = reelId;
	}
	public String getOptionsId() {
		return optionsId;
	}
	public void setOptionsId(String optionsId) {
		this.optionsId = optionsId;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOptionImgUrl() {
		return optionImgUrl;
	}
	public void setOptionImgUrl(String optionImgUrl) {
		this.optionImgUrl = optionImgUrl;
	}
	public String getOptionVideoUrl() {
		return optionVideoUrl;
	}
	public void setOptionVideoUrl(String optionVideoUrl) {
		this.optionVideoUrl = optionVideoUrl;
	}
	public String getCountNum() {
		return countNum;
	}
	public void setCountNum(String countNum) {
		this.countNum = countNum;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getOptionnum() {
		return optionnum;
	}

	public void setOptionnum(Integer optionnum) {
		this.optionnum = optionnum;
	}

	public String getIsMultipleChoice() {
		return isMultipleChoice;
	}

	public void setIsMultipleChoice(String isMultipleChoice) {
		this.isMultipleChoice = isMultipleChoice;
	}
}
