package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseSource;
import net.yuanmomo.cdut.course.web.dao.ICourseSourceDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

public class ICourseSourceDAOImpl extends BasicSqlSupport implements ICourseSourceDAO {

	public CourseSource getCourseSourceObjById(int id) {
		// TODO Auto-generated method stub
		CourseSource temp=null;
		temp=(CourseSource)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.CourseSource.getCourseSourceObjById",id);
		return temp;
	}

	public CourseSource getCourseSourceObjByName(String name) {
		// TODO Auto-generated method stub
		CourseSource temp=null;
		temp=(CourseSource)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.CourseSource.getCourseSourceObjByName",name);
		return temp;
	}

	public List<CourseSource> queryAllCourseSourceObj() {
		// TODO Auto-generated method stub
		List<CourseSource> res=new ArrayList<CourseSource>();
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.CourseSource.queryAllCourseSourceObj");
		return res;
	}

}
