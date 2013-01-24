package net.yuanmomo.cdut.course.web.bean;

import java.util.Date;

public class MajorTable{
	private int majorId;
	private String majorName;
	private int inCollege;
	private Date startSelectDate;
	private Date endSelectDate;
	private String majorNumber;
	
	
	public Date getEndSelectDate() {
		return endSelectDate;
	}
	public void setEndSelectDate(Date endSelectDate) {
		this.endSelectDate = endSelectDate;
	}
	public String getMajorNumber() {
		return majorNumber;
	}
	public void setMajorNumber(String majorNumber) {
		this.majorNumber = majorNumber;
	}
	private int isDeleted;
	
	public int getMajorId() {
		return majorId;
	}
	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public int getInCollege() {
		return inCollege;
	}
	public void setInCollege(int inCollege) {
		this.inCollege = inCollege;
	}
	public Date getStartSelectDate() {
		return startSelectDate;
	}
	public void setStartSelectDate(Date startSelectDate) {
		this.startSelectDate = startSelectDate;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
