package net.yuanmomo.cdut.course.web.bean;

public class CollegeTable{
	private int collegeId;
	private String collegeName;
	private String collegeDescription;
	private int isDeleted;
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getCollegeDescription() {
		return collegeDescription;
	}
	public void setCollegeDescription(String collegeDescription) {
		this.collegeDescription = collegeDescription;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
