package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.yuanmomo.cdut.course.web.bean.Announcement;
import net.yuanmomo.cdut.course.web.dao.IAnnouncementDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

import org.apache.ibatis.session.RowBounds;

public class IAnnouncementDAOImpl extends BasicSqlSupport implements IAnnouncementDAO {

	public boolean doDelete(int id) {
		// TODO Auto-generated method stub
		boolean flag=false;
		int count=this.session.delete("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.doDelete",id);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public boolean doInsert(Announcement annou) {
		// TODO Auto-generated method stub
		boolean flag=false;
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.doInsert",annou);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<Announcement> doQuery(int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		List<Announcement> res=new ArrayList<Announcement>();
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.doQuery",
					null,new RowBounds((currentPage-1)*pageSize,pageSize));
		return res;
	}

	public boolean doUpdate(Announcement annou) {
		boolean flag=false;
		System.out.println(annou.getTitle());
		System.out.println(annou.getContent());
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.doUpdate",annou);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public Announcement getAnnouncementById(int id) {
		Announcement temp=null;
		temp=(Announcement)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.getAnnouncementById",id);
		return temp;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		int count=0;
		count=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.getCount");
		return count;
	}

	public void initialSystem() {
		// TODO Auto-generated method stub
		this.session.delete("net.yuanmomo.cudt.course.web.dao.mapper.Announcement.initialSystem");
	}
}
