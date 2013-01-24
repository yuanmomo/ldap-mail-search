package net.yuanmomo.cdut.course.web.bean;

import java.util.Date;

public class CourseTea{
	private int course_Id;
	private String course_Name;
	private String course_Description;
	private String userName;
	private int userId;
	private int student;
	private int isSelected;
	private Date publishDate;
	private String tag;
	private int isDeleted;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStudent() {
		return student;
	}
	public void setStudent(int student) {
		this.student = student;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getCourse_Id() {
		return course_Id;
	}
	public void setCourse_Id(int courseId) {
		course_Id = courseId;
	}
	public String getCourse_Name() {
		return course_Name;
	}
	public void setCourse_Name(String courseName) {
		course_Name = courseName;
	}
	public String getCourse_Description() {
		return course_Description;
	}
	public void setCourse_Description(String courseDescription) {
		course_Description = courseDescription;
	}
	public int getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(int isSelected) {
		this.isSelected = isSelected;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
