package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseSource;

public interface ICourseSourceDAO {
	public CourseSource getCourseSourceObjById(int id);
	public CourseSource getCourseSourceObjByName(String name);
	public List<CourseSource> queryAllCourseSourceObj();
}
