package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.TimeTable;
import net.yuanmomo.cdut.course.web.dao.ITimeTableDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

public class ITimeTableDAOImpl extends BasicSqlSupport implements ITimeTableDAO {

	@SuppressWarnings("unchecked")
	public List<TimeTable> doGetAllTimes() {
		List<TimeTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Time.doGetAllTimes");
		return res;
	}

	public boolean doUpdate(TimeTable time) {
		int count=this.session.update("net.yuanmomo.cudt.course.web.dao.mapper.Time.doUpdate",time);
		return count>0?true:false;
	}

	public TimeTable getATimeTableById(int id) {
		// TODO Auto-generated method stub
		TimeTable time=null;
		time=(TimeTable)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Time.getATimeTableById",id);
		return time;
	}

	public TimeTable getATimeTableByName(String name) {
		// TODO Auto-generated method stub
		TimeTable time=null;
		time=(TimeTable)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Time.getATimeTableByName",name);
		return time;
	}

}
