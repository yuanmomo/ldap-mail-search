package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.Announcement;

public interface IAnnouncementDAO {
	//insert
	public boolean doInsert(Announcement annou);
	//delete
	public boolean doDelete(int id);
	//update an Announcement
	public boolean doUpdate(Announcement annou);
	//get an announcement
	public Announcement getAnnouncementById(int id);
	//query a list of announcements
	public List<Announcement> doQuery(int currentPage,int pageSize);
	//get count of the table
	public int getCount();
	//delete datas when initial system 
	public void initialSystem();
}
