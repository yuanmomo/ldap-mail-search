package net.yuanmomo.cdut.course.web.business;

import net.yuanmomo.cdut.course.web.bean.CourseTable;
import net.yuanmomo.cdut.course.web.bean.UserTable;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SelectionBusiness {
	@Transactional
	public void doSelect(UserTable user,UserTable teacher,int id,CourseTable course,
			IUserTableDAOBusiness userDao,ICourseTableDAOBusiness courseDao) throws Exception{
			// 教师带生人数没有达到上限，课题没有被选择，学生也没有选到课题
			// 刷新学生信息
			// id 是学生点击的课题的ID
			user.setStuTeacherId(course.getTeacher());
			user.setStuCourseId(id);
			// 刷新课题信息
			course.setIsSelected(1);
			course.setStudent(user.getUserId());
			// 刷新该课题对应的教师当前带生人数
			teacher.setTeaCurrentStu(teacher.getTeaCurrentStu()+1);
			
			//更新数据库
			userDao.doUpdate(user);
			userDao.doUpdate(teacher);
			courseDao.doUpdate(course);
	}
	@Transactional
	public void doDeselect(UserTable user,int id,CourseTable course,
			IUserTableDAOBusiness userDao,ICourseTableDAOBusiness courseDao){
			user.setStuCourseId(0);
			user.setStuTeacherId(0);
			//更新课题信息
			course.setStudent(0);
			course.setIsSelected(0);
			//更新该课题对应教师信息
			UserTable teacher = userDao.getUserById(course.getTeacher());
			if(teacher!=null){
				// 刷新该课题对应的教师当前带生人数
				teacher.setTeaCurrentStu(teacher.getTeaCurrentStu()-1);
				//更新数据库信息
				courseDao.doUpdate(course);
				userDao.doUpdate(teacher);
				userDao.doUpdate(user);
			}
	}
}
