package net.yuanmomo.cdut.course.web.bean;

public class CourseSource {
	private int id;
	private String sourceName;
	private String sourceChar;
	private int isDeleted;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getSourceChar() {
		return sourceChar;
	}
	public void setSourceChar(String sourceChar) {
		this.sourceChar = sourceChar;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
}
