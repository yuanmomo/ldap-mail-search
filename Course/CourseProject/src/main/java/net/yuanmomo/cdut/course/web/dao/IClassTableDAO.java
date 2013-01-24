package net.yuanmomo.cdut.course.web.dao;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.ClassDetail;
import net.yuanmomo.cdut.course.web.bean.ClassTable;

public interface IClassTableDAO {
	//insert a class
	public boolean doInsert(ClassTable cla);
	//update a class
	public boolean doUpdate(ClassTable cla);
	//delete a class
	public boolean doDelete(int id);
	//get a class by classNameNumber
	public ClassTable getClassByClassNameNumber(String classNameNumber);
	//get a class by classId
	public ClassDetail getClassByClassId(int classId);
	//get a class by classNameChinese
	public ClassTable getClassByClassNameChi(String chineseName);
	//get classes by majorid
	public List<ClassTable> getClassesByMajorId(int majorId);
	//get all classes
	public  List<ClassDetail> getAllClasses(int currentPage,int pageSize);
	//get count of all classes
	public int getCount();
	//is major number exist
	public int isClassNumberExsit(String classNumber);
	//is major name exist
	public int isClassNameExsit(String className);
}
