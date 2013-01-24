package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.PositionTable;
import net.yuanmomo.cdut.course.web.dao.IPositionTableDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

import org.apache.ibatis.session.RowBounds;

public class IPositionTableDAOImpl extends BasicSqlSupport implements IPositionTableDAO {

	public PositionTable getPositionById(int id) {
		PositionTable position=null;
		position=(PositionTable)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Position.getPositionById",id);
		return position;
	}

	@SuppressWarnings("unchecked")
	public List<PositionTable> doQueryAll() {
		List<PositionTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Position.doQueryAll");
		return res;
	}

	public boolean doInsert(PositionTable position) {
		boolean flag=false;
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.Position.doInsert",position);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public PositionTable getPositionByName(String name) {
		PositionTable position=null;
		position=(PositionTable)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Position.getPositionByName",name);
		return position;
	}

	@SuppressWarnings("unchecked")
	public List<PositionTable> getAll(int currentPage, int pageSize) {
		List<PositionTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Position.getAll",null,
				new RowBounds((currentPage-1)*pageSize,pageSize));
		return res;
	}

	public int getAllCount() {
		// TODO Auto-generated method stub
		return (Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Position.getAllCount");
	}

	public boolean doDelete(int id) {
		// TODO Auto-generated method stub\\
		boolean flag=false;
		int count=this.session.delete("net.yuanmomo.cudt.course.web.dao.mapper.Position.doDelete",id);
		flag=count>0?true:false;
		return flag;
	}

	public boolean doUpdate(PositionTable p) {
		// TODO Auto-generated method stub\\
		boolean flag=false;
		int count=this.session.update("net.yuanmomo.cudt.course.web.dao.mapper.Position.doUpdate",p);
		flag=count>0?true:false;
		return flag;
	}

	public int isPositionNameExit(String name) {
		Integer temp=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Position.isPostionNameExist",name);
		int res=0;
		if(temp!=null){
			res=temp;
		}
		return res;
	}
}
