package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseTable;
import net.yuanmomo.cdut.course.web.bean.CourseTea;

public interface ICourseTableDAO {
	//do insert a course topic
	public boolean doInsert(CourseTable course);
	//do insert a course topic with a courseTea
	public boolean doInsert(CourseTea course);
	//do update a course topic
	public boolean doUpdate(CourseTable course);
	//do update a course topic with a courseTea
	public boolean doUpdate(CourseTea course);
	//do delete a course topic
	public boolean doDelete(int courseId);
	//do quuery all courses
	public List<CourseTable> doQuery(int currentPage,int pageSize);
	//do quuery all courses
	public List<CourseTable> doQuery();
	//query a list of courses and teacher name
	public List<CourseTea> doQueryAndTeacher(int currentPage,int pageSize);
	//do get a course by course id
	public CourseTable doGetACourseById(int courseId);
	
	public CourseTable doGetACourseByName(String courseName);
	//do get a course with a teacher name and Id by id 
	public CourseTea doGetACourseWithTeaById(int courseId);
	//get count of courses
	public int getCount();
	//do get a list of courses of one teacher
	public List<CourseTable> doQueryOneTeacher(int currentPage,int pageSize,int teacherId);
	//do get a list of courses of one teacher
	public List<CourseTable> doQueryOneTeacher(int teacherId);
	// get count of courses of one teacher
	public int getCountOneTeacher(int teacherId);
	//get courses of a list of teacherId
	public List<CourseTea> doGetCoursesByTeaIds(int currentPage,int pageSize,List<Integer> ids);
	//get courses of a list of teacherId
	public List<CourseTea> doGetCoursesByTeaIds(List<Integer> ids);
	//get the count of the courses of a list of teacherId
	public int getCountOfTeaIds(List<Integer> ids);
	//is a course name exist
	public int isCourseNameExist(String name);
	//delete datas when initial system 
	public void initialSystem();
	
//	//get course detail by teacher id
//	public List<CourseDetail> getCourseDetailByTeaId(int teacherId);
//	//get all courses detail by admin
//	public List<CourseDetail> getCourseDetail();
}
