package net.yuanmomo.cdut.course.web.dao.impl;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.MajorTable;
import net.yuanmomo.cdut.course.web.dao.IMajorTableDAO;
import net.yuanmomo.cdut.course.web.util.BasicSqlSupport;

import org.apache.ibatis.session.RowBounds;

public class IMajorTableDAOImpl extends BasicSqlSupport implements IMajorTableDAO {

	public boolean doDelete(MajorTable major) {
		boolean flag=false;
		int count=this.session.delete("net.yuanmomo.cudt.course.web.dao.mapper.Major.doDelete",major.getMajorId());
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public boolean doInsert(MajorTable major) {
		boolean flag=false;
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.Major.doInsert",major);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public boolean doUpdate(MajorTable major) {
		boolean flag=false;
		int count=this.session.insert("net.yuanmomo.cudt.course.web.dao.mapper.Major.doUpdate",major);
		if(count>0){
			flag=true;
		}
		return flag;
	}

	public MajorTable getById(int id) {
		MajorTable major=null;
		major=(MajorTable)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Major.getById",id);
		return major;
	}

	public int getCount() {
		int count=0;
		count=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Major.getCount");
		return count;
	}

	public int getCountOfCollege(int collegeId) {
		int count=0;
		count=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Major.getCountOfCollege",collegeId);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<MajorTable> getMajors(int currentPage, int pageSize) {
		List<MajorTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Major.getMajors",null,
				new RowBounds((currentPage-1)*pageSize,pageSize));
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<MajorTable> getMajorsByCollegeId(int collegeId) {
		List<MajorTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Major.getMajorsByCollegeId",collegeId);
		return res;
	}
	@SuppressWarnings("unchecked")
	public List<MajorTable> getByMajorName(String name) {
		List<MajorTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Major.getByMajorName",name);
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<MajorTable> getMajorsByGroupMajorId(List<String> majorsList) {
		List<MajorTable> res=null;
		res=this.session.selectList("net.yuanmomo.cudt.course.web.dao.mapper.Major.getMajorsByGroupMajorId",majorsList);
		return res;
	}

	public int isMajorNameExsit(String majorName) {
		Integer temp=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Major.isMajorNameExsit",majorName);
		int res=0;
		if(temp!=null){
			res=temp;
		}
		return res;
	}

	public int isMajorNumberExsit(String majorNumber) {
		// TODO Auto-generated method stub
		Integer temp=(Integer)this.session.selectOne("net.yuanmomo.cudt.course.web.dao.mapper.Major.isMajorNumberExsit",majorNumber);
		int res=0;
		if(temp!=null){
			res=temp;
		}
		return res;
	}
}
