package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CollegeTable;
import net.yuanmomo.cdut.course.web.dao.ICollegeTableDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

import org.apache.ibatis.session.RowBounds;

public class ICollegeTableDAOImpl extends BasicSqlSupport implements ICollegeTableDAO {

	public boolean doDelete(CollegeTable coll) {
		boolean flag=false;
		int count=this.session.delete("net.yuanmomo.cudt.course.web.dao.mapper.College.doDelete",coll.getCollegeId());
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public boolean doInsert(CollegeTable coll) {
		boolean flag=false;
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.College.doInsert",coll);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<CollegeTable> doQueryAll() {
		// TODO Auto-generated method stub
		List<CollegeTable> res=new ArrayList<CollegeTable>();
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.College.doQueryAll",
					null,new RowBounds(0,30));
		return res;
	}

	public boolean doUpdate(CollegeTable coll) {
		boolean flag=false;
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.College.doUpdate",coll);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public CollegeTable getCollegeById(int id) {
		CollegeTable temp=null;
		temp=(CollegeTable)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.College.getCollegeById",id);
		return temp;
	}

	public int getCount() {
		int count=0;
		count=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.College.getCount");
		return count;
	}

}
