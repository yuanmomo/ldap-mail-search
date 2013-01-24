package net.yuanmomo.cdut.course.web.bean;

public class ClassTable{
	private int classId;
	private String classNameNumber;
	private String classNameChi;
	private int inMajor;
	private int isDeleted;
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassNameNumber() {
		return classNameNumber;
	}
	public void setClassNameNumber(String classNameNumber) {
		this.classNameNumber = classNameNumber;
	}
	public String getClassNameChi() {
		return classNameChi;
	}
	public void setClassNameChi(String classNameChi) {
		this.classNameChi = classNameChi;
	}
	public int getInMajor() {
		return inMajor;
	}
	public void setInMajor(int inMajor) {
		this.inMajor = inMajor;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
