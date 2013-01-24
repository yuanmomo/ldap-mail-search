package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseType;
import net.yuanmomo.cdut.course.web.dao.ICourseTypeDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

public class ICourseTypeDAOImpl extends BasicSqlSupport implements ICourseTypeDAO {

	public CourseType getCourseTypeObjById(int id) {
		// TODO Auto-generated method stub
		CourseType temp=null;
		temp=(CourseType)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.CourseType.getCourseTypeObjById",id);
		return temp;
	}

	public CourseType getCourseTypeObjByName(String name) {
		// TODO Auto-generated method stub
		CourseType temp=null;
		temp=(CourseType)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.CourseType.getCourseTypeObjByName",name);
		return temp;
	}

	public List<CourseType> queryAllCourseTypeObj() {
		// TODO Auto-generated method stub
		List<CourseType> res=new ArrayList<CourseType>();
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.CourseType.queryAllCourseTypeObj");
		return res;
	}

}
