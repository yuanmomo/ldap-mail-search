package net.yuanmomo.cdut.course.web.bean;

import java.util.Date;

public class UserTable{
//	userid  自增长编号
//	username 用户名(学生姓名，或者教师姓名)
//	password 用户密码
//	phone  电话
//	email  邮箱
//	role  角色
//	regTime  注册时间
//	regIp   注册IP
//	lastLoginTime 上次登录时间
//	lastLoginIp 上次登录IP
//
//	isdeleted   是否删除？
//
//	教师所有：
//	teaposition  教师职位
//	teaDescription  教师描述
//	Max_Stu_Count 最大带生人数
//	Current_Stu_Count  当前带生人数
//
//
//	学生所有字段
//	stunumber 学生学号 教师没有  为null
//	stuclass   学生班级 教师为Null
//	stucourseid   学生所选课题编号
//	stuteacherid   学生所选课题的带队老师编号

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
	private String teaDescription;
	private int teaMaxStu;
	private int teaCurrentStu;
	
	private String stuNumber;
	private int stuClassId;
	private int stuCourseId;
	private int stuTeacherId;
	private int isDeleted;
	
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
}
