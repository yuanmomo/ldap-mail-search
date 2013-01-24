package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseType;

public interface ICourseTypeDAO {
	public CourseType getCourseTypeObjById(int id);
	public CourseType getCourseTypeObjByName(String name);
	public List<CourseType> queryAllCourseTypeObj();
}
