package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.TimeTable;

public interface ITimeTableDAO {
	public List<TimeTable> doGetAllTimes();
	public boolean doUpdate(TimeTable time);
	public TimeTable getATimeTableById(int id);
	public TimeTable getATimeTableByName(String name);
}
