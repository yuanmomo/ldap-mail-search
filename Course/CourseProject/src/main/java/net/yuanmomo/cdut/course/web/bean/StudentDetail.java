package net.yuanmomo.cdut.course.web.bean;

import java.util.Date;

public class StudentDetail {
	private int userId;
	private String userName;
	private String password;
	private String phone;
	private String email;
	private int role;
	private Date regTime;
	private String regIp;
	private Date lastLoginTime;
	private String lastLoginIp;
	
	private int teaPosition;
	private String teaPositionName;
	private String teaDescription;
	private int teaMaxStu;
	private int teaCurrentStu;
	
	private String stuNumber;
	private int stuClassId;
	private String majorName;
	private String classNameNum;
	private String classNameChi;
	private int stuCourseId;
	private String courseName;
	
	private int stuCourseSourceId;
	private String stuCourseSourceName;
	private String stuCourseSourceChar;
	private int stuCourseTypeId;
	private String stuCourseTypeName;
	private String stuCourseTypeChar;
	
	private int stuTeacherId;
	private String teacherName;
	private int isDeleted;
	
	
	public String getTeaPositionName() {
		return teaPositionName;
	}
	public void setTeaPositionName(String teaPositionName) {
		this.teaPositionName = teaPositionName;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getClassNameNum() {
		return classNameNum;
	}
	public void setClassNameNum(String classNameNum) {
		this.classNameNum = classNameNum;
	}
	public String getClassNameChi() {
		return classNameChi;
	}
	public void setClassNameChi(String classNameChi) {
		this.classNameChi = classNameChi;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getRegIp() {
		return regIp;
	}
	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public int getTeaPosition() {
		return teaPosition;
	}
	public void setTeaPosition(int teaPosition) {
		this.teaPosition = teaPosition;
	}
	public String getTeaDescription() {
		return teaDescription;
	}
	public void setTeaDescription(String teaDescription) {
		this.teaDescription = teaDescription;
	}
	public int getTeaMaxStu() {
		return teaMaxStu;
	}
	public void setTeaMaxStu(int teaMaxStu) {
		this.teaMaxStu = teaMaxStu;
	}
	public int getTeaCurrentStu() {
		return teaCurrentStu;
	}
	public void setTeaCurrentStu(int teaCurrentStu) {
		this.teaCurrentStu = teaCurrentStu;
	}
	public String getStuNumber() {
		return stuNumber;
	}
	public void setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
	}
	public int getStuClassId() {
		return stuClassId;
	}
	public void setStuClassId(int stuClassId) {
		this.stuClassId = stuClassId;
	}
	public int getStuCourseId() {
		return stuCourseId;
	}
	public void setStuCourseId(int stuCourseId) {
		this.stuCourseId = stuCourseId;
	}
	public int getStuTeacherId() {
		return stuTeacherId;
	}
	public void setStuTeacherId(int stuTeacherId) {
		this.stuTeacherId = stuTeacherId;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getStuCourseSourceId() {
		return stuCourseSourceId;
	}
	public void setStuCourseSourceId(int stuCourseSourceId) {
		this.stuCourseSourceId = stuCourseSourceId;
	}
	public String getStuCourseSourceName() {
		return stuCourseSourceName;
	}
	public void setStuCourseSourceName(String stuCourseSourceName) {
		this.stuCourseSourceName = stuCourseSourceName;
	}
	public String getStuCourseSourceChar() {
		return stuCourseSourceChar;
	}
	public void setStuCourseSourceChar(String stuCourseSourceChar) {
		this.stuCourseSourceChar = stuCourseSourceChar;
	}
	public int getStuCourseTypeId() {
		return stuCourseTypeId;
	}
	public void setStuCourseTypeId(int stuCourseTypeId) {
		this.stuCourseTypeId = stuCourseTypeId;
	}
	public String getStuCourseTypeName() {
		return stuCourseTypeName;
	}
	public void setStuCourseTypeName(String stuCourseTypeName) {
		this.stuCourseTypeName = stuCourseTypeName;
	}
	public String getStuCourseTypeChar() {
		return stuCourseTypeChar;
	}
	public void setStuCourseTypeChar(String stuCourseTypeChar) {
		this.stuCourseTypeChar = stuCourseTypeChar;
	}
}
