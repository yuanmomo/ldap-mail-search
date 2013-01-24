package net.yuanmomo.cdut.course.web.test;

import java.util.List;

import net.yuanmomo.cdut.course.web.bean.CourseTable;
import net.yuanmomo.cdut.course.web.bean.StudentDetail;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateCourseTableStudent {

	private BeanFactory beanFactory = null;

	@Before
	public void setUp() throws Exception {
		beanFactory = new ClassPathXmlApplicationContext(
				"classpath:org/yhb/config/ApplicationContext.xml");
	}

	// After是指在运行完这个测试方法过后回执行的一个方法。一般都是回收资源的。
	@After
	public void tearDown() throws Exception {
		beanFactory = null;
	}

	@Test
	public void saveUserTest() {
		// 测试插入方法
		ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)beanFactory.getBean("ICourseTableDAOBusiness");
		IUserTableDAOBusiness userDao=(IUserTableDAOBusiness)beanFactory.getBean("IUserTableDAOBusiness");
		List<CourseTable> courseList=courseDao.doQuery();
		List<StudentDetail> userList=userDao.getStudentsWithClass();
		for(CourseTable c:courseList){
			if(c.getIsSelected()==0){
				for(StudentDetail s:userList){
					if(s.getStuCourseId()==c.getCourse_Id()){
						System.out.println("当前学生"+s.getUserName()+" ,指导老师" +s.getStuTeacherId());
						c.setStudent(s.getUserId());
						c.setIsSelected(1);
						courseDao.doUpdate(c);
					}
				}
			}
		}
	}
	@Test
	public void count() {
		// 测试插入方法
		ICourseTableDAOBusiness courseDao=(ICourseTableDAOBusiness)beanFactory.getBean("ICourseTableDAOBusiness");
		List<CourseTable> courseList=courseDao.doQuery();
		int count=0;
		for(CourseTable c:courseList){
			if(c.getIsSelected()!=0){
				count++;
			}
		}
		System.out.println("!!!!!!!!!!!!!!!!!!!!"+count);
	}
	
}
