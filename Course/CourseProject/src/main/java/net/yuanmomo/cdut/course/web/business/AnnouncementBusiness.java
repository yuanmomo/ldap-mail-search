package net.yuanmomo.cdut.course.web.business;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.Announcement;
import net.yuanmomo.cdut.course.web.dao.IAnnouncementDAO;

public class AnnouncementBusiness extends BasicBusiness {
	
	public List<Announcement> adminGetAnnouList(int currentPage,int pageSize){
		List<Announcement> res=null;
		res=this.announcementDAO.doQuery(currentPage, pageSize);
		return res;
	}
	public boolean doDelete(int id) {
		boolean flag=false;
		flag=this.announcementDAO.doDelete(id);
		return flag;
	}
	public boolean doInsert(Announcement annou) {
		boolean flag=false;
		flag=this.announcementDAO.doInsert(annou);
		return flag;
	}
	public List<Announcement> doQuery(int currentPage, int pageSize) {
		List<Announcement> res=this.announcementDAO.doQuery(currentPage, pageSize);
		return res;
	}
	public boolean doUpdate(Announcement annou) {
		boolean flag=false;
		flag=this.announcementDAO.doUpdate(annou);
		return flag;
	}
	public Announcement getAnnouncementById(int id) {
		Announcement a=null;
		a=this.announcementDAO.getAnnouncementById(id);
		return a;
	}
	public int getCount() {
		return this.announcementDAO.getCount();
	}
	public void initialSystem() {
		this.announcementDAO.initialSystem();
	}
}
