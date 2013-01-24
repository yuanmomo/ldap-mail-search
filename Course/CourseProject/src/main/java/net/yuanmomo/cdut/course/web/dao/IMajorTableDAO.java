package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.MajorTable;

public interface IMajorTableDAO {
	//insert a new major in college
	public boolean doInsert(MajorTable major);
	//update a major info
	public boolean doUpdate(MajorTable major);
	//delete a major
	public boolean doDelete(MajorTable major);
	//get a major by id
	public MajorTable getById(int id);
	//get a major by major name
	public List<MajorTable> getByMajorName(String name);
	//get all majors by a collegeId
	public List<MajorTable> getMajorsByCollegeId(int collegeId);
	//get a list of majors by a group of major idS
	public List<MajorTable> getMajorsByGroupMajorId(List<String> majorsList);
	//get all majors
	public List<MajorTable> getMajors(int currentPage,int pageSize);
	//get count of majors in a college
	public int getCountOfCollege(int colleeId);
	//get count of majors
	public int getCount();
	//is major number exist
	public int isMajorNumberExsit(String majorNumber);
	//is major name exist
	public int isMajorNameExsit(String majorName);
	
}
