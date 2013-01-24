package net.yuanmomo.cdut.course.web.bean;

public class PositionTable{
	private int position_Id;
	private String position_Name;
	private int default_Stu_Count;
	private int isDeleted;
	
	public int getPosition_Id() {
		return position_Id;
	}
	public void setPosition_Id(int positionId) {
		position_Id = positionId;
	}
	public String getPosition_Name() {
		return position_Name;
	}
	public void setPosition_Name(String positionName) {
		position_Name = positionName;
	}
	public int getDefault_Stu_Count() {
		return default_Stu_Count;
	}
	public void setDefault_Stu_Count(int defaultStuCount) {
		default_Stu_Count = defaultStuCount;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
