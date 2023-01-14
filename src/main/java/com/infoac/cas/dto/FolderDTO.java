package com.infoac.cas.dto;
/**
 * 文件夹类
 */
public class FolderDTO {
	
	private String folderId;
	private String createTime;
	private String folderName;
	private String reelId;
	private String delFlag;//0:正常,1废纸篓
	private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getReelId() {
		return reelId;
	}
	public void setReelId(String reelId) {
		this.reelId = reelId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}
