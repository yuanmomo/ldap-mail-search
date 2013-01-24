package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.PositionTable;

public interface IPositionTableDAO {
	//do update
	public boolean doUpdate(PositionTable p);
	//do delete
	public boolean doDelete(int id);
	//do get a role
	public PositionTable getPositionById(int id);
	//do get a role
	public PositionTable getPositionByName(String name);
	//do query all positions
	public List<PositionTable> doQueryAll();
	//do insert a position
	public boolean doInsert(PositionTable position);
	//do get all positions
	public List<PositionTable> getAll(int currentPage,int pageSize);
	//do get count of all positions
	public int getAllCount();
	//is a position Name exist
	public int isPositionNameExit(String name);
}
