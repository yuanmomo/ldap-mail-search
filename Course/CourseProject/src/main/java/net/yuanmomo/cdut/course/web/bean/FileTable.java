package net.yuanmomo.cdut.course.web.bean;

import java.util.Date;

public class FileTable {
	private int fileId;
	private String fileName;
	private Date uploadTime;
	private int downLoadTimes;
	private int userRole;
	private String userRoleName;
	private String filePath;
	private String uploadIp;
	private int isDeleted;
	
	
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public int getDownLoadTimes() {
		return downLoadTimes;
	}
	public void setDownLoadTimes(int downLoadTimes) {
		this.downLoadTimes = downLoadTimes;
	}
	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUploadIp() {
		return uploadIp;
	}
	public void setUploadIp(String uploadIp) {
		this.uploadIp = uploadIp;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
