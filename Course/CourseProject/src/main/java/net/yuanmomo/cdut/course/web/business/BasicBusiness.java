package net.yuanmomo.cdut.course.web.business;

import javax.annotation.Resource;

import net.yuanmomo.cdut.course.web.dao.IAnnouncementDAO;

public class BasicBusiness {
	@Resource(name="iAnnouncementDAOImpl")
	protected IAnnouncementDAO announcementDAO=null;

	public void setAnnouncementDAO(IAnnouncementDAO announcementDAO) {
		this.announcementDAO = announcementDAO;
	}
}
