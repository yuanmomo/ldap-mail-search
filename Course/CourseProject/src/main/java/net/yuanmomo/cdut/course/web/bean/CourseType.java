package net.yuanmomo.cdut.course.web.bean;

public class CourseType {
	private int id;
	private String typeName;
	private String typeChar;
	private int isDeleted;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeChar() {
		return typeChar;
	}
	public void setTypeChar(String typeChar) {
		this.typeChar = typeChar;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
